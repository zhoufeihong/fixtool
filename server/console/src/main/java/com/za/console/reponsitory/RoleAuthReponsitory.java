package com.za.console.reponsitory;

import com.za.console.entity.RoleAuthPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RoleAuthReponsitory extends JpaRepository<RoleAuthPO, Long> {

    void deleteByRoleId(Long roleId);

    Set<RoleAuthPO> findByRoleId(Long roleId);
}
