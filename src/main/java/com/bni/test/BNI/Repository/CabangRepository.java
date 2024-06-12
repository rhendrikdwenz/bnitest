package com.bni.test.BNI.Repository;

import com.bni.test.BNI.Entity.Cabang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CabangRepository extends JpaRepository<Cabang, String> {
}
