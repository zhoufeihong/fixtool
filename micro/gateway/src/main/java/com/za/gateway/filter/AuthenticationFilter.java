package com.za.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.za.common.constant.Constant;
import com.za.common.dto.ResultDTO;
import com.za.gateway.service.IUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;

@Log4j2
@Component
public class AuthenticationFilter {

    @Autowired
    private IUserService userService;

    private static final String MAX_AGE = "18000L";

    @Bean
    public GlobalFilter authFilter() {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            //不需要拦截的请求，直接跳过
            if (!this.needFilter(request.getPath().pathWithinApplication().value())) {
                return chain.filter(exchange);
            }

            List<String> accessTokens = request.getHeaders().get("Authorization");
            if (null == accessTokens || accessTokens.isEmpty()) {
                return response401(response, HttpStatus.NON_AUTHORITATIVE_INFORMATION, "没有访问权限！");
            } else {
                try {
                    ResultDTO resultDTO = userService.getUserInfo(accessTokens.get(0));
                    if (!resultDTO.getSuccess()) {
                        return response401(response, HttpStatus.NON_AUTHORITATIVE_INFORMATION, "权限已经失效，请重新登录！");
                    }
                    LinkedHashMap linkedHashMap = (LinkedHashMap) resultDTO.getData();
                    ServerHttpRequest host = exchange
                            .getRequest()
                            .mutate()
                            .header("userId", linkedHashMap.get("id").toString())
                            .header("userName", linkedHashMap.get("userCode").toString())
                            .build();
                    exchange = exchange.mutate().request(host).build();
                } catch (Exception e) {
                    log.error(e);
                    return response401(response, HttpStatus.UNAUTHORIZED, "权限已经失效，请重新登录！");
                }
            }
            return chain.filter(exchange);
        };
    }

    private boolean needFilter(String path) {
        if (path.endsWith("/user/getToken") || path.endsWith("/user/refreshToken")) {
            return false;
        }
        return true;
    }

    private Mono<Void> response401(ServerHttpResponse response, HttpStatus status, String msg) {
        response.setStatusCode(status);
        JSONObject message = new JSONObject();
        message.put("code", 0);
        message.put("specificCode", Constant.Authorization.SpecificCode.ILLEGAL_TOKEN);
        message.put("msg", msg);
        DataBuffer buffer = response.bufferFactory().wrap(message.toString().getBytes(StandardCharsets.UTF_8));

        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }

}