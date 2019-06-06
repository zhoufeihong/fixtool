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

import java.util.List;

@Api(tags = "用户功能")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @ApiOperation(value = "获取用户信息", notes = "通过id获取用户信息")
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "long", example = "1", paramType = "query")
    @GetMapping("/getUser")
    public ResultDTO<UserDTO> getUser(long id) {
        return userService.getUser(id);
    }

    @ApiOperation(value = "查询用户信息")
    @GetMapping("/listUser")
    public ResultDTO<List<UserDTO>> listUser(String userName) {
        return userService.listUser(userName);
    }

    @ApiOperation(value = "更新角色信息")
    @PostMapping("/updateRole")
    public ResultDTO updateRole(@RequestBody UserDTO user) {
        return userService.updateRole(user);
    }

    @ApiOperation(value = "更新用户信息")
    @PostMapping("/updateUser")
    public ResultDTO updateUser(@RequestBody UserDTO userDto) {
        return userService.updateUser(userDto);
    }

    @ApiOperation(value = "修改密码")
    @PostMapping("/updatePassword")
    public ResultDTO updatePassword(@RequestBody UserDTO userDto) {
        return userService.updatePassword(userDto);
    }

    @ApiOperation(value = "获取Token")
    @PostMapping("/getToken")
    public ResultDTO getToken(@RequestBody GetTokenParam getTokenParam) {
        return userService.getToken(getTokenParam.getUserName(), getTokenParam.getPassword());
    }

    @ApiOperation(value = "获取用户信息")
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
