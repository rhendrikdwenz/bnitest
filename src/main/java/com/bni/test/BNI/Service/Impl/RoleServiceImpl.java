package com.bni.test.BNI.Service.Impl;

import com.bni.test.BNI.Constant.ERole;
import com.bni.test.BNI.Entity.Role;
import com.bni.test.BNI.Repository.RoleRepository;
import com.bni.test.BNI.Service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role getOrSave(ERole role) {
        //kalau role ada maka akan kita ambil
        Optional<Role> optionalRole = roleRepository.findByRole(role);
        if (optionalRole.isPresent()) return optionalRole.get();
        //jika tidak ada, maka akan disimpan
        Role newRole = Role.builder()
                .role(role)
                .build();
        return roleRepository.saveAndFlush(newRole);
    }

}
