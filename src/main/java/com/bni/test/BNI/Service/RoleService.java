package com.bni.test.BNI.Service;

import com.bni.test.BNI.Constant.ERole;
import com.bni.test.BNI.Entity.Role;

public interface RoleService {
    Role getOrSave(ERole role);
}
