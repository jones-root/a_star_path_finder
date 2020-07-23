/*
 * This project belogns to SuperJones
 * Copyright © - All rights reserved 2020
 */
package stantardjavaswing;

//@author SuperJones2018
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextField;

public class Text extends JTextField {

    private Object placeHolder = "";
    private boolean blockChar;
    private boolean blockInteger;
    private boolean blockNumber;
    private int maxLength = 9999;

    public Text(String pc) {
        placeHolder = pc;
        setText(placeHolder + "");
        setForeground(Color.gray);
        setEvents();
    }

    public Text() {
        setText(placeHolder + "");
        setForeground(Color.gray);
        setEvents();
    }

    void setEvents() {
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                int dots = getText().length() - getText().replace(".", "").length();
                char c = e.getKeyChar();
                if (getText().length() >= maxLength || dots > 0 && c == '.' && blockChar) {
                    e.consume();
                }
                if (blockInteger) {
                    if (Character.isDigit(c) || c == '.' && blockChar) {
                        e.consume();
                    }
                }
                if (blockNumber) {
                    if (c == '.' || getText().length() < 1 && c == '.') {
                        e.consume();
                    }
                }
                if (blockChar && !Character.isDigit(c)) {
                    if (blockNumber && c != '.' || !blockNumber && c != '.') {
                        e.consume();
                    }
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        this.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                desactive();
            }

            @Override
            public void focusLost(FocusEvent e) {
                active();
            }
        });
    }

    void setPlaceHolder(Object h) {
        placeHolder = h;
        active();
    }
    
    void clear(){
        setText("");
        setPlaceHolder(placeHolder);
    }
    
    Object getPlaceHolder(){
        return placeHolder;
    }

    void setMaxLength(int m) {
        maxLength = m;
    }

    private void active() {
        if (getText().equals(placeHolder) || getText().equals("")) {
            setForeground(Color.gray);
            setText(placeHolder + "");
        }
    }

    private void desactive() {
        if (getText().equals(placeHolder)) {
            setForeground(Color.black);
            setCaretPosition(0);
            setText("");
        }
    }

    boolean isEmpty() {
        if (getText().equals(placeHolder)) {
            return true;
        }
        return false;
    }

    void blockChar() {
        blockChar = true;
    }

    void blockInteger() {
        blockInteger = true;
    }

    void blockNumber() {
        blockNumber = true;
    }

    static void blockChar(Text... txts) {
        for (Text t : txts) {
            t.blockChar();
        }
    }

    static void blockInteger(Text... txts) {
        for (Text t : txts) {
            t.blockInteger();
        }
    }

    static void blockNumber(Text... txts) {
        for (Text t : txts) {
            t.blockNumber();
        }
    }

    void resetPermissions() {
        blockChar = false;
        blockInteger = false;
        blockNumber = false;
    }
}
