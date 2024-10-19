/*
 * This project belogns to SuperJones
 * Copyright © - All rights reserved 2020
 */
package stantardjavaswing;

//@author SuperJones2018
import java.awt.Color;
import javax.swing.*;

public class Node extends JButton {

		public Node(){
			this.setOpaque(true);
		}

    static public int clicks = 0;
    static public boolean rightMousePressed, leftMousePressed;
    private int x, y, nodeType;
    private double hCost, gCost, fCost;
    private boolean canUserEdit = true;
    private Color[] colorsNodeType = {Color.red, Color.white, Color.black, Color.green};
    private Node parentNode = this;

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setCosts(double h, double g, double f) {
        hCost = h;
        gCost = g;
        fCost = f;
    }

    public void setNodeType(int nodeType) {
        this.nodeType = nodeType;
        canUserEdit = nodeType == 1;
        this.setBackground(colorsNodeType[nodeType]);
    }

    public int getNodeType() {
        //node types ->
        //0 = start node
        //1 = free block
        //2 = block
        //3 = final node
        return nodeType;
    }

    public boolean isBlock() {
        return getNodeType() == 2;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Node other = (Node) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Node{" + "x=" + x + ", y=" + y + ", g=" + gCost + ", h=" + hCost + ", f=" + fCost + '}';
    }

    public void showData(int s) {
        String data = "";
        if (s == 0) {
            data = "";
        }
        if (s == 1) {
            data = "<html>G:" + PathFinderUI.format(gCost) + " <br /> H:" + PathFinderUI.format(hCost) + " <br /> F:" + PathFinderUI.format(fCost) + "</html>";
        }
        setText(data);
    }

    public int getXPos(){
        return x;
    }
    
    public int getYPos(){
        return y;
    }
    
    public static int getClicks() {
        return clicks;
    }

    public static void setClicks(int clicks) {
        Node.clicks = clicks;
    }

    public static boolean isRightMousePressed() {
        return rightMousePressed;
    }

    public static void setRightMousePressed(boolean rightMousePressed) {
        Node.rightMousePressed = rightMousePressed;
    }

    public static boolean isLeftMousePressed() {
        return leftMousePressed;
    }

    public static void setLeftMousePressed(boolean leftMousePressed) {
        Node.leftMousePressed = leftMousePressed;
    }

    public double gethCost() {
        return hCost;
    }

    public double getfCost() {
        return fCost;
    }

    public double getgCost() {
        return gCost;
    }

    public boolean isCanUserEdit() {
        return canUserEdit;
    }

    public void setCanUserEdit(boolean canUserEdit) {
        this.canUserEdit = canUserEdit;
    }

    public Node getParentNode() {
        return parentNode;
    }

    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }
    
    public static void updateClick(){
        clicks++;
    }
}
