package com.za.console.service;

import com.za.common.dto.ResultDTO;
import com.za.common.utils.AssertExtUtils;
import com.za.common.utils.BeanExtUtils;
import com.za.console.entity.RoleAuthPO;
import com.za.console.entity.RolePO;
import com.za.console.reponsitory.RoleReponsitory;
import com.za.console.service.dto.RoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    RoleReponsitory roleReponsitory;

    /**
     * 新增角色
     * @param roleDTO
     * @return
     */
    public ResultDTO<RoleDTO> addRole(RoleDTO roleDTO) {
        AssertExtUtils.notEmpty(roleDTO, "roleDTO");
        RolePO rolePO = BeanExtUtils.copyProperties(roleDTO, RolePO.class);
        rolePO = roleReponsitory.save(rolePO);
        return ResultDTO.success(BeanExtUtils.copyProperties(rolePO, RoleDTO.class));
    }

    /**
     * 删除角色
     * @param roleDTO
     * @return
     */
    public ResultDTO removeRole(RoleDTO roleDTO) {
        AssertExtUtils.notEmpty(roleDTO, "roleDTO");
        RolePO rolePO = roleReponsitory.findById(roleDTO.getId()).orElse(null);
        if (rolePO == null) {
            return ResultDTO.error("没有找到需要删除的角色信息.");
        }
        roleReponsitory.deleteById(roleDTO.getId());
        return ResultDTO.success();
    }

    /**
     * 获取角色信息
     *
     * @param id
     * @return
     */
    public ResultDTO<RoleDTO> getRole(Long id) {
        return ResultDTO.success(BeanExtUtils.copyProperties(roleReponsitory.findById(id).orElse(null), RoleDTO.class));
    }

    /**
     * 查询角色信息
     *
     * @param name
     * @return
     */
    public ResultDTO<List<RoleDTO>> listRole(String name) {
        RolePO rolePO = new RolePO();
        rolePO.setName(name);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
        Example example = Example.of(rolePO, exampleMatcher);
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return ResultDTO.success(BeanExtUtils.copyPropertiesOfList(roleReponsitory.findAll(example, sort), RoleDTO.class));
    }

    /**
     * 更新角色信息
     *
     * @param roleDTO
     * @return
     */
    public ResultDTO updateRole(RoleDTO roleDTO) {
        AssertExtUtils.notEmpty(roleDTO, "roleDTO");
        RolePO rolePO = roleReponsitory.findById(roleDTO.getId()).orElse(null);
        if (rolePO == null) {
            return ResultDTO.error("没有找到更新的角色信息.");
        }
        rolePO.setName(roleDTO.getName());
        rolePO.setCode(roleDTO.getCode());
        rolePO.setStatus(roleDTO.getStatus());
        roleReponsitory.saveAndFlush(rolePO);
        return ResultDTO.success();
    }

    /**
     * 角色授权
     *
     * @param roleDTO
     * @return
     */
    public ResultDTO grantAuthorization(RoleDTO roleDTO) {
        AssertExtUtils.notEmpty(roleDTO, "roleDTO");
        RolePO rolePO = roleReponsitory.findById(roleDTO.getId()).orElse(null);
        if (rolePO == null) {
            return ResultDTO.error("没有找到要授权的角色信息.");
        }
        //
        rolePO.getRoleAuths().clear();
        //重新添加权限信息
        rolePO.setRoleAuths(BeanExtUtils.copyPropertiesOfList(roleDTO.getRoleAuths(), RoleAuthPO.class));
        roleReponsitory.saveAndFlush(rolePO);
        return ResultDTO.success();
    }
}
