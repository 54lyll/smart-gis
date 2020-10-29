package com.wonderzh.gis.core.coordinate;


/**
 * 空间直角坐标
 *
 * @Author: wonderzh
 * @Date: 2019/3/19
 * @Version: 1.0
 */
public class SpaceCoordinate extends AbstractCoordinate {

    private double X;
    private double Y;
    private double Z;


    public SpaceCoordinate() {

    }

    public SpaceCoordinate(double X, double Y, double Z) {
        this(X, Y, Z, 6);
    }

    public SpaceCoordinate(double X, double Y, double Z, int projType) {
        this.X = X;
        this.Y = Y;
        this.Z = Z;
        this.projType = projType;
    }

    public SpaceCoordinate(String No,double X, double Y, double Z, int projNum, int projType, int ellipsoid) {
        this.No = No;
        this.X = X;
        this.Y = Y;
        this.Z = Z;
        this.projNum = projNum;
        this.projType = projType;
        this.ellipsoid = ellipsoid;
    }

    public double X() {
        return X;
    }

    public void setX(double x) {
        X = x;
    }

    public double Y() {
        return Y;
    }

    public void setY(double y) {
        Y = y;
    }

    public double Z() {
        return Z;
    }

    public void setZ(double z) {
        Z = z;
    }

    public int projNum() {
        return projNum;
    }

    public void setProjNum(int projNum) {
        this.projNum = projNum;
    }

    public int projType() {
        return projType;
    }

    public void setProjType(int projType) {
        this.projType = projType;
    }

    public int ellipsoid() {
        return ellipsoid;
    }

    public void setellipsoid(int ellipsoid) {
        this.ellipsoid = ellipsoid;
    }

    @Override
    public String toString() {
        return "SpaceCoordinate{" +
                "X=" + X +
                ", Y=" + Y +
                ", Z=" + Z +
                ", projNum=" + projNum +
                ", projType=" + projType +
                ", ellipsoid=" + ellipsoid +
                '}';
    }
}
