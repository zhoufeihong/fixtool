package com.za.console.reponsitory;

import com.za.console.entity.RolePO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleReponsitory extends JpaRepository<RolePO, Long> {
}
