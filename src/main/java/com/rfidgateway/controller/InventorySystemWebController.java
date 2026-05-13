package com.rfidgateway.controller;

import com.rfidgateway.inventory.InventoryOrchestrationService;
import com.rfidgateway.inventory.InventorySystemCommandService;
import com.rfidgateway.model.InventorySystem;
import com.rfidgateway.repository.InventorySystemReaderRepository;
import com.rfidgateway.repository.InventorySystemRepository;
import com.rfidgateway.repository.ReaderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

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
    private InventorySystemCommandService inventorySystemCommandService;

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

    /**
     * Vista en vivo de EPC vistos en inventario continuo (poll a la API de solo lectura).
     */
    @GetMapping("/{id}/epcs")
    public String liveEpcs(@PathVariable String id, Model model) {
        return inventorySystemRepository.findById(id)
            .map(s -> {
                model.addAttribute("systemId", s.getId());
                model.addAttribute("systemName", s.getName());
                return "inventory-system-epcs";
            })
            .orElse("redirect:/inventory-systems");
    }

    @PostMapping
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
            inventorySystemCommandService.createSystem(
                sid,
                name,
                globalCycleSeconds,
                enabled,
                memberReaderId,
                memberOrder,
                memberSlotSeconds
            );
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
            inventorySystemCommandService.updateSystem(
                id,
                name,
                globalCycleSeconds,
                enabled,
                memberReaderId,
                memberOrder,
                memberSlotSeconds
            );
            inventoryOrchestrationService.reload();
            redirect.addFlashAttribute("success", "Sistema actualizado.");
        } catch (Exception e) {
            log.warn("Error actualizando sistema {}: {}", id, e.getMessage());
            redirect.addFlashAttribute("error", "No se pudo actualizar: " + e.getMessage());
        }
        return "redirect:/inventory-systems";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id, RedirectAttributes redirect) {
        try {
            inventorySystemCommandService.deleteSystem(id);
            inventoryOrchestrationService.reload();
            redirect.addFlashAttribute("success", "Sistema eliminado.");
        } catch (Exception e) {
            log.warn("Error eliminando sistema {}: {}", id, e.getMessage());
            redirect.addFlashAttribute("error", "No se pudo eliminar.");
        }
        return "redirect:/inventory-systems";
    }
}
