package com.bni.test.BNI.Repository;

import com.bni.test.BNI.Entity.Kontrak;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KontrakRepository extends JpaRepository<Kontrak, String> {
    List<Kontrak> findByPegawai_FullName(String fullName);

    List<Kontrak> findByPegawai_Email(String emailPegawai);
}
