package com.telstra.webauth.service;

import com.telstra.webauth.model.Password;
import com.telstra.webauth.model.User;
import com.telstra.webauth.repository.PasswordRepository;
import com.telstra.webauth.repository.RoleRepository;
import com.telstra.webauth.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private PasswordRepository passwordRepo;
    @Value("${password.expiry.duration:60}")
    private int passwordExpiry;

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(roleRepository.findAll()));
        //calculate and set expiry date
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, passwordExpiry);
        user.setPasswordExpiryDate(c.getTime());
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void changePassword(User user) {
        Password password = new Password();
        password.setUserId(user.getId());
        password.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        password.setPasswordDate(new Date());
        User newUser = findByUsername(user.getUsername());
        newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        newUser.setPasswordChangeDate(new Date());
        //calculate and set expiry date
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, passwordExpiry);
        user.setPasswordExpiryDate(c.getTime());
        userRepository.save(newUser);
        passwordRepo.save(password);
    }

	@Override
	public void resetPassword(String username) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unlock(String username) {
		User user = findByUsername(username);
		if(user != null) {
			//reset failed attempts to 0 and expire the password
			user.setFailedAttempts(0);
			 //calculate and set expiry date
	        Calendar c = Calendar.getInstance();
	        c.add(Calendar.DATE, -1);
	        user.setPasswordExpiryDate(c.getTime());
	        userRepository.save(user);
		}
	}
}
