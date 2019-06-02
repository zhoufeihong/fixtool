package com.za.console.service.dto;

import com.za.console.entity.ModulePO;
import lombok.Data;

@Data
public class RoleAuthDTO {

    private Long id;

    private Long roleId;

    private Long moduleId;

    private String permission;

    private ModulePO module;
}
