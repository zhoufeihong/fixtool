package com.za.console.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.za.console.service.dto.base.CreateAndUpdateTime;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class UserDTO extends CreateAndUpdateTime {

    @ApiModelProperty(value = "用户id", example = "1")
    private Long id;

    private String userName;

    @JsonIgnore
    private String password;

    private String name;

    private String email;

    private String avatar;

    private Integer status;

    @JsonIgnore
    private String mfaSecret;

    private Set<RoleDTO> roles;
}
