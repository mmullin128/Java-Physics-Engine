package com.physics;

import java.util.Random;

class Velocity {
    double x;
    double y;
    public Velocity(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Velocity(Velocity v) {
        this.x = v.x;
        this.y = v.y;
    }

    public static Velocity random(double lowBound, double highBound) {
        Random r = new Random();
        double x = r.nextDouble(lowBound,highBound);
        double y = r.nextDouble(lowBound,highBound);
        return new Velocity(x,y);
    }
}
