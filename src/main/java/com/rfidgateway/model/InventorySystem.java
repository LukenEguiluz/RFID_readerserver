package com.rfidgateway.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "inventory_systems")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventorySystem {

    @Id
    @Column(length = 64)
    private String id;

    @Column(nullable = false, unique = true, length = 200)
    private String name;

    /**
     * Tiempo entre inicios de ciclo completos (todos los lectores del sistema), en segundos.
     */
    @Column(name = "global_cycle_seconds", nullable = false)
    private Integer globalCycleSeconds = 300;

    @Column(nullable = false)
    private Boolean enabled = false;

    @OneToMany(mappedBy = "system", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("orderIndex ASC")
    private List<InventorySystemReader> members = new ArrayList<>();

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
