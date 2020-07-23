/*
 * This project belogns to SuperJones
 * Copyright © - All rights reserved 2020
 */
package stantardjavaswing;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Methods {

    //screen-based font size
    static final int xxsmallFont = (int) Math.hypot(Main.WIDTH, Main.HEIGHT) / 120;
    static final int xsmallFont = (int) Math.hypot(Main.WIDTH, Main.HEIGHT) / 80;
    static final int smallFont = (int) Math.hypot(Main.WIDTH, Main.HEIGHT) / 60;
    static final int normalFont = (int) Math.hypot(Main.WIDTH, Main.HEIGHT) / 50;
    static final int bigFont = (int) Math.hypot(Main.WIDTH, Main.HEIGHT) / 40;
    static final int xbigFont = (int) Math.hypot(Main.WIDTH, Main.HEIGHT) / 30;

    //add
    static void add(Window window, JComponent... comp) {
        for (JComponent c : comp) {
            window.add(c);
            //font(normalFont, 0, c);
        }
    }

    static void add(JComponent window, JComponent... comp) {
        for (JComponent c : comp) {
            window.add(c);
            //font(normalFont, 0, c);
        }
    }

    //font
    static void font(int s, int o, JComponent... comp) {
        for (JComponent c : comp) {
            c.setFont(new Font(c.getFont().toString(), o, s));
        }
    }

    //border
    static void border(JComponent... comp) {
        for (JComponent c : comp) {
            c.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }
    }

    //visible
    static void visible(boolean v, JComponent... comp) {
        for (JComponent c : comp) {
            c.setVisible(v);
        }
    }

    //position
    static void pos(double x, double y, JComponent... comp) {
        for (JComponent c : comp) {
            c.setLocation(relX(x, c) - c.getWidth() / 2, relY(y, c) - c.getHeight() / 2);
        }
    }

    static void setX(double x, JComponent... comp) {
        for (JComponent c : comp) {
            c.setLocation(relX(x, c) - c.getWidth() / 2, c.getY());
        }
    }

    static void setY(double y, JComponent... comp) {
        for (JComponent c : comp) {
            c.setLocation(c.getX(), relY(y, c) - c.getHeight() / 2);
        }
    }

    //size
    static void size(double width, double height, JComponent... comp) {
        for (JComponent c : comp) {
            c.setSize(relX(width, c), relY(height, c));
        }
    }

    static void setWidth(double width, JComponent... comp) {
        for (JComponent c : comp) {
            c.setSize(relX(width, c), c.getHeight());
        }
    }

    static void setHeight(double height, JComponent... comp) {
        for (JComponent c : comp) {
            c.setSize(c.getWidth(), relY(height, c));
        }
    }

    //sequencial setters
    static void setObjsY(double start, double gap, JComponent... comps) {
        for (int i = 0; i < comps.length; i++) {
            JComponent c = comps[i];
            c.setLocation(c.getX(), (relY(gap, c) * i) + relY(start, c) - c.getHeight() / 2);
        }
    }

    static void setObjsX(double gap, double start, JComponent... comps) {
        for (int i = 0; i < comps.length; i++) {
            JComponent c = comps[i];
            c.setLocation((relX(gap, c) * i) + relX(start, c) - c.getWidth() / 2, c.getY());
        }
    }

    //arrays
    static void addInArray(JComponent objectClass, JComponent[] comp, int index) {
        try {
            for (int i = 0; i < index; i++) {
                Class classObj = objectClass.getClass();
                JComponent obj = (JComponent) classObj.newInstance();
                comp[i] = obj;
            }
        } catch (IllegalAccessException | InstantiationException e) {
        }

    }

    static JComponent[] joinArrays(JComponent[] array1, JComponent[] array2) {
        JComponent[] finalArray = new JComponent[array1.length + array2.length];
        System.arraycopy(array1, 0, finalArray, 0, array1.length);
        System.arraycopy(array2, 0, finalArray, array1.length, array2.length);
        return finalArray;
    }

    static JComponent[] toArray(JComponent... comps) {
        JComponent[] JArray = new JComponent[comps.length];
        System.arraycopy(comps, 0, JArray, 0, comps.length);
        return JArray;
    }

    static Object[] listToArray(ArrayList<Object> comps) {
        Object[] array = new Object[comps.size()];
        for (int i = 0; i < comps.size(); i++) {
            array[i] = comps.get(i);
        }
        return array;
    }

    static Object formatArray(Object[] comps, boolean comma, boolean qm, boolean line) {
        String txt = "", cm = "", q = "", l = "";
        if (comma) {
            cm = ", ";
        }
        if (qm) {
            q = "'";
        }
        if (line) {
            l = "\n";
        }
        for (int i = 0; i < comps.length; i++) {
            Object c = comps[i];
            txt += i != comps.length - 1 ? q + c + q + cm + l : q + c + q;
        }
        return txt;
    }

    //others
    static int toInt(Double x) {
        return x.intValue();
    }

    static int toInt(String x) {
        return Integer.parseInt(x);
    }

    static int relX(double pc, JComponent comp) {
        int width = Main.WIDTH;
        try {
            if (comp.getParent().getWidth() != 0) {
                width = comp.getParent().getWidth();
            }
        } catch (Exception err) {
        }
        return toInt((double)(width * pc));
    }

    static int relY(double pc, JComponent comp) {
        int height = Main.HEIGHT;
        try {
            if (comp.getParent().getWidth() != 0) {
                height = comp.getParent().getHeight();
            }
        } catch (Exception err) {
        }
        return toInt((double)(height * pc));
    }

    static void deb(Object o) {
        System.out.println(o);
    }

    static public Color randomColor() {
        Random rand = new Random();
        int r = rand.nextInt(255);
        int g = rand.nextInt(255);
        int b = rand.nextInt(255);
        Color color = new Color(r, g, b);
        return color;
    }
}
