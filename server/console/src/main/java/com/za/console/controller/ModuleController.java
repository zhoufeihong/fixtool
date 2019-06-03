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
@RequestMapping("/module")
public class ModuleController {

    @Autowired
    ModuleService moduleService;

    @ApiOperation(value = "获取菜单信息")
    @GetMapping("/getModule")
    public ResultDTO<ModuleDTO> getModuleDTO(Long id) {
        return  moduleService.getModuleDTO(id);
    }

    @ApiOperation(value = "查询菜单信息")
    @GetMapping("/listMenu")
    public ResultDTO<MenuDTO> listMenu() {
        return moduleService.listMenu();
    }

    @ApiOperation(value = "根据父节点查询模块信息")
    @GetMapping("/listModuleByParentId")
    public ResultDTO<List<ModuleDTO>> listModuleByParentId(Long parentId) {
        return moduleService.listModuleDTO(parentId);
    }

    @ApiOperation(value = "查询模块信息")
    @GetMapping("/listModule")
    public ResultDTO<List<ModuleDTO>> listModule(String name, Integer page, Integer limit, String sort) {
        return moduleService.listModuleDTO(name, PageRequestDTO.ofOperation(page - 1, limit, sort));
    }

    @ApiOperation("添加模块")
    @PostMapping("/addModule")
    public ResultDTO addModule(@RequestBody ModuleDTO module) {
        return moduleService.addModule(module);
    }

    @ApiOperation("修改模块")
    @PostMapping("/updateModule")
    public ResultDTO updateModule(@RequestBody ModuleDTO module) {
        return moduleService.updateModule(module);
    }

    @ApiOperation("删除模块")
    @PostMapping("/removeModule")
    public ResultDTO removeModule(@RequestBody ModuleDTO module) {
        return moduleService.removeModule(module);
    }
}
