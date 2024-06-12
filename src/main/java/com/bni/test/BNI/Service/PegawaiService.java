package com.bni.test.BNI.Service;

import com.bni.test.BNI.Entity.Pegawai;
import com.bni.test.BNI.Model.Request.PegawaiRequest;
import org.springframework.data.domain.Page;

public interface PegawaiService {
    Pegawai tambahPegawai(PegawaiRequest pegawaiRequest);
    Page<Pegawai> getAllPegawai(Integer page, Integer size);
    Pegawai getById(String id);
    Pegawai update(Pegawai pegawai);
    void deleteById(String id);
}
