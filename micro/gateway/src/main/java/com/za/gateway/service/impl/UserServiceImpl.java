package com.za.gateway.service.impl;

import com.za.common.dto.ResultDTO;
import com.za.gateway.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class UserServiceImpl implements IUserService {

    @Override
    public ResultDTO getUser(@RequestParam("id") long id) {
        return ResultDTO.error("获取用户信息失败，请稍后重试。");
    }

    @Override
    public ResultDTO getUserInfo(String accessToken) {
        return ResultDTO.error("获取用户信息失败，请稍后重试.");
    }

}