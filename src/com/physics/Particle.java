package com.physics;
import java.awt.*;
import java.sql.Array;

public class Particle {
    Position pos;
    Velocity vel;
    Color color;
    int radius;
    int id;
    double interactionRadius = 10;
    double rotation = 0;

    static Color defaultColor = Color.RED;
    static int num = 0;


    public Particle(Particle p) {
        this.pos = new Position(p.pos);
        this.vel = new Velocity(p.vel);
        this.radius = p.radius;
        this.color = defaultColor;
        this.id = num;
        num++;
    }

    public Particle(Position pos, Velocity vel, int radius) {
        this.pos = pos;
        this.vel = vel;
        this.radius = radius;
        this.color = defaultColor;
        this.id = num;
        num++;
    }
    public Particle(Position pos, Velocity vel, int radius, Color color) {
        this.pos = pos;
        this.vel = vel;
        this.radius = radius;
        this.color = color;
    }
    public double getX() {
        return this.pos.x;
    }
    public double getY() {
        return this.pos.y;
    }
    public Position getPos() {
        return this.pos;
    }
    public Velocity getVel() {
        return this.vel;
    }
    public int getRadius() {
        return this.radius;
    }

    public void draw(Graphics g) {
        g.setColor(this.color);
        g.drawArc((int) this.pos.x-this.radius,(int) this.pos.y-this.radius,this.radius,this.radius,0,360);
    }

    public void integrate() {
        this.pos.x += this.vel.x;
        this.pos.y += this.vel.y;
    }
    public static boolean colliding(Particle p1, Particle p2) {
        return (Math.sqrt(Math.pow(p1.pos.x-p2.pos.x,2) + Math.pow(p1.pos.y-p2.pos.y,2)) <= p1.radius + p2.radius);
    }
    public void interact(Particle p) {
        double[] R = new double[] {p.pos.x - this.pos.x, p.pos.y - this.pos.y};
        double d = Math.hypot(R[0],R[1]);
        double[] V = new double[] {R[0] / d , R[1] / d};
        double F = (2*Environment.G)/d;
        this.vel.x += V[0]*F;
        this.vel.y += V[1]*F;
    }
}
