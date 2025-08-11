package com.acs.Test.repository;

import com.acs.Test.pojo.PoType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional/*(transactionManager = "poTransactionManager")*/
public interface PoTypeRepository extends JpaRepository<PoType, Integer> {
}

/*
@Repository
@Transactional(transactionManager = "poTransactionManager") // Use PO transaction manager
public class PoTypeRepository {

    @PersistenceContext(unitName = "poEntityManagerFactory")
    private EntityManager poEntityManager;

    public PoType findById(Integer id) {
        return poEntityManager.find(PoType.class, id);
    }

    public List<PoType> findAll() {
        return poEntityManager.createQuery("SELECT p FROM PoType p", PoType.class)
                .getResultList();
    }

    public PoType save(PoType poType) {
        return poEntityManager.merge(poType); // transaction handled by Spring
    }
}
*/

/*
public class PoTypeRepository {

    private final EntityManager poEntityManager;

    public PoTypeRepository(@Qualifier("poEntityManagerFactory") EntityManagerFactory emf) {
        this.poEntityManager = emf.createEntityManager();
    }

    public PoType findById(Integer id) {
        return poEntityManager.find(PoType.class, id);
    }

    public List<PoType> findAll() {
        return poEntityManager.createQuery("SELECT p FROM PoType p", PoType.class)
                .getResultList();
    }

    public PoType save(PoType poType) {
        EntityTransaction tx = poEntityManager.getTransaction();
        tx.begin();
        PoType merged = poEntityManager.merge(poType);
        tx.commit();
        return merged;
    }
}
*/

