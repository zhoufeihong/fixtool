package com.za.console.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "t_role")
@SQLDelete(sql = "update t_role set deleted = 1 where id = ?")
@Where(clause = "deleted = 0")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class RolePO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private Integer status;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "role_Id",referencedColumnName = "id")
    private Set<RoleAuthPO> roleAuths;

}