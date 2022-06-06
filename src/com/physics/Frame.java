package com.physics;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Frame extends JFrame {
    boolean highlightMouse = true;
    Color highlightMouseColor = new Color(10,200,10);

    static Dimension defaultSize = new Dimension(500,500);
    static Color defaultBackground = Color.WHITE;

    public Frame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setSize(new Dimension(this.tree.width,this.tree.height));
        pack();
        setVisible(true);
    }



}
