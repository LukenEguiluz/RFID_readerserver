package com.rfidgateway.repository;

import com.rfidgateway.model.InventorySystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventorySystemRepository extends JpaRepository<InventorySystem, String> {
    List<InventorySystem> findByEnabledTrue();
}
