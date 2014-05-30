package com.helome.monitor

import java.security.MessageDigest

/**
 * Created with IntelliJ IDEA.
 * User: helome
 * Date: 13-12-26
 * Time: 上午11:24
 * To change this template use File | Settings | File Templates.
 */
class MD5 {
    static def generateMD5(String s) {
        MessageDigest digest = MessageDigest.getInstance("MD5")
        digest.update(s.bytes);
        new BigInteger(1, digest.digest()).toString(16).padLeft(32, '0')
    }
}
