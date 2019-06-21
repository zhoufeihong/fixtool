package com.za.console.controller;

import com.za.common.dto.ResultDTO;
import com.za.console.service.ModuleService;
import com.za.console.service.dto.MenuDTO;
import com.za.console.service.dto.ModuleDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.framework.dto.PageRequestDTO;

import java.util.List;

@Api(tags = "菜单功能")
@RestController
@RequestMapping("/api/module")
public class ModuleController {

    @Autowired
    ModuleService moduleService;

    @ApiOperation(value = "查询模块信息")
    @GetMapping()
    public ResultDTO<List<ModuleDTO>> searchPageList(String name, Integer page, Integer limit, String sort) {
        return moduleService.listModuleDTO(name, PageRequestDTO.ofOperation(page, limit, sort));
    }

    @ApiOperation(value = "获取菜单信息")
    @GetMapping("/{id}")
    public ResultDTO<ModuleDTO> get(@PathVariable("id") Long id) {
        return moduleService.getModuleDTO(id);
    }

    @ApiOperation("添加模块")
    @PostMapping()
    public ResultDTO add(@RequestBody ModuleDTO module) {
        return moduleService.addModule(module);
    }

    @ApiOperation("修改模块")
    @PutMapping("/{id}")
    public ResultDTO update(@PathVariable("id") Long id, @RequestBody ModuleDTO module) {
        return moduleService.updateModule(module);
    }

    @ApiOperation("删除模块")
    @DeleteMapping("/{id}")
    public ResultDTO delete(@PathVariable("id") Long id) {
        return moduleService.removeModule(id);
    }

    @ApiOperation(value = "查询菜单信息")
    @GetMapping("/menus")
    public ResultDTO<MenuDTO> listMenu() {
        return moduleService.listMenu();
    }

    @ApiOperation(value = "根据父节点查询模块信息")
    @GetMapping("/{id}/Submodule")
    public ResultDTO<List<ModuleDTO>> listModuleByParentId(@PathVariable("id") Long id) {
        return moduleService.listModuleDTO(id);
    }

}
