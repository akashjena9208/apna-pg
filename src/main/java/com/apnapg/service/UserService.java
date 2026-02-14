package com.apnapg.service;

import com.apnapg.entity.User;

public interface UserService {

    User findByEmail(String email);

    User findById(Long id);

    void incrementFailedAttempts(User user);

    void resetFailedAttempts(User user);

    void lockUser(User user);

    void enableUser(String email);

    void disableUser(String email);

    void unlockIfLockExpired(User user);




}
