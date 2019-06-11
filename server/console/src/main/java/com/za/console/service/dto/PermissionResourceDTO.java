package com.za.console.service.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PermissionResourceDTO {

    private Long id;

    private String code;

    private String parentCode;

    private String name;

    private String remark;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}
