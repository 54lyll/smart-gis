package com.wonderzh.gis.core.constant.spheroid;

/**
 * 坐标系统参考椭球体
 * @Author: wonderzh
 * @Date: 2019/3/18
 * @Version: 1.0
 */

public enum Ellipsoid {
    /**
     * 西安80参心椭球
     */
    XIAN_80(80,6378140.0,6356755.28815753,1 / 298.25722101),
    /**
     * 北京54参心椭球
     */
    BEIJING_54(54,6378245.0,6356863.0188,1/298.3),
    /**
     * WGS84地心椭球
     */
    WGS_84(84,6378137.0,6356752.31419,1/298.25722356),
    /**
     *国家大地2000地心椭球
     */
    CGCS_2000(2000,6378137.0,6356752.31414,1/ 298.257222101),;

    private int id;
    /**
     * 椭球长轴，单位米
     */
    private double macroAxis;
    /**
     * 椭球短轴，单位米
     */
    private double minorAxis;
    /**
     * 椭球扁率
     */
    private double flattening;

    Ellipsoid(int id,double macroAxis, double minorAxis, double flattening) {
        this.id = id;
        this.macroAxis = macroAxis;
        this.minorAxis = minorAxis;
        this.flattening = flattening;
    }

    public static Ellipsoid getSpheroid(int id) {
        for (Ellipsoid ellipsoid : Ellipsoid.values()) {
            if (ellipsoid.getId() == id) {
                return ellipsoid;
            }
        }
        throw new IllegalArgumentException();
    }

    public double getMacroAxis() {
        return macroAxis;
    }

    public double getMinorAxis() {
        return minorAxis;
    }

    public double getFlattening() {
        return flattening;
    }

    public int getId() {
        return id;
    }


}
