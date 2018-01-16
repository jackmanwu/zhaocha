package com.jackmanwu.zhaocha.ui;

import com.jackmanwu.zhaocha.ZhaoChaServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by JackManWu on 2018/1/15.
 */
public class ImageJPanel extends JPanel {
    private int dx;
    private int dy;
    private int width;
    private int height;
    private JFrame jFrame;

    public ImageJPanel(int dx, int dy, int width, int height, JFrame jFrame) {
        this.dx = dx;
        this.dy = dy;
        this.width = width;
        this.height = height;
        this.jFrame = jFrame;
        this.addMouseListener(mouseListener());
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(ZhaoChaServer.image, 0, 0, width, height, null);
    }

    private MouseListener mouseListener() {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    Point point = e.getPoint();
                    jFrame.setVisible(false);
                    try {
                        Robot robot = new Robot();
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
