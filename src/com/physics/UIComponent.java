package com.physics;

import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

public class UIComponent extends ContentComponent {
    boolean cursorVisible;
    Color cursorColor  = new Color(80,240,80);
    public UIComponent(QuadTree tree) {
        this.tree = tree;
        this.tree.setTreeVisible(true);
        this.tree.setContentVisible(false);
        this.cursorVisible = true;
        setBackground(new Color(0,0,0,0));
        this.setPreferredSize(new Dimension((int)this.tree.width,(int)this.tree.height));
    }
    public void addCursor() {
        this.addMouseListener(new MouseListener());
    }
    public void paintComponent(Graphics g) {
        QuadTreeNode node = MouseListener.selected;
        if (node  == null) return;
        g.setColor(this.cursorColor);
        g.drawRect((int)node.x,(int)node.y,(int)node.width,(int)node.height);
        repaint();
    }
    class MouseListener extends MouseInputAdapter {
        public static QuadTreeNode selected;
        public void mouseClicked(MouseEvent e) {
            int radius = 5;
            tree.add(new Particle(new Position(e.getX(),e.getY()), new Velocity(0,0), radius));
        }
        @Override
        public void mouseMoved(MouseEvent e) {
            //super.mouseMoved(e);
            QuadTreeNode node = QuadTreeNode.find(tree.root,new Position(e.getX(),e.getY()));
            if (selected != null) {
                if (node.id != selected.id) {
                    selected.setColor(QuadTreeNode.defaultColor);
                    selected.setTreeVisible(false);
                    node.setColor(cursorColor);
                    node.setTreeVisible(true);
                    selected = node;
                    int OFFSET = 3;
                    //repaint((int)selected.x-OFFSET,(int)selected.y-OFFSET,(int)selected.width+OFFSET,(int)selected.height+OFFSET);
                    repaint();
                }
            } else {
                node.setColor(cursorColor);
                node.setTreeVisible(true);
                selected = node;
                int OFFSET = 3;
                //repaint((int)selected.x-OFFSET,(int)selected.y-OFFSET,(int)selected.width+OFFSET,(int)selected.height+OFFSET);
                repaint();
            }
            /*
            ArrayList<QuadTreeNode> neighbors = new ArrayList<QuadTreeNode>();
            neighbors.addAll(QuadTreeNode.getNeighbors(selected));
            for (QuadTreeNode n:neighbors) {
                n.setColor(new Color(200,20,20));
                n.setVisible(true);
            }
            repaint();
             */

        }
    }
}
