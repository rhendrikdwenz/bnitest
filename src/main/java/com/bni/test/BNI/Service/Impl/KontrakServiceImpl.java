package com.bni.test.BNI.Service.Impl;

import com.bni.test.BNI.Constant.StatusContract;
import com.bni.test.BNI.Entity.Cabang;
import com.bni.test.BNI.Entity.Jabatan;
import com.bni.test.BNI.Entity.Kontrak;
import com.bni.test.BNI.Entity.Pegawai;
import com.bni.test.BNI.Model.Request.KontrakRequest;
import com.bni.test.BNI.Model.Response.KontrakResponse;
import com.bni.test.BNI.Repository.CabangRepository;
import com.bni.test.BNI.Repository.JabatanRepository;
import com.bni.test.BNI.Repository.KontrakRepository;
import com.bni.test.BNI.Repository.PegawaiRepository;
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

    @Autowired
    private PegawaiRepository pegawaiRepository;

    @Autowired
    private JabatanRepository jabatanRepository;

    @Autowired
    private CabangRepository cabangRepository;

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
    @Transactional
    public List<Kontrak> findKontrakByStatusExpired() {
        return kontrakRepository.findByStatusContract(StatusContract.Expired);
    }

    @Override
    public List<Kontrak> findKontrakByStatusActive() {
        return kontrakRepository.findByStatusContract(StatusContract.Active);
    }

    @Override
    public Kontrak getById(String id) {
        Optional<Kontrak> optionalKontrak = kontrakRepository.findById(id);
        if (optionalKontrak.isPresent()) return optionalKontrak.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kontrak dengan id : " + id + " tidak ditemukan");
    }
    @Override
    @Transactional
    public Kontrak updateKontrakByNamaPegawai(String fullName, KontrakRequest kontrakRequest) {
        Optional<Pegawai> optionalPegawai = pegawaiRepository.findByFullName(fullName);
        if (optionalPegawai.isPresent()) {
            Pegawai pegawai = optionalPegawai.get();
            Optional<Kontrak> optionalKontrak = kontrakRepository.findByPegawai(pegawai);
            if (optionalKontrak.isPresent()) {
                Kontrak kontrak = optionalKontrak.get();

                // Update fields based on KontrakRequest
                kontrak.setTanggalMulai(kontrakRequest.getTanggalMulai());
                kontrak.setTanggalAkhir(kontrakRequest.getTanggalAkhir());

                // Update status if needed
                if (LocalDate.now().isAfter(kontrakRequest.getTanggalAkhir())) {
                    kontrak.setStatusContract(StatusContract.Expired);
                } else {
                    kontrak.setStatusContract(StatusContract.Active);
                }


                if (kontrakRequest.getJabatanId() != null) {
                    Optional<Jabatan> optionalJabatan = jabatanRepository.findById(kontrakRequest.getJabatanId());
                    optionalJabatan.ifPresent(kontrak::setJabatan);
                } else {
                    kontrak.setJabatan(null); // Handle case when jabatanId is null
                }


                if (kontrakRequest.getCabangId() != null) {
                    Optional<Cabang> optionalCabang = cabangRepository.findById(kontrakRequest.getCabangId());
                    optionalCabang.ifPresent(kontrak::setCabang);
                } else {
                    kontrak.setCabang(null); // Handle case when cabangId is null
                }


                return kontrakRepository.save(kontrak);
            } else {
                throw new RuntimeException("Kontrak for Pegawai with fullName " + fullName + " not found");
            }
        } else {
            throw new RuntimeException("Pegawai with fullName " + fullName + " not found");
        }
    }

    @Override
    @Transactional
    public Kontrak updateKontrak(String kontrakId, KontrakRequest kontrakRequest) {
        Optional<Kontrak> optionalKontrak = kontrakRepository.findById(kontrakId);
        if (optionalKontrak.isPresent()) {
            Kontrak kontrak = optionalKontrak.get();

            // Update fields based on KontrakRequest
            kontrak.setTanggalMulai(kontrakRequest.getTanggalMulai());
            kontrak.setTanggalAkhir(kontrakRequest.getTanggalAkhir());
            kontrak.setStatusContract(kontrakRequest.getStatusContract());

            // Set Pegawai
            if (kontrakRequest.getPegawaiId() != null) {
                Optional<Pegawai> optionalPegawai = pegawaiRepository.findById(kontrakRequest.getPegawaiId());
                optionalPegawai.ifPresent(kontrak::setPegawai);
            } else {
                kontrak.setPegawai(null);
            }


            if (kontrakRequest.getJabatanId() != null) {
                Optional<Jabatan> optionalJabatan = jabatanRepository.findById(kontrakRequest.getJabatanId());
                optionalJabatan.ifPresent(kontrak::setJabatan);
            } else {
                kontrak.setJabatan(null);
            }


            if (kontrakRequest.getCabangId() != null) {
                Optional<Cabang> optionalCabang = cabangRepository.findById(kontrakRequest.getCabangId());
                optionalCabang.ifPresent(kontrak::setCabang);
            } else {
                kontrak.setCabang(null);
            }


            if (kontrak.getTanggalAkhir() != null && LocalDate.now().isBefore(kontrak.getTanggalAkhir())) {
                kontrak.setStatusContract(StatusContract.Active);
            } else {
                kontrak.setStatusContract(StatusContract.Expired);
            }


            return kontrakRepository.save(kontrak);
        } else {
            throw new RuntimeException("Kontrak with ID " + kontrakId + " not found");
        }
    }

    /*@Override
    @Transactional
    public List<Kontrak> findKontrakByEmailPegawai(String emailPegawai) {
        return kontrakRepository.findByPegawai_Email(emailPegawai);
    }*/

    @Override
    @Transactional
    public Kontrak updateTanggalKontrak(String kontrakId, LocalDate tanggalAkhir) {
        Optional<Kontrak> optionalKontrak = kontrakRepository.findById(kontrakId);
        if (optionalKontrak.isPresent()) {
            Kontrak kontrak = optionalKontrak.get();

            if (tanggalAkhir != null) {
                kontrak.setTanggalAkhir(tanggalAkhir);

                // Update status contract based on the tanggalAkhir
                if (tanggalAkhir.isBefore(LocalDate.now())) {
                    kontrak.setStatusContract(StatusContract.Expired);
                } else {
                    kontrak.setStatusContract(StatusContract.Active);
                }
            }

            return kontrakRepository.save(kontrak);
        } else {
            throw new RuntimeException("Kontrak with ID " + kontrakId + " not found");
        }
    }




    @Override
    @Transactional
    public List<Kontrak> findKontrakByEmailPegawai(String emailPegawai) {
        List<Kontrak> kontrakList = kontrakRepository.findByPegawai_Email(emailPegawai);
        if (kontrakList == null || kontrakList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Email : " + emailPegawai + " tidak tersedia");
        }
        return kontrakList;
    }

    /*
     @Override
     public Nasabah getById(String id) {
         Optional<Nasabah> optionalNasabah = nasabahRepository.findById(id);
         if (optionalNasabah.isPresent()) return optionalNasabah.get();
         throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Nasabah Not Found");
     }

      */
   /* @Override
    @Transactional
    public List<Kontrak> findKontrakByNamaPegawai(String fullName) {
        return kontrakRepository.findByPegawai_FullName(fullName);
    }*/
    @Override
    @Transactional
    public List<Kontrak> findKontrakByNamaPegawai(String fullName) {
        List<Kontrak> kontrakList = kontrakRepository.findByPegawai_FullName(fullName);
        if (kontrakList == null || kontrakList.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tidak ada data kontrak yang ditemukan untuk nama " + fullName);
        }
        return  kontrakList;
    }






    @Override
    public void deleteById(String id) {
        this.getById(id);
        kontrakRepository.deleteById(id);
    }

    @Override
    @Scheduled(cron = "0 0 0 * * ?")
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