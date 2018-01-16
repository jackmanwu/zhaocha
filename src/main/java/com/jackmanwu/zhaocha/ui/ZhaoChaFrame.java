package com.jackmanwu.zhaocha.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created by JackManWu on 2018/1/16.
 */
public class ZhaoChaFrame extends JFrame implements WindowListener {
    @Override
    public void windowOpened(WindowEvent e) {
        System.out.println("hello");
    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("closing");
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {
        System.out.println(SystemTray.getSystemTray());
        System.out.println(SystemTray.isSupported());
        if (!SystemTray.isSupported()) {
        }
    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {
        System.out.println("active");
    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
