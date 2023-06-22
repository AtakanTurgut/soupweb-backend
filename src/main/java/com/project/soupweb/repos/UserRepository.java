package com.project.soupweb.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.soupweb.entities.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUserName(String userName);
	List<User> findByUserNameContaining(String userName);
}
