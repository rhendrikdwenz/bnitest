package com.bni.test.BNI.Service.Impl;

import com.bni.test.BNI.Entity.Cabang;
import com.bni.test.BNI.Entity.Jabatan;
import com.bni.test.BNI.Model.Request.CabangRequest;
import com.bni.test.BNI.Repository.CabangRepository;
import com.bni.test.BNI.Service.CabangService;
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
public class CabangServiceImpl implements CabangService {

    private final CabangRepository cabangRepository;
    @Override
    public Cabang tambahCabang(CabangRequest cabangRequest) {
        Cabang newCabang = Cabang.builder()
                .namaCabang(cabangRequest.getNamaCabang())
                .alamat(cabangRequest.getAlamat())
                .build();
        return cabangRepository.save(newCabang);
    }

    @Override
    public Page<Cabang> getAllCabang(Integer page, Integer size) {
        if (page <= 0){
            page = 1;
        }
        Pageable pageable = PageRequest.of(page-1, size);
        return cabangRepository.findAll(pageable);
    }

    @Override
    public Cabang getById(String id) {
        Optional<Cabang> optionalCabang = cabangRepository.findById(id);
        if (optionalCabang.isPresent()) return optionalCabang.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cabang tidak ditemukan");
    }

    @Override
    public Cabang update(Cabang cabang) {
        this.getById(cabang.getId());
        return cabangRepository.save(cabang);
    }

    @Override
    public void deleteById(String id) {
        this.getById(id);
        cabangRepository.deleteById(id);
    }
}
