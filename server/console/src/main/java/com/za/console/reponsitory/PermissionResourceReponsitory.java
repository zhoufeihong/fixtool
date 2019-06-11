package com.za.console.reponsitory;

import com.za.console.entity.PermissionResourcePO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PermissionResourceReponsitory extends JpaRepository<PermissionResourcePO, Long> {
    @Query(value = "select tpr.id,tpr.code,tpr.parent_code,tpr.name,tpr.remark,tpr.status,tpr.version,tpr.deleted ,tpr.create_time,tpr.create_by,tpr.update_time,tpr.update_by " +
            " from t_Role tr " +
            " inner join t_Role_Auth tra on tr.id  = tra.role_Id " +
            " inner join t_Permission_Resource tpr on tra.permission_Resources_Code = tpr.code " +
            " where tr.id = :roleId ",nativeQuery = true)
    List<PermissionResourcePO> queryPermissionResource(@Param("roleId") Long roleId);
}
