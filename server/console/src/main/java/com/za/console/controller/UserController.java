package com.za.console.controller;

import com.za.common.dto.ResultDTO;
import com.za.console.controller.vo.GetTokenParam;
import com.za.console.service.UserService;
import com.za.console.service.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.framework.dto.PageRequestDTO;
import za.framework.dto.PageResultDTO;

import java.util.List;

@Api(tags = "用户功能")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @ApiOperation(value = "添加用户信息", notes = "添加用户信息")
    @PostMapping()
    public ResultDTO add(@RequestBody UserDTO user) {
        return userService.addUser(user);
    }

    @ApiOperation(value = "获取用户信息", notes = "通过id获取用户信息")
    @GetMapping("/{id}")
    public ResultDTO<UserDTO> get(@PathVariable("id") long id) {
        return userService.getUser(id);
    }

    @ApiOperation(value = "查询用户信息")
    @GetMapping("/search")
    public PageResultDTO<List<UserDTO>> search(String userName, Integer page, Integer limit, String sort) {
        return userService.listUser(userName, PageRequestDTO.ofOperation(page, limit, sort));
    }

    @ApiOperation(value = "更新用户信息")
    @PutMapping("/{id}")
    public ResultDTO update(@PathVariable Long id, @RequestBody UserDTO userDto) {
        return userService.updateUser(userDto);
    }

    @ApiOperation(value = "更新角色信息")
    @PutMapping("/updateRole")
    public ResultDTO updateRole(@RequestBody UserDTO user) {
        return userService.updateRole(user);
    }

    @ApiOperation(value = "修改密码")
    @PutMapping("/updatePassword")
    public ResultDTO updatePassword(@RequestBody UserDTO userDto) {
        return userService.updatePassword(userDto);
    }

    @ApiOperation(value = "获取Token")
    @PostMapping("/getToken")
    public ResultDTO getToken(@RequestBody GetTokenParam getTokenParam) {
        return userService.getToken(getTokenParam.getUserCode(), getTokenParam.getPassword());
    }

    @ApiOperation(value = "获取用户信息")
    @ApiImplicitParam(name = "token", value = "token信息", required = true, dataType = "String", paramType = "query")
    @GetMapping("/getUserInfo")
    public ResultDTO getUserInfo(@RequestParam("accessToken") String accessToken) {
        return userService.getUserInfo(accessToken);
    }

    @ApiOperation(value = "刷新Token")
    @PostMapping("/refreshToken")
    public ResultDTO refreshToken(@RequestParam("accessToken") String accessToken) {
        return userService.refreshToken(accessToken);
    }

    @ApiOperation(value = "验证秘钥")
    @GetMapping("/verify")
    public ResultDTO verify(@RequestParam("accessToken") String accessToken) {
        return userService.verify(accessToken);
    }

    @ApiOperation(value = "退出登录")
    @GetMapping("/logout")
    public ResultDTO logout() {
        return ResultDTO.success();
    }

}
