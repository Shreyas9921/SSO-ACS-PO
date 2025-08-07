/*
 * @author Advatix
 *
 * @since 2019
 *
 * @version 1.0
 */
package com.acs.Test.dao.impl;

import com.acs.Test.dao.IUserDao;
import com.acs.Test.pojo.Users;
import com.acs.Test.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDaoImpl implements IUserDao {

  //  @Autowired
    private final UsersRepository userRepository;

    public UserDaoImpl(UsersRepository  usersRepository) {
        this.userRepository = usersRepository;
    }

    
    @Override
    public Optional<Users> findByUserName(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    public Optional<Users> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Users save(Users user) {
        return userRepository.save(user);
    }

    @Override
    public Boolean existsByUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }

    @Override
    public Users findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    @Override
    public Users findByIdAndKeyCloakUserIdIsNull(Long id) {
        return userRepository.findByIdAndKeyCloakUserIdIsNull(id).orElse(null);
    }

    @Override
    public Page<Users> findAll(Specification<?> queryBuilder, Integer pageNumber, Integer pageSize, Sort sorting) {
        return userRepository.findAll(queryBuilder, PageRequest.of(pageNumber, pageSize, sorting));
    }

    @Override
    public List<Users> findUserByFirstNameOrLastNameLikeAndStatus(String firstName, String lastName, Integer status) {
        return userRepository.findUserByFirstNameOrLastNameLikeAndStatus(firstName, lastName, status);
    }
    

    @Override
    public List<Users> findAllUsersByAvailable(String available) {
        return null;
    }

    @Override
    public List<Users> findAll(Specification<?> queryBuilder) {
        return userRepository.findAll(queryBuilder);
    }

    @Override
    public List<Users> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Boolean existsByUserEmail(String userEmail) {
        return userRepository.existsByEmail(userEmail);
    }

	@Override
	public Optional<Users> findByUserNameIgnoreCase(String username) {
		   return userRepository.findByUserNameIgnoreCase(username);
	}
	
    @Override
    public Boolean existsByUserNameIgnoreCase(String userName) {
        return userRepository.existsByUserNameIgnoreCase(userName);
    }


}
