package com.jackmanwu.zhaocha.util;

/**
 * Created by JackManWu on 2018/1/14.
 */
public class ColorUtil {
    public static int getRed(int pixel) {
        return offset(pixel >> 16);
    }

    public static int getGreen(int pixel) {
        return offset(pixel >> 8);
    }

    public static int getBlue(int pixel) {
        return offset(pixel);
    }

    private static int offset(int value) {
        return value & 0xff;
    }

    public static int getPixel(int red, int green, int blue) {
        return (offset(red) << 16) + (offset(green) << 8) + offset(blue);
    }

    public static boolean isSameRGB(int pixel1, int pixel2, int offset) {
        int redDiff = Math.abs(getRed(pixel1) - getRed(pixel2));
        int greenDiff = Math.abs(getGreen(pixel1) - getGreen(pixel2));
        int blueDiff = Math.abs(getBlue(pixel1) - getBlue(pixel2));
        int totalDiff = redDiff + greenDiff + blueDiff;
        if (totalDiff > offset) {
            return false;
        }
        return true;
    }

    public static boolean isSameRGB(int pixel, int red, int green, int blue, int offset) {
        int redDiff = Math.abs(getRed(pixel) - red);
        int greenDiff = Math.abs(getGreen(pixel) - green);
        int blueDiff = Math.abs(getBlue(pixel) - blue);
        int totalDiff = redDiff + greenDiff + blueDiff;
        if (totalDiff > offset) {
            return false;
        }
        return true;
    }
}
