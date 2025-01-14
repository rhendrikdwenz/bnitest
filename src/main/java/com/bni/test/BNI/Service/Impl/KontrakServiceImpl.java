package com.bni.test.BNI.Service.Impl;

import com.bni.test.BNI.Constant.StatusContract;
import com.bni.test.BNI.Entity.Cabang;
import com.bni.test.BNI.Entity.Jabatan;
import com.bni.test.BNI.Entity.Kontrak;
import com.bni.test.BNI.Entity.Pegawai;
import com.bni.test.BNI.Model.Request.KontrakRequest;
import com.bni.test.BNI.Model.Response.KontrakResponse;
import com.bni.test.BNI.Repository.KontrakRepository;
import com.bni.test.BNI.Service.CabangService;
import com.bni.test.BNI.Service.JabatanService;
import com.bni.test.BNI.Service.KontrakService;
import com.bni.test.BNI.Service.PegawaiService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class KontrakServiceImpl implements KontrakService {

    @Autowired
    private final KontrakRepository kontrakRepository;
    @Autowired
    private final CabangService cabangService;
    @Autowired
    private final PegawaiService pegawaiService;
    @Autowired
    private final JabatanService jabatanService;

    @Override
    public KontrakResponse tambahKontrak(KontrakRequest kontrakRequest) {

        Pegawai pegawai = pegawaiService.getById(kontrakRequest.getPegawaiId());
        Jabatan jabatan = jabatanService.getById(kontrakRequest.getJabatanId());
        Cabang cabang = cabangService.getById(kontrakRequest.getCabangId());

        Kontrak newKontrak = Kontrak.builder()
                .pegawai(pegawai)
                .jabatan(jabatan)
                .cabang(cabang)
                .tanggalMulai(kontrakRequest.getTanggalMulai() != null ? kontrakRequest.getTanggalMulai() : LocalDate.now())
                .tanggalAkhir(kontrakRequest.getTanggalAkhir())
                .statusContract(kontrakRequest.getStatusContract() != null ? kontrakRequest.getStatusContract() : StatusContract.Active)
                .build();

        if (newKontrak.getTanggalAkhir().isBefore(LocalDate.now())) {
            newKontrak.setStatusContract(StatusContract.Expired);
        }

        Kontrak savedKontrak = kontrakRepository.save(newKontrak);

        return KontrakResponse.builder()
                .id(savedKontrak.getId())
                .pegawaiId(savedKontrak.getPegawai().getId())
                .jabatanId(savedKontrak.getJabatan().getId())
                .cabangId(savedKontrak.getCabang().getId())
                .tanggalMulai(savedKontrak.getTanggalMulai())
                .tanggalAkhir(savedKontrak.getTanggalAkhir())
                .statusContract(savedKontrak.getStatusContract())
                .build();
    }

    @Override
    public Page<Kontrak> getAllKontrak(Integer page, Integer size) {
        if (page <= 0) {
            page = 1;
        }
        Pageable pageable = PageRequest.of(page - 1, size);
        return kontrakRepository.findAll(pageable);
    }

    @Override
    public Kontrak getById(String id) {
        Optional<Kontrak> optionalKontrak = kontrakRepository.findById(id);
        if (optionalKontrak.isPresent()) return optionalKontrak.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kontrak tidak ditemukan");
    }

    @Override
    public Kontrak update(Kontrak kontrak) {
        this.getById(kontrak.getId());
        return kontrakRepository.save(kontrak);
    }

    @Override
    public void deleteById(String id) {
        this.getById(id);
        kontrakRepository.deleteById(id);
    }

    @Override
    @Scheduled(cron = "0 0 0 * * ?")//Dijadwalkan setiap hari pada tengah malam
    public void checkAndUpdateProductStatus() {
        List<Kontrak> semuaKontrak = kontrakRepository.findAll();
        LocalDate sekarang = LocalDate.now();

        for (Kontrak kontrak : semuaKontrak) {
            if (kontrak.getTanggalAkhir().isBefore(sekarang) && kontrak.getStatusContract() != StatusContract.Expired) {
                kontrak.setStatusContract(StatusContract.Expired);
                kontrakRepository.save(kontrak);
            }
        }

    }
}
