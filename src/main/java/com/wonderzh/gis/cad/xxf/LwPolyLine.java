package com.wonderzh.gis.cad.xxf;

import java.util.LinkedList;

/**
 * 多段线
 * @Author: wonderzh
 * @Date: 2019/3/14
 * @Version: 1.0
 */

public class LwPolyLine extends AbstractLine {

    //顶点数
    private int vertexCount;
    //顶点集合
    private LinkedList<Vertex> vertices =new LinkedList<>();


    @Override
    public double calculateLength() {

        if (hasVertices()) {
            double head=computeDistance(aNode, vertices.getFirst());
            double tail = computeDistance(vertices.getLast(), zNode);
            double body = calculateBodyLength(vertices);
            this.length=String.format("%.5f",head+tail+body);
        } else {
            this.length=String.format("%.5f",computeDistance(aNode,zNode));
        }
        return Double.valueOf(length);
    }

    public boolean hasVertices() {
        return vertices.size() > 0;
    }

    private double calculateBodyLength(LinkedList<Vertex> vertexList) {
        double body = .0;
        if (vertexList.size() > 1) {
            Vertex pre = vertexList.getFirst();
            for (int i = 1; i < vertexList.size(); i ++) {
                Vertex node = vertexList.get(i);
                body = body + computeDistance(pre, node);
                pre = node;
            }
        }
        return body;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public void setVertexCount(int vertexCount) {
        this.vertexCount = vertexCount;
    }

    public LinkedList<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(LinkedList<Vertex> vertices) {
        this.vertices = vertices;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public Vertex getANode() {
        return aNode;
    }

    public void setANode(Vertex aNode) {
        this.aNode = aNode;
    }

    public Vertex getZNode() {
        return zNode;
    }

    public void setZNode(Vertex zNode) {
        this.zNode = zNode;
    }

    public String getLayer() {
        return layer;
    }

    public void setLayer(String layer) {
        this.layer = layer;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "LwPolyLine{" +
                "vertexCount=" + vertexCount +
                ", vertices=" + vertices +
                ", id=" + id +
                ", aNode=" + aNode +
                ", zNode=" + zNode +
                ", layer='" + layer + '\'' +
                ", length='" + length + '\'' +
                '}';
    }
}
