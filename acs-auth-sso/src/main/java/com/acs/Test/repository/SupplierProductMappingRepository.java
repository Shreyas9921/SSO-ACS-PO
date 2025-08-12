package com.acs.Test.repository;

import com.acs.Test.pojo.SupplierProductMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierProductMappingRepository extends JpaRepository<SupplierProductMapping, Integer>,
        JpaSpecificationExecutor<SupplierProductMapping> {

//    int countBySupplierId(Long supplierId);
    int countBySupplierId(Integer supplierId);
    @Query("SELECT m.product.sku FROM SupplierProductMapping m WHERE m.supplier.id = :supplierId")
    List<String> findProductsBySupplierId(@Param("supplierId") Integer supplierId);

}
