package com.wonderzh.gis.core.param;

import Jama.Matrix;
import com.wonderzh.gis.core.coordinate.GaussCoordinate;
import lombok.Data;

import java.text.DecimalFormat;
import java.util.List;

/**
 * @Author: wonderzh
 * @Date: 2019/3/19
 * @Version: 1.0
 */
@Data
public class FourParam implements ParamCalculator {
    /**
     * x轴偏移
     */
    private double dx;
    /**
     * y轴偏移
     */
    private double dy;
    /**
     * 形变因子 1+m
     */
    private double scale;
    /**
     * 转角 单位:度
     */
    private double rotate;
    /**
     * 目标椭球体
     */
    private Integer targetEllipsoid;

    private Integer targetProjNum;

    private Integer targetProjType;


    public FourParam() {

    }

    public FourParam(List<GaussCoordinate> source, List<GaussCoordinate> target) {
        this(source.toArray(new GaussCoordinate[source.size()]), target.toArray(new GaussCoordinate[target.size()]));
    }

    public FourParam(GaussCoordinate[] source, GaussCoordinate[] target) {
        this.targetEllipsoid=target[0].ellipsoid();
        this.targetProjNum = target[0].projNum();
        this.targetProjType = target[0].projType();
        calculate(source, target);
    }

    private void calculate(GaussCoordinate[] p1, GaussCoordinate[] p2) {
        if (p1.length != p2.length) {
            throw new IllegalArgumentException(" The two sets of coordinates are different in number");
        }
        if (p1.length < 2) {
            throw new NullPointerException("The number of point is 0. At least one pair of coordinate points");
        }

        double u = 1.0, v = 0, Dx = 0.0, Dy = 0.0;
        int intCount = p1.length;
        double[][] B1 = new double[2 * intCount][4];
        double[][] W1 = new double[2 * intCount][1];

        for (int i = 0; i < intCount; i++) {
            //计算误差方程系数
            B1[2 * i][0] = 1;
            B1[2 * i][1] = 0;
            B1[2 * i][2] = p1[i].x_B();
            B1[2 * i][3] = -p1[i].y_L();

            B1[2 * i + 1][0] = 0;
            B1[2 * i + 1][1] = 1;
            B1[2 * i + 1][2] = p1[i].y_L();
            B1[2 * i + 1][3] = p1[i].x_B();
        }
        Matrix B = new Matrix(B1);//误差方程系数矩阵

        for (int i = 0; i < intCount; i++) {
            //计算误差方程系常数
            W1[2 * i][0] = p2[i].x_B() - u * p1[i].x_B() + v * p1[i].x_B() - Dx;
            W1[2 * i + 1][0] = p2[i].y_L() - u * p1[i].y_L() - v * p1[i].x_B() - Dy;
        }
        Matrix W = new Matrix(W1); //误差方程常数项

        //最小二乘求解
        Matrix BT = B.transpose();
        Matrix N = BT.times(B);
        Matrix InvN = N.inverse();
        Matrix BTW = BT.times(W);
        Matrix dx1 = InvN.times(BTW); //误差方程改正数

        DecimalFormat df = new DecimalFormat("0.0000000");
        dx = Double.valueOf(df.format(Dx + dx1.get(0, 0)));
        dy = Double.valueOf(df.format(Dy + dx1.get(1, 0)));
        u = u + dx1.get(2, 0);//(1+m)cos&
        v = v + dx1.get(3, 0);//(1+m)sin&
        rotate = Double.valueOf(df.format(Math.atan(v / u)));
        scale = Double.valueOf(df.format(u / Math.cos(rotate)));
    }


    //求坐标方位角
    private static double FWJ(GaussCoordinate p1, GaussCoordinate p2) {
        Double dx, dy;
        dx = p2.x_B() - p1.x_B();
        dy = p2.y_L() - p1.y_L();
        return Math.PI - Math.sin(dy) - Math.atan(dx / dy);
    }

    //两点之间的距离公式
    private static double Dist(GaussCoordinate p1, GaussCoordinate p2) {
        double d;
        d = Math.sqrt(Math.pow((p2.x_B() - p1.x_B()), 2) + Math.pow((p2.y_L() - p1.y_L()), 2));
        return d;
    }

    /// 两点法求四参数
    public static void canshu4(GaussCoordinate[] p1, GaussCoordinate[] p2) {
        double rota = FWJ(p2[0], p2[1]) - FWJ(p1[0], p1[1]);
        double scale = Dist(p2[0], p2[1]) / Dist(p1[0], p1[1]);
        double dx = p2[0].x_B() - scale * Math.cos(rota) * p1[0].x_B() + scale * Math.sin(rota) * p1[0].y_L();
        double dy = p2[0].y_L() - scale * Math.sin(rota) * p1[0].x_B() - scale * Math.cos(rota) * p1[0].y_L();
    }

    public double dx() {
        return dx;
    }

    public double dy() {
        return dy;
    }

    public double scale() {
        return scale;
    }

    public double rotate() {
        return rotate;
    }

    public int targetEllipsoid() {
        return targetEllipsoid;
    }
}
