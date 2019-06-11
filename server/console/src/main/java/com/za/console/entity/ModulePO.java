package com.za.console.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.za.console.entity.base.AbstractAuditingWithVersionPo;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "t_module")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
@SQLDelete(sql = "update t_module set deleted = 1 where id = ? and version = ?")
@Where(clause = "deleted = 0")
public class ModulePO extends AbstractAuditingWithVersionPo implements Serializable {

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "is_leaf")
    private Integer isLeaf;

    @Column(name = "status")
    private Integer status;

    @Column(name = "permission_resources_code")
    private String permissionResourcesCode;

    @Column(name = "options")
    private String options;

    @Column(name = "rank_index")
    private Integer rankIndex;

}