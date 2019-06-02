package com.za.console.service;

import com.za.console.entity.RolePO;
import com.za.console.service.dto.RoleDTO;
import za.framework.dto.PageRequestDTO;
import za.framework.dto.PageResultDTO;
import com.za.common.dto.ResultDTO;
import com.za.common.utils.AssertExtUtils;
import com.za.common.utils.BeanExtUtils;
import com.za.console.entity.ModulePO;
import com.za.console.reponsitory.ModuleReponsitory;
import com.za.console.service.dto.MenuDTO;
import com.za.console.service.dto.ModuleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuleService {

    private static final String MODULEDTO_PARAM = "moduleDTO";

    @Autowired
    ModuleReponsitory moduleReponsitory;

    /**
     * 获取模块信息
     *
     * @param id
     * @return
     */
    public ResultDTO<ModuleDTO> getModuleDTO(Long id) {
        return ResultDTO.success(BeanExtUtils.copyProperties(moduleReponsitory.findById(id).orElse(null), ModuleDTO.class));
    }

    /**
     * 根据父节点查找模块
     *
     * @param parentId
     * @return
     */
    public ResultDTO<List<ModuleDTO>> listModuleDTO(Long parentId) {
        return ResultDTO.success(BeanExtUtils.copyPropertiesOfList(moduleReponsitory.findByParentId(parentId), ModuleDTO.class));
    }

    /**
     * 查询模块信息
     *
     * @param name
     * @return
     */
    public ResultDTO<List<ModuleDTO>> listModuleDTO(String name) {
        Example example = getModuleExample(name);
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return ResultDTO.success(BeanExtUtils.copyPropertiesOfList(moduleReponsitory.findAll(example, sort), ModuleDTO.class));
    }

    private Example getModuleExample(String name) {
        ModulePO modulePO = new ModulePO();
        modulePO.setName(name);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
        return Example.of(modulePO, exampleMatcher);
    }

    /**
     * @param name
     * @param pageRequestDTO
     * @return
     */
    public PageResultDTO<List<ModuleDTO>> listModuleDTO(String name, PageRequestDTO pageRequestDTO) {
        Example example = getModuleExample(name);
        return PageResultDTO.pageSuccess(moduleReponsitory.findAll(example, pageRequestDTO.toPageRequest()), ModuleDTO.class);
    }

    /**
     * 添加模块
     *
     * @param moduleDTO
     * @return
     */
    public ResultDTO addModule(ModuleDTO moduleDTO) {
        AssertExtUtils.notEmpty(moduleDTO, MODULEDTO_PARAM);
        moduleDTO.setIsLeaf(1);
        moduleReponsitory.save(BeanExtUtils.copyProperties(moduleDTO, ModulePO.class));
        return ResultDTO.success();
    }

    /**
     * 删除模块
     *
     * @param moduleDTO
     * @return
     */
    public ResultDTO removeModule(ModuleDTO moduleDTO) {
        AssertExtUtils.notEmpty(moduleDTO, MODULEDTO_PARAM);
        ModulePO modulePO = moduleReponsitory.findById(moduleDTO.getId()).orElse(null);
        if (modulePO == null) {
            return ResultDTO.error("没有找到需要删除的模块信息.");
        }
        moduleReponsitory.deleteById(modulePO.getId());
        return ResultDTO.success();
    }

    /**
     * 更新模块信息
     *
     * @param moduleDTO
     * @return
     */
    public ResultDTO updateModule(ModuleDTO moduleDTO) {
        AssertExtUtils.notEmpty(moduleDTO, MODULEDTO_PARAM);
        ModulePO modulePO = moduleReponsitory.findById(moduleDTO.getId()).orElse(null);
        if (modulePO == null) {
            return ResultDTO.error("没有找到更新的模块信息.");
        }
        modulePO.setName(moduleDTO.getName());
        modulePO.setIsLeaf(1);
        if (moduleReponsitory.countByParentId(moduleDTO.getId()) > 0) {
            modulePO.setIsLeaf(0);
        }
        if (moduleDTO.getParentId() != MenuDTO.ROOT_ID) {
            ModulePO parentModulePO = moduleReponsitory.findById(moduleDTO.getId()).orElse(null);
            if (parentModulePO != null) {
                parentModulePO.setIsLeaf(0);
                moduleReponsitory.save(parentModulePO);
            }
        }
        modulePO.setParentId(moduleDTO.getParentId());
        modulePO.setStatus(moduleDTO.getStatus());
        modulePO.setUrl(moduleDTO.getUrl());
        modulePO.setOptions(moduleDTO.getOptions());
        modulePO.setRankIndex(moduleDTO.getRankIndex());
        moduleReponsitory.saveAndFlush(modulePO);
        return ResultDTO.success();
    }

    /**
     * 查询菜单
     *
     * @param
     * @return
     */
    public ResultDTO<MenuDTO> listMenu() {
        List<ModuleDTO> moduleDTOS = BeanExtUtils.copyPropertiesOfList(moduleReponsitory.findAll(), ModuleDTO.class);
        MenuDTO menuDTO = new MenuDTO(MenuDTO.ROOT_ID);
        menuDTO.loadChildMenus(moduleDTOS);
        return ResultDTO.success(menuDTO);
    }

}
