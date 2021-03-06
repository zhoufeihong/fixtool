package com.za.common.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BusErroException extends RuntimeException {

    protected final String code;

    protected String showInfo;

    public BusErroException(Throwable throwable)
    {
        super(throwable);
        this.code = "0";
    }

    public BusErroException(String code, String showInfo)
    {
        this.code = code;
        this.showInfo = showInfo;
    }

    public BusErroException(String code, String showInfo, String message)
    {
        super(message);
        this.code = code;
        this.showInfo = showInfo;
    }

    public BusErroException(String code, Throwable throwable)
    {
        super(throwable);
        this.code = code;
    }

    public BusErroException(String code, String showInfo, Throwable throwable)
    {
        super(throwable);
        this.code = code;
        this.showInfo = showInfo;
    }

}
