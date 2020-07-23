/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stantardjavaswing;

import java.awt.Dimension;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.Toolkit;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author SuperJones2018
 */
public class Window {

    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    private int maxWidth = screen.width;
    private int maxHeight = screen.height;
    String title;
    private JFrame jframe = new JFrame();

    public Window(String title, int width, int height, boolean visible, boolean max) {
        this.title = title;
        if (max) {
            Main.WIDTH = width = maxWidth;
            Main.HEIGHT = height = maxHeight;
            jframe.setExtendedState(MAXIMIZED_BOTH);
        }
        jframe.setTitle(title);
        jframe.setSize(width, height);
        jframe.setLocationRelativeTo(null);
        jframe.setLayout(null);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if (visible) {
            jframe.setVisible(true);
        }
    }

    void add(JComponent o) {
        jframe.add((JComponent) o);
    }

    void setWindowVisible(boolean r) {
        jframe.setVisible(r);
    }
}
