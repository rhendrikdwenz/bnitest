package com.bni.test.BNI.Service.Impl;

import com.bni.test.BNI.Constant.ERole;
import com.bni.test.BNI.Entity.Pegawai;
import com.bni.test.BNI.Entity.Role;
import com.bni.test.BNI.Entity.UserCredential;
import com.bni.test.BNI.Model.Request.AuthRequest;
import com.bni.test.BNI.Model.Request.PegawaiRequest;
import com.bni.test.BNI.Model.Response.PegawaiResponse;
import com.bni.test.BNI.Repository.UserCredentialRepository;
import com.bni.test.BNI.Security.JwtUtils;
import com.bni.test.BNI.Service.AuthService;
import com.bni.test.BNI.Service.PegawaiService;
import com.bni.test.BNI.Service.RoleService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final RoleService roleService;
    private final UserCredentialRepository credentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final PegawaiService pegawaiService;

    @Value("${app.bni-test.username-admin}")
    private String usernameAdmin;
    @Value("${app.bni-test.password-admin}")
    private String passwordAdmin;

    @PostConstruct
    public void initSuperAdmin(){
        Optional<UserCredential> optionalUserCred = credentialRepository.findByUsername(usernameAdmin);
        if (optionalUserCred.isPresent()) return;

        Role superAdminRole = roleService.getOrSave(ERole.ROLE_SUPER_ADMIN);
        Role adminRole = roleService.getOrSave(ERole.ROLE_ADMIN);
        Role customerRole = roleService.getOrSave(ERole.ROLE_PEGAWAI);
        String hashPassword = passwordEncoder.encode(passwordAdmin);

        UserCredential userCredential = UserCredential.builder()
                .username(usernameAdmin)
                .password(hashPassword)
                .roles(List.of(superAdminRole,adminRole,customerRole))
                .build();
        credentialRepository.saveAndFlush(userCredential);
    }

    @Override
    public PegawaiResponse daftarPegawai(PegawaiRequest pegawaiRequest) {
        Role rolePegawai = roleService.getOrSave(ERole.ROLE_PEGAWAI);
        String hashPassword = passwordEncoder.encode(pegawaiRequest.getPassword());
        Pegawai pegawai = pegawaiService.tambahPegawai(pegawaiRequest);
        UserCredential userCredential = UserCredential.builder()
                .username(pegawaiRequest.getUsername())
                .password(pegawaiRequest.getPassword())
                .roles(List.of(rolePegawai))
                .build();
        UserCredential savedUserCredential = credentialRepository.saveAndFlush(userCredential);
        List<String> roles = userCredential.getRoles().stream().map(role -> role.getRole().name()).toList();
        return PegawaiResponse.builder()
                .fullName(pegawai.getFullName())
                .gender(pegawai.getGender())
                .email(pegawai.getEmail())
                .phoneNumber(pegawai.getPhoneNumber())
                .address(pegawai.getAddress())
                .username(pegawaiRequest.getUsername())
                .roles(roles)
                .build();
    }
    /*
         @Override
    public NasabahResponse register(NasabahRequest nasabahRequest) {
        //untuk role terlebih dahlu (sebagaiCustomer)
        Role roleCustomer = roleService.getOrSave(ERole.ROLE_COSTUMER);
        //hash Password
        String hashPassword = passwordEncoder.encode(nasabahRequest.getPassword());
        //Nasabah Baru
        Nasabah nasabah = nasabahService.createNasabah(nasabahRequest);
        //User Credential Baru
        UserCredential userCredential = UserCredential.builder()
                .username(nasabahRequest.getUsername())
                .password(hashPassword)
                .roles(List.of(roleCustomer))
                .build();
        UserCredential savedUserCredential = credentialRepository.saveAndFlush(userCredential);
        //List Role
        List<String> roles = userCredential.getRoles().stream().map(role -> role.getRole().name()).toList();
        return NasabahResponse.builder()
                .fullName(nasabah.getFullName())
                .email(nasabah.getEmail())
                .phoneNumber(nasabah.getPhoneNumber())
                .address(nasabah.getAddress())
                .username(savedUserCredential.getUsername())
                .roles(roles)
                .build();
    }
         */



    @Override
    public String login(AuthRequest authRequest) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(),
                authRequest.getPassword()
        );
        //Call method untuk kebutuhan validasi credential
        Authentication authenticated = authenticationManager.authenticate(authentication);
        //jika valid username dan password, maka selanjutnya simpan sesi untuk akses resource tertentu
        SecurityContextHolder.getContext().setAuthentication(authenticated);
        //berikan token
        UserCredential userCredential = (UserCredential) authenticated.getPrincipal();
        return jwtUtils.generateToken(userCredential);
    }
}
