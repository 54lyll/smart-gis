package com.wonderzh.gis.core.util;

import lombok.Data;

/**
 * @Author: wonderzh
 * @Date: 2019/3/21
 * @Version: 1.0
 */

public class AngleSupport {



    /**
     * 将度转换成弧度
     *
     * @param degree
     * @return
     */
    public static double convertDegreetoRadian(double degree) {
        return (degree * Math.PI) / 180.0;
    }

    /**
     * 将弧度转换成度
     *
     * @param radian
     * @return
     */
    public static double convertRadiantoDegree(double radian) {
        return radian * 180.0 / Math.PI;
    }

    /**
     * 经纬度分秒转带小数点的度
     *
     * @param  "N112°33′6.15″"
     * @return    112.556745
     */
    public static double[] angleToDegree(String lng, String lat) {

        lng = lng.replaceAll("N", "");
        lng = lng.replaceAll("S", "");
        lng = lng.replaceAll("E", "");
        lng = lng.replaceAll("W", "");

        lat = lat.replaceAll("N", "");
        lat = lat.replaceAll("S", "");
        lat = lat.replaceAll("E", "");
        lat = lat.replaceAll("W", "");

        double lngD = 0;
        String[] fs = lng.split("°");
        if (fs.length > 0) {
            lngD = Double.parseDouble(fs[0]);
        }

        fs = fs[1].split("′");
        if (fs.length > 0) {
            double fen = Double.parseDouble(fs[0]);
            fen = fen / 60;
            lngD += fen;
        }

        fs = fs[1].split("″");
        if (fs.length > 0) {
            double mi = Double.parseDouble(fs[0]);
            mi = mi / (60 * 60);
            lngD += mi;
        }

        double latD = 0;
        fs = lat.split("°");
        if (fs.length > 0) {
            latD = Double.parseDouble(fs[0]);
        }

        fs = fs[1].split("′");
        if (fs.length > 0) {
            double fen = Double.parseDouble(fs[0]);
            fen = fen / 60;
            latD += fen;
        }

        fs = fs[1].split("″");
        if (fs.length > 0) {
            double mi = Double.parseDouble(fs[0]);
            mi = mi / (60 * 60);
            latD += mi;
        }

        return new double[]{lngD, latD};
    }

    /**
     * 度转度分秒
     * @param degree   度
     * @return   度度度.分分秒秒秒秒形式的度,保留6位小数
     */
    public static double convertDegretoAngle(double degree)
    {

        Angle angle=degreeToDmsObj(degree);
        int du = angle.getDegree(), fen =angle.getMinute();
        double miao = angle.getSecond();
        int intMiao = (int)(miao * 1000000);

        String strAngle;
        //if (degree < -0.0000001) {
        //    strAngle = string.Format("-{0}.{1}{2}", du.ToString(), fen.ToString("00"), intMiao.ToString("00000000"));
        //}
        //else {
        //    strAngle = string.Format("{0}.{1}{2}", du.ToString(), fen.ToString("00"), intMiao.ToString("00000000"));
        //}

        return 0;
    }

    /**
     * 度转度分秒对象
     * @param angle
     * @return
     */
    private static Angle degreeToDmsObj(double angle)
    {
        double angleValue = Math.abs( angle);
        int degree = (int)angleValue;
        double minuAndSeco = Math.abs(angleValue) - degree;
        int minute = (int)(minuAndSeco * 60);
        double second =(minuAndSeco * 60 - minute) * 60;

        return new Angle(degree,minute,second);
    }

    @Data
    public static class  Angle{
        private int degree;
        private int minute;
        private double second;

        public Angle(int degree, int minute, double second) {
            this.degree = degree;
            this.minute = minute;
            this.second = second;
        }
    }
}
