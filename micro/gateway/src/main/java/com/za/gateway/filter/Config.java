package com.za.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.za.common.dto.ResultDTO;
import com.za.gateway.service.IUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import static org.springframework.web.cors.CorsConfiguration.ALL;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.za.common.constant.*;

@Log4j2
@Component
public class Config {

    @Autowired
    private IUserService userService;
    @Autowired
    private CacheManager cacheManager;

    private static final String MAX_AGE = "18000L";

    @Bean
    public GlobalFilter authFilter() {
        return (exchange, chain) -> {
            log.info("before权限验证拦截器...");
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            if (!this.needFilter(request.getPath().pathWithinApplication().value())) {
                return chain.filter(exchange);
            }

            List<String> accessTokens = request.getHeaders().get("Authorization");
            if (null == accessTokens || accessTokens.isEmpty()) {
                return response401(response, HttpStatus.NON_AUTHORITATIVE_INFORMATION, "没有访问权限！");
            } else {
                try {
                    ResultDTO resultDTO = userService.getUserInfo(accessTokens.get(0));
                    if(resultDTO.getCode() != ResultDTO.SUCCESS)
                    {
                        return response401(response, HttpStatus.NON_AUTHORITATIVE_INFORMATION, "权限已经失效，请重新登录！");
                    }
                    LinkedHashMap linkedHashMap = (LinkedHashMap) resultDTO.getData();
                    ServerHttpRequest host = exchange
                            .getRequest()
                            .mutate()
                            .header("userId", linkedHashMap.get("id").toString())
                            .header("userName", linkedHashMap.get("userName").toString())
                            .build();
                    exchange = exchange.mutate().request(host).build();
                } catch (Exception e) {
                    log.error(e);
                    return response401(response, HttpStatus.UNAUTHORIZED, "权限已经失效，请重新登录！");
                }
            }
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("拦截之后处理");
            }));
        };
    }

    @Bean
    public WebFilter corsFilter() {
        return (ServerWebExchange ctx, WebFilterChain chain) -> {
            ServerHttpRequest request = ctx.getRequest();
            if (!CorsUtils.isCorsRequest(request)) {
                return chain.filter(ctx);
            }

            HttpHeaders requestHeaders = request.getHeaders();
            ServerHttpResponse response = ctx.getResponse();
            HttpMethod requestMethod = requestHeaders.getAccessControlRequestMethod();
            HttpHeaders headers = response.getHeaders();
            headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, requestHeaders.getOrigin());
            headers.addAll(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, requestHeaders.getAccessControlRequestHeaders());
            if (requestMethod != null) {
                headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, requestMethod.name());
            }
            headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
            headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, ALL);
            headers.add(HttpHeaders.ACCESS_CONTROL_MAX_AGE, MAX_AGE);
            if (request.getMethod() == HttpMethod.OPTIONS) {
                response.setStatusCode(HttpStatus.OK);
                return Mono.empty();
            }
            return chain.filter(ctx);
        };
    }

    private boolean needFilter(String path) {
        if (path.equals("/user/getToken") || path.equals("/user/refreshToken")) {
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