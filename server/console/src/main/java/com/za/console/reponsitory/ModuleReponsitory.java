package com.za.console.reponsitory;

import com.za.console.entity.ModulePO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModuleReponsitory extends JpaRepository<ModulePO, Long> {
    List<ModulePO> findByParentId(Long parentId);
    Integer countByParentId(Long parentId);
}
