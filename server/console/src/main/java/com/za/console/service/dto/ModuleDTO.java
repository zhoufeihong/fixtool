package com.za.console.service.dto;

import com.za.console.service.dto.base.CreateAndUpdateTimeDTO;
import lombok.Data;

import java.io.Serializable;

@Data
public class ModuleDTO extends CreateAndUpdateTimeDTO implements Serializable {
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
