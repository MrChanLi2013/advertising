package com.ad.util;

public class Utils {
    public static String uploadPath() {
        return Utils.class.getResource("/static/products").getPath() + "/";
    }

}
