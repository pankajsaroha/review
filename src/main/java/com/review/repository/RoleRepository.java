package com.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.review.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{

	Role findByRole(String role);
}
