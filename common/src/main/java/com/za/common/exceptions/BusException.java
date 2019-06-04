package com.za.common.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BusException extends RuntimeException {

    protected String code;

    protected String showInfo;

    public BusException(String code, String showInfo) {
        this.code = code;
        this.showInfo = showInfo;
    }

}