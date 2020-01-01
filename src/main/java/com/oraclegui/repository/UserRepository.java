package com.oraclegui.repository;

import org.springframework.data.repository.CrudRepository;

import com.oraclegui.entity.UserDao;

public interface UserRepository extends CrudRepository<UserDao, Integer> {
	
	UserDao findById(Long id);
	
	UserDao findByUsername(String username);
}
