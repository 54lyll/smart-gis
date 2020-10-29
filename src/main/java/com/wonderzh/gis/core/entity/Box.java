package com.wonderzh.gis.core.entity;

/**
 * 坐标范围
 *
 * @Author: wonderzh
 * @Date: 2019/10/22
 * @Version: 1.0
 */

public class Box {

    private double xMin;
    private double xMax;
    private double yMin;
    private double yMax;

    public Box() {

    }

    public Box(double xMin, double xMax, double yMin, double yMax) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
    }


    public double getxMin() {
        return xMin;
    }

    public void setxMin(double xMin) {
        this.xMin = xMin;
    }

    public double getxMax() {
        return xMax;
    }

    public void setxMax(double xMax) {
        this.xMax = xMax;
    }

    public double getyMin() {
        return yMin;
    }

    public void setyMin(double yMin) {
        this.yMin = yMin;
    }

    public double getyMax() {
        return yMax;
    }

    public void setyMax(double yMax) {
        this.yMax = yMax;
    }
}
