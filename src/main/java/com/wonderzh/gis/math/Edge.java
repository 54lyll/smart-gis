package com.wonderzh.gis.math;



/**
 * @Author: wonderzh
 * @Date: 2019/10/16
 * @Version: 1.0
 */

public class Edge {

    private int id;

    private Vector2D a;

    private Vector2D b;

    private Edge next;

    private boolean hasReversal=false;

    private boolean hasNext=false;

    public Edge() {

    }

    public Edge(Vector2D a, Vector2D b) {
        this.a = a;
        this.b = b;
    }

    public Edge(int id,Vector2D a, Vector2D b) {
        this.id = id;
        this.a = a;
        this.b = b;
    }

    public void reversal() {
        Vector2D temp = this.a;
        this.a = this.b;
        this.b = temp;
        hasReversal = true;
    }

    public Vector2D getA() {
        return a;
    }

    public void setA(Vector2D a) {
        this.a = a;
    }

    public Vector2D getB() {
        return b;
    }

    public void setB(Vector2D b) {
        this.b = b;
    }

    public Edge getNext() {
        return next;
    }

    public void setNext(Edge next) {
        if (next != null) {
            this.next = next;
            hasNext = true;
        }

    }


    public boolean hasReversal() {
        return this.hasReversal;
    }

    public boolean hasNext() {
        return this.hasNext;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
