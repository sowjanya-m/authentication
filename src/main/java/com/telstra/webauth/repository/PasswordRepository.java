package com.telstra.webauth.repository;

import com.telstra.webauth.model.Password;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRepository extends JpaRepository<Password, Long> {
}
