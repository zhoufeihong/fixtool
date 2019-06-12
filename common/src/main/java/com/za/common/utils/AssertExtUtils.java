package com.za.common.utils;

import com.za.common.exceptions.BusErroException;
import org.springframework.lang.Nullable;

public class AssertExtUtils {

    private AssertExtUtils() {
    }

    private static final String NOT_EMPTY_MSG = "参数'%s'不能为空.";

    /**
     * @param object
     * @param argumentName
     */
    public static void notEmpty(@Nullable Object object, String argumentName) {
        if (object == null) {
            String messageTemp = String.format(NOT_EMPTY_MSG, argumentName);
            throw new BusErroException("0", messageTemp, new IllegalArgumentException(messageTemp));
        }
    }

    /**
     * @param object
     * @param message
     */
    public static void checkNotNull(@Nullable Object object, String message) {
        if (object == null) {
            throw new BusErroException("0", message, new NullPointerException(message));
        }
    }
}
