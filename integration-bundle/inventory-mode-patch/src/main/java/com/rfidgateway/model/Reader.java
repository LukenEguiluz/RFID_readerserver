package com.rfidgateway.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "readers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reader {

    @Id
    private String id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String hostname;

    @Column(nullable = false)
    private Boolean enabled = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "operation_mode", nullable = false, length = 32)
    private ReaderOperationMode operationMode = ReaderOperationMode.TUNNEL;

    @Column(name = "inventory_system_id", length = 64)
    private String inventorySystemId;

    @Column(name = "is_connected")
    private Boolean isConnected = false;

    @Column(name = "is_reading")
    private Boolean isReading = false;

    @Column(name = "last_seen")
    private LocalDateTime lastSeen;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PrePersist
    public void prePersistReader() {
        if (operationMode == null) {
            operationMode = ReaderOperationMode.TUNNEL;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
