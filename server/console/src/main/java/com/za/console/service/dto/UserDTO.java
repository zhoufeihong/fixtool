package com.za.console.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.za.console.service.dto.base.CreateAndUpdateTimeDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

@Data
public class UserDTO extends CreateAndUpdateTimeDTO {

    @ApiModelProperty(value = "用户id", example = "1")
    private Long id;

    private String userCode;

    private String password;

    @JsonIgnoreProperties
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String name;

    private String email;

    private String avatar;

    private Integer status;

    @JsonIgnore
    private String mfaSecret;

    private Set<RoleDTO> userRoles;
}
