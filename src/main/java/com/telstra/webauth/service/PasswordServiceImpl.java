package com.telstra.webauth.service;

import com.telstra.webauth.model.Password;
import com.telstra.webauth.model.User;
import com.telstra.webauth.repository.PasswordRepository;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordServiceImpl implements PasswordService {
    @Autowired
    private PasswordRepository passwordRepo;
    
	public void logPasswordHistory(User user) {
		Password password = new Password();
		password.setUserId(user.getId());
		password.setPassword(user.getPassword());
		password.setPasswordDate(new Date());
		passwordRepo.save(password);
	}
	
	public List<Password> retrievePasswordHistory(Long userId){
		return passwordRepo.findTop10ByUserIdOrderByPasswordDateAsc(userId);
	}
}
