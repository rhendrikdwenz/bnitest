package com.bni.test.BNI.Service;

import com.bni.test.BNI.Entity.Kontrak;
import com.bni.test.BNI.Model.Request.KontrakRequest;
import com.bni.test.BNI.Model.Response.KontrakResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface KontrakService {

    KontrakResponse tambahKontrak(KontrakRequest kontrakRequest);

    Page<Kontrak> getAllKontrak(Integer page, Integer size);

    Kontrak getById(String id);

    List<Kontrak> findKontrakByNamaPegawai(String fullName);

    List<Kontrak> findKontrakByEmailPegawai(String emailPegawai);
    Kontrak updateKontrakByNamaPegawai(String fullName, KontrakRequest kontrakRequest);

    Kontrak updateKontrak(String kontrakId, KontrakRequest kontrakRequest);

    void deleteById(String id);

    void checkAndUpdateProductStatus();

    // Pegawai tambahPegawai(PegawaiRequest pegawaiRequest);
    //    Page<Pegawai> getAllPegawai(Integer page, Integer size);
    //    Pegawai getById(String id);
    //    Pegawai update(Pegawai pegawai);
    //    void deleteById(String id);
}


//interface servicena jd kws kieu