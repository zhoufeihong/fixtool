package com.za.console.service.dto;

import lombok.Data;

import java.util.Set;

@Data
public class RoleDTO {
    private Long id;

    private String code;

    private String name;

    private Integer status;

    private Set<RoleAuthDTO> roleAuths;
}
