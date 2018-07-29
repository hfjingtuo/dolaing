package com.dolaing.pay.client.utils;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * @Author:张立华
 * @Description:
 * @Date：Created in 9:43 2018/5/11
 * @Modified By:
 */
public class IdUtil {
    private static final char[] BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    private static SecureRandom random = new SecureRandom();


    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 使用SecureRandom随机生成Long.
     */
    public static long randomLong() {
        return Math.abs(random.nextLong());
    }

    /**
     * 基于Base62编码的SecureRandom随机生成bytes.
     */
    public static String randomBase62(int length) {
        byte[] randomBytes = new byte[length];
        random.nextBytes(randomBytes);
        char[] chars = new char[randomBytes.length];
        for (int i = 0; i < randomBytes.length; i++) {
            chars[i] = BASE62[((randomBytes[i] & 0xFF) % BASE62.length)];
        }
        return new String(chars);
    }
}
