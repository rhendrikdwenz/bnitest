package com.bni.test.BNI.Repository;

import com.bni.test.BNI.Constant.ERole;
import com.bni.test.BNI.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByRole(ERole role);
}
