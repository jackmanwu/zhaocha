package com.jackmanwu.zhaocha.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by JackManWu on 2018/1/16.
 */
public class TempImageJPanel extends JPanel {
    private int width;

    private int height;

    private BufferedImage image;

    public TempImageJPanel(int width, int height, BufferedImage image) {
        this.width = width;
        this.height = height;
        this.image = image;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(image, 0, 0, width, height, null);
    }
}
