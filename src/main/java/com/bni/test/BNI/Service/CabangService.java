package com.bni.test.BNI.Service;

import com.bni.test.BNI.Entity.Cabang;
import com.bni.test.BNI.Model.Request.CabangRequest;
import org.springframework.data.domain.Page;

public interface CabangService {

    Cabang tambahCabang(CabangRequest cabangRequest);

    Page<Cabang> getAllCabang(Integer page, Integer size);

    Cabang getById(String id);
    Cabang update(Cabang cabang);

    void deleteById(String id);
    //void addCabangToExcel(Cabang cabang);

    // Pegawai tambahPegawai(PegawaiRequest pegawaiRequest);
    //    Page<Pegawai> getAllPegawai(Integer page, Integer size);
    //    Pegawai getById(String id);
    //    Pegawai update(Pegawai pegawai);
    //    void deleteById(String id);
}
