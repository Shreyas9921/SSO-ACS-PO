package com.acs.Test.repository;

import com.acs.Test.pojo.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

    @Query("SELECT p FROM Product p " +
    "WHERE p.id IN ( " +
    "SELECT spm.product.id FROM SupplierProductMapping spm ) " +
    "AND (:sku = '' OR LOWER(p.sku) LIKE LOWER(CONCAT('%', :sku, '%'))) " +
    "AND (:name = '' OR LOWER(p.productName) LIKE LOWER(CONCAT('%', :name, '%')))")
    List<Product> searchMappedProductsBySkuAndName(
            @Param("sku") String sku,
            @Param("name") String name
    );

    @Query("SELECT p FROM Product p " +
    "WHERE (:sku = '' OR LOWER(p.sku) LIKE LOWER(CONCAT('%', :sku, '%'))) " +
    "AND (:name = '' OR LOWER(p.productName) LIKE LOWER(CONCAT('%', :name, '%')))")
    List<Product> searchAllProducts(
            @Param("sku") String sku,
            @Param("name") String name
    );

}