/*
 * @author Advatix
 *
 * @since 2019
 *
 * @version 1.0
 */
package com.acs.Test.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.acs.Test.pojo.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional(transactionManager = "authTransactionManager")
public interface UsersRepository extends JpaRepository<Users, Long> {

    Page<Users> findAll(Specification<?> build, Pageable pageable);

    Optional<Users> findByUserName(String username);

    Optional<Users> findByEmail(String email);

    Boolean existsByUserName(String username);

    @Query("SELECT user FROM Users user WHERE (user.firstName LIKE %:firstName% or user.lastName LIKE %:lastName%) AND user.status =:status")
    List<Users> findUserByFirstNameOrLastNameLikeAndStatus(
            @Param("firstName") String firstName, @Param("lastName") String lastName, @Param("status") Integer status);

    List<Users> findAll(Specification<?> build);
    

    @Override
    Optional<Users> findById(Long Id);
    Optional<Users> findByIdAndKeyCloakUserIdIsNull(Long Id);

    Boolean existsByEmail(String userEmail);

	Optional<Users> findByUserNameIgnoreCase(String username);

	Boolean existsByUserNameIgnoreCase(String userName);

	List<Users> findAllByIdInAndStatusOrderByUserNameAsc(Collection<Long> id, Integer status);
	
	List<Users> findTop100ByIdInAndStatusOrderByUserNameAsc(Collection<Long> id, Integer status);
	
	List<Users> findTop100ByIdInAndUserNameContainingIgnoreCaseAndStatusOrderByUserNameAsc(Collection<Long> id, String userName, Integer status);
	
	List<Users> findAllByStatusOrderByUserNameAsc(Integer status);
	
	List<Users> findTop100ByStatusOrderByUserNameAsc(Integer status);
	
	List<Users> findTop100ByUserNameContainingIgnoreCaseAndStatusOrderByUserNameAsc(String userName, Integer status);
}
