package com.telstra.webauth.service;

import com.telstra.webauth.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);

    void changePassword(User user);
    
    void resetPassword(String username);
    
    void unlock(String username);
    
    void failedLoginAttempt(String username);
    
}
