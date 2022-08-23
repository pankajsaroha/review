package com.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.review.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByEmail(String email);
}
