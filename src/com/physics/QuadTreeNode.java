package com.physics;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class QuadTreeNode {
    double x;
    double y;
    double width;
    double height;
    int divideThreshold;
    boolean divided;
    boolean treeIsVisible = true;
    boolean contentIsVisible = true;
    int id;
    int n;
    Color color;
    Color backgroundColor = new Color(255,255,255);

    ArrayList<Particle> Particles;

    QuadTreeNode Northeast;
    QuadTreeNode Northwest;
    QuadTreeNode Southeast;
    QuadTreeNode Southwest;
    QuadTreeNode Parent;

    static int num = 0;
    static Color defaultColor = new Color(0,0,0);


    public QuadTreeNode(double x, double y, double w, double h, int divideThreshold) {
        this.x = x;
        this.y = y;
        this.color = defaultColor;
        this.height = h;
        this.width = w;
        this.Particles = new ArrayList<Particle>();
        this.divideThreshold = divideThreshold;
        this.divided = false;
        this.id = num;
        num++;
    }
    public QuadTreeNode(QuadTreeNode parent, int n) {
        this.Parent = parent;
        this.n = n;
        this.x = parent.x;
        this.y = parent.y;
        if (n == 0 || n == 3) this.x = parent.x + parent.width/2;
        if (n == 2 || n == 3) this.y = parent.y + parent.height/2;
        this.color = parent.color;
        this.width = parent.width/2;
        this.height = parent.height/2;
        this.Particles = new ArrayList<Particle>();
        this.divideThreshold = parent.divideThreshold;
        this.divided = false;
        this.id = num;
        num++;
    }

    public QuadTreeNode(double x, double y, double w, double h, int divideThreshold, ArrayList<Particle> particles) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.divideThreshold = divideThreshold;
        this.Particles = new ArrayList<Particle>();
        for (Particle p:particles) {
            this.Particles.add(p);
            if (this.Particles.size() >= this.divideThreshold) {
                divide(this);
            }
        }

    }
    /*
    public QuadTreeNode(QuadTreeNode node, QuadTreeNode parent) {
        this(node);
        this.Parent = parent;

    }
     */
    public QuadTreeNode(QuadTreeNode node) {
        this.n = node.n;
        this.x = node.x;
        this.y = node.y;
        this.width = node.width;
        this.height = node.height;
        this.color = node.color;
        this.contentIsVisible = node.contentIsVisible;
        this.treeIsVisible = node.treeIsVisible;
        this.Particles = new ArrayList<Particle>();
        for (Particle p:node.Particles) {
            this.Particles.add(new Particle(p));
        }
        this.divideThreshold = node.divideThreshold;
        this.divided = node.divided;
        this.id = num;
        if (this.divided) {
            this.Northeast = new QuadTreeNode(node.Northeast,0);
            this.Northwest = new QuadTreeNode(node.Northwest,1);
            this.Southeast = new QuadTreeNode(node.Southeast,3);
            this.Southwest = new QuadTreeNode(node.Southwest,2);
        }
        num++;
    }

    public static ArrayList<QuadTreeNode> sortX(QuadTreeNode node, ArrayList<QuadTreeNode> sorted) {
        if (!node.divided) {
            sorted.add(node);
            return sorted;
        }
        sortX(node.Southeast,sorted);
        sortX(node.Northeast,sorted);
        sortX(node.Southwest,sorted);
        sortX(node.Northwest,sorted);
        return sorted;

    }

    public void setTreeVisible(boolean bool) {
        this.treeIsVisible = bool;
        if (this.divided) {
            this.Northeast.setTreeVisible(bool);
            this.Northwest.setTreeVisible(bool);
            this.Southeast.setTreeVisible(bool);
            this.Southwest.setTreeVisible(bool);
        }
    }
    public void setContentVisible(boolean bool) {
        this.contentIsVisible = bool;
        if (this.divided) {
            this.Northeast.setContentVisible(bool);
            this.Northwest.setContentVisible(bool);
            this.Southeast.setContentVisible(bool);
            this.Southwest.setContentVisible(bool);
        }
    }

    public void setColor(Color c) {
        this.color = c;
        if (this.divided) {
            this.Northeast.setColor(c);
            this.Northwest.setColor(c);
            this.Southeast.setColor(c);
            this.Southwest.setColor(c);
        }
    }


    public static QuadTreeNode getExtreme(QuadTreeNode node, int direction) {
        if (!node.divided) {
            return node;
        }
        return getExtreme(node.getChild(direction),direction);
    }
    public static ArrayList<QuadTreeNode> collectSouth(QuadTreeNode node) {
        if (node == null) return new ArrayList<>();
        return collectSouth(node,new ArrayList<QuadTreeNode>());
    }
    public static ArrayList<QuadTreeNode> collectSouth(QuadTreeNode node, ArrayList<QuadTreeNode> arrlist) {
        ArrayList auglist = new ArrayList<QuadTreeNode>(arrlist);
        if (!node.divided) {
            auglist.add(node);
            return auglist;
        }
        auglist.addAll(collectSouth(node.Southeast,arrlist));
        auglist.addAll(collectSouth(node.Southwest,arrlist));
        return auglist;
    }


    public static ArrayList<QuadTreeNode> collectWest(QuadTreeNode node) {
        if (node == null) return new ArrayList<>();
        return collectWest(node,new ArrayList<QuadTreeNode>());
    }
    public static ArrayList<QuadTreeNode> collectWest(QuadTreeNode node, ArrayList<QuadTreeNode> arrlist) {
        ArrayList auglist = new ArrayList<QuadTreeNode>(arrlist);
        if (!node.divided) {
            auglist.add(node);
            return auglist;
        }
        auglist.addAll(collectWest(node.Northwest,arrlist));
        auglist.addAll(collectWest(node.Southwest,arrlist));
        return auglist;
    }


    public static ArrayList<QuadTreeNode> collectNorth(QuadTreeNode node) {
        if (node == null) return new ArrayList<>();
        return collectNorth(node,new ArrayList<QuadTreeNode>());
    }
    public static ArrayList<QuadTreeNode> collectNorth(QuadTreeNode node, ArrayList<QuadTreeNode> arrlist) {
        ArrayList auglist = new ArrayList<QuadTreeNode>(arrlist);
        if (!node.divided) {
            auglist.add(node);
            return auglist;
        }
        auglist.addAll(collectNorth(node.Northwest,arrlist));
        auglist.addAll(collectNorth(node.Northeast,arrlist));
        return auglist;
    }


    public static ArrayList<QuadTreeNode> collectEast(QuadTreeNode node) {
        if (node == null) return new ArrayList<>();
        return collectEast(node,new ArrayList<QuadTreeNode>());
    }
    public static ArrayList<QuadTreeNode> collectEast(QuadTreeNode node, ArrayList<QuadTreeNode> arrlist) {
        ArrayList auglist = new ArrayList<QuadTreeNode>(arrlist);
        if (!node.divided) {
            auglist.add(node);
            return auglist;
        }
        auglist.addAll(collectEast(node.Northeast,arrlist));
        auglist.addAll(collectEast(node.Southeast,arrlist));
        return auglist;
    }


    public static ArrayList<QuadTreeNode> getNeighbors(QuadTreeNode node) {
        ArrayList<QuadTreeNode> neighbors = new ArrayList<QuadTreeNode>();
        ArrayList<QuadTreeNode> northneighbors = getNorthNeighbors(node);
        ArrayList<QuadTreeNode> eastneighbors = getEastNeighbors(node);
        ArrayList<QuadTreeNode> westneighbors = getWestNeighbors(node);
        ArrayList<QuadTreeNode> southneighbors = getSouthNeighbors(node);
        neighbors.addAll(northneighbors);
        neighbors.addAll(eastneighbors);
        neighbors.addAll(westneighbors);
        neighbors.addAll(southneighbors);
        return neighbors;
    }


    public static ArrayList<QuadTreeNode> getNorthNeighbors(QuadTreeNode node) {
        return collectSouth(getNorthNeighbor(node));
    }
    public static QuadTreeNode getNorthNeighbor(QuadTreeNode node) {
        if (node.Parent == null) return null;
        if (node.n == 2) return node.Parent.Northwest;
        if (node.n == 3) return node.Parent.Northeast;
        QuadTreeNode neighbor = getNorthNeighbor(node.Parent);
        if (neighbor == null || !neighbor.divided) return neighbor;
        if (node.n == 1) return neighbor.getChild(2);
        if (node.n == 0) return neighbor.getChild(3);
        return null;
    }


    public static ArrayList<QuadTreeNode> getEastNeighbors(QuadTreeNode node) {
        return collectWest(getEastNeighbor(node));
    }
    public static QuadTreeNode getEastNeighbor(QuadTreeNode node) {
        if (node.Parent == null) return null;
        if (node.n == 1) return node.Parent.Northeast;
        if (node.n == 2) return node.Parent.Southeast;
        QuadTreeNode neighbor = getEastNeighbor(node.Parent);
        if (neighbor == null || !neighbor.divided) return neighbor;
        if (node.n == 0) return neighbor.getChild(1);
        if (node.n == 3) return neighbor.getChild(2);
        return null;
    }


    public static ArrayList<QuadTreeNode> getWestNeighbors(QuadTreeNode node) {
        return collectEast(getWestNeighbor(node));
    }
    public static QuadTreeNode getWestNeighbor(QuadTreeNode node) {
        if (node.Parent == null) return null;
        if (node.n == 0) return node.Parent.Northwest;
        if (node.n == 3) return node.Parent.Southwest;
        QuadTreeNode neighbor = getWestNeighbor(node.Parent);
        if (neighbor == null || !neighbor.divided) return neighbor;
        if (node.n == 1) return neighbor.getChild(0);
        if (node.n == 2) return neighbor.getChild(3);
        return null;
    }


    public static ArrayList<QuadTreeNode> getSouthNeighbors(QuadTreeNode node) {
        return collectNorth(getSouthNeighbor(node));
    }
    public static QuadTreeNode getSouthNeighbor(QuadTreeNode node) {
        if (node.Parent == null) return null;
        if (node.n == 0) return node.Parent.Southeast;
        if (node.n == 1) return node.Parent.Southwest;
        QuadTreeNode neighbor = getSouthNeighbor(node.Parent);
        if (neighbor == null || !neighbor.divided) return neighbor;
        if (node.n == 3) return neighbor.getChild(0);
        if (node.n == 2) return neighbor.getChild(1);
        return null;
    }
    public static QuadTreeNode getWest(ArrayList<QuadTreeNode> arr) {
        QuadTreeNode west = null;
        for (QuadTreeNode node:arr) {
            if (west == null || node.x < west.x) {
                west = node;
            }
        }
        return west;
    }
    public static QuadTreeNode getSouth(ArrayList<QuadTreeNode> arr) {
        QuadTreeNode south = null;
        for (QuadTreeNode node:arr) {
            if (south == null || node.x < south.x) {
                south = node;
            }
        }
        return south;
    }
    public static QuadTreeNode find(QuadTreeNode node, Position pos) {
        if (!node.divided) {
            return node;
        }
        int n = getQuadrant(node,pos);
        if (n == 0) return find(node.Northeast,pos);
        if (n == 1) return find(node.Northwest,pos);
        if (n == 2) return find(node.Southwest,pos);
        if (n == 3) return find(node.Southeast,pos);
        return null;
    }
    public static ArrayList<QuadTreeNode> findNeighbors(QuadTreeNode node, ArrayList<QuadTreeNode> neighbors) {
        return neighbors;
    }

    public static int size(QuadTreeNode node) {
        if (!node.divided) return 1;
        return size(node.Southeast) + size(node.Southwest) + size(node.Northeast) + size(node.Northwest);
    }

    public static ArrayList<Particle> getParticles(QuadTreeNode node,ArrayList<Particle> arrayList) {
        if (!node.divided) {
            arrayList.addAll(node.Particles);
            return arrayList;
        }
        arrayList.addAll(getParticles(node.Northwest,arrayList));
        arrayList.addAll(getParticles(node.Northeast,arrayList));
        arrayList.addAll(getParticles(node.Southwest,arrayList));
        arrayList.addAll(getParticles(node.Southeast,arrayList));
        return  arrayList;
    }

    public ArrayList<Particle> getParticles(ArrayList<QuadTreeNode> nodeList) {
        ArrayList<Particle> particles = new ArrayList<>();
        for (QuadTreeNode node:nodeList) {
            particles.addAll(QuadTreeNode.getParticles(node,particles));
        }
        return particles;
    }

    public QuadTreeNode getChild(int n) {
        if (n == 0) return Northeast;
        if (n == 1) return Northwest;
        if (n == 2) return Southwest;
        if (n == 3) return Southeast;
        return null;
    }

    public static void divide(QuadTreeNode node) {
        node.Southeast = new QuadTreeNode(node,3);
        node.Southwest = new QuadTreeNode(node,2);
        node.Northeast = new QuadTreeNode(node,0);
        node.Northwest = new QuadTreeNode(node,1);
        node.divided = true;
        for (Particle np : node.Particles) {
            add(node,np);
        }
    }
    public static void add(QuadTreeNode node, Particle p) {
        if (!node.divided) {
            node.Particles.add(p);
            if (node.Particles.size() < node.divideThreshold) {
                return;
            }
            divide(node);
            return;
        }
        int n = getQuadrant(node,p);
        if (n == 0) add(node.Northeast,p);
        if (n == 1) add(node.Northwest,p);
        if (n == 2) add(node.Southwest,p);
        if (n == 3) add(node.Southeast,p);

    }
    public static int getQuadrant(QuadTreeNode node, Particle p) {
        return getQuadrant(node, p.pos);
    }
    public static int getQuadrant(QuadTreeNode node, Position pos) {
        if (pos.x < node.x || pos.x > node.x+node.width || pos.y < node.y || pos.y > node.y+node.width) {
            return -1;
        }
        if (pos.x > node.x+node.width/2) {
            if (pos.y > node.y+node.height/2) {
                //Southeast;
                return 3;
            }
            //Northeast;
            return 0;
        }
        if (pos.y > node.y+node.height/2) {
            //Southwest
            return 2;
        }
        //Northwest
        return 1;
    }

    public void draw(boolean drawContent, boolean drawTree, Graphics g) {
        if (drawTree || this.treeIsVisible) {
            g.setColor(this.color);
            g.drawRect((int) this.x, (int) this.y, (int) this.width, (int) this.height);
        }
        if (drawContent || this.contentIsVisible) {
            if (!this.divided) {
                ArrayList<Particle> particles = new ArrayList<>(this.Particles);
                for (Particle p:particles) {
                    p.draw(g);
                }
                return;
            }
            this.Northeast.draw(drawContent,drawTree,g);
            this.Northwest.draw(drawContent,drawTree,g);
            this.Southeast.draw(drawContent,drawTree,g);
            this.Southwest.draw(drawContent,drawTree,g);
        }
    }

    public static void compute(ArrayList<Particle> arr, QuadTreeNode node) {
        if (node.divided) {
            compute(arr, node.Northeast);
            compute(arr, node.Northwest);
            compute(arr, node.Southwest);
            compute(arr, node.Southeast);
            return;
        }
        ArrayList<QuadTreeNode> neighbors = getNeighbors(node);
        for (Particle p1:node.Particles) {
            Particle newP = new Particle(p1);
            for (Particle p2 : node.Particles) {
                if (p1.id == p2.id) continue;
                newP.interact(p2);
            }

            for (QuadTreeNode neighborNode : neighbors) {
                for (Particle p2 : neighborNode.Particles) {
                    newP.interact(p2);
                }
            }
            newP.integrate();
            arr.add(newP);
        }

    }
}
