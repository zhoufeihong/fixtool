package com.za.console.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.za.console.entity.base.AbstractAuditingWithVersionPo;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "t_permission_resource")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
@SQLDelete(sql = "update t_permission_resource set deleted = 1 where id = ? and version = ?")
@Where(clause = "deleted = 0")
public class PermissionResourcePO extends AbstractAuditingWithVersionPo implements Serializable {

    @Column(name = "code")
    private String code;

    @Column(name = "parent_code")
    private String parentCode;

    @Column(name = "name")
    private String name;

    @Column(name = "remark")
    private String remark;

    @Column(name = "status")
    private Integer status;

}
