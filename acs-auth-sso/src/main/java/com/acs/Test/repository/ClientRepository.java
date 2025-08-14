package com.acs.Test.repository;

import com.acs.Test.pojo.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    boolean existsByUuid(String uuid);
    Optional<Client> findByUuid(String uuid);

    Optional<Client> findByAccountIdAndAcsApiKey(String accountId, String acsApiKey);

    Optional<Client> findByCookie(String cookie);
    Optional<Client> findByAccountId(String accountId);

    // NEW: fetch the first matching client from a list of accountIds
    Optional<Client> findFirstByAccountIdIn(List<String> accountIds);

}

