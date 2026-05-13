package com.rfidgateway.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "epc_presence_events", indexes = {
    @Index(name = "idx_epc_presence_system_time", columnList = "system_id,occurred_at")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EpcPresenceEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "system_id", nullable = false, length = 64)
    private String systemId;

    @Column(nullable = false, length = 128)
    private String epc;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false, length = 16)
    private EpcPresenceEventType eventType;

    @Column(name = "occurred_at", nullable = false)
    private LocalDateTime occurredAt;

    @Column(name = "reader_id", length = 64)
    private String readerId;

    @Column(name = "antenna_port")
    private Short antennaPort;
}
