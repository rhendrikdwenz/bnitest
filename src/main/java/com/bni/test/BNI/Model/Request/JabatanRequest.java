package com.bni.test.BNI.Model.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JabatanRequest {
    @NotBlank(message = "Jabatan tidak boleh kosong")
    private String namaJabatan;
    @NotBlank(message = "Gaji tidak boleh kosong")
    private Long gaji;
}
