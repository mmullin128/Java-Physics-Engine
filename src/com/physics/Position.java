package com.physics;

import java.util.Random;

class Position {
    double x;
    double y;
    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Position(Position p) {
        this.x = p.x;
        this.y = p.y;
    }
    public static Position random(double lowBound, double highBound) {
        Random r = new Random();
        double x = r.nextDouble(lowBound,highBound);
        double y = r.nextDouble(lowBound,highBound);
        return new Position(x,y);
    }
}