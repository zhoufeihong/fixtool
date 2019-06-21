package com.za.gateway.service;

import com.za.common.dto.ResultDTO;
import com.za.gateway.service.impl.UserServiceImpl;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "console-server",fallback = UserServiceImpl.class)
@CacheConfig(cacheNames = "user")
public interface IUserService {

    @GetMapping("api/user/{id}")
    @Cacheable(key = "#id")
    ResultDTO getUser(@PathVariable long id);

    @GetMapping("api/user/access_token/user_info")
    @Cacheable(key = "#accessToken")
    ResultDTO getUserInfo(@RequestParam("accessToken") String accessToken);
}

