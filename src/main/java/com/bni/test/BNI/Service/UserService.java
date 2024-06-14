package com.bni.test.BNI.Service;

import com.bni.test.BNI.Entity.UserCredential;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserCredential loadByUserId(String userId);
}
