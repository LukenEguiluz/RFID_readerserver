package com.rfidgateway.repository;

import com.rfidgateway.model.InventorySystemEpcState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventorySystemEpcStateRepository extends JpaRepository<InventorySystemEpcState, Long> {
    List<InventorySystemEpcState> findBySystemIdAndPresentTrueOrderByLastSeenAtDesc(String systemId);

    Page<InventorySystemEpcState> findBySystemIdOrderByLastSeenAtDesc(String systemId, Pageable pageable);

    Optional<InventorySystemEpcState> findBySystemIdAndEpc(String systemId, String epc);

    List<InventorySystemEpcState> findBySystemIdAndPresentTrue(String systemId);

    void deleteBySystemId(String systemId);
}
