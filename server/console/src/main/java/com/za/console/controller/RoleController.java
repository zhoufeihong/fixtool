package com.za.console.controller;

import com.za.common.dto.ResultDTO;
import com.za.console.service.RoleService;
import com.za.console.service.dto.PermissionResourceDTO;
import com.za.console.service.dto.RoleDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "角色功能")
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @ApiOperation(value = "新增角色")
    @PostMapping("/addRole")
    public ResultDTO<RoleDTO> addRole(@RequestBody RoleDTO roleDTO) {
        return roleService.addRole(roleDTO);
    }

    @ApiOperation(value = "删除角色")
    @PostMapping("/removeRole")
    public ResultDTO removeRole(Long id) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(id);
        return roleService.removeRole(roleDTO);
    }

    @ApiOperation(value = "获取角色信息")
    @GetMapping("/getRole")
    public ResultDTO<RoleDTO> getRole(@RequestParam("id") Long id) {
        return roleService.getRole(id);
    }

    @ApiOperation(value = "查询角色信息")
    @GetMapping("/listRole")
    public ResultDTO<List<RoleDTO>> listRole(String name) {
        return roleService.listRole(name);
    }

    @ApiOperation(value = "新增角色")
    @PostMapping("/updateRole")
    public ResultDTO updateRole(@RequestBody RoleDTO roleDTO) {
        return roleService.updateRole(roleDTO);
    }

    @ApiOperation(value = "角色授权")
    @PostMapping("/grantAuthorization")
    public ResultDTO grantAuthorization(@RequestBody RoleDTO roleDTO) {
        return roleService.grantAuthorization(roleDTO);
    }

    /**
     * 根据角色Id查询权限资源项
     *
     * @param roleId
     * @return
     */
    @ApiOperation(value = "根据角色查询权限资源项")
    @PostMapping("/queryPermissionResource")
    public ResultDTO<List<PermissionResourceDTO>> queryPermissionResource(Long roleId) {
        return roleService.queryPermissionResource(roleId);
    }

}
