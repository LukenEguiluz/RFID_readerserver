package com.rfidgateway.inventory;

import com.rfidgateway.model.EpcPresenceEvent;
import com.rfidgateway.model.EpcPresenceEventType;
import com.rfidgateway.model.InventorySystemEpcState;
import com.rfidgateway.repository.EpcPresenceEventRepository;
import com.rfidgateway.repository.InventorySystemEpcStateRepository;
import com.rfidgateway.websocket.EventWebSocketHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class InventoryEpcService {

    private final ConcurrentHashMap<String, Boolean> cycleActive = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Set<String>> seenThisCycle = new ConcurrentHashMap<>();

    @Autowired
    private InventorySystemEpcStateRepository stateRepository;

    @Autowired
    private EpcPresenceEventRepository eventRepository;

    @Autowired(required = false)
    private EventWebSocketHandler webSocketHandler;

    public void beginCycle(String systemId) {
        seenThisCycle.put(systemId, ConcurrentHashMap.newKeySet());
        cycleActive.put(systemId, true);
        if (webSocketHandler != null) {
            webSocketHandler.sendInventoryCycleStart(systemId);
        }
    }

    @Transactional
    public void finishCycle(String systemId) {
        cycleActive.put(systemId, false);
        Set<String> seen = seenThisCycle.remove(systemId);
        if (seen == null) {
            seen = Set.of();
        }
        LocalDateTime now = LocalDateTime.now();
        List<InventorySystemEpcState> presentRows = stateRepository.findBySystemIdAndPresentTrue(systemId);
        for (InventorySystemEpcState row : presentRows) {
            if (!seen.contains(row.getEpc())) {
                row.setPresent(false);
                stateRepository.save(row);
                EpcPresenceEvent ev = new EpcPresenceEvent();
                ev.setSystemId(systemId);
                ev.setEpc(row.getEpc());
                ev.setEventType(EpcPresenceEventType.REMOVE);
                ev.setOccurredAt(now);
                ev.setReaderId(null);
                ev.setAntennaPort(null);
                eventRepository.save(ev);
                if (webSocketHandler != null) {
                    webSocketHandler.sendInventoryEpcRemove(systemId, row.getEpc());
                }
            }
        }
    }

    @Transactional
    public void recordTag(String systemId, String readerId, String epcRaw, Short antennaPort,
                          Double rssi, Double phase) {
        if (!Boolean.TRUE.equals(cycleActive.get(systemId))) {
            return;
        }
        String epc = normalizeEpc(epcRaw);
        if (epc.isEmpty()) {
            return;
        }
        Set<String> seen = seenThisCycle.get(systemId);
        if (seen == null) {
            return;
        }
        seen.add(epc);
        LocalDateTime now = LocalDateTime.now();
        Optional<InventorySystemEpcState> opt = stateRepository.findBySystemIdAndEpc(systemId, epc);
        if (opt.isEmpty()) {
            InventorySystemEpcState s = new InventorySystemEpcState();
            s.setSystemId(systemId);
            s.setEpc(epc);
            s.setFirstSeenAt(now);
            s.setLastSeenAt(now);
            s.setPresent(true);
            s.setLastReaderId(readerId);
            s.setLastAntennaPort(antennaPort);
            stateRepository.save(s);
            EpcPresenceEvent ev = new EpcPresenceEvent();
            ev.setSystemId(systemId);
            ev.setEpc(epc);
            ev.setEventType(EpcPresenceEventType.ADD);
            ev.setOccurredAt(now);
            ev.setReaderId(readerId);
            ev.setAntennaPort(antennaPort);
            eventRepository.save(ev);
            if (webSocketHandler != null) {
                webSocketHandler.sendInventoryEpcAdd(systemId, epc, readerId, antennaPort, rssi, phase);
            }
        } else {
            InventorySystemEpcState s = opt.get();
            s.setLastSeenAt(now);
            s.setPresent(true);
            s.setLastReaderId(readerId);
            s.setLastAntennaPort(antennaPort);
            stateRepository.save(s);
        }
    }

    private static String normalizeEpc(String epc) {
        return epc == null ? "" : epc.trim().toUpperCase();
    }
}
