package com.project.soupweb.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.soupweb.entities.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{

	RefreshToken findByUserId(Long userId);
	
}
