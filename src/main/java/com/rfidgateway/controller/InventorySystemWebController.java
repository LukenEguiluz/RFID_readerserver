package com.rfidgateway.controller;

import com.rfidgateway.inventory.InventoryOrchestrationService;
import com.rfidgateway.model.InventorySystem;
import com.rfidgateway.model.InventorySystemReader;
import com.rfidgateway.model.Reader;
import com.rfidgateway.model.ReaderOperationMode;
import com.rfidgateway.repository.EpcPresenceEventRepository;
import com.rfidgateway.repository.InventorySystemEpcStateRepository;
import com.rfidgateway.repository.InventorySystemReaderRepository;
import com.rfidgateway.repository.InventorySystemRepository;
import com.rfidgateway.repository.ReaderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Controller
@RequestMapping("/inventory-systems")
public class InventorySystemWebController {

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

    @Autowired
    private InventoryOrchestrationService inventoryOrchestrationService;

    @GetMapping
    public String list(Model model) {
        try {
            model.addAttribute("systems", inventorySystemRepository.findAll());
        } catch (Exception e) {
            log.warn("Error listando sistemas: {}", e.getMessage());
            model.addAttribute("systems", Collections.emptyList());
        }
        return "inventory-systems";
    }

    @GetMapping("/new")
    public String formNew(Model model) {
        model.addAttribute("system", new InventorySystem());
        model.addAttribute("members", Collections.emptyList());
        model.addAttribute("allReaders", readerRepository.findAll());
        return "inventory-system-form";
    }

    @GetMapping("/{id}/edit")
    public String formEdit(@PathVariable String id, Model model) {
        return inventorySystemRepository.findById(id)
            .map(sys -> {
                model.addAttribute("system", sys);
                model.addAttribute("members", memberRepository.findBySystem_IdOrderByOrderIndexAsc(id));
                model.addAttribute("allReaders", readerRepository.findAll());
                return "inventory-system-form";
            })
            .orElse("redirect:/inventory-systems");
    }

    @PostMapping
    @Transactional
    public String create(
        @RequestParam String id,
        @RequestParam String name,
        @RequestParam int globalCycleSeconds,
        @RequestParam(defaultValue = "false") boolean enabled,
        @RequestParam(required = false) List<String> memberReaderId,
        @RequestParam(required = false) List<Integer> memberOrder,
        @RequestParam(required = false) List<Integer> memberSlotSeconds,
        RedirectAttributes redirect
    ) {
        String sid = id != null ? id.trim() : "";
        if (sid.isEmpty()) {
            redirect.addFlashAttribute("error", "El ID del sistema es obligatorio.");
            return "redirect:/inventory-systems/new";
        }
        if (inventorySystemRepository.existsById(sid)) {
            redirect.addFlashAttribute("error", "Ya existe un sistema con ese ID.");
            return "redirect:/inventory-systems/new";
        }
        try {
            InventorySystem s = new InventorySystem();
            s.setId(sid);
            s.setName(name != null ? name.trim() : sid);
            s.setGlobalCycleSeconds(Math.max(30, globalCycleSeconds));
            s.setEnabled(enabled);
            inventorySystemRepository.save(s);
            applyMembers(sid, memberReaderId, memberOrder, memberSlotSeconds);
            inventoryOrchestrationService.reload();
            redirect.addFlashAttribute("success", "Sistema creado. Activa el sistema para iniciar ciclos.");
        } catch (Exception e) {
            log.warn("Error creando sistema: {}", e.getMessage());
            redirect.addFlashAttribute("error", "No se pudo crear: " + e.getMessage());
            return "redirect:/inventory-systems/new";
        }
        return "redirect:/inventory-systems";
    }

    @PostMapping("/{id}/edit")
    @Transactional
    public String update(
        @PathVariable String id,
        @RequestParam String name,
        @RequestParam int globalCycleSeconds,
        @RequestParam(defaultValue = "false") boolean enabled,
        @RequestParam(required = false) List<String> memberReaderId,
        @RequestParam(required = false) List<Integer> memberOrder,
        @RequestParam(required = false) List<Integer> memberSlotSeconds,
        RedirectAttributes redirect
    ) {
        try {
            inventorySystemRepository.findById(id).ifPresent(s -> {
                s.setName(name != null ? name.trim() : s.getName());
                s.setGlobalCycleSeconds(Math.max(30, globalCycleSeconds));
                s.setEnabled(enabled);
                inventorySystemRepository.save(s);
            });
            applyMembers(id, memberReaderId, memberOrder, memberSlotSeconds);
            inventoryOrchestrationService.reload();
            redirect.addFlashAttribute("success", "Sistema actualizado.");
        } catch (Exception e) {
            log.warn("Error actualizando sistema {}: {}", id, e.getMessage());
            redirect.addFlashAttribute("error", "No se pudo actualizar: " + e.getMessage());
        }
        return "redirect:/inventory-systems";
    }

    @PostMapping("/{id}/delete")
    @Transactional
    public String delete(@PathVariable String id, RedirectAttributes redirect) {
        try {
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
            inventoryOrchestrationService.reload();
            redirect.addFlashAttribute("success", "Sistema eliminado.");
        } catch (Exception e) {
            log.warn("Error eliminando sistema {}: {}", id, e.getMessage());
            redirect.addFlashAttribute("error", "No se pudo eliminar.");
        }
        return "redirect:/inventory-systems";
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
