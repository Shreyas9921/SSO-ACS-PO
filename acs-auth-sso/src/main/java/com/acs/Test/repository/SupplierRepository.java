package com.acs.Test.repository;

import com.acs.Test.pojo.PoType;
import com.acs.Test.pojo.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional/*(transactionManager = "poTransactionManager")*/
public interface SupplierRepository extends JpaRepository<Supplier, Integer>, JpaSpecificationExecutor<Supplier> {
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
