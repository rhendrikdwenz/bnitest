package com.bni.test.BNI.Service.Impl;

import com.bni.test.BNI.Entity.Pegawai;
import com.bni.test.BNI.Model.Request.PegawaiRequest;
import com.bni.test.BNI.Repository.PegawaiRepository;
import com.bni.test.BNI.Service.PegawaiService;
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
public class PegawaiServiceImpl implements PegawaiService {

    private final PegawaiRepository pegawaiRepository;
    @Override
    public Pegawai tambahPegawai(PegawaiRequest pegawaiRequest) {
        Pegawai newPegawai = Pegawai.builder()
                .fullName(pegawaiRequest.getFullName())
                .gender(pegawaiRequest.getGender())
                .email(pegawaiRequest.getEmail())
                .phoneNumber(pegawaiRequest.getPhoneNumber())
                .address(pegawaiRequest.getAddress())
                .build();

        // Simpan objek Pegawai ke database
        return pegawaiRepository.save(newPegawai);
    }

    @Override
    public Page<Pegawai> getAllPegawai(Integer page, Integer size) {
       if (page <= 0){
           page = 1;
       }
        Pageable pageable = PageRequest.of(page-1, size);
       return pegawaiRepository.findAll(pageable);
    }

    @Override
    public Pegawai getById(String id) {
        Optional<Pegawai> optionalPegawai = pegawaiRepository.findById(id);
        if (optionalPegawai.isPresent()) return optionalPegawai.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan Not Found");
    }

    @Override
    public Pegawai update(Pegawai pegawai) {
        this.getById(pegawai.getId());
        return pegawaiRepository.save(pegawai);
    }

    @Override
    public void deleteById(String id) {
        this.getById(id);
        pegawaiRepository.deleteById(id);

    }
}
