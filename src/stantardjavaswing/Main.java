/*
 * This project belogns to SuperJones
 * Copyright © - All rights reserved 2020
 */

package stantardjavaswing;

//@author SuperJones2018

import javax.swing.*;

public class Main{
    static Window window;
    static int WIDTH = 1200;
    static int HEIGHT = 900;
    
    public static void main(String[] args){
        //Runnable run = () -> {
            window = new Window("A*", WIDTH, HEIGHT, false, true);
            //code here
            new PathFinderUI(window);
            window.setWindowVisible(true);
        //};
        //SwingUtilities.invokeLater(run);
        
    }
}
