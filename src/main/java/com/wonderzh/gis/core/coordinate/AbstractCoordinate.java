package com.wonderzh.gis.core.coordinate;

/**
 * @Author: wonderzh
 * @Date: 2019/3/25
 * @Version: 1.0
 */

public class AbstractCoordinate {

    /**
     * 坐标标号
     */
    protected String No;

    /**
     * 投影带号
     */
    protected int projNum;
    /**
     * 投影带类型  3 度带   6度带
     */
    protected int projType;

    /**
     * 椭球体
     */
    protected int ellipsoid;

    public String getNo() {
        return No;
    }

    public void setNo(String no) {
        No = no;
    }
}
