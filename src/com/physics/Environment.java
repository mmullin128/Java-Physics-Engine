package com.physics;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Environment extends Thread {
    QuadTree ParticleTree;
    ContentComponent ParticleComponent;
    UIComponent uiComponent;

    JFrame frame;
    double width;
    double height;

    public static double G = .01;

    static int defaultDivideThreshold = 3;
    public Environment(int w, int h) {
        this.width = w;
        this.height = h;
        this.ParticleTree = new QuadTree(0,0,w,h,defaultDivideThreshold);
        this.ParticleComponent = new ContentComponent(this.ParticleTree);
        this.ParticleComponent.setBackground(new Color(200,0,0));
        this.ParticleComponent.setOpaque(true);
        this.uiComponent = new UIComponent(this.ParticleTree);
        this.uiComponent.setBackground(new Color(0,100,0,0));
        this.uiComponent.setOpaque(false);
        this.frame = new JFrame();
        this.frame.getContentPane().add(this.ParticleComponent,0);
        //this.frame.getContentPane().add(this.uiComponent, 1);
        //this.frame.getContentPane().add(uiComponent);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.pack();
        this.frame.setVisible(true);
    }
    public Environment(double w, double h, ArrayList<Particle> particles) {
        this.width = w;
        this.height = h;
        this.ParticleTree = new QuadTree(0,0,w,h,defaultDivideThreshold,particles);

    }
    public void next() {
        this.ParticleTree = this.ParticleTree.next();
        this.ParticleComponent.setTree(this.ParticleTree);
    }
    public void repaint() {
        this.ParticleComponent.repaint();

    }
    public void addParticle(Particle p) {
        this.ParticleTree.add(p);
    }

    public void populateRandom(int n) {
        Random generator = new Random();
        double posx;
        double posy;
        int radius = 10;
        for (int i=0; i<n; i++) {
            posx = generator.nextDouble(this.ParticleTree.width);
            posy = generator.nextDouble(this.ParticleTree.height);
            this.ParticleTree.add(new Particle(new Position(posx,posy),new Velocity(0,0),radius));
        }
    }
}
