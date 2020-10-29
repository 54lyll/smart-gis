package com.wonderzh.gis.math;

import java.util.Objects;

/**
 * 平面向量
 *
 * @Author: wonderzh
 * @Date: 2019/10/16
 * @Version: 1.0
 */

public class Vector2D {
    private int id;
    private double x;
    private double y;
    private double distance;
    private Vector2D next;


    public Vector2D() {
        x = 0;
        y = 0;
    }

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    //获取弧度
    public double radian() {
        return Math.atan2(y, x);
    }

    //获取角度
    public double angle() {
        return radian() / Math.PI * 180;
    }

    public Vector2D clone() {
        return new Vector2D(x, y);
    }

    public double length() {
        return Math.sqrt(getLengthSQ());
    }

    public double getLengthSQ() {
        return x * x + y * y;
    }

    //向量置零
    public Vector2D Zero() {
        x = 0;
        y = 0;
        return this;
    }

    public boolean isZero() {
        return x == 0 && y == 0;
    }

    //向量的长度设置为我们期待的value
    public void setLength(double value) {
        double _angle = angle();
        x = Math.cos(_angle) * value;
        y = Math.sin(_angle) * value;
    }

    //向量的标准化（方向不变，长度为1）
    public Vector2D normalize() {
        double length = length();
        x = x / length;
        y = y / length;
        return this;
    }

    //是否已经标准化
    public boolean isNormalized() {
        return length() == 1.0;
    }

    //向量的方向翻转
    public Vector2D reverse() {
        x = -x;
        y = -y;
        return this;
    }

    //2个向量的数量积(点积)
    public double dotProduct(Vector2D v) {
        return x * v.x + y * v.y;
    }

    //2个向量的向量积(叉积)
    public double crossProduct(Vector2D v) {
        return x * v.y - y * v.x;
    }

    //计算2个向量的夹角弧度
    //参考点积公式:v1 * v2 = cos<v1,v2> * |v1| *|v2|
    public static double radianBetween(Vector2D v1, Vector2D v2) {
        if (!v1.isNormalized()) v1 = v1.clone().normalize(); // |v1| = 1
        if (!v2.isNormalized()) v2 = v2.clone().normalize(); // |v2| = 1
        return Math.acos(v1.dotProduct(v2));
    }

    //弧度 = 角度乘以PI后再除以180、 推理可得弧度换算角度的公式
    //弧度转角度
    public static double radian2Angle(double radian) {
        return radian / Math.PI * 180;
    }

    //向量加
    public Vector2D add(Vector2D v) {
        return new Vector2D(x + v.x, y + v.y);
    }

    //向量减
    public Vector2D subtract(Vector2D v) {
        return new Vector2D(x - v.x, y - v.y);
    }

    //向量乘
    public Vector2D multiply(double value) {
        return new Vector2D(x * value, y * value);
    }

    //向量除
    public Vector2D divide(double value) {
        return new Vector2D(x / value, y / value);
    }


    public double distanceToPoint(Vector2D point) {
        double dx = this.x - point.getX();
        double dy = this.y - point.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        return Math.abs(distance);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Vector2D next() {
        return next;
    }

    public void setNext(Vector2D next) {
        this.next = next;
    }

    /**
     * 线段中点
     *
     * @return
     */
    public Vector2D center() {
        return new Vector2D(getX() * 0.5, getY() * 0.5);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2D vector2D = (Vector2D) o;
        return id == vector2D.id &&
                Double.compare(vector2D.x, x) == 0 &&
                Double.compare(vector2D.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
