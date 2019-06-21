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
@RequestMapping("/api/permission_resource")
public class PermissionResourceController {

    @Autowired
    private PermissionResourceService permissionResourceService;

    /**
     * 查询权限资源项信息
     *
     * @param
     * @return
     */
    @ApiOperation(value = "查询权限资源项信息")
    @GetMapping()
    public ResultDTO<List<PermissionResourceDTO>> searchPageList(String name, Integer page, Integer limit, String sort) {
        if (page == null) {
            return permissionResourceService.listPermissionResource(name);
        }
        return permissionResourceService.listPermissionResource(name, PageRequestDTO.ofOperation(page, limit, sort));
    }

    /**
     * 获取权限资源项信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "获取权限资源项信息")
    @GetMapping("/{id}")
    public ResultDTO<PermissionResourceDTO> getPermissionResource(@PathVariable("id") Long id) {
        return permissionResourceService.getPermissionResource(id);
    }

    /**
     * 新增权限资源项
     *
     * @param
     * @return
     */
    @ApiOperation(value = "添加权限资源项")
    @PostMapping()
    public ResultDTO<PermissionResourceDTO> add(@RequestBody PermissionResourceDTO permissionResourceDTO) {
        return permissionResourceService.addPermissionResource(permissionResourceDTO);
    }

    /**
     * 更新权限资源项信息
     *
     * @param permissionResourceDTO
     * @return
     */
    @ApiOperation(value = "更新权限资源项信息")
    @PutMapping("/{id}")
    public ResultDTO update(@PathVariable("id") Long id, @RequestBody PermissionResourceDTO permissionResourceDTO) {
        return permissionResourceService.updatePermissionResource(permissionResourceDTO);
    }

    /**
     * 删除权限资源项
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除权限资源项")
    @DeleteMapping("/{id}")
    public ResultDTO delete(@PathVariable("id") Long id) {
        return permissionResourceService.removePermissionResource(id);
    }

}
