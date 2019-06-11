package com.za.console.service;

import com.za.common.dto.ResultDTO;
import com.za.common.utils.AssertExtUtils;
import com.za.console.entity.PermissionResourcePO;
import com.za.console.entity.RoleAuthPO;
import com.za.console.entity.RolePO;
import com.za.console.reponsitory.PermissionResourceReponsitory;
import com.za.console.reponsitory.RoleAuthReponsitory;
import com.za.console.reponsitory.RoleReponsitory;
import com.za.console.service.dto.PermissionResourceDTO;
import com.za.console.service.dto.RoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.za.common.utils.BeanExtUtils.copyProperties;
import static com.za.common.utils.BeanExtUtils.copyPropertiesOfList;

@Service
public class RoleService {

    @Autowired
    RoleReponsitory roleReponsitory;

    @Autowired
    PermissionResourceReponsitory permissionResourceReponsitory;

    @Autowired
    RoleAuthReponsitory roleAuthReponsitory;

    /**
     * 新增角色
     *
     * @param roleDTO
     * @return
     */
    public ResultDTO<RoleDTO> addRole(RoleDTO roleDTO) {
        AssertExtUtils.notEmpty(roleDTO, "roleDTO");
        RolePO rolePO = copyProperties(roleDTO, RolePO.class);
        rolePO = roleReponsitory.save(rolePO);
        return ResultDTO.success(copyProperties(rolePO, RoleDTO.class));
    }

    /**
     * 删除角色
     *
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
        return ResultDTO.success(copyProperties(roleReponsitory.findById(id).orElse(null), RoleDTO.class));
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
        return ResultDTO.success(copyPropertiesOfList(roleReponsitory.findAll(example, sort), RoleDTO.class));
    }

    /**
     *  获取角色信息(包含权限信息)
     * @param roleId
     * @return
     */
    public ResultDTO<RoleDTO> getRoleWithPermission(Long roleId) {
        RolePO rolePO = roleReponsitory.findById(roleId).orElse(null);
        if (rolePO != null) {
            RoleDTO roleDTO = copyProperties(rolePO, RoleDTO.class);
            Set<RoleAuthPO> roleAuthPOs = roleAuthReponsitory.findByRoleId(roleId);
            if (roleAuthPOs != null) {
                roleDTO.setPermissionResources(roleAuthPOs.stream().filter(f -> f.getPermissionResource() != null).map(m ->
                        copyProperties(m.getPermissionResource(), PermissionResourceDTO.class)
                ).collect(Collectors.toSet()));
            }
            return ResultDTO.success(roleDTO);
        }
        return ResultDTO.success(null);
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
        //重新添加权限信息
        Set<RoleAuthPO> roleAuthPOS = new LinkedHashSet<>();
        if (roleDTO.getPermissionResources() != null) {
            for (PermissionResourceDTO permissionResourceDTO : roleDTO.getPermissionResources()) {
                PermissionResourcePO tempPermissionResource = permissionResourceReponsitory.findFirstByCode(permissionResourceDTO.getCode());
                if (tempPermissionResource == null) {
                    return ResultDTO.error("不能添加不存在的权限资源信息.");
                }
                RoleAuthPO roleAuthPO = new RoleAuthPO();
                roleAuthPO.setRoleId(roleDTO.getId());
                roleAuthPO.setPermissionResourcesCode(permissionResourceDTO.getCode());
                roleAuthPOS.add(roleAuthPO);
            }
        }
        roleAuthReponsitory.deleteByRoleId(roleDTO.getId());
        roleAuthReponsitory.saveAll(roleAuthPOS);
        roleReponsitory.flush();
        return ResultDTO.success();
    }
}
