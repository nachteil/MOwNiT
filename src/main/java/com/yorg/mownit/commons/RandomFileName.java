package com.yorg.mownit.commons;

import org.apache.commons.lang.RandomStringUtils;

public class RandomFileName {

    private static String chars = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890";

    public static String getRandomFileName(String prefix, String extension) {

        String longRandomString = RandomStringUtils.random(15, chars);
        String randomFileName = prefix.concat("-").concat(longRandomString).concat(".").concat(extension);
        return  randomFileName;
    }
}
