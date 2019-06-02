package com.za.console.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class UserDTO {

    @ApiModelProperty(value = "用户id", example = "1")
    private Long id;

    private String userName;

    @JsonIgnore
    private String password;

    private String name;

    private String email;

    private Date createTime;

    public Date getCreateTime() {
        if (createTime != null) {
            return (Date) createTime.clone();
        }
        return null;
    }

    public void setCreateTime(Date createTime) {
        if (createTime != null) {
            this.createTime = (Date) createTime.clone();
        } else {
            this.createTime = null;
        }
    }

    private String avatar;

    private Integer status;

    @JsonIgnore
    private String mfaSecret;

    private Set<RoleDTO> roles;
}
