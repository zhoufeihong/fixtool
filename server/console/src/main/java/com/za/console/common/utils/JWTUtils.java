package com.za.console.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class JWTUtils {

    private JWTUtils() {
    }

    // 过期时间8分钟
    private static final long EXPIRE_TIME = 8 * 60 * 1000L;

    private static final String USER_NAME = "user_name";

    private static final String USER_ID = "user_id";

    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static void verify(String token, String username, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim(USER_NAME, username)
                    .build();
            verifier.verify(token);
        } catch (InvalidClaimException exception) {
            throw new InvalidClaimException("非法的Token值！");
        } catch (TokenExpiredException exception) {
            throw new TokenExpiredException("Token已过期！");
        } catch (Exception exception) {
            throw new JWTVerificationException("Token验证发生异常！");
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(USER_NAME).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static Long getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(USER_ID).asLong();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名
     *
     * @param userName 用户名
     * @param secret   用户的密码
     * @return 加密的token
     */
    public static TokenResponse sign(String userName, Long userId, String secret, Long expireTime) {
        try {
            if (null == expireTime) {
                expireTime = EXPIRE_TIME;
            }
            Date date = new Date(System.currentTimeMillis() + expireTime);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 附带username信息
            String accessToken = JWT.create()
                    .withClaim(USER_NAME, userName)
                    .withClaim(USER_ID, userId)
                    .withExpiresAt(date)
                    .sign(algorithm);
            return new TokenResponse(accessToken, date.getTime());
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * 生成签名,默认超时时间5min
     *
     * @param userName
     * @param secret
     * @return
     */
    public static TokenResponse sign(String userName, Long userId, String secret) {
        return sign(userName, userId, secret, null);
    }

    @Data
    public static class TokenResponse {
        private String accessToken;
        private long expireTime;

        public TokenResponse(String accessToken, long expireTime) {
            this.accessToken = accessToken;
            this.expireTime = expireTime;
        }
    }

}