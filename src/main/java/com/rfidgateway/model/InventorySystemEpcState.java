package com.rfidgateway.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "inventory_system_epc_states",
    uniqueConstraints = @UniqueConstraint(columnNames = {"system_id", "epc"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventorySystemEpcState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "system_id", nullable = false, length = 64)
    private String systemId;

    @Column(nullable = false, length = 128)
    private String epc;

    @Column(name = "first_seen_at", nullable = false)
    private LocalDateTime firstSeenAt;

    @Column(name = "last_seen_at", nullable = false)
    private LocalDateTime lastSeenAt;

    @Column(nullable = false)
    private Boolean present = true;

    @Column(name = "last_reader_id", length = 64)
    private String lastReaderId;

    @Column(name = "last_antenna_port")
    private Short lastAntennaPort;
}
