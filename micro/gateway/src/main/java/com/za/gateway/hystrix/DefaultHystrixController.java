package com.za.gateway.hystrix;

import com.za.common.dto.ResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 默认降级处理
 */
@RestController
@Slf4j
public class DefaultHystrixController {

    @RequestMapping("/defaultfallback")
    public ResultDTO defaultfallback(){
        log.debug("降级处理...");
        return ResultDTO.exception("服务请求超时，请稍后重试...");
    }
}