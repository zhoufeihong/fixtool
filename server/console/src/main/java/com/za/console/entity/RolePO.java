package com.za.console.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.za.console.entity.base.AbstractAuditingWithVersionPo;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "t_role")
@SQLDelete(sql = "update t_role set deleted = 1 where id = ? and version = ?")
@Where(clause = "deleted = 0")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class RolePO extends AbstractAuditingWithVersionPo {

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "remark")
    private String remark;

    @Column(name = "status")
    private Integer status;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "role_Id",referencedColumnName = "id")
    private Set<RoleAuthPO> roleAuth;

}