package com.bni.test.BNI.Service.Impl;

import com.bni.test.BNI.Entity.Jabatan;
import com.bni.test.BNI.Entity.Pegawai;
import com.bni.test.BNI.Model.Request.JabatanRequest;
import com.bni.test.BNI.Repository.JabatanRepository;
import com.bni.test.BNI.Service.JabatanService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class JabatanServiceImpl implements JabatanService {

    private final JabatanRepository jabatanRepository;

    @Override
    public Page<Jabatan> getAllJabatan(Integer page, Integer size) {
        if (page <= 0){
            page = 1;
        }
        Pageable pageable = PageRequest.of(page-1, size);
        return jabatanRepository.findAll(pageable);
    }

    @Override
    public Jabatan getById(String id) {
        Optional<Jabatan> optionalJabatan = jabatanRepository.findById(id);
        if (optionalJabatan.isPresent()) return optionalJabatan.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Jabatan tidak ditemukan");
    }

    @Override
    public Jabatan update(Jabatan jabatan) {
        this.getById(jabatan.getId());
        return jabatanRepository.save(jabatan);
    }

    @Override
    public void deleteById(String id) {
        this.getById(id);
        jabatanRepository.deleteById(id);
    }

    @Override
    public Jabatan tambahJabatan(JabatanRequest jabatanRequest) {
        Jabatan addJabatan = Jabatan.builder()
                .namaJabatan(jabatanRequest.getNamaJabatan())
                .gaji(jabatanRequest.getGaji())
                .build();
        return jabatanRepository.save(addJabatan);
    }
}
