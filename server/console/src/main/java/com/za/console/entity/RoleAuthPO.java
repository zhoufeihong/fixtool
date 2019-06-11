package com.za.console.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.za.console.entity.base.AbstractAuditingWithVersionPo;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@Entity
@Table(name = "T_Role_Auth")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
@SQLDelete(sql = "update T_Role_Auth set deleted = 1 where id = ? and version = ?")
@Where(clause = "deleted = 0")
public class RoleAuthPO extends AbstractAuditingWithVersionPo {

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "permission_resources_code")
    private String permissionResourcesCode;

}
