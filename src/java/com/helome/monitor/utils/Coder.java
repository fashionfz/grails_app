package com.helome.monitor.utils;

import org.apache.commons.codec.binary.Base64;

/**
 * User: bin.liu
 * Date: 14-1-16
 * Time: 下午1:53
 * To change this template use File | Settings | File Templates.
 */
public class Coder {
    /**
     * Base64解码
     * @param key
     * @return
     */
    public static byte[] decryptBASE64(String key){
        return Base64.decodeBase64(key);
    }

    /**
     * Base64编码
     * @param sign
     * @return
     */
    public static String encryptBASE64(byte[] sign){
        return Base64.encodeBase64String(sign);
    }
}
