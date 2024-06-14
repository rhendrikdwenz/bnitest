package com.bni.test.BNI.Controller;

import com.bni.test.BNI.Model.Request.AuthRequest;
import com.bni.test.BNI.Model.Request.PegawaiRequest;
import com.bni.test.BNI.Model.Response.PegawaiResponse;
import com.bni.test.BNI.Model.Response.WebResponse;
import com.bni.test.BNI.Service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest){
        String token = authService.login(authRequest);
        WebResponse<String> response = WebResponse.<String>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Success Login")
                .data(token)
                .build();
        return ResponseEntity.ok(response);
    }
    @PostMapping("/register-pegawai")
    public ResponseEntity<WebResponse<PegawaiResponse>> createPegawai(@Valid @RequestBody PegawaiRequest pegawaiRequest){
        PegawaiResponse pegawaiResponse = authService.daftarPegawai(pegawaiRequest);
        WebResponse<PegawaiResponse> response = WebResponse.<PegawaiResponse>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Sukses register pegawai baru")
                .data(pegawaiResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    /*
     @PostMapping("/register-nasabah")
    public ResponseEntity<WebResponse<NasabahResponse>> createNasabah(@Valid @RequestBody NasabahRequest nasabahRequest){
        NasabahResponse nasabahResponse = authService.register(nasabahRequest);
        WebResponse<NasabahResponse> response = WebResponse.<NasabahResponse>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Success Resgister New Nasabah")
                .data(nasabahResponse)
                .build();
        return ResponseEntity.ok(response);
    }
     */
}