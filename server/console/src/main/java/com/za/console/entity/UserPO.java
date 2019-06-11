package com.za.console.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.za.console.entity.base.AbstractAuditingWithVersionPo;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name = "t_user")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
@SQLDelete(sql = "update t_user set deleted = 1 where id = ? and version = ?")
@Where(clause = "deleted = 0")
public class UserPO extends AbstractAuditingWithVersionPo implements Serializable {

    @Column(name = "userName")
    @Basic
    private String userName;

    @Column(name = "password")
    @Basic
    private String password;

    @Column(name = "name")
    @Basic
    private String name;

    @Column(name = "email")
    @Basic
    private String email;

    @Column(name = "avatar")
    @Basic
    private String avatar;

    @Column(name = "status")
    @Basic
    private Integer status;

    @Column(name = "mfa_secret")
    @Basic
    private String mfaSecret;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "t_user_role", joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<RolePO> userRoles;

}
