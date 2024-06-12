package com.bni.test.BNI.Model.Response;

import com.bni.test.BNI.Constant.StatusContract;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KontrakResponse {
    private String id;
    private String pegawaiId;
    private String jabatanId;
    private String cabangId;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Jakarta")
    private LocalDate tanggalMulai;

    private LocalDate tanggalAkhir;
    private StatusContract statusContract;
}
