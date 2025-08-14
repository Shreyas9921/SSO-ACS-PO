package com.acs.Test.repository;

import com.acs.Test.pojo.PoType;
import com.acs.Test.pojo.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional/*(transactionManager = "poTransactionManager")*/
public interface SupplierRepository extends JpaRepository<Supplier, Integer>, JpaSpecificationExecutor<Supplier> {

    Optional<Supplier> findBySupplierNameAndClientId(String supplierName, Integer clientId);
    Optional<Supplier> findBySupplierCodeAndClientId(String supplierCode, Integer clientId);

    Optional<Supplier> findBySupplierName(String supplierName);
    Optional<Supplier> findBySupplierCode(String supplierCode);

    // Lookup query - list of active suppliers
    // ✅ New AND-based code-name filter for active suppliers (replaces searchByActiveSuppliers)
    @Query("SELECT s FROM Supplier s " +
    "WHERE s.status = 'Active' " +
            "AND (:code = '' OR LOWER(s.supplierCode) LIKE LOWER(CONCAT('%', :code, '%'))) " +
      "AND (:name = '' OR LOWER(s.supplierName) LIKE LOWER(CONCAT('%', :name, '%')))")
    List<Supplier> findBySupplierCodeOrNamePartial(@Param("code") String code, @Param("name") String name);

    @Query("SELECT s FROM Supplier s WHERE s.status = 'Active'")
    List<Supplier> findAllActiveSuppliers();

    @Query("SELECT DISTINCT sa.country FROM SupplierAddress sa WHERE sa.country IS NOT NULL")
    List<String> findDistinctCountries();

    @Query("SELECT DISTINCT sa.state FROM SupplierAddress sa WHERE sa.country IN :countries AND sa.state IS NOT NULL")
    List<String> findDistinctStatesByCountries(@Param("countries") List<String> countries);

}

/*
@Repository
@Transactional(transactionManager = "poTransactionManager") // Use PO transaction manager
public class SupplierRepository {
    @PersistenceContext(unitName = "poEntityManagerFactory")
    private EntityManager poEntityManager;

    public  Optional<Supplier> findById(Integer id) {
        return Optional.ofNullable(poEntityManager.find(Supplier.class, id));
    }

    public List<Supplier> findAll() {
        return poEntityManager.createQuery("SELECT s FROM Supplier s", Supplier.class)
                .getResultList();
    }

    public Supplier save(Supplier supplier) {
        return poEntityManager.merge(supplier); // transaction handled by Spring
    }
}
*/
