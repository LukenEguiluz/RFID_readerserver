package com.rfidgateway.repository;

import com.rfidgateway.model.InventorySystemReader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventorySystemReaderRepository extends JpaRepository<InventorySystemReader, Long> {
    List<InventorySystemReader> findBySystem_IdOrderByOrderIndexAsc(String systemId);

    /**
     * Borra miembros del sistema y fuerza flush para evitar violación UK (system_id, reader_id)
     * al reinsertar en la misma transacción.
     */
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("DELETE FROM InventorySystemReader m WHERE m.system.id = :systemId")
    void deleteBySystem_Id(@Param("systemId") String systemId);

    Optional<InventorySystemReader> findByReader_Id(String readerId);
}
