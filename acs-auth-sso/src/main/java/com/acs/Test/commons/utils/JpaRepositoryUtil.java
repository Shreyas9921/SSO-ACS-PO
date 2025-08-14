package com.acs.Test.commons.utils;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public class JpaRepositoryUtil {

    public static <T> void deleteBatch(JpaRepository<T, ?> repo, List<T> entities) {
        if (entities == null || entities.isEmpty()) {
            return;
        }
        try {
            // Try Spring Boot 3.x method
            repo.getClass()
                    .getMethod("deleteAllInBatch", Iterable.class)
                    .invoke(repo, entities);
        } catch (NoSuchMethodException e) {
            // Fallback to Spring Boot 2.x
            try {
                repo.getClass()
                        .getMethod("deleteInBatch", Iterable.class)
                        .invoke(repo, entities);
            } catch (Exception inner) {
                throw new RuntimeException("Batch delete method not found in repository", inner);
            }
        } catch (Exception e) {
            throw new RuntimeException("Batch delete failed", e);
        }
    }

}
