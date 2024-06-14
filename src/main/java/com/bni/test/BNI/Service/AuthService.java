package com.bni.test.BNI.Service;

import com.bni.test.BNI.Model.Request.AuthRequest;

public interface AuthService {
    String login(AuthRequest authRequest);
}
