package com.za.console.service.dto;

import com.za.console.service.dto.base.CreateAndUpdateTimeDTO;
import lombok.Data;

import java.util.Set;

@Data
public class RoleDTO extends CreateAndUpdateTimeDTO {
    private Long id;

    private String code;

    private String name;

    private Integer status;

    private String remark;

    private Set<PermissionResourceDTO> permissionResources;
}
