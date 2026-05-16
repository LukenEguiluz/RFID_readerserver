package com.rfidgateway.repository;

import com.rfidgateway.model.EpcPresenceEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EpcPresenceEventRepository extends JpaRepository<EpcPresenceEvent, UUID> {
    Page<EpcPresenceEvent> findBySystemIdOrderByOccurredAtDesc(String systemId, Pageable pageable);

    List<EpcPresenceEvent> findBySystemIdAndEpcOrderByOccurredAtAsc(String systemId, String epc);

    void deleteBySystemId(String systemId);
}
