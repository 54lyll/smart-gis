package com.wonderzh.gis.core.converter;

import com.wonderzh.gis.core.constant.spheroid.Ellipsoid;
import com.wonderzh.gis.core.coordinate.BLHCoordinate;
import com.wonderzh.gis.core.coordinate.GaussCoordinate;
import com.wonderzh.gis.core.coordinate.SpaceCoordinate;

import java.math.BigDecimal;

/**
 * 相同椭球体坐标系转换工具
 * 经纬——空间直角——高斯投影平面
 *
 * @Author: wonderzh
 * @Date: 2019/3/15
 * @Version: 1.0
 */

public class ProjectionConverter {
    /**
     * 3度带
     */
    public static final int THREE_DEGREE = 3;
    /**
     * 6度带
     */
    public static final int SIX_DEGREE = 6;
    // 参考点
    private double[] d34 = null;
    private double[] d33 = null;
    private double[] gs34 = null;
    private double[] gs33 = null;

    //参考点位置
    private double x34, y34;
    // 计算参数
    private double a, b, c, bl;

    public ProjectionConverter() {
    }

    /**
     * 以参考点为基础，根据经纬度算XY坐标
     *
     * @param ds34   参考点34 单位度
     * @param ds33   参考点33 单位度
     * @param x34
     * @param y34
     * @param length 本地坐标系两点距离
     */
    public ProjectionConverter(double[] ds34, double[] ds33, double x34, double y34, double length, Ellipsoid ellipsoid) {
        this.x34 = x34;
        this.y34 = y34;
        this.d33 = ds33;
        this.d34 = ds34;
        //gs34 = BLToGauss(d34[0], d34[1],ellipsoid2);
        //gs33 = BLToGauss(d33[0], d33[1],ellipsoid2);

        // 点33到点34的高斯横向距离
        a = gs34[0] - gs33[0];
        // 点33到点34的高斯纵向距离
        b = gs34[1] - gs33[1];
        // 点33到点34的高斯直线距离
        c = Math.sqrt(a * a + b * b);
        // 高斯距离与本坐标系的比例
        bl = length / c;
    }

    /**
     * 根据经纬度算XY
     *
     * @param lng
     * @param lat
     * @return
     */
    public int[] getXY(double lng, double lat) {

        // 与参考点比较经度计算坐标
        if (lng > d34[0]) {
            return getXY1(lng, lat);
        } else {
            return getXY2(lng, lat);
        }
    }

    /**
     * 已有定点为基准算XY坐标
     */
    private int[] getXY1(double lng, double lat) {

        //// 将经纬度转换为高斯投影坐标
        //double[] gs = BLToGauss(lng, lat);
        //
        //// 与点34的横向高斯距离
        //double d = Math.abs(gs[0] - gs34[0]);
        //double e = d * c / b;
        //double f = a * e / c;
        //
        //// 与点34的纵向高斯距离
        //double gf = Math.abs(gs[1] - gs34[1]);
        //double g = gf - f;
        //double h = a * g / c;
        //double i = b * g / c;
        //double x = x34 - i * bl;
        //double y = y34 + (h + e) * bl;
        //return new int[]{(int) Math.rint(x), (int) Math.rint(y)};
        return null;
    }

    /**
     * 已有定点为基准算XY坐标
     */
    private int[] getXY2(double lng, double lat) {

        //// 将经纬度转换为高斯投影坐标
        //double[] gs = BLToGauss(lng, lat);
        //// 与点34的横向高斯距离
        //double d = Math.abs(gs[0] - gs34[0]);
        //double e = d * c / a;
        //double f = e * b / c;
        //// 与点34的纵向高斯距离
        //double gf = Math.abs(gs[1] - gs34[1]);
        //double g = gf - f;
        //double h = a * g / c;
        //double i = b * g / c;
        //double x = x34 - (e + i) * bl;
        //double y = y34 + h * bl;
        //return new int[]{(int) Math.rint(x), (int) Math.rint(y)};
        return null;
    }


