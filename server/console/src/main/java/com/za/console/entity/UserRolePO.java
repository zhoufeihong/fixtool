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
@Table(name = "t_user_role")
@SQLDelete(sql = "update t_user_role set deleted = 1 where id = ? and version = ?")
@Where(clause = "deleted = 0")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class UserRolePO extends AbstractAuditingWithVersionPo implements Serializable {

    @Column(name = "user_id")
    @Basic
    private Long userId;

    @Column(name = "role_id")
    @Basic
    private Long roleId;

}
