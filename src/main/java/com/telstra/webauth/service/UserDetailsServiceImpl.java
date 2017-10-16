package com.telstra.webauth.service;

import com.telstra.webauth.model.Role;
import com.telstra.webauth.model.User;
import com.telstra.webauth.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;
    
    @Value("${invalid.login.attempts.allowed:5}")
    private Integer maxInvalidAttempts;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, CredentialsExpiredException {
    	User user = userRepository.findByUsername(username);
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        if(user != null) {
        	credentialsNonExpired =	new Date().before(user.getPasswordExpiryDate());
        	accountNonLocked = (user.getFailedAttempts() != null && user.getFailedAttempts() >= maxInvalidAttempts)? false: true;
        	for (Role role : user.getRoles()){
                grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
            }
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true, true, credentialsNonExpired,accountNonLocked, grantedAuthorities);
    }
}
