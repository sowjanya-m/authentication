package com.telstra.webauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.telstra.webauth.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
}
