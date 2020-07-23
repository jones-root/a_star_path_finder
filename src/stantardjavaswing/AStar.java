/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stantardjavaswing;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SuperJones2018
 */
public class AStar extends Methods {

    private Thread startPath;
    private int compWidth, compHeight;
    private JComponent component = null;
    private double objWidth;
    private double objHeight;
    private boolean setSize = true;
    private Node nodes[][];
    private int rows, columns;
    private final ArrayList<Node> openNodes = new ArrayList<>();
    private final ArrayList<Node> closedNodes = new ArrayList<>();
    private Node curNode;
    private Node startNode;
    private Node finalNode;
    private final ArrayList<Node> blocks = new ArrayList<>();
    private double xPos, yPos, gridWidth, gridHeight, xGap, yGap;
    public boolean pathing = false;
    ArrayList<int[]> blocksImgPos;
    public int calculateType = 0;
    private long startTime;
    private long endTime;
    private boolean showSearch = false;
    private boolean noPath = false;

    public AStar(JComponent window, JComponent object, double xPos, double yPos, double gridWidth, double gridHeight,
            double xGap, double yGap, int rows, int columns) {
        component = window;
        compWidth = window.getWidth();
        compHeight = window.getHeight();
        this.rows = rows;
        this.columns = columns;
        nodes = new Node[rows][columns];
        this.xPos = xPos;
        this.yPos = yPos;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.xGap = xGap;
        this.yGap = yGap;
        generateGrid();
        startPath = new Thread(run);
    }

