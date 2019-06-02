package com.za.common.constant;

/**
 * 常量
 */
public interface Constant {

    interface Authorization {

        String TOKEN_HEADER_STRING = "Authorization";

        interface SpecificCode {
            //token过期
            Integer TOKEN_VALID = 50000;
            //无效token
            Integer ILLEGAL_TOKEN = 50008;
            //token过期
            Integer TOKEN_EXPIRED = 50009;
        }
    }

    interface Result {
        // 成功
        Integer SUCESS = 1;
        // 失败
        Integer ERRO = 0;
        // 异常
        Integer EXCEPTION = -1;
    }

}
