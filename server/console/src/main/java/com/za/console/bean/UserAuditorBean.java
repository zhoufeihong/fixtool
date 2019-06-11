package com.za.console.bean;

import com.za.common.dto.ResultDTO;
import com.za.console.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Configuration
public class UserAuditorBean implements AuditorAware<String> {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserService userService;

    @Override
    public Optional<String> getCurrentAuditor() {

        SecurityContext ctx = SecurityContextHolder.getContext();
        if (ctx == null) {
            return returnUserId();
        }
        if (ctx.getAuthentication() == null) {
            return returnUserId();
        }
        if (ctx.getAuthentication().getPrincipal() == null) {
            return returnUserId();
        }
        Object principal = ctx.getAuthentication().getPrincipal();
        if (principal.getClass().isAssignableFrom(Long.class)) {
            return Optional.of(principal.toString());
        } else {
            return returnUserId();
        }
    }

    private Optional<String> returnUserId() {
        if (request != null) {
            String token = request.getHeader("Authorization");
            if (StringUtils.isNotBlank(token)) {
                ResultDTO<Long> result = userService.getUserId(token);
                if (result.getCode() == ResultDTO.SUCCESS_CODE) {
                    return Optional.of(result.getData().toString());
                }
            }
        }
        return Optional.of("emptyUser");
    }
}