    private Runnable run = new Runnable() {
        @Override
        public void run() {
            pathing = true;
            try {
                calculateCosts(startNode, calculateType);
                while (!curNode.equals(finalNode) && !noPath) {
                    startPath();
                    Thread.sleep(1000 / PathFinderUI.fps);
                }
                if(!noPath)
                paintFinalPath();
            } catch (InterruptedException ex) {
                Logger.getLogger(AStar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    private void paintFinalPath() {
        while (!curNode.equals(startNode) && !noPath) {
            ArrayList<Node> closedPossibleNeighbors = getNeighbors(curNode, true);
            for (Node nb : closedPossibleNeighbors) {
                if (nb.equals(curNode.getParentNode())) {
                    curNode.setBackground(Color.cyan);
                    curNode = nb;
                }
            }
        }
        finalNode.setBackground(Color.magenta);
    }

    public void reset() {
        startPath.interrupt();
        pathing = false;
        noPath = false;
        Node.setClicks(0);
        openNodes.forEach(n -> n.setNodeType(1));
        closedNodes.forEach(n -> n.setNodeType(1));
        if (startNode != null) {
            startNode.setNodeType(1);
        }
        if (finalNode != null) {
            finalNode.setNodeType(1);
        }
        curNode = startNode = finalNode = null;
        blocks.clear();
        openNodes.clear();
        closedNodes.clear();
        startPath = new Thread(run);
    }

    public void resetAll() {
        reset();
        clearScreen();
        paintBorder();
    }

    public void startThreadPathing() {
        if (mainNodes()) {
            showSearch = true;
            startPath.start();
        }
    }

    private boolean mainNodes() {
        return startNode != null && finalNode != null;
    }

    public void exeFrame() {
        if (mainNodes()) {
            showSearch = true;
            startPath();
        }
    }

    public void performPath() {
        if (mainNodes()) {
            showSearch = false;
            calculateCosts(startNode, calculateType);
            startTime = System.currentTimeMillis();
            while (!curNode.equals(finalNode) && !noPath) {
                startPath();
            }
            if (!noPath) {
                endTime = System.currentTimeMillis();
                String mes = "Time elapsed: " + (double) (endTime - startTime) / 1000 + " seconds";
                paintFinalPath();
                JOptionPane.showMessageDialog(null, mes);
            }
        }
    }

    public void resizeGrid(int rows, int columns) {
        resetAll();
        blocks.clear();
        Component[] componentList = component.getComponents();
        for (Component c : componentList) {
            if (c instanceof Node) {
                component.remove(c);
            }
        }
        component.revalidate();
        component.repaint();
        nodes = new Node[rows][columns];
        this.rows = rows;
        this.columns = columns;
        setSize = true;
        generateGrid();
    }

    private void generateGrid() {
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                try {
                    Class className = new Node().getClass();
                    Node obj = (Node) className.newInstance();
                    nodes[x][y] = obj;
                    Node currentNode = nodes[x][y];
                    currentNode.setPos(x, y);
                    currentNode.setNodeType(1);
                    currentNode.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            if (Node.isLeftMousePressed()) {
                                selectNode(currentNode);
                            } else if (Node.isRightMousePressed()) {
                                eraseNode(currentNode);
                            }
                        }

                        @Override
                        public void mousePressed(MouseEvent e) {
                            if (Node.getClicks() == 0 && e.getButton() == MouseEvent.BUTTON1) {
                                if (!currentNode.isBlock()) {
                                    startNode = curNode = currentNode;
                                    startNode.setNodeType(0);
                                    Node.updateClick();
                                }
                            } else if (Node.getClicks() == 1 && e.getButton() == MouseEvent.BUTTON1) {
                                if (!currentNode.isBlock()) {
                                    finalNode = currentNode;
                                    finalNode.setNodeType(3);
                                    Node.updateClick();
                                }
                            } else if (e.getButton() == MouseEvent.BUTTON1) {
                                selectNode(currentNode);
                                Node.setLeftMousePressed(true);
                            } else if (e.getButton() == MouseEvent.BUTTON3) {
                                eraseNode(currentNode);
                                Node.setRightMousePressed(true);
                            }
                        }

                        @Override
                        public void mouseReleased(MouseEvent e) {
                            if (e.getButton() == MouseEvent.BUTTON1) {
                                Node.setLeftMousePressed(false);
                            } else if (e.getButton() == MouseEvent.BUTTON3) {
                                Node.setRightMousePressed(false);
                            }
                        }
                    });
                    border(currentNode);
                    add(component, obj);
                    if (setSize) {
                        objWidth = (double) (relX(gridWidth, obj) / columns) / compWidth;
                        objHeight = (double) (relY(gridHeight, obj) / rows) / compHeight;
                        setSize = false;
                    }
                    size(objWidth - (xGap * 2), objHeight - (yGap * 2), obj);
                    pos((xPos + objWidth / 2) + objWidth * y, (yPos + objHeight / 2) + objHeight * x, obj);
                } catch (IllegalAccessException | InstantiationException ex) {
                }
            }
        }
        paintBorder();
    }

    private void clearScreen() {
        openNodes.forEach(n -> n.showData(0));
        closedNodes.forEach(n -> n.showData(0));
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                nodes[x][y].setNodeType(1);
            }
        }
    }

    public void imgBlockNodes(ArrayList<int[]> blockPos, int rows, int columns) {
        resizeGrid(columns, rows);
        for (int i = 0; i < blockPos.size(); i++) {
            int[] pos = blockPos.get(i);
            int x = pos[0];
            int y = pos[1];
            Node node = nodes[y][x];
            node.setNodeType(2);
            node.setCanUserEdit(true);
            blocks.add(node);
        }
    }

    private void paintBorder() {
        for (int i = 0; i < columns; i++) {
            Node node = nodes[0][i];
            Node node1 = nodes[rows - 1][i];
            node.setNodeType(2);
            node1.setNodeType(2);
            blocks.add(node);
            blocks.add(node1);
        }
        for (int i = 0; i < rows; i++) {
            Node node = nodes[i][0];
            Node node1 = nodes[i][columns - 1];
            node.setNodeType(2);
            node1.setNodeType(2);
            blocks.add(node);
            blocks.add(node1);
        }
    }

    private void selectNode(Node curNode) {
        if (curNode.isCanUserEdit()) {
            blocks.add(curNode);
            curNode.setNodeType(2);
            curNode.setCanUserEdit(true);
        }
    }

    private void eraseNode(Node curNode) {
        if (curNode.isCanUserEdit()) {
            blocks.remove(curNode);
            curNode.setNodeType(1);
        }
    }

    private void startPath() {
        ArrayList<Node> neighbors = getNeighbors(curNode, false);
        closedNodes.add(curNode);
        openNodes.remove(curNode);
        for (Node nb : neighbors) {
            calculateCosts(nb, calculateType);
            if (!openNodes.contains(nb)) {
                openNodes.add(nb);
                if (showSearch) {
                    nb.setBackground(Color.yellow);
                }
            }
        }
        //next node
        if (openNodes.size() != 0) {
            curNode = smallestFCostNode();
            ArrayList<Node> possibleParents = new ArrayList<>();
            ArrayList<Node> neighborsCur = getNeighbors(curNode, true);
            for (Node nb : neighborsCur) {
                if (closedNodes.contains(nb)) {
                    possibleParents.add(nb);
                }
            }
            Node pn = smallestGCostNode(possibleParents);
            curNode.setParentNode(pn);
            if (showSearch) {
                curNode.setBackground(Color.red);
            }
            startNode.setBackground(Color.blue);
        } else {
            JOptionPane.showMessageDialog(null, "No path");
            noPath = true;
        }
    }

    public void updateData(int s) {
        openNodes.forEach(n -> n.showData(s));
        closedNodes.forEach(n -> n.showData(s));
    }

    private ArrayList<Node> findDiagonal(Node node) {
        int x = node.getXPos();
        int y = node.getYPos();
        int[][] cornerNodes = {{x + 1, y - 1}, {x - 1, y + 1}, {x + 1, y + 1}, {x - 1, y - 1}};
        int[][] possibleDiagonal = {{x, y - 1}, {x - 1, y}, {x + 1, y}, {x, y - 1}};
        int[][] possibleDiagonalV = {{x + 1, y}, {x, y + 1}, {x, y + 1}, {x - 1, y}};
        ArrayList<Node> denyNodes = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Node dNode = nodes[possibleDiagonal[i][0]][possibleDiagonal[i][1]];
            Node dNodeV = nodes[possibleDiagonalV[i][0]][possibleDiagonalV[i][1]];
            if (dNode.isBlock() && dNodeV.isBlock()) {
                denyNodes.add(nodes[cornerNodes[i][0]][cornerNodes[i][1]]);
            }
        }
        return denyNodes;
    }

    private Node smallestFCostNode() {
        ArrayList<Double> onFCostList = new ArrayList<>();
        openNodes.forEach(on -> {
            onFCostList.add(on.getfCost());
        });
        double onFCost = Collections.min(onFCostList);
        ArrayList<Integer> nodesIndex = containsTwice(onFCostList, onFCost);
        if (nodesIndex.size() > 1) {
            ArrayList<Node> nodeList = new ArrayList<>();
            nodesIndex.forEach((i) -> {
                nodeList.add(openNodes.get(i));
            });
            return smallestHCostNode(nodeList);
        }
        int index = onFCostList.indexOf(onFCost);
        return openNodes.get(index);
    }

    private Node smallestHCostNode(ArrayList<Node> nodes) {
        ArrayList<Double> hCostList = new ArrayList<>();
        nodes.forEach((n) -> {
            hCostList.add(n.gethCost());
        });
        int index = hCostList.indexOf(Collections.min(hCostList));
        return nodes.get(index);
    }

    private ArrayList<Integer> containsTwice(ArrayList<Double> list, Object value) {
        ArrayList<Integer> nodesIndex = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(value)) {
                nodesIndex.add(i);
            }
        }
        return nodesIndex;
    }

    private ArrayList<Node> getNeighbors(Node node, boolean getClosed) {
        ArrayList<Node> neighbors = new ArrayList<>();
        int x = node.getXPos();
        int y = node.getYPos();
        int[][] neighborsPos = {{x + 1, y}, {x + 1, y + 1}, {x, y + 1}, {x - 1, y + 1}, {x - 1, y}, {x - 1, y - 1}, {x, y - 1}, {x + 1, y - 1}};
        for (int[] pos : neighborsPos) {
            Node curNeighbor = nodes[pos[0]][pos[1]];
            boolean can = getClosed ? closedNodes.contains(curNeighbor) : !closedNodes.contains(curNeighbor);
            if (!curNeighbor.isBlock() && can) {
                neighbors.add(curNeighbor);
            }
        }
        ArrayList<Node> denyNodes = findDiagonal(node);
        if (denyNodes.size() > 0) {
            denyNodes.forEach(n -> {
                neighbors.remove(n);
            });
        }
        return neighbors;
    }

    private Node smallestGCostNode(ArrayList<Node> nodes) {
        ArrayList<Double> gCostList = new ArrayList<>();
        nodes.forEach((n) -> {
            gCostList.add(n.getgCost());
        });
        int index = gCostList.indexOf(Collections.min(gCostList));
        return nodes.get(index);
    }

    private void calculateCosts(Node node, int calcType) {
        int distFromFinalX = Math.abs(finalNode.getXPos() - node.getXPos());
        int distFromFinalY = Math.abs(finalNode.getYPos() - node.getYPos());
        int distFromStartX = Math.abs(startNode.getXPos() - node.getXPos());
        int distFromStartY = Math.abs(startNode.getYPos() - node.getYPos());
        double h, g, f;
        h = g = f = 0;
        if (calcType == 0) {
            //enclidian-based
            h = Math.hypot(distFromFinalX, distFromFinalY);
            g = Math.hypot(distFromStartX, distFromStartY);
        } else if (calcType == 1) {
            //manhattan-based
            h = distFromFinalX + distFromFinalY;
            g = distFromStartX + distFromStartY;
        } else if (calcType == 2) {
            //calc based on diagonals(sqrt(2) = 1.4) and nodes(1)
            int difH = Math.abs(distFromFinalX - distFromFinalY);
            double diagonalH = distFromFinalY - distFromFinalX > 0 ? (distFromFinalY - difH) * 1.4 : (distFromFinalX - difH) * 1.4;
            int difG = Math.abs(distFromStartX - distFromStartY);
            double diagonalG = distFromStartY - distFromStartX > 0 ? (distFromStartY - difG) * 1.4 : (distFromStartX - difG) * 1.4;
            h = difH + diagonalH;
            g = difG + diagonalG;
        }
        f = h + g;
        node.setCosts(h, g, f);
    }
}