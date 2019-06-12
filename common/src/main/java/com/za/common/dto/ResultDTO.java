package com.za.common.dto;

import com.za.common.constant.Constant;

import java.io.Serializable;

public class ResultDTO<T> implements Serializable {

    private static final long serialVersionUID = 1234567L;

    private int code;
    private String specificCode;
    private String msg;

    private transient T data;

    public static final int SUCCESS_CODE = Constant.Result.SUCESS;
    public static final int ERROR_CODE = Constant.Result.ERRO;
    public static final int EXCEPTION_CODE = Constant.Result.EXCEPTION;

    public final int getCode() {
        return this.code;
    }

    public final void setCode(int var1) {
        this.code = var1;
    }

    public final boolean getSuccess() {
        return this.code == SUCCESS_CODE;
    }

    public final String getSpecificCode() {
        return this.specificCode;
    }

    public final void setSpecificCode(String var1) {
        this.specificCode = var1;
    }

    public final String getMsg() {
        return this.msg;
    }

    public final void setMsg(String var1) {
        this.msg = var1;
    }

    public final T getData() {
        return this.data;
    }

    public final void setData(T var1) {
        this.data = var1;
    }

    public static <T> ResultDTO<T> success(String msg, T data) {
        ResultDTO<T> result = new ResultDTO<>();
        result.setCode(SUCCESS_CODE);
        result.setData(data);
        if (msg == null) {
            msg = "执行成功";
        }

        result.setMsg(msg);
        return result;
    }

    public static <T> ResultDTO<T> success(String msg) {
        return ResultDTO.success(msg, null);
    }

    public static <T> ResultDTO<T> success(T data) {
        return ResultDTO.success(null, data);
    }

    public static ResultDTO success() {
        return ResultDTO.success(null, null);
    }

    public static <T> ResultDTO<T> exception(String msg) {
        ResultDTO<T> result = new ResultDTO<>();
        result.setCode(EXCEPTION_CODE);
        result.setMsg(msg == null ? "执行失败！" : msg);
        return result;
    }

    public static ResultDTO error(String msg) {
        ResultDTO result = new ResultDTO<>();
        result.setCode(ERROR_CODE);
        result.setMsg(msg == null ? "执行失败！" : msg);
        return result;
    }

    public static ResultDTO error(String msg, Object... args) {
        return error(String.format(msg, args));
    }

    public static ResultDTO error() {
        return ResultDTO.error("");
    }

    public void setResult(ResultDTO<T> result) {
        this.msg = result.getMsg();
        this.code = result.getCode();
        this.data = result.getData();
    }

}

