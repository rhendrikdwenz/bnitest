package com.bni.test.BNI.Model.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JabatanResponse {
    private String id;
    private String namaJabatan;
    private Long gaji;
}
