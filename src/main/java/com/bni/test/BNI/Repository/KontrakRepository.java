package com.bni.test.BNI.Repository;

import com.bni.test.BNI.Entity.Kontrak;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KontrakRepository extends JpaRepository<Kontrak, String> {
}
