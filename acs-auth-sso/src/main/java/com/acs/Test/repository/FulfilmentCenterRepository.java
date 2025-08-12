package com.acs.Test.repository;

import com.acs.Test.pojo.FulfilmentCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FulfilmentCenterRepository extends JpaRepository<FulfilmentCenter, Integer> {

   /* @Query("""
    SELECT fc FROM FulfilmentCenter fc
    WHERE LOWER(fc.fcName) LIKE LOWER(CONCAT('%', :search, '%'))
    """)*/
   @Query("SELECT fc FROM FulfilmentCenter fc " +
           "WHERE LOWER(fc.fcName) LIKE LOWER(CONCAT('%', :search, '%'))")
   List<FulfilmentCenter> searchByFcName(@Param("search") String search);

    /*@Query("""
      SELECT DISTINCT fc
      FROM FulfilmentCenter fc
      LEFT JOIN SupplierFcMappings m ON m.fulfilmentCenter.id = fc.id
      WHERE (:supplierId IS NULL OR m.supplier.id = :supplierId)
        AND (:search = '' OR LOWER(fc.fcName) LIKE LOWER(CONCAT('%', :search, '%')))
    """)*/
    @Query("SELECT DISTINCT fc " +
            "FROM FulfilmentCenter fc " +
            "LEFT JOIN SupplierFcMappings m ON m.fulfilmentCenter.id = fc.id " +
            "WHERE (:supplierId IS NULL OR m.supplier.id = :supplierId) " +
            "AND (:search = '' OR LOWER(fc.fcName) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<FulfilmentCenter> findBySupplierAndFcName(
            @Param("supplierId") Integer supplierId,
            @Param("search") String search
    );

    Optional<FulfilmentCenter> findByFcNameIgnoreCase(String fcName);

}
