package com.telstra.webauth.repository;

import com.telstra.webauth.model.Password;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRepository extends JpaRepository<Password, Long> {
	 List<Password> findTop10ByUserIdOrderByPasswordDateAsc(Long userId);
}
