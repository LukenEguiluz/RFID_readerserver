package com.rfidgateway.inventory;

import com.rfidgateway.model.InventorySystem;
import com.rfidgateway.model.InventorySystemReader;
import com.rfidgateway.model.Reader;
import com.rfidgateway.model.ReaderOperationMode;
import com.rfidgateway.repository.EpcPresenceEventRepository;
import com.rfidgateway.repository.InventorySystemEpcStateRepository;
import com.rfidgateway.repository.InventorySystemReaderRepository;
import com.rfidgateway.repository.InventorySystemRepository;
import com.rfidgateway.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Persistencia de sistemas de inventario en una transacción clara.
 * No captura excepciones: el controlador puede mostrar mensajes sin provocar
 * commit sobre una transacción ya marcada rollback-only.
 */
@Service
public class InventorySystemCommandService {

    @Autowired
    private InventorySystemRepository inventorySystemRepository;

    @Autowired
    private InventorySystemReaderRepository memberRepository;

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private InventorySystemEpcStateRepository epcStateRepository;

    @Autowired
    private EpcPresenceEventRepository presenceEventRepository;

    @Transactional
    public void createSystem(
        String sid,
        String name,
        int globalCycleSeconds,
        boolean enabled,
        List<String> memberReaderId,
        List<Integer> memberOrder,
        List<Integer> memberSlotSeconds
    ) {
        InventorySystem s = new InventorySystem();
        s.setId(sid);
        s.setName(name != null ? name.trim() : sid);
        s.setGlobalCycleSeconds(Math.max(30, globalCycleSeconds));
        s.setEnabled(enabled);
        inventorySystemRepository.save(s);
        applyMembers(sid, memberReaderId, memberOrder, memberSlotSeconds);
    }

    @Transactional
    public void updateSystem(
        String id,
        String name,
        int globalCycleSeconds,
        boolean enabled,
        List<String> memberReaderId,
        List<Integer> memberOrder,
        List<Integer> memberSlotSeconds
    ) {
        inventorySystemRepository.findById(id).ifPresent(s -> {
            s.setName(name != null ? name.trim() : s.getName());
            s.setGlobalCycleSeconds(Math.max(30, globalCycleSeconds));
            s.setEnabled(enabled);
            inventorySystemRepository.save(s);
        });
        applyMembers(id, memberReaderId, memberOrder, memberSlotSeconds);
    }

    @Transactional
    public void deleteSystem(String id) {
        memberRepository.findBySystem_IdOrderByOrderIndexAsc(id).forEach(m -> {
            Reader r = m.getReader();
            if (r != null) {
                r.setOperationMode(ReaderOperationMode.TUNNEL);
                r.setInventorySystemId(null);
                readerRepository.save(r);
            }
        });
        memberRepository.deleteBySystem_Id(id);
        epcStateRepository.deleteBySystemId(id);
        presenceEventRepository.deleteBySystemId(id);
        inventorySystemRepository.deleteById(id);
    }

    private void applyMembers(
        String systemId,
        List<String> memberReaderId,
        List<Integer> memberOrder,
        List<Integer> memberSlotSeconds
    ) {
        memberRepository.deleteBySystem_Id(systemId);
        InventorySystem system = inventorySystemRepository.findById(systemId).orElseThrow();

        Set<String> assigned = new LinkedHashSet<>();
        if (memberReaderId != null) {
            for (int i = 0; i < memberReaderId.size(); i++) {
                String rid = memberReaderId.get(i);
                if (rid == null || rid.isBlank()) {
                    continue;
                }
                rid = rid.trim();
                if (!assigned.add(rid)) {
                    continue;
                }
                Reader reader = readerRepository.findById(rid).orElse(null);
                if (reader == null) {
                    continue;
                }
                /* Un lector solo puede estar en un sistema: quitar fila en otro sistema antes de insertar. */
                memberRepository.findByReader_Id(rid).ifPresent(memberRepository::delete);

                int ord = memberOrder != null && i < memberOrder.size() && memberOrder.get(i) != null
                    ? memberOrder.get(i) : i;
                int slot = memberSlotSeconds != null && i < memberSlotSeconds.size() && memberSlotSeconds.get(i) != null
                    ? memberSlotSeconds.get(i) : 60;
                slot = Math.max(5, slot);

                reader.setOperationMode(ReaderOperationMode.CONTINUOUS);
                reader.setInventorySystemId(systemId);
                readerRepository.save(reader);

                InventorySystemReader row = new InventorySystemReader();
                row.setSystem(system);
                row.setReader(reader);
                row.setOrderIndex(ord);
                row.setReaderSlotSeconds(slot);
                memberRepository.save(row);
            }
        }

        readerRepository.findAll().stream()
            .filter(r -> systemId.equals(r.getInventorySystemId()) && !assigned.contains(r.getId()))
            .forEach(r -> {
                r.setOperationMode(ReaderOperationMode.TUNNEL);
                r.setInventorySystemId(null);
                readerRepository.save(r);
            });
    }
}
