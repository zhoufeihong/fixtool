package com.za.console.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.za.console.entity.base.AbstractAuditingWithVersionPo;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "T_Role_Auth")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class RoleAuthPO extends AbstractAuditingWithVersionPo implements Serializable {

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "permission_resources_code")
    private String permissionResourcesCode;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "permission_resources_code", referencedColumnName = "code",insertable = false,updatable = false)
    private PermissionResourcePO permissionResource;

}
