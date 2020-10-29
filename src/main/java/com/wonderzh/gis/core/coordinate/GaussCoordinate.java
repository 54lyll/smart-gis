package com.wonderzh.gis.core.coordinate;


/**
 * 高斯平面坐标
 *
 * @Author: wonderzh
 * @Date: 2019/3/18
 * @Version: 1.0
 */
public class GaussCoordinate extends AbstractCoordinate {
    /**
     * cad图纸x轴，对应西安80图纸  Y 东轴
     */
    private double x_L;
    /**
     *  cad图纸y轴，对应西安80图纸  X 北轴
     */
    private double y_B;
    /**
     * 高程
     */
    private double z_H;


    public GaussCoordinate() {
    }

    public GaussCoordinate(double x_L, double y_B) {
        this(null,x_L, y_B,0,0,0);
    }

    public GaussCoordinate(double x_L, double y_B, int projNo,int projType) {
        this(null ,x_L, y_B,0d,projNo,projType,0);
    }

    public GaussCoordinate(double x_L, double y_B, int projNo,int projType,int ellipsoid) {
        this(null,x_L, y_B, 0d, projNo, projType, ellipsoid);
    }

    public GaussCoordinate(String No,double x_L, double y_B, int projNo,int projType,int ellipsoid) {
        this(No,x_L, y_B, 0d, projNo, projType, ellipsoid);
    }

    public GaussCoordinate(String No,double x_L, double y_B, Double z_H, Integer projNo,int projType,int ellipsoid) {
        z_H = z_H == null ? 0 : z_H;
        projNo = projNo == null ? 0 : projNo;
        this.No = No;
        this.x_L = x_L;
        this.y_B = y_B;
        this.z_H = z_H;
        this.projNum = projNo;
        this.projType = projType;
        this.ellipsoid = ellipsoid;
    }

    public GaussCoordinate(double x_L, double y_B, double z_H, int projType,int ellipsoid) {
        this.x_L = x_L;
        this.y_B = y_B;
        this.z_H = z_H;
        this.projType = projType;
        this.ellipsoid = ellipsoid;
    }

    public GaussCoordinate(Double x, Double y, Double z, Integer projNum, Integer projType, Integer ellipsoid) {
        this(null,x, y, z, projNum, projType, ellipsoid);
    }

    public GaussCoordinate standard() {
        long X0 = 1000000L * projNum ;
        x_L = x_L + X0;
        return this;
    }

    public GaussCoordinate reality() {
        int X0 = -500000 ;
        x_L = x_L + X0;
        return this;
    }

    public double x_B() {
        return x_L;
    }

    public void setX_L(double x_L) {
        this.x_L = x_L;
    }

    public double y_L() {
        return y_B;
    }

    public void setY_B(double y_B) {
        this.y_B = y_B;
    }

    public double z_H() {
        return z_H;
    }

    public void setZ_H(double z_H) {
        this.z_H = z_H;
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

    public void setEllipsoid(int ellipsoid) {
        this.ellipsoid = ellipsoid;
    }

    @Override
    public String toString() {
        return "GaussCoordinate{" +
                "x_L=" + x_L +
                ", y_B=" + y_B +
                ", z_H=" + z_H +
                ", projNum=" + projNum +
                ", projType=" + projType +
                ", ellipsoid=" + ellipsoid +
                '}';
    }
}
