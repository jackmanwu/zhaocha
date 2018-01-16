package com.jackmanwu.zhaocha.util;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by JackManWu on 2018/1/14.
 */
public class ImgUtil {
    public static BufferedImage getScreenImg() throws AWTException {
        Robot robot = new Robot();
        return robot.createScreenCapture(new Rectangle());
    }
}
