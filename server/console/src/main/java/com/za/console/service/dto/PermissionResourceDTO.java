package com.za.console.service.dto;

import com.za.console.service.dto.base.CreateAndUpdateTimeDTO;
import lombok.Data;

@Data
public class PermissionResourceDTO extends CreateAndUpdateTimeDTO {

    private Long id;

    private String code;

    private String parentCode;

    private String name;

    private String remark;

    private Integer status;

}
