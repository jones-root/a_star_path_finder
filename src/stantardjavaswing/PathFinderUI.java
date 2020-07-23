/*
 * This project belogns to SuperJones
 * Copyright © - All rights reserved 2020
 */
package stantardjavaswing;

//@author SuperJones2018
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.*;

public class PathFinderUI extends Methods {

    static public int fps = 60;
    private final JPanel mainPanel = new JPanel();
    private final JButton startBtn = new JButton("Start");
    private final JButton resetBtn = new JButton("Reset path");
    private final JButton stepBtn = new JButton("Execute frame");
    private final JSlider fpsSlider = new JSlider(JSlider.HORIZONTAL, 1, 999, 60);
    private final JLabel fpsLbl = new JLabel("FPS: " + fpsSlider.getValue(), SwingConstants.CENTER);
    int initialRows = 20;
    int initialColumns = 25;
    private final JSlider gridRowsSlider = new JSlider(JSlider.HORIZONTAL, 6, 300, initialRows);
    private final JSlider gridColumnsSlider = new JSlider(JSlider.HORIZONTAL, 6, 300, initialColumns);
    private final JLabel rowsLbl = new JLabel("GRID ROWS: " + gridRowsSlider.getValue(), SwingConstants.CENTER);
    private final JLabel columnsLbl = new JLabel("GRID COLUMNS: " + gridColumnsSlider.getValue(), SwingConstants.CENTER);
    private final JButton setGridSizeBtn = new JButton("Set new grid size");
    private final JCheckBox showCosts = new JCheckBox("Show Costs");
    private final Text imgPathTxt = new Text("Paste image path here");
    private final JButton imgPathBtn = new JButton("Set image grid");
    private final JButton resetAllBtn = new JButton("RESET ALL");
    private final ButtonGroup bg = new ButtonGroup();
    private final JRadioButton euclidianRadio = new JRadioButton("Euclidian Distance");
    private final JRadioButton manhattanRadio = new JRadioButton("Manhattan Distance");
    private final JRadioButton gridRadio = new JRadioButton("Grid Distance");
    private final JRadioButton radios[] = {euclidianRadio, manhattanRadio, gridRadio};
    private final JButton performPathBtn = new JButton("Perform Path");

    JComponent[] objs = {startBtn, performPathBtn, stepBtn, resetBtn, fpsLbl, fpsSlider, rowsLbl,
        gridRowsSlider, columnsLbl, gridColumnsSlider, setGridSizeBtn,
        showCosts, euclidianRadio, manhattanRadio, gridRadio, imgPathTxt, imgPathBtn, resetAllBtn};

    public PathFinderUI(Window window) {
        mainPanel.setLayout(null);
        add(window, mainPanel);
        add(mainPanel, objs);
        size(1, 1, mainPanel);
        size(.14, .05, objs);
        setX(.075, objs);
        setObjsY(.05, .05, objs);
        font(xxsmallFont, 0, objs);
        resetAllBtn.setBackground(Color.pink);
        resetBtn.setBackground(new Color(100, 255, 100));

        fpsSlider.addChangeListener((e) -> {
            fpsLbl.setText("FPS: " + fpsSlider.getValue());
            fps = fpsSlider.getValue();
        });
        gridRowsSlider.addChangeListener((e) -> {
            rowsLbl.setText("GRID ROWS: " + gridRowsSlider.getValue());
        });
        gridColumnsSlider.addChangeListener((e) -> {
            columnsLbl.setText("GRID COLUMNS: " + gridColumnsSlider.getValue());
        });

        AStar gg = new AStar(mainPanel, new Node(), .15, 0, .85, .95, 0, 0, initialRows, initialColumns);

        performPathBtn.addActionListener(e -> {
            gg.performPath();
        });
        
        euclidianRadio.doClick();
        Arrays.asList(radios).forEach(r -> {
            bg.add(r);
            r.addActionListener(e -> {
                gg.calculateType = Arrays.asList(radios).indexOf(r);
                deb(Arrays.asList(radios).indexOf(r));
            });
        });

        startBtn.addActionListener(e -> {
            if (!gg.pathing) {
                fps = fpsSlider.getValue();
                gg.startThreadPathing();
            }
        });

        imgPathBtn.addActionListener(e -> {
            if (showCosts.isSelected()) {
                showCosts.doClick();
            }
            gg.imgBlockNodes(getImagesBlocks(new File(imgPathTxt.getText())), initialRows, initialColumns);
            gridRowsSlider.setValue(initialRows);
            gridColumnsSlider.setValue(initialColumns);
        });

        resetBtn.addActionListener(e -> {
            if (showCosts.isSelected()) {
                showCosts.doClick();
            }
            gg.reset();
        });

        resetAllBtn.addActionListener(e -> {
            if (showCosts.isSelected()) {
                showCosts.doClick();
            }
            gg.resetAll();
        });

        stepBtn.addActionListener(e -> {
            gg.exeFrame();
            if (showCosts.isSelected()) {
                gg.updateData(1);
            }
        });

        setGridSizeBtn.addActionListener(e -> {
            if (showCosts.isSelected()) {
                showCosts.doClick();
            }
            gg.resizeGrid(gridRowsSlider.getValue(), gridColumnsSlider.getValue());
        });

        showCosts.addActionListener(e -> {
            if (showCosts.isSelected()) {
                gg.updateData(1);
            } else {
                gg.updateData(0);
            }
        });

    }

    private ArrayList<int[]> getImagesBlocks(File file) {
        BufferedImage maze = null;
        ArrayList<int[]> blocksPos = new ArrayList<int[]>();
        try {
            maze = ImageIO.read(file);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "<html>Image NOT found / make sure you added the <h3>NAME</h3> and the <h3>EXTENSION</h3></html>");
        }
        int mazeW = maze.getWidth();
        int mazeH = maze.getHeight();
        initialRows = mazeW;
        initialColumns = mazeH;
        for (int x = 0; x < mazeW; x++) {
            for (int y = 0; y < mazeH; y++) {
                Color pixelColor = new Color(maze.getRGB(x, y), true);
                if (pixelColor.equals(Color.black)) {
                    blocksPos.add(new int[]{x, y});
                }
            }
        }
        return blocksPos;
    }

    public static double format(double n) {
        DecimalFormat df = new DecimalFormat("#.0");
        String h = df.format(n);
        return Double.parseDouble(h.replace(',', '.'));
    }

}
