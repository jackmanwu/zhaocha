package com.jackmanwu.zhaocha.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

/**
 * Created by JackManWu on 2018/1/15.
 */
public class ImageJPanel extends JPanel {
    private int dx;
    private int dy;
    private int width;
    private int height;
    private BufferedImage image;
    private JFrame jFrame;

    public ImageJPanel(int dx, int dy, int width, int height, BufferedImage image, JFrame jFrame) {
        this.dx = dx;
        this.dy = dy;
        System.out.println("差值：" + dx + "," + dy);
        this.width = width;
        this.height = height;
        this.image = image;
        this.jFrame = jFrame;
        this.addMouseListener(mouseListener());
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(image, 0, 0, width, height, null);
    }

    private MouseListener mouseListener() {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    Point point = e.getPoint();
                    System.out.println("点击坐标：" + point);
                    jFrame.setVisible(false);
                    try {
                        Robot robot = new Robot();
                        System.out.println((point.x + dx) + "," + (point.y + dy));
                        robot.mouseMove(point.x + dx, point.y + dy);
                        robot.delay(10);
                        robot.mousePress(InputEvent.BUTTON1_MASK);
                        robot.mouseRelease(InputEvent.BUTTON1_MASK);
                        robot.delay(10);
                    } catch (AWTException e1) {
                        e1.printStackTrace();
                        System.out.println("模拟点击失败...");
                    }
                    jFrame.setVisible(true);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
    }
}
