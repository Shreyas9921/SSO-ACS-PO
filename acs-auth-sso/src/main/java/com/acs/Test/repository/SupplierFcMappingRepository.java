package com.acs.Test.repository;

import com.acs.Test.pojo.SupplierFcMappings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierFcMappingRepository extends JpaRepository<SupplierFcMappings, Integer> {

    List<SupplierFcMappings> findBySupplierId(Integer supplierId);
}
