package com.za.common.utils;

import java.security.MessageDigest;

public class PasswordUtils {

    private PasswordUtils() {
    }

    /**
     * MD5方法
     *
     * @param text 明文
     * @param salt 密钥
     * @return 密文
     * @throws Exception
     */
    public static String encrypt(String text, String salt) {
        try {
            String dataStr = text + salt;
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(dataStr.getBytes("UTF8"));
            byte[] s = m.digest();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < s.length; i++) {
                stringBuilder.append(Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6));
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * MD5验证方法
     *
     * @param text 明文
     * @param key  密钥
     * @param md5  密文
     * @return true/false
     * @throws Exception
     */
    public static boolean verify(String text, String key, String md5) {
        //根据传入的密钥进行验证
        String md5Text = encrypt(text, key);
        return md5Text.equalsIgnoreCase(md5);
    }

}
