package com.telstra.webauth.service;

import java.util.List;

import com.telstra.webauth.model.Password;
import com.telstra.webauth.model.User;

public interface PasswordService {
    
	public void logPasswordHistory(User user);
	
	public List<Password> retrievePasswordHistory(Long userId);
}
