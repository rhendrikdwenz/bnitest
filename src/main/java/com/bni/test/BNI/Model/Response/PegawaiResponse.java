package com.bni.test.BNI.Model.Response;

import com.bni.test.BNI.Constant.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PegawaiResponse {
    private String id;
    private String fullName;
    private Gender gender;
    private String email;
    private String phoneNumber;
    private String address;
    private String username;
    private List<String> roles;
}