package com.za.console.service.dto;

import com.za.console.service.dto.base.CreateAndUpdateTime;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ModuleDTO extends CreateAndUpdateTime implements Serializable {
    private Long id;
    private String name;
    private String url;
    private Long parentId;
    private Integer isLeaf;
    private Integer status;
    private String options;
    private Integer rankIndex;
    private Integer version;
    private String permissionResourcesCode;
}
