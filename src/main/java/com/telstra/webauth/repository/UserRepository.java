package com.telstra.webauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.telstra.webauth.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