    /**
     * 大地坐标系换换成空间直角坐标系
     *
     * @param ellipsoid 椭球体
     * @return
     */
    public static SpaceCoordinate BLHtoXYZ(BLHCoordinate coordinate, Ellipsoid ellipsoid) {
        double L = coordinate.L_longitude(), B = coordinate.B_latitude(), H = coordinate.H_height();
        double dblD2R = Math.PI / 180;
        double aAxis = ellipsoid.getMacroAxis(), bAxis = ellipsoid.getMinorAxis();
        double e1 = Math.sqrt(Math.pow(aAxis, 2) - Math.pow(bAxis, 2)) / aAxis;
        double N = aAxis / Math.sqrt(1.0 - Math.pow(e1, 2) * Math.pow(Math.sin(B * dblD2R), 2));

        double X = (N + H) * Math.cos(B * dblD2R) * Math.cos(L * dblD2R);
        double Y = (N + H) * Math.cos(B * dblD2R) * Math.sin(L * dblD2R);
        double Z = (N * (1.0 - Math.pow(e1, 2)) + H) * Math.sin(B * dblD2R);
        return new SpaceCoordinate(coordinate.getNo(),X, Y, Z,coordinate.projNum(),coordinate.projType(),coordinate.ellipsoid());
    }

    /**
     * 空间直接坐标系转换为大地坐标系
     *
     * @param ellipsoid 椭球体
     * @return
     */
    public static BLHCoordinate XYZtoBLH(SpaceCoordinate coordinate, Ellipsoid ellipsoid) {
        double X = coordinate.X(), Y = coordinate.Y(), Z = coordinate.Z();
        double aAxis = ellipsoid.getMacroAxis(), bAxis = ellipsoid.getMinorAxis();
        double e1 = (Math.pow(aAxis, 2) - Math.pow(bAxis, 2)) / Math.pow(aAxis, 2);
        double e2 = (Math.pow(aAxis, 2) - Math.pow(bAxis, 2)) / Math.pow(bAxis, 2);

        double S = Math.sqrt(Math.pow(X, 2) + Math.pow(Y, 2));
        double cosL = X / S;
        double B = 0;
        double L = 0;

        L = Math.acos(cosL);
        L = Math.abs(L);

        double tanB = Z / S;
        B = Math.atan(tanB);
        double c = aAxis * aAxis / bAxis;
        double preB0 = 0.0;
        double ll = 0.0;
        double N = 0.0;
        //迭代计算纬度
        do {
            preB0 = B;
            ll = Math.pow(Math.cos(B), 2) * e2;
            N = c / Math.sqrt(1 + ll);

            tanB = (Z + N * e1 * Math.sin(B)) / S;
            B = Math.atan(tanB);
        }
        while (Math.abs(preB0 - B) >= 0.0000000001);

        ll = Math.pow(Math.cos(B), 2) * e2;
        N = c / Math.sqrt(1 + ll);

        double targetH = Z / Math.sin(B) - N * (1 - e1);
        double targetB = B * 180 / Math.PI;
        double targetL = L * 180 / Math.PI;
        return new BLHCoordinate(coordinate.getNo(),targetL, targetB, targetH,coordinate.projType(),ellipsoid.getId());
    }

    /**
     * 由高斯一般投影坐标反算成经纬度
     * 需要包装带号，带号不同计算的blh不同
     *
     * @param coordinate
     * @return
     */
    public static BLHCoordinate GaussToBLNormal(GaussCoordinate coordinate) {
        Ellipsoid ellipsoid = Ellipsoid.getSpheroid(coordinate.ellipsoid());
        double X = coordinate.x_B() + coordinate.projNum() * 1000000L;
        GaussCoordinate newCoordinate = new GaussCoordinate(coordinate.getNo(), X, coordinate.y_L(), coordinate.z_H(), coordinate.projNum(), coordinate.projType(), coordinate.ellipsoid());
        BLHCoordinate blh = GaussToBL( newCoordinate, ellipsoid);
        return  blh;
    }

