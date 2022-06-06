package com.physics;

import java.awt.*;
import java.util.ArrayList;

public class Main extends Thread {

    public static void main(String[] args) {
        Environment env = new Environment(700, 700);
        env.populateRandom(100);
        env.ParticleTree.setTreeVisible(true);
        while (true) {
            try {
                Thread.sleep(5);
                env.next();
                env.repaint();
            } catch(Exception e){
                e.printStackTrace();
                break;
            }
        }
    }
}
