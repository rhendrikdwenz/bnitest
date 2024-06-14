package com.bni.test.BNI.Service;

import com.bni.test.BNI.Model.Request.AuthRequest;
import com.bni.test.BNI.Model.Request.PegawaiRequest;
import com.bni.test.BNI.Model.Response.PegawaiResponse;

public interface AuthService {
    String login(AuthRequest authRequest);

    PegawaiResponse daftarPegawai(PegawaiRequest pegawaiRequest);
}
