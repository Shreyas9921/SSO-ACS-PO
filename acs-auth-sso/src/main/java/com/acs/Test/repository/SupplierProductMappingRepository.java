package com.acs.Test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierProductMappingRepository extends JpaRepository<SupplierProductMapping, Integer>,
        JpaSpecificationExecutor<SupplierProductMapping> {
}
