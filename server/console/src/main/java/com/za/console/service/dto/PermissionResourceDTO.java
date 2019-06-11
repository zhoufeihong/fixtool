package com.za.console.service.dto;

import com.za.console.service.dto.base.CreateAndUpdateTime;
import lombok.Data;

import java.util.Date;

@Data
public class PermissionResourceDTO extends CreateAndUpdateTime {

    private Long id;

    private String code;

    private String parentCode;

    private String name;

    private String remark;

    private Integer status;

}
