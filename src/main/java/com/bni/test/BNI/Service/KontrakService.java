package com.bni.test.BNI.Service;

import com.bni.test.BNI.Entity.Kontrak;
import com.bni.test.BNI.Model.Request.KontrakRequest;
import com.bni.test.BNI.Model.Response.KontrakResponse;
import org.springframework.data.domain.Page;

public interface KontrakService {

    KontrakResponse tambahKontrak(KontrakRequest kontrakRequest);

    Page<Kontrak> getAllKontrak(Integer page, Integer size);

    Kontrak getById(String id);

    Kontrak update(Kontrak kontrak);

    void deleteById(String id);

    void checkAndUpdateProductStatus();

    // Pegawai tambahPegawai(PegawaiRequest pegawaiRequest);
    //    Page<Pegawai> getAllPegawai(Integer page, Integer size);
    //    Pegawai getById(String id);
    //    Pegawai update(Pegawai pegawai);
    //    void deleteById(String id);
}
