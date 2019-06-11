package com.za.console.service;

import com.za.common.dto.ResultDTO;
import com.za.common.utils.AssertExtUtils;
import com.za.common.utils.BeanExtUtils;
import com.za.console.entity.PermissionResourcePO;
import com.za.console.reponsitory.PermissionResourceReponsitory;
import com.za.console.service.dto.PermissionResourceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionResourceService {

    @Autowired
    PermissionResourceReponsitory permissionResourceReponsitory;

    /**
     * 新增权限资源项
     *
     * @param
     * @return
     */
    public ResultDTO<PermissionResourceDTO> addPermissionResource(PermissionResourceDTO permissionResourceDTO) {
        AssertExtUtils.notEmpty(permissionResourceDTO, "permissionResourceDTO");
        PermissionResourcePO rolePO = BeanExtUtils.copyProperties(permissionResourceDTO, PermissionResourcePO.class);
        rolePO = permissionResourceReponsitory.save(rolePO);
        return ResultDTO.success(BeanExtUtils.copyProperties(rolePO, PermissionResourceDTO.class));
    }

    /**
     * 删除权限资源项
     *
     * @param permissionResourceDTO
     * @return
     */
    public ResultDTO removePermissionResource(PermissionResourceDTO permissionResourceDTO) {
        AssertExtUtils.notEmpty(permissionResourceDTO, "permissionResourceDTO");
        PermissionResourcePO rolePO = permissionResourceReponsitory.findById(permissionResourceDTO.getId()).orElse(null);
        if (rolePO == null) {
            return ResultDTO.error("没有找到需要删除的权限资源项信息.");
        }
        permissionResourceReponsitory.deleteById(permissionResourceDTO.getId());
        return ResultDTO.success();
    }

    /**
     * 获取权限资源项信息
     *
     * @param id
     * @return
     */
    public ResultDTO<PermissionResourceDTO> getPermissionResource(Long id) {
        return ResultDTO.success(BeanExtUtils.copyProperties(permissionResourceReponsitory.findById(id).orElse(null), PermissionResourceDTO.class));
    }

    /**
     * 查询权限资源项信息
     *
     * @param name
     * @return
     */
    public ResultDTO<List<PermissionResourceDTO>> listPermissionResource(String name) {
        PermissionResourcePO permissionResourcePO = new PermissionResourcePO();
        permissionResourcePO.setName(name);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
        Example example = Example.of(permissionResourcePO, exampleMatcher);
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return ResultDTO.success(BeanExtUtils.copyPropertiesOfList(permissionResourceReponsitory.findAll(example, sort), PermissionResourceDTO.class));
    }

    /**
     * 更新权限资源项信息
     *
     * @param roleDTO
     * @return
     */
    public ResultDTO updatePermissionResource(PermissionResourceDTO roleDTO) {
        AssertExtUtils.notEmpty(roleDTO, "roleDTO");
        PermissionResourcePO permissionResourcePO = permissionResourceReponsitory.findById(roleDTO.getId()).orElse(null);
        if (permissionResourcePO == null) {
            return ResultDTO.error("没有找到更新的权限资源项信息.");
        }
        permissionResourcePO.setName(roleDTO.getName());
        permissionResourcePO.setCode(roleDTO.getCode());
        permissionResourcePO.setStatus(roleDTO.getStatus());
        permissionResourceReponsitory.saveAndFlush(permissionResourcePO);
        return ResultDTO.success();
    }

    /**
     *  根据角色查询权限资源项
     * @param roleId
     * @return
     */
    public ResultDTO<List<PermissionResourceDTO>> queryPermissionResource(Long roleId) {
        List<PermissionResourcePO> permissionResourcePOList = permissionResourceReponsitory.queryPermissionResource(roleId);
        return ResultDTO.success(BeanExtUtils.copyPropertiesOfList(permissionResourcePOList, PermissionResourceDTO.class));
    }
}
