package com.za.common.utils;

import java.util.regex.Pattern;

public class RegexMatchingRuleUtils {

    private RegexMatchingRuleUtils() {
    }

    private static final Pattern USER_CODE_PATTERN = Pattern.compile("^([0-9]|[A-Za-z]){4,20}$");

    public static boolean userCode(String val) {
        return USER_CODE_PATTERN.matcher(val).matches();
    }

}
