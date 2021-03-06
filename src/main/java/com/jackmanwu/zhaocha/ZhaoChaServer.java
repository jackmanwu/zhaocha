package com.jackmanwu.zhaocha;

import com.jackmanwu.zhaocha.ui.ImageJPanel;
import com.jackmanwu.zhaocha.ui.TempImageJPanel;
import com.jackmanwu.zhaocha.util.ColorUtil;
import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by JackManWu on 2018/1/14.
 */
public class ZhaoChaServer {
    private static final String path = System.getProperty("user.dir") + File.separator;

    private static final int width = 381;

    private static final int height = 286;

    private static final int dx = 457;

    private static final int diff = 128;

    private static int x;

    private static int y;

    private static JFrame imageJFrame;

    public static BufferedImage image;

    public static void main(String[] args) throws Exception {
        JFrame jFrame = new JFrame("大家来找茬辅助");
        jFrame.setBounds(1150, 384, 100, 100);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLayout(new BorderLayout());

        //热键刷新图片
        JIntellitype.getInstance().registerHotKey(0, JIntellitype.MOD_ALT, 'Q');
        JIntellitype.getInstance().registerHotKey(1, JIntellitype.MOD_CONTROL + JIntellitype.MOD_SHIFT, 'F');
        JIntellitype.getInstance().registerHotKey(2, JIntellitype.MOD_CONTROL + JIntellitype.MOD_SHIFT, 'D');
        JIntellitype.getInstance().registerHotKey(3, JIntellitype.MOD_CONTROL + JIntellitype.MOD_SHIFT, 'C');
        JIntellitype.getInstance().addHotKeyListener(new HotkeyListener() {
            @Override
            public void onHotKey(int i) {
                switch (i) {
                    case 1:
                        flushImage();
                        break;
                    case 2:
                        showImage();
                        break;
                    case 3:

                        break;
                    default:
                        JIntellitype.getInstance().cleanUp();
                        System.exit(0);
                        break;
                }
            }
        });

        JPanel jPanel = new JPanel();
        jPanel.setBackground(Color.GRAY);

        JButton jButton = new JButton("刷新");
        jButton.addMouseListener(mouseClick());
        jPanel.setLayout(new BorderLayout());
        jPanel.add(jButton, BorderLayout.CENTER);

        JButton tempImageButton = new JButton("大图");
        tempImageButton.addMouseListener(tempImageClick());
        jPanel.add(tempImageButton, BorderLayout.EAST);

        jFrame.add(jPanel, BorderLayout.CENTER);
        jFrame.setAlwaysOnTop(true);
        jFrame.setVisible(true);
    }