    /**
     * 由高斯通用标准投影坐标反算成经纬度
     *
     * @param ellipsoid 椭球体  对应坐标体系
     * @return
     */
    public static BLHCoordinate GaussToBL(GaussCoordinate coordinate, Ellipsoid ellipsoid) {
        double X=coordinate.x_B(),  Y=coordinate.y_L();
        int projType = coordinate.projType();
        int ProjNo;
        int ZoneWide;           //带宽
        double longitude1, latitude1, longitude0, X0, Y0, xval, yval;//latitude0,
        double e1, e2, f, a, ee, NN, T, C, M, D, R, u, fai, iPI;

        iPI = 0.0174532925199433;           //3.1415926535898/180.0;
        a = ellipsoid.getMacroAxis();//长轴
        f = ellipsoid.getFlattening(); //扁率

        ProjNo = (int) (X / 1000000L);        //查找带号
        ZoneWide = projType;
        if (ZoneWide == THREE_DEGREE) {//3度带
            longitude0 = ProjNo * ZoneWide;
        } else {//默认6度带计算
            longitude0 = ProjNo * ZoneWide - ZoneWide / 2;
        }
        longitude0 = longitude0 * iPI;     //中央经线度转弧度

        X0 = ProjNo * 1000000L + 500000L;//带号+500公里
        Y0 = 0;
        xval = X - X0;
        yval = Y - Y0;           //带内大地坐标
        e2 = 2 * f - f * f;
        e1 = (1.0 - Math.sqrt(1 - e2)) / (1.0 + Math.sqrt(1 - e2));
        ee = e2 / (1 - e2);
        M = yval;
        u = M / (a * (1 - e2 / 4 - 3 * e2 * e2 / 64 - 5 * e2 * e2 * e2 / 256));
        fai = u + (3 * e1 / 2 - 27 * e1 * e1 * e1 / 32) * Math.sin(2 * u) + (21 * e1 * e1 / 16 - 55 * e1 * e1 * e1 * e1 / 32) * Math.sin(
                4 * u)
                + (151 * e1 * e1 * e1 / 96) * Math.sin(6 * u) + (1097 * e1 * e1 * e1 * e1 / 512) * Math.sin(8 * u);
        C = ee * Math.cos(fai) * Math.cos(fai);
        T = Math.tan(fai) * Math.tan(fai);
        NN = a / Math.sqrt(1.0 - e2 * Math.sin(fai) * Math.sin(fai));
        R = a * (1 - e2) / Math.sqrt((1 - e2 * Math.sin(fai) * Math.sin(fai)) * (1 - e2 * Math.sin(fai) * Math.sin(fai)) * (1 - e2 * Math.sin
                (fai) * Math.sin(fai)));
        D = xval / NN;

        //计算经度(Longitude) 纬度(Latitude)  弧度
        longitude1 = longitude0 + (D - (1 + 2 * T + C) * D * D * D / 6 + (5 - 2 * C + 28 * T - 3 * C * C + 8 * ee + 24 * T * T) * D
                * D * D * D * D / 120) / Math.cos(fai);
        latitude1 = fai - (NN * Math.tan(fai) / R) * (D * D / 2 - (5 + 3 * T + 10 * C - 4 * C * C - 9 * ee) * D * D * D * D / 24
                + (61 + 90 * T + 298 * C + 45 * T * T - 256 * ee - 3 * C * C) * D * D * D * D * D * D / 720);

        //转换为度 DD
        double longitude = longitude1 / iPI;
        double latitude = latitude1 / iPI;
        return new BLHCoordinate(coordinate.getNo(),longitude, latitude,coordinate.z_H(), ZoneWide,ellipsoid.getId());
    }

