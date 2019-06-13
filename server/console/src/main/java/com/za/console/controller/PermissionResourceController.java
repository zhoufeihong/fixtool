package com.za.console.controller;

import com.za.common.dto.ResultDTO;
import com.za.console.service.PermissionResourceService;
import com.za.console.service.dto.PermissionResourceDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.framework.dto.PageRequestDTO;

import java.util.List;

@Api(tags = "权限资源项功能")
@RestController
@RequestMapping("/permission_resource")
public class PermissionResourceController {

    @Autowired
    private PermissionResourceService permissionResourceService;

    /**
     * 新增权限资源项
     *
     * @param
     * @return
     */
    @ApiOperation(value = "添加权限资源项")
    @PostMapping("/addPermissionResource")
    public ResultDTO<PermissionResourceDTO> addPermissionResource(@RequestBody PermissionResourceDTO permissionResourceDTO) {
        return permissionResourceService.addPermissionResource(permissionResourceDTO);
    }

    /**
     * 删除权限资源项
     *
     * @param permissionResourceDTO
     * @return
     */
    @ApiOperation(value = "删除权限资源项")
    @PostMapping("/removePermissionResource")
    public ResultDTO removePermissionResource(@RequestBody PermissionResourceDTO permissionResourceDTO) {
        return permissionResourceService.removePermissionResource(permissionResourceDTO);
    }

    /**
     * 获取权限资源项信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "获取权限资源项信息")
    @GetMapping("/getPermissionResource")
    public ResultDTO<PermissionResourceDTO> getPermissionResource(Long id) {
        return permissionResourceService.getPermissionResource(id);
    }

    /**
     * 查询权限资源项信息
     *
     * @param
     * @return
     */
    @ApiOperation(value = "查询权限资源项信息")
    @GetMapping("/listPermissionResource")
    public ResultDTO<List<PermissionResourceDTO>> listPermissionResource(String name, Integer page, Integer limit, String sort) {
        return permissionResourceService.listPermissionResource(name, PageRequestDTO.ofOperation(page - 1, limit, sort));
    }

    /**
     * 查询权限资源项信息
     *
     * @param
     * @return
     */
    @ApiOperation(value = "查询权限资源项信息")
    @GetMapping("/listPermissionResourceByName")
    public ResultDTO<List<PermissionResourceDTO>> listPermissionResourceByName(String name) {
        return permissionResourceService.listPermissionResource(name);
    }

    /**
     * 更新权限资源项信息
     *
     * @param permissionResourceDTO
     * @return
     */
    @ApiOperation(value = "更新权限资源项信息")
    @PostMapping("/updatePermissionResource")
    public ResultDTO updatePermissionResource(@RequestBody PermissionResourceDTO permissionResourceDTO) {
        return permissionResourceService.updatePermissionResource(permissionResourceDTO);
    }

}
