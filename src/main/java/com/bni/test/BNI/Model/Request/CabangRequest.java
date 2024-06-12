package com.bni.test.BNI.Model.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CabangRequest {
    @NotBlank(message = "Nama Cabang tidak boleh kosong")
    private String namaCabang;
    @NotBlank(message = "Alamat tidak boleh kosong")
    private String alamat;
}