    /**
     * 由经纬度反算成高斯一般投影坐标
     * 不带带号
     *
     * @param coordinate
     * @return
     */
    public static GaussCoordinate BLToGaussNormal(BLHCoordinate coordinate) {
        Ellipsoid ellipsoid = Ellipsoid.getSpheroid(coordinate.ellipsoid());
        if (ellipsoid == null) {
            throw new NullPointerException("椭球体获取失败");
        }
        return BLToGaussNormal(coordinate,ellipsoid);
    }

    /**
     * 由经纬度反算成高斯一般投影坐标
     * 不带带号
     *
     * @param ellipsoid 椭球体  对应坐标体系
     * @return
     */
    public static GaussCoordinate BLToGaussNormal(BLHCoordinate coordinate,Ellipsoid ellipsoid) {
        int projNo = 0;             //带号
        int ZoneWide;             //带宽
        double longitude1, latitude1, longitude0, latitude0, X0, Y0, xval, yval;
        double a, f, e2, ee, NN, T, C, A, M, iPI;
        double longitude = coordinate.L_longitude();
        double latitude = coordinate.B_latitude();

        iPI = 0.0174532925199433;               //3.1415926535898/180.0;
        a = ellipsoid.getMacroAxis();//长轴
        f = ellipsoid.getFlattening(); //扁率

        ZoneWide =coordinate.projType();          //带宽
        if (ZoneWide == THREE_DEGREE) {
            Double temp = longitude / ZoneWide;
            BigDecimal bd = new BigDecimal(temp).setScale(0, BigDecimal.ROUND_HALF_UP);
            projNo = bd.intValue();
            //System.out.println(projNo);
            longitude0 = projNo * ZoneWide;
        } else {
            projNo = (int) (longitude / ZoneWide + 1);
            longitude0 = projNo * ZoneWide - ZoneWide / 2;//中央经度
            //System.out.println(projNo);
        }

        longitude0 = longitude0 * iPI;//中央经度转换为弧度
        longitude1 = longitude * iPI; //经度转换为弧度
        latitude1 = latitude * iPI; //纬度转换为弧度

        e2 = 2 * f - f * f;
        ee = e2 * (1.0 - e2);
        NN = a / Math.sqrt(1.0 - e2 * Math.sin(latitude1) * Math.sin(latitude1));
        T = Math.tan(latitude1) * Math.tan(latitude1);
        C = ee * Math.cos(latitude1) * Math.cos(latitude1);
        A = (longitude1 - longitude0) * Math.cos(latitude1);
        M = a * ((1 - e2 / 4 - 3 * e2 * e2 / 64 - 5 * e2 * e2 * e2 / 256) * latitude1 - (3 * e2 / 8 + 3 * e2 * e2 / 32 + 45 * e2 * e2
                * e2 / 1024) * Math.sin(2 * latitude1)
                + (15 * e2 * e2 / 256 + 45 * e2 * e2 * e2 / 1024) * Math.sin(4 * latitude1) - (35 * e2 * e2 * e2 / 3072) * Math.sin(6 * latitude1));

        xval = NN * (A + (1 - T + C) * A * A * A / 6 + (5 - 18 * T + T * T + 72 * C - 58 * ee) * A * A * A * A * A / 120);
        yval = M + NN * Math.tan(latitude1) * (A * A / 2 + (5 - T + 9 * C + 4 * C * C) * A * A * A * A / 24
                + (61 - 58 * T + T * T + 600 * C - 330 * ee) * A * A * A * A * A * A / 720);
        X0 = 500000L;
        xval = xval + X0;
        return new GaussCoordinate(coordinate.getNo(),xval, yval, coordinate.H_height(),projNo, ZoneWide,coordinate.ellipsoid());
    }

    /**
     * 由经纬度反算成高斯通用标准投影坐标
     *
     * @return X坐标带两位带号
     */
    public static GaussCoordinate BLToGauss(BLHCoordinate blhCoordinate) {
        GaussCoordinate coordinate = BLToGaussNormal(blhCoordinate);
        coordinate.standard();
        return coordinate;
    }



}
