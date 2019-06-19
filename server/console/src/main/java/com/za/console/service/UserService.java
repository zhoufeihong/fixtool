package com.za.console.service;

import com.za.common.dto.ResultDTO;
import com.za.common.generate.SnowflakeIdWorker;
import com.za.common.utils.AssertExtUtils;
import com.za.common.utils.BeanExtUtils;
import com.za.common.utils.PasswordUtils;
import com.za.common.utils.RegexMatchingRuleUtils;
import com.za.console.common.utils.JWTUtils;
import com.za.console.entity.RolePO;
import com.za.console.entity.UserPO;
import com.za.console.reponsitory.RoleReponsitory;
import com.za.console.reponsitory.UserReponsitory;
import com.za.console.service.dto.RoleDTO;
import com.za.console.service.dto.UserDTO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import za.framework.dto.PageRequestDTO;
import za.framework.dto.PageResultDTO;

import javax.persistence.criteria.Predicate;
import java.util.*;

@Service
@CacheConfig(cacheNames = "user")
@DependsOn("snowflakeIdWorker.userCode")
public class UserService {

    // 过期时间10分钟
    private static final long EXPIRE_TIME = 10L * 60 * 1000;

    private static final String NOT_FIND_USER = "没有找到用户信息";

    private static final String INVALID_TOKEN = "无效秘钥";

    private static final String ADD_USER_ERROR = "添加用户失败:";

    @Autowired
    UserReponsitory userReponsitory;

    @Autowired
    RoleReponsitory roleReponsitory;

    @Autowired
    @Qualifier("snowflakeIdWorker.userCode")
    SnowflakeIdWorker snowflakeIdWorker;

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    public ResultDTO addUser(UserDTO user) {
        if (user == null) {
            return ResultDTO.error(ADD_USER_ERROR + "用户信息为空");
        }
        if (StringUtils.isBlank(user.getPassword())) {
            return ResultDTO.error(ADD_USER_ERROR + "密码格式不正确");
        }
        String userCode = user.getUserCode();
        if (StringUtils.isNotBlank(user.getUserCode())) {
            if (!RegexMatchingRuleUtils.userCode(user.getUserCode())) {
                return ResultDTO.error(ADD_USER_ERROR + "用户编号格式不正确");
            }
            if (userReponsitory.findByUserCode(user.getUserCode()) != null) {
                return ResultDTO.error(ADD_USER_ERROR + "已经存在用户编号");
            }
        } else {
            userCode = String.valueOf(snowflakeIdWorker.nextId());
        }
        UserPO userPO = BeanExtUtils.copyProperties(user, UserPO.class);
        userPO.setMfaSecret(UUID.randomUUID().toString().replace("-", ""));
        userPO.setPassword(PasswordUtils.encrypt(user.getPassword(), userPO.getMfaSecret()));
        userPO.setUserCode(userCode);
        userReponsitory.saveAndFlush(userPO);
        return ResultDTO.success();
    }

    /**
     * 获取用户信息
     *
     * @param id
     * @return
     */
    @Cacheable(key = "#id")
    public ResultDTO<UserDTO> getUser(long id) {
        UserPO userPO = userReponsitory.findById(id).orElse(null);
        if (userPO == null) {
            return null;
        }
        //复制user信息
        UserDTO userResult = BeanExtUtils.copyProperties(userPO, UserDTO.class);
        userResult.setUserRoles(BeanExtUtils.copyPropertiesOfList(userPO.getUserRoles(), RoleDTO.class));
        return ResultDTO.success(userResult);
    }

