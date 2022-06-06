package com.physics;

import java.awt.*;
import java.util.ArrayList;

public class QuadTree {
    QuadTreeNode root;
    double x;
    double y;
    double width;
    double height;
    int divideThreshold;
    boolean treeIsVisible;
    boolean contentIsVisible;
    Color color;
    Color backgroundColor = new Color(255,255,255);

    static Color defaultColor = Color.BLACK;

    public QuadTree(){}

    public QuadTree(double x, double y, double w, double h, int divideThreshold) {
        this.width = w;
        this.height = h;
        this.x = x;
        this.y = y;
        this.root = new QuadTreeNode(x,y,w,h,divideThreshold);
        this.divideThreshold = divideThreshold;
        this.contentIsVisible = true;
        this.treeIsVisible = false;
        this.color = defaultColor;
    }

    public QuadTree(QuadTree tree) {
        this.width = tree.width;
        this.height = tree.height;
        this.x = tree.x;
        this.y = tree.y;
        this.divideThreshold = tree.divideThreshold;
        this.contentIsVisible = tree.contentIsVisible;
        this.treeIsVisible = tree.treeIsVisible;
        this.color = tree.color;
        this.root = new QuadTreeNode(tree.root);
    }
    public QuadTree(double x, double y, double width, double height, int divideThreshold, ArrayList<Particle> particles) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.divideThreshold = divideThreshold;
        this.root = new QuadTreeNode(x,y,width,height,divideThreshold, particles);
        this.treeIsVisible = false;
        this.contentIsVisible = true;
        this.color = defaultColor;
    }


    public void setTreeVisible(boolean bool) {
        this.treeIsVisible = bool;
    }
    public void setContentVisible(boolean bool) {
        this.contentIsVisible = bool;
    }
    public void setColor(Color c) {
        this.color = c;
    }
    public void add(Particle p) {
        QuadTreeNode.add(this.root,p);
    }
    public void addAll(ArrayList<Particle> arrList) {
        for (Particle p:arrList) {
            QuadTreeNode.add(this.root,p);
        }
    }

    public void draw(Graphics g) {
        g.setColor(this.backgroundColor);
        g.fillRect((int) this.x, (int) this.y, (int) this.width, (int) this.height);
        this.root.draw(this.contentIsVisible,this.treeIsVisible,g);
    }

    public QuadTree next() {
        QuadTree newTree = new QuadTree(this.x,this.y,this.width,this.height,this.divideThreshold);
        newTree.setColor(QuadTree.defaultColor);
        ArrayList<Particle> newParticles = new ArrayList<Particle>();
        QuadTreeNode.compute(newParticles,this.root);
        newTree.addAll(newParticles);
        return newTree;
    }
}
