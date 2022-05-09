package com.shoeshop.application.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.shoeshop.application.entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	List<User> findAll();
	
}