    private static MouseListener tempImageClick() {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    showImage();
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

    private static void showImage() {
        BufferedImage tempImage;
        try {
            tempImage = getImage(path + "temp.png");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("获取图片失败");
            return;
        }
        JFrame tempImageJFrame = new JFrame("大图");
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        tempImageJFrame.setBounds((dimension.width - 762) / 2, (dimension.height - 572) / 2, 762, 572);
        tempImageJFrame.setLayout(new BorderLayout());
        JPanel jPanel = new TempImageJPanel(762, 572, tempImage);
        tempImageJFrame.add(jPanel, BorderLayout.CENTER);
        tempImageJFrame.setVisible(true);
    }

    private static MouseListener mouseClick() {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    flushImage();
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

    private static void flushImage() {
        if (imageJFrame != null) {
            imageJFrame.setVisible(false);
        }
        try {
            zhaocha();
            image = getImage(path + "temp.png");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("临时图片获取失败...");
        }
        if (image == null) {
            System.out.println("临时图片为空...");
            return;
        }
        if (imageJFrame == null) {
            imageJFrame = new JFrame();
            imageJFrame.setBounds(x, y, width, height);
            imageJFrame.setLayout(new BorderLayout());
            imageJFrame.setUndecorated(true);
            JPanel imageJPanel = new ImageJPanel(x, y, width, height, imageJFrame);
            imageJFrame.add(imageJPanel, BorderLayout.CENTER);
        }
        imageJFrame.setState(JFrame.NORMAL);
        imageJFrame.setVisible(true);
    }

    private static BufferedImage getImage(String path) throws Exception {
        return ImageIO.read(new FileInputStream(new File(path)));
    }

    private static void zhaocha() throws Exception {
        Robot robot = new Robot();
        Point mousePoint = MouseInfo.getPointerInfo().getLocation();
        robot.mouseMove(0, 0);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        BufferedImage image = robot.createScreenCapture(new Rectangle(0, 0, dimension.width, dimension.height));
        robot.mouseMove(mousePoint.x, mousePoint.y);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        byte[] bytes = outputStream.toByteArray();
        FileOutputStream fileOutputStream = new FileOutputStream(path + "screen.png");
        fileOutputStream.write(bytes);

        int baseX = 0;
        int baseY = 0;
        for (int i = 0; i < image.getHeight() - 1; i++) {
            for (int j = 0; j < image.getWidth() - 1; j++) {
                //计算基准点
                if (ColorUtil.isSameRGB(image.getRGB(j, i), 32, 52, 88, 0)) {
                    if (baseX == 0 || j < baseX) {
                        baseX = j;
                    }
                    if (i <= baseY) {
                        baseY = i;
                    }
                }
                //查取左顶点
                /*if (j > 0 && i > 0
                        && !ColorUtil.isSameRGB(image.getRGB(j, i), 80, 148, 176, 0)
                        && ColorUtil.isSameRGB(image.getRGB(j - 1, i), 80, 148, 176, 0)
                        && ColorUtil.isSameRGB(image.getRGB(j, i - 1), 80, 148, 176, 0)) {
                    System.out.println("左上角：" + j + "-" + i);
                }*/

                /*if (i > 0
                        && !ColorUtil.isSameRGB(image.getRGB(j, i), 152, 216, 232, 0)
                        && ColorUtil.isSameRGB(image.getRGB(j + 1, i), 152, 216, 232, 0)
                        && ColorUtil.isSameRGB(image.getRGB(j, i - 1), 152, 216, 232, 0)) {
                    System.out.println("右上角：" + j + "-" + i);
                }*/

               /* if (j > 0
                        && !ColorUtil.isSameRGB(image.getRGB(j, i), 112, 144, 176, 0)
                        && ColorUtil.isSameRGB(image.getRGB(j - 1, i), 112, 144, 176, 0)
                        && ColorUtil.isSameRGB(image.getRGB(j, i + 1), 112, 144, 176, 0)) {
                    System.out.println("左下角：" + j + "-" + i);
                }

                if (ColorUtil.isSameRGB(image.getRGB(j, i), 112, 144, 176, 0)) {
                    System.out.println(j + "-" + i);
                }

                if (j > 0 && i > 0
                        && !ColorUtil.isSameRGB(image.getRGB(j, i), 152, 212, 232, 0)
                        && ColorUtil.isSameRGB(image.getRGB(j - 1, i), 152, 212, 232, 0)
                        && ColorUtil.isSameRGB(image.getRGB(j, i - 1), 152, 212, 232, 0)) {
                    System.out.println("左上角：" + j + "-" + i);
                }*/
            }
        }

        int leftX = baseX + 93;
        int leftY = baseY + 312;
        x = leftX;
        y = leftY;

        BufferedImage leftImageByte = image.getSubimage(leftX, leftY, width, height);
        ImageIO.write(leftImageByte, "png", new File(path + "left.png"));
        BufferedImage rightImageByte = image.getSubimage(leftX + dx, leftY, width, height);
        ImageIO.write(rightImageByte, "png", new File(path + "right.png"));

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int pixelA = leftImageByte.getRGB(j, i);
                int pixelB = rightImageByte.getRGB(j, i);
                int r = ColorUtil.getRed(pixelA) - ColorUtil.getRed(pixelB) + diff;
                int g = ColorUtil.getGreen(pixelA) - ColorUtil.getGreen(pixelB) + diff;
                int blue = ColorUtil.getBlue(pixelA) - ColorUtil.getBlue(pixelB) + diff;
                leftImageByte.setRGB(j, i, ColorUtil.getPixel(r, g, blue));
            }
        }
        ImageIO.write(leftImageByte, "png", new File(path + "temp.png"));
    }
}
