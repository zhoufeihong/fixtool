package com.za.console.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "T_Role_Auth")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class RoleAuthPO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "module_id")
    private Long moduleId;

    @Column(name = "permission")
    private String permission;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "module_Id",referencedColumnName = "id")
    private ModulePO module;

}
