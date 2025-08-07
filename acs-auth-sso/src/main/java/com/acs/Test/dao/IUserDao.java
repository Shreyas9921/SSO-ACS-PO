package com.acs.Test.dao;

import com.acs.Test.pojo.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface IUserDao {

    Optional<Users> findByUserName(String username);

    Optional<Users> findByEmail(String email);

    Users save(Users user);

    Boolean existsByUserName(String userName);

    Users findById(Long id);
    Users findByIdAndKeyCloakUserIdIsNull(Long id);

    Page<Users> findAll(Specification<?> queryBuilder, Integer pageNumber, Integer pageSize,
                        Sort sorting);

    List<Users> findAll(Specification<?> queryBuilder);

    List<Users> findAll();

    List<Users> findUserByFirstNameOrLastNameLikeAndStatus(String firstName, String lastName, Integer status);

    List<Users> findAllUsersByAvailable(String available);
    // (String firstName, String lastName, Integer role,Integer status);

    Boolean existsByUserEmail(String userEmail);

	Optional<Users> findByUserNameIgnoreCase(String userName);
	
	Boolean existsByUserNameIgnoreCase(String userName);
}
