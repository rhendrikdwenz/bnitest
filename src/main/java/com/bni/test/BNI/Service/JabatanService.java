package com.bni.test.BNI.Service;

import com.bni.test.BNI.Entity.Jabatan;
import com.bni.test.BNI.Model.Request.JabatanRequest;
import org.springframework.data.domain.Page;

public interface JabatanService {

    Jabatan tambahJabatan(JabatanRequest jabatanRequest);

    Page<Jabatan> getAllJabatan(Integer page, Integer size);

    Jabatan getById(String id);
    Jabatan update(Jabatan jabatan);
    void deleteById(String id);

    // Pegawai tambahPegawai(PegawaiRequest pegawaiRequest);
    //    Page<Pegawai> getAllPegawai(Integer page, Integer size);
    //    Pegawai getById(String id);
    //    Pegawai update(Pegawai pegawai);
    //    void deleteById(String id);
}
