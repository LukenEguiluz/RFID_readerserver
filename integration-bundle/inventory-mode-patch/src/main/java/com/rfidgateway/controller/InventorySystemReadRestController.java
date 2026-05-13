package com.rfidgateway.controller;

import com.rfidgateway.model.EpcPresenceEvent;
import com.rfidgateway.model.InventorySystemEpcState;
import com.rfidgateway.repository.EpcPresenceEventRepository;
import com.rfidgateway.repository.InventorySystemEpcStateRepository;
import com.rfidgateway.repository.InventorySystemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * API solo lectura para inventario continuo por sistema.
 */
@Slf4j
@RestController
@RequestMapping("/api/inventory-systems")
public class InventorySystemReadRestController {

    @Autowired
    private InventorySystemRepository inventorySystemRepository;

    @Autowired
    private InventorySystemEpcStateRepository epcStateRepository;

    @Autowired
    private EpcPresenceEventRepository presenceEventRepository;

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> listSystems() {
        List<Map<String, Object>> list = inventorySystemRepository.findAll().stream()
            .map(s -> {
                Map<String, Object> m = new HashMap<>();
                m.put("id", s.getId());
                m.put("name", s.getName());
                m.put("globalCycleSeconds", s.getGlobalCycleSeconds());
                m.put("enabled", s.getEnabled());
                return m;
            })
            .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getSystem(@PathVariable String id) {
        return inventorySystemRepository.findById(id)
            .map(s -> {
                Map<String, Object> m = new HashMap<>();
                m.put("id", s.getId());
                m.put("name", s.getName());
                m.put("globalCycleSeconds", s.getGlobalCycleSeconds());
                m.put("enabled", s.getEnabled());
                return ResponseEntity.ok(m);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/epcs/current")
    public ResponseEntity<List<InventorySystemEpcState>> currentEpcs(@PathVariable String id) {
        if (!inventorySystemRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(epcStateRepository.findBySystemIdAndPresentTrueOrderByLastSeenAtDesc(id));
    }

    @GetMapping("/{id}/epcs/all")
    public ResponseEntity<Page<InventorySystemEpcState>> allEpcs(
        @PathVariable String id,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "50") int size
    ) {
        if (!inventorySystemRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Page<InventorySystemEpcState> p = epcStateRepository.findBySystemIdOrderByLastSeenAtDesc(
            id, PageRequest.of(page, Math.min(size, 200), Sort.by(Sort.Direction.DESC, "lastSeenAt")));
        return ResponseEntity.ok(p);
    }

    @GetMapping("/{id}/events")
    public ResponseEntity<Page<EpcPresenceEvent>> events(
        @PathVariable String id,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "100") int size
    ) {
        if (!inventorySystemRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Page<EpcPresenceEvent> p = presenceEventRepository.findBySystemIdOrderByOccurredAtDesc(
            id, PageRequest.of(page, Math.min(size, 500), Sort.by(Sort.Direction.DESC, "occurredAt")));
        return ResponseEntity.ok(p);
    }

    @GetMapping("/{id}/epcs/{epc}/timeline")
    public ResponseEntity<Map<String, Object>> epcTimeline(@PathVariable String id, @PathVariable String epc) {
        if (!inventorySystemRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        String norm = epc == null ? "" : epc.trim().toUpperCase();
        Map<String, Object> out = new HashMap<>();
        out.put("systemId", id);
        out.put("epc", norm);
        epcStateRepository.findBySystemIdAndEpc(id, norm).ifPresentOrElse(
            st -> {
                out.put("state", st);
            },
            () -> out.put("state", null)
        );
        out.put("events", presenceEventRepository.findBySystemIdAndEpcOrderByOccurredAtAsc(id, norm));
        return ResponseEntity.ok(out);
    }
}
