package com.bni.test.BNI.Repository;

import com.bni.test.BNI.Entity.Pegawai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PegawaiRepository extends JpaRepository<Pegawai, String> {
    Optional<Pegawai> findByFullName(String fullName);
}