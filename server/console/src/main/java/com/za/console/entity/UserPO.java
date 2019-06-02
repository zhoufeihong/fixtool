package com.za.console.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "t_user")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class UserPO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Basic
    private Long id;

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

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "create_time",updatable = false)
    @Basic
    private Date createTime;

    public Date getCreateTime() {
        if (createTime != null) {
            return (Date) createTime.clone();
        }
        return null;
    }

    public void setCreateTime(Date createTime) {
        if (createTime != null) {
            this.createTime = (Date) createTime.clone();
        } else {
            this.createTime = null;
        }
    }

    @Column(name = "avatar")
    @Basic
    private String avatar;

    @Column(name = "status")
    @Basic
    private Integer status;

    @Column(name = "mfa_secret")
    @Basic
    private String mfaSecret;

    @ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinTable(name = "t_user_role", joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id") })
    private Set<RolePO> roles;

}
