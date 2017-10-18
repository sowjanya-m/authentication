package com.telstra.webauth.service;

import com.telstra.webauth.model.Password;
import com.telstra.webauth.model.User;
import com.telstra.webauth.repository.PasswordRepository;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordServiceImpl implements PasswordService {
    @Autowired
    private PasswordRepository passwordRepo;
    
    @Override
	public void logPasswordHistory(User user) {
		Password password = new Password();
		password.setUserId(user.getId());
		password.setPassword(user.getPassword());
		password.setPasswordDate(new Date());
		passwordRepo.save(password);
	}
	
	@Override
	public List<Password> retrievePasswordHistory(Long userId){
		return passwordRepo.findTop10ByUserIdOrderByPasswordDateAsc(userId);
	}

	@Override
	public String generateRandomPassword() {
		final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$_=";
		final int PasswordSize = 8;
		return RandomStringUtils.random(PasswordSize, 0, characters.length(), false, false, characters.toCharArray(), new SecureRandom());
	}
}
