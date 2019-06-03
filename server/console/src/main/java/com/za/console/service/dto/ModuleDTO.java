package com.za.console.service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ModuleDTO implements Serializable {
    private Long id;
    private String name;
    private String url;
    private Long parentId;
    private Integer isLeaf;
    private Integer status;
    private String options;
    private Integer rankIndex;
    private Integer version;
}
