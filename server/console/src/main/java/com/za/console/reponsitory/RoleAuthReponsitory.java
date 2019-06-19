package com.za.console.reponsitory;

import com.za.console.entity.RoleAuthPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface RoleAuthReponsitory extends JpaRepository<RoleAuthPO, Long> {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void deleteByRoleId(Long roleId);

    Set<RoleAuthPO> findByRoleId(Long roleId);
}
