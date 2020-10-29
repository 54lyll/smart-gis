package com.wonderzh.gis.cad.xxf;


import com.smartwater.common.util.DoubleArithUtil;

/**
 * 直线
 * @Author: wonderzh
 * @Date: 2019/3/13
 * @Version: 1.0
 */

public class Line extends AbstractLine {

    @Override
    public double calculateLength() {
        double a = DoubleArithUtil.sub(Double.valueOf(aNode.getX()), Double.valueOf(zNode.getX()));
        double b = DoubleArithUtil.sub(Double.valueOf(aNode.getY()), Double.valueOf(zNode.getY()));
        length=String.format("%.5f",Math.sqrt(a * a + b * b));
        return Double.valueOf(length);
    }

    @Override
    public String toString() {
        return "Line{" +
                "aNode=" + aNode +
                ", zNode=" + zNode +
                ", id=" + id +
                ", layer='" + layer + '\'' +
                ", length='" + length + '\'' +
                '}';
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

}
