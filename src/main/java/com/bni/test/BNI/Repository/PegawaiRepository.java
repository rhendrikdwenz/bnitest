package com.bni.test.BNI.Repository;

import com.bni.test.BNI.Entity.Pegawai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PegawaiRepository extends JpaRepository<Pegawai, String> {

}