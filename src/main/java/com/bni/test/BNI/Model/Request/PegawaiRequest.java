package com.bni.test.BNI.Model.Request;

import com.bni.test.BNI.Constant.Gender;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PegawaiRequest {
    @NotBlank(message = "Nama tidak boleh kosong")
    private String fullName;
    @NotBlank(message = "Gender tidak boleh kosong")
    private Gender gender;
    @NotBlank(message = "Email tidak boleh kosong")
    private String email;
    @NotBlank(message = "Nomber telepon tidak boleh kosong")
    private String phoneNumber;
    @NotBlank(message = "Alamat tidak boleh kosong")
    private String address;
}
