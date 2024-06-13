package com.bni.test.BNI.Repository;

import com.bni.test.BNI.Entity.Kontrak;
import com.bni.test.BNI.Entity.Pegawai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KontrakRepository extends JpaRepository<Kontrak, String> {
    List<Kontrak> findByPegawai_FullName(String fullName);

    List<Kontrak> findByPegawai_Email(String emailPegawai);
    Optional<Kontrak> findByPegawai(Pegawai pegawai);
}
