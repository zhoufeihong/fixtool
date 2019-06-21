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
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @ApiOperation(value = "查询角色信息")
    @GetMapping()
    public ResultDTO<List<RoleDTO>> search(String name) {
        return roleService.listRole(name);
    }

    @ApiOperation(value = "获取角色信息")
    @GetMapping("/{id}")
    public ResultDTO<RoleDTO> get(@PathVariable("id") Long id) {
        return roleService.getRole(id);
    }

    @ApiOperation(value = "新增角色")
    @PostMapping
    public ResultDTO<RoleDTO> add(@RequestBody RoleDTO roleDTO) {
        return roleService.addRole(roleDTO);
    }

    @ApiOperation(value = "修改角色信息")
    @PutMapping("/{id}")
    public ResultDTO update(@PathVariable("id") Long id, @RequestBody RoleDTO roleDTO) {
        return roleService.updateRole(roleDTO);
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("/{id}")
    public ResultDTO delete(@PathVariable("id") Long id) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(id);
        return roleService.removeRole(roleDTO);
    }

    @ApiOperation(value = "角色授权")
    @PatchMapping("/{id}/permission_resources")
    public ResultDTO updatePermissionResources(@PathVariable("id") Long id, @RequestBody RoleDTO roleDTO) {
        return roleService.grantAuthorization(roleDTO);
    }

    /**
     * 根据角色Id查询权限资源项
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据角色查询权限资源项")
    @GetMapping("/{id}/permission_resources")
    public ResultDTO<List<PermissionResourceDTO>> queryPermissionResource(@PathVariable("id") Long id) {
        return roleService.queryPermissionResource(id);
    }

}
