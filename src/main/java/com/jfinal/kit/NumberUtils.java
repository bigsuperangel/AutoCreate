package com.jfinal.kit;

/**
 * @des
 * @auther linyu
 * @create 2017-02-13:16:41
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.math.BigDecimal;

public class NumberUtils {
    private NumberUtils() {
    }

    public static int parseInt(Object obj) {
        int value = 0;
        if(obj != null) {
            try {
                value = Integer.parseInt(obj.toString());
            } catch (Exception var3) {
                value = 0;
            }
        }

        return value;
    }

    public static BigDecimal parseBigDecimal(Object obj) {
        BigDecimal value = BigDecimal.ZERO;
        if(obj != null) {
            try {
                value = new BigDecimal(obj.toString());
            } catch (Exception var3) {
                value = BigDecimal.ZERO;
            }
        }

        return value;
    }

    public static int parseInt(String str) {
        return parseInt(str, 0);
    }

    public static int parseInt(String str, int defaultValue) {
        try {
            defaultValue = Integer.parseInt(str);
        } catch (Exception var3) {
            ;
        }

        return defaultValue;
    }

    public static double parseDbl(String str) {
        return parseDbl(str, 0.0D);
    }

    public static double parseDbl(String str, double defaultValue) {
        try {
            defaultValue = Double.parseDouble(str);
        } catch (Exception var4) {
            ;
        }

        return defaultValue;
    }

    public static float parseFloat(String str) {
        return parseFloat(str, 0.0F);
    }

    public static float parseFloat(String str, float b) {
        try {
            return Float.parseFloat(str);
        } catch (Exception var3) {
            return b;
        }
    }

    public static long parseLong(String str) {
        return parseLong(str, 0L);
    }

    public static long parseLong(String str, long defaultValue) {
        try {
            defaultValue = Long.parseLong(str);
        } catch (Exception var4) {
            ;
        }

        return defaultValue;
    }
}

