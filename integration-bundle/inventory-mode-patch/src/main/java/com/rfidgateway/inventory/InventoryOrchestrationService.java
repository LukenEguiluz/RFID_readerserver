package com.rfidgateway.inventory;

import com.rfidgateway.model.Antenna;
import com.rfidgateway.model.InventorySystem;
import com.rfidgateway.model.InventorySystemReader;
import com.rfidgateway.model.Reader;
import com.rfidgateway.model.ReaderOperationMode;
import com.rfidgateway.reader.ReaderManager;
import com.rfidgateway.repository.AntennaRepository;
import com.rfidgateway.repository.InventorySystemReaderRepository;
import com.rfidgateway.repository.InventorySystemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class InventoryOrchestrationService {

    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(8);
    private final ConcurrentHashMap<String, ScheduledFuture<?>> futures = new ConcurrentHashMap<>();

    @Autowired
    private InventorySystemRepository systemRepository;

    @Autowired
    private InventorySystemReaderRepository memberRepository;

    @Autowired
    private AntennaRepository antennaRepository;

    @Autowired
    private ReaderManager readerManager;

    @Autowired
    private InventoryEpcService inventoryEpcService;

    @PostConstruct
    public void init() {
        reload();
    }

    public synchronized void reload() {
        for (ScheduledFuture<?> f : futures.values()) {
            f.cancel(false);
        }
        futures.clear();
        for (InventorySystem s : systemRepository.findAll()) {
            if (Boolean.TRUE.equals(s.getEnabled())) {
                startLoop(s.getId());
            }
        }
    }

    private void startLoop(String systemId) {
        CycleRunner runner = new CycleRunner(systemId);
        ScheduledFuture<?> f = executor.schedule(runner, 5, TimeUnit.SECONDS);
        futures.put(systemId, f);
    }

    private final class CycleRunner implements Runnable {
        private final String systemId;

        CycleRunner(String systemId) {
            this.systemId = systemId;
        }

        @Override
        public void run() {
            InventorySystem s = systemRepository.findById(systemId).orElse(null);
            if (s == null || !Boolean.TRUE.equals(s.getEnabled())) {
                futures.remove(systemId);
                return;
            }
            long t0 = System.currentTimeMillis();
            try {
                runOneCycle(s);
            } catch (Exception e) {
                log.error("Ciclo inventario continuo falló sistema {}: {}", systemId, e.getMessage(), e);
            }
            long elapsed = System.currentTimeMillis() - t0;
            long waitMs = Math.max(0L, s.getGlobalCycleSeconds() * 1000L - elapsed);
            ScheduledFuture<?> next = executor.schedule(this, waitMs, TimeUnit.MILLISECONDS);
            futures.put(systemId, next);
        }
    }

    private void runOneCycle(InventorySystem system) throws Exception {
        String systemId = system.getId();
        inventoryEpcService.beginCycle(systemId);
        try {
            List<InventorySystemReader> members = memberRepository.findBySystem_IdOrderByOrderIndexAsc(systemId);
            for (InventorySystemReader member : members) {
                Reader reader = member.getReader();
                if (reader == null) {
                    continue;
                }
                if (reader.getOperationMode() != ReaderOperationMode.CONTINUOUS) {
                    continue;
                }
                if (!systemId.equals(reader.getInventorySystemId())) {
                    continue;
                }
                if (!Boolean.TRUE.equals(reader.getEnabled())) {
                    continue;
                }
                processReaderSlots(reader.getId(), member.getReaderSlotSeconds());
            }
        } finally {
            inventoryEpcService.finishCycle(systemId);
        }
    }

    private void processReaderSlots(String readerId, int readerSlotSeconds) throws Exception {
        long totalMs = readerSlotSeconds * 1000L;
        List<Antenna> ants = antennaRepository.findByReaderIdAndEnabledTrueOrderByPortNumberAsc(readerId);
        List<Short> ports = new ArrayList<>();
        for (Antenna a : ants) {
            ports.add(a.getPortNumber());
        }
        if (ports.isEmpty()) {
            readerManager.runInventoryAntennaSlot(readerId, (short) 1, Math.max(200L, totalMs));
            return;
        }
        int n = ports.size();
        long perAntMs = Math.max(200L, totalMs / n);
        for (Short port : ports) {
            readerManager.runInventoryAntennaSlot(readerId, port, perAntMs);
        }
    }
}
