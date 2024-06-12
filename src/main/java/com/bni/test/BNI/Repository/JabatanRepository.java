package com.bni.test.BNI.Repository;

import com.bni.test.BNI.Entity.Jabatan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JabatanRepository extends JpaRepository<Jabatan, String> {
}
