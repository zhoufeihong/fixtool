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

    private static final String INPUT_DTO_NAME = "permissionResourceDTO";

    @Autowired
    PermissionResourceReponsitory permissionResourceReponsitory;

    /**
     * 新增权限资源项
     *
     * @param
     * @return
     */
    public ResultDTO<PermissionResourceDTO> addPermissionResource(PermissionResourceDTO permissionResourceDTO) {
        AssertExtUtils.notEmpty(permissionResourceDTO, INPUT_DTO_NAME);
        PermissionResourcePO permissionResourcePO = BeanExtUtils.copyProperties(permissionResourceDTO, PermissionResourcePO.class);
        permissionResourcePO = permissionResourceReponsitory.save(permissionResourcePO);
        return ResultDTO.success(BeanExtUtils.copyProperties(permissionResourcePO, PermissionResourceDTO.class));
    }

    /**
     * 删除权限资源项
     *
     * @param permissionResourceDTO
     * @return
     */
    public ResultDTO removePermissionResource(PermissionResourceDTO permissionResourceDTO) {
        AssertExtUtils.notEmpty(permissionResourceDTO, INPUT_DTO_NAME);
        PermissionResourcePO permissionResourcePO = permissionResourceReponsitory.findById(permissionResourceDTO.getId()).orElse(null);
        if (permissionResourcePO == null) {
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
     * @param permissionResourceDTO
     * @return
     */
    public ResultDTO updatePermissionResource(PermissionResourceDTO permissionResourceDTO) {
        AssertExtUtils.notEmpty(permissionResourceDTO, INPUT_DTO_NAME);
        PermissionResourcePO permissionResourcePO = permissionResourceReponsitory.findById(permissionResourceDTO.getId()).orElse(null);
        if (permissionResourcePO == null) {
            return ResultDTO.error("没有找到更新的权限资源项信息.");
        }
        permissionResourcePO.setName(permissionResourceDTO.getName());
        permissionResourcePO.setCode(permissionResourceDTO.getCode());
        permissionResourcePO.setStatus(permissionResourceDTO.getStatus());
        permissionResourceReponsitory.saveAndFlush(permissionResourcePO);
        return ResultDTO.success();
    }

}