    /**
     * 查询用户信息
     *
     * @return
     */
    public PageResultDTO<List<UserDTO>> listUser(String userName) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(0, 1000, sort);
        return getListUserResultDTO(userName, pageable);
    }

    /**
     * 查询用户信息
     *
     * @return
     */
    public PageResultDTO<List<UserDTO>> listUser(String userName, PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.toPageRequest();
        //查询
        return getListUserResultDTO(userName, pageable);
    }

    private PageResultDTO<List<UserDTO>> getListUserResultDTO(String userName, Pageable pageable) {
        //查询
        Page<UserPO> userPOS = userReponsitory.findAll((root, criteriaQuery, criteriaBuilder) -> {
            //查询条件
            List<Predicate> predicates = new ArrayList<>();
            if (!StringUtils.isEmpty(userName)) {
                Predicate predicate1 = criteriaBuilder.like(root.get("userCode"), userName + "%");
                Predicate predicate2 = criteriaBuilder.like(root.get("name"), userName + "%");
                predicates.add(criteriaBuilder.or(predicate1, predicate2));
            }
            return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]))
                    .getRestriction();
        }, pageable);
        return PageResultDTO.pageSuccess(userPOS, UserDTO.class);
    }

    /**
     * 更新角色信息
     *
     * @param user
     * @return
     */
    @CachePut(key = "#user.id")
    public ResultDTO updateRole(UserDTO user) {
        AssertExtUtils.notEmpty(user, "user");
        UserPO userPo = userReponsitory.findById(user.getId()).orElse(null);
        if (userPo == null) {
            return ResultDTO.error(NOT_FIND_USER);
        }
        Set<RolePO> rolePOS = new LinkedHashSet<>();
        if (user.getUserRoles() != null) {
            for (RoleDTO roleDTO : user.getUserRoles()) {
                RolePO tempRolePO = roleReponsitory.getOne(roleDTO.getId());
                if (tempRolePO == null) {
                    return ResultDTO.error("不能添加不存在的角色信息.");
                }
                rolePOS.add(tempRolePO);
            }
        }
        userPo.setUserRoles(rolePOS);
        userReponsitory.saveAndFlush(userPo);
        return ResultDTO.success();
    }

    /**
     * 更新用户信息
     *
     * @param userDto
     * @return
     */
    @CachePut(key = "#userDto.id")
    public ResultDTO updateUser(UserDTO userDto) {
        AssertExtUtils.notEmpty(userDto, "userDto");
        UserPO userPO = userReponsitory.findById(userDto.getId()).orElse(null);
        if (userPO == null) {
            return ResultDTO.error("没有找到用户信息.");
        }
        userPO.setAvatar(userDto.getAvatar());
        userPO.setStatus(userDto.getStatus());
        userPO.setName(userDto.getName());
        userPO.setEmail(userDto.getEmail());
        userReponsitory.saveAndFlush(userPO);
        return ResultDTO.success(BeanExtUtils.copyProperties(userPO, UserDTO.class));
    }

    /**
     * 修改密码
     *
     * @param userDto
     * @return
     */
    public ResultDTO updatePassword(UserDTO userDto) {
        AssertExtUtils.notEmpty(userDto, "userDto.");
        if (StringUtils.isBlank(userDto.getPassword())) {
            return ResultDTO.error("密码不能为空");
        }
        UserPO userPO = userReponsitory.findById(userDto.getId()).orElse(null);
        if (userPO == null) {
            return ResultDTO.error("没有找到用户信息.");
        }
        if (StringUtils.isNotBlank(userDto.getPassword())) {
            userPO.setPassword(PasswordUtils.encrypt(userDto.getPassword(), userDto.getMfaSecret()));
        }
        userReponsitory.saveAndFlush(userPO);
        return ResultDTO.success();
    }

    /**
     * 获取Token
     *
     * @param userCode
     * @param password
     * @return
     */
    public ResultDTO getToken(String userCode, String password) {
        if (StringUtils.isBlank(userCode)) {
            return ResultDTO.error("用户格式不正确.");
        }
        UserPO userPO = userReponsitory.findByUserCode(userCode);
        if (userPO == null) {
            return ResultDTO.error("不存在的用户.");
        }
        if (userPO.getStatus() != 1) {
            return ResultDTO.error("用户未激活或被锁定..");
        }
        //验证密码
        if (PasswordUtils.verify(password, userPO.getMfaSecret(), userPO.getPassword())) {
            return ResultDTO.success(JWTUtils.sign(userCode, userPO.getId(), userPO.getPassword(), EXPIRE_TIME));
        }
        return ResultDTO.error("请输入正确的用户名或密码.");
    }

    /**
     * 获取用户信息
     *
     * @param accessToken
     * @return
     */
    public ResultDTO<UserDTO> getUserInfo(String accessToken) {
        String userCode = JWTUtils.getUsername(accessToken);
        if (StringUtils.isBlank(userCode)) {
            return ResultDTO.error(INVALID_TOKEN);
        }
        UserPO userPO = userReponsitory.findByUserCode(userCode);
        try {
            JWTUtils.verify(accessToken, userCode, userPO.getPassword());
        } catch (Exception ex) {
            return ResultDTO.error(INVALID_TOKEN);
        }
        return ResultDTO.success(BeanExtUtils.copyProperties(userPO, UserDTO.class));
    }

    /**
     * 刷新Token
     *
     * @param accessToken
     * @return
     */
    public ResultDTO refreshToken(String accessToken) {
        String userCode = JWTUtils.getUsername(accessToken);
        if (StringUtils.isBlank(userCode)) {
            return ResultDTO.error("无效秘钥.");
        }
        UserPO userPO = userReponsitory.findByUserCode(userCode);
        try {
            JWTUtils.verify(accessToken, userCode, userPO.getPassword());
        } catch (Exception ex) {
            return ResultDTO.error("刷新失败.");
        }
        return ResultDTO.success(JWTUtils.sign(userCode, userPO.getId(), userPO.getPassword(), EXPIRE_TIME));
    }

    /**
     * 令牌校验
     *
     * @param accessToken
     * @return
     */
    public ResultDTO verify(String accessToken) {
        String userCode = JWTUtils.getUsername(accessToken);
        UserPO userPO = userReponsitory.findByUserCode(userCode);
        try {
            JWTUtils.verify(accessToken, userCode, userPO.getPassword());
        } catch (Exception ex) {
            return ResultDTO.error("无效令牌..");
        }
        return ResultDTO.success();
    }

    public ResultDTO<Long> getUserId(String accessToken) {
        try {
            Long userId = JWTUtils.getUserId(accessToken);
            return ResultDTO.success(userId);
        } catch (Exception ex) {
            return ResultDTO.error();
        }
    }
}
