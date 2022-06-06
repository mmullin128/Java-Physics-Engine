package com.physics;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

class ContentComponent extends JComponent {
    QuadTree tree;
    public ContentComponent(){}
    public ContentComponent(QuadTree tree) {
        this.tree = tree;
        this.setPreferredSize(new Dimension((int)this.tree.width,(int)this.tree.height));
    }

    public void setTreeVisible(boolean bool) {
        this.tree.setTreeVisible(bool);
    }
    public void setTreeColor(Color color) {
        this.tree.setColor((color));
    }
    public void setTree(QuadTree tree) {
        this.tree = tree;
    }
    @Override
    public void paintComponent(Graphics g) {
        if (isVisible()) {
            this.tree.draw(g);
        }
    }




}