package com.rfidgateway.repository;

import com.rfidgateway.model.InventorySystemReader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventorySystemReaderRepository extends JpaRepository<InventorySystemReader, Long> {
    List<InventorySystemReader> findBySystem_IdOrderByOrderIndexAsc(String systemId);

    void deleteBySystem_Id(String systemId);

    Optional<InventorySystemReader> findByReader_Id(String readerId);
}
