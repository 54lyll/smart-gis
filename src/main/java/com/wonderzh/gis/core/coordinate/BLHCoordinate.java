package com.wonderzh.gis.core.coordinate;


import com.wonderzh.gis.core.util.GisUtil;

/**
 * 经纬度坐标
 *
 * @Author: wonderzh
 * @Date: 2019/3/18
 * @Version: 1.0
 */
public class BLHCoordinate extends AbstractCoordinate {
    /**
     * 经度
     */
    private double L_longitude;
    /**
     * 维度
     */
    private double B_latitude;
    /**
     * 高程
     */
    private double H_height;

    /**
     * 商业地图类型
     */
    private int mapType;

    public BLHCoordinate() {

    }


    public BLHCoordinate(double longitude, double latitude,int projType) {
        this(null,longitude, latitude, 0.0,projType,0);
    }

    public BLHCoordinate(double longitude, double latitude, double height) {
        this(null,longitude, latitude, height, 0,6);
    }

    public BLHCoordinate(double longitude, double latitude,int projType,int ellipsoid) {
        this(null,longitude, latitude, 0,projType, ellipsoid);
    }

    public BLHCoordinate(String No,double longitude, double latitude,int projType,int ellipsoid) {
        this(No,longitude, latitude, 0,projType, ellipsoid);
    }

    public BLHCoordinate(String No,double longitude, double latitude, double height,int projType,int ellipsoid) {
        this.No = No;
        this.L_longitude = longitude;
        this.B_latitude = latitude;
        this.H_height = height;
        this.projType = projType;
        this.ellipsoid = ellipsoid;
        this.projNum= GisUtil.countProjNumber(this.L_longitude,this.projType);
    }

    public BLHCoordinate(double longitude, double latitude, Double height,int projType,int ellipsoid) {
        height = height == null ? 0d : height;
        this.L_longitude = longitude;
        this.B_latitude = latitude;
        this.H_height = height;
        this.projType = projType;
        this.ellipsoid = ellipsoid;
        this.projNum= GisUtil.countProjNumber(this.L_longitude,this.projType);
    }

    public double L_longitude() {
        return L_longitude;
    }

    public void setL_longitude(double l_longitude) {
        L_longitude = l_longitude;
    }

    public double B_latitude() {
        return B_latitude;
    }

    public void setB_latitude(double b_latitude) {
        B_latitude = b_latitude;
    }

    public double H_height() {
        return H_height;
    }

    public void setH_height(double h_height) {
        H_height = h_height;
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

    public int mapType() {
        return mapType;
    }

    public void setMapType(int mapType) {
        this.mapType = mapType;
    }

    public int projNum() {
        return this.projNum;
    }

    public  void setProjNum(int projNum) {
        this.projNum = projNum;
    }

    @Override
    public String toString() {
        return "BLHCoordinate{" +
                "L_longitude=" + L_longitude +
                ", B_latitude=" + B_latitude +
                ", H_height=" + H_height +
                ", mapType=" + mapType +
                ", No='" + No +
                ", projNum=" + projNum +
                ", projType=" + projType +
                ", ellipsoid=" + ellipsoid +
                '}';
    }
}
