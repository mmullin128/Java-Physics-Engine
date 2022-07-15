package com.physics;

import java.util.ArrayList;

public class Bond {
    public Particle p1;
    public int p1BondIndex;
    public double p1BondAngle;
    public Bond bond;
    public boolean bound;
    public int forceIndex;
    public int boundIndex;
    public double CovalentRotationForce = .01;
    public  double CovalentTranslationForce = .01;
    public double bondRadius;
    public double L;
    public double dampingFactor;


    public static double defaultBondRadius = 30;
    public static double defaultCovalentRotationForce = .1;
    public static double defaultCovalentTranslationForce = .01;
    public static double defaultDampingFactor = .1;
    public static ArrayList<Bond> BOUND = new ArrayList<Bond>();



    public Bond(Particle p1, int p1BondIndex, double p1BondAngle) {
        this.p1 = p1;
        this.p1BondIndex = p1BondIndex;
        this.p1BondAngle = p1BondAngle;
        this.bound = false;
        this.bond = null;
        this.bondRadius = defaultBondRadius;
    }
    public void join(Bond bond) {
        this.bond = bond;
        this.bound = true;
        this.bond.bound = true;
        ArrayList<Particle> particles = new ArrayList<Particle>();
        particles.add(this.p1);
        particles.add(this.bond.p1);
        this.forceIndex = Force.add(new CovalentForce(particles));
        this.boundIndex = BOUND.size();
    }
    public void split() {
        this.bond.bound = false;
        this.bond = null;
        this.bound = false;
        //Force.remove(this.forceIndex);
    }

    public static void updateAll() {
    }

}
