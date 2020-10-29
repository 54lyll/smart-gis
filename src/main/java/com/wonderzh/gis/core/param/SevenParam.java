package com.wonderzh.gis.core.param;

import Jama.Matrix;
import com.wonderzh.gis.core.coordinate.SpaceCoordinate;
import lombok.Data;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 七参数转换
 * 将地理坐标系所对应的空间直角坐标转换为另一坐标系的空间直角坐标
 *
 * @Author: wonderzh
 * @Date: 2019/3/18
 * @Version: 1.0
 */
@Data
public class SevenParam implements ParamCalculator {
    /**
     * X坐标平移量  单位：米
     */
    private double xOffset;
    /**
     * Y坐标平移量  单位：米
     */
    private double yOffset;
    /**
     * Z坐标平移量  单位：米
     */
    private double zOffset;
    /**
     * 比例因子
     */
    private double scale;
    /**
     *X轴旋转角参数
     */
    private double xRotate;
    /**
     *Y轴旋转角参数
     */
    private double yRotate;
    /**
     *Z轴旋转角参数
     */
    private double zRotate;
    /**
     * 目标椭球体
     */
    private int targetEllipsoid;

    private Integer targetProjNum;

    private Integer targetProjType;

    public SevenParam() {
    }

    public SevenParam(List<SpaceCoordinate> source,List<SpaceCoordinate> target) {
        SpaceCoordinate[] sourceArr = new SpaceCoordinate[source.size()];
        source.toArray(sourceArr);
        SpaceCoordinate[] targetArr = new SpaceCoordinate[target.size()];
        target.toArray(targetArr);
        this.targetEllipsoid = targetArr[0].ellipsoid();
        this.targetProjNum = targetArr[0].projNum();
        this.targetProjType = targetArr[0].projType();
        calculate(sourceArr,targetArr);
    }

    public SevenParam(SpaceCoordinate[] source, SpaceCoordinate[] target) {
        this.targetEllipsoid = target[0].ellipsoid();
        this.targetProjNum = target[0].projNum();
        this.targetProjType = target[0].projType();
        calculate(source, target);
    }

    /**
     * 计算空间直角坐标系
     * @param p1
     * @param p2
     */
    public void  calculate(SpaceCoordinate[] p1, SpaceCoordinate[] p2) {
        if (p1.length != p2.length) {
            throw new IllegalArgumentException(" The two sets of coordinates are different in number");
        }
        if (p1.length < 3 ) {
            throw new NullPointerException(" At least 3 pair of coordinate points");
        }
        int pointCount = p1.length;
        double[][] B = new double[pointCount * 3][7];
        double[][] L = new double[pointCount * 3][1];
        //初始化L矩阵
        for (int i = 0; i < pointCount * 3; i++) {
            if (i % 3 == 0) {
                L[i][0] = p2[i / 3].X();
            } else if (i % 3 == 1) {
                L[i][0] = p2[i / 3].Y();
            } else if (i % 3 == 2) {
                L[i][0] = p2[i / 3].Z();
            }
        }
        //初始化B矩阵
        for (int i = 0; i < pointCount * 3; i++) {
            if (i % 3 == 0) {
                B[i][0] = 1;
                B[i][1] = 0;
                B[i][2] = 0;
                B[i][3] = p1[i / 3].X();
                B[i][4] = 0;
                B[i][5] = -p1[i / 3].Z();
                B[i][6] = p1[i / 3].Y();

            } else if (i % 3 == 1) {
                B[i][0] = 0;
                B[i][1] = 1;
                B[i][2] = 0;
                B[i][3] = p1[i / 3].Y();
                B[i][4] = p1[i / 3].Z();
                B[i][5] = 0;
                B[i][6] = -p1[i / 3].X();
            } else if (i % 3 == 2) {
                B[i][0] = 0;
                B[i][1] = 0;
                B[i][2] = 1;
                B[i][3] = p1[i / 3].Z();
                B[i][4] = -p1[i / 3].Y();
                B[i][5] = p1[i / 3].X();
                B[i][6] = 0;
            }

        }
        Matrix bM = new Matrix(B);
        Matrix lM = new Matrix(L);
        //转置
        Matrix bt = bM.transpose();
        //法方程矩阵
        //N=BT*B
        Matrix n = bt.times(bM);
        //求逆
        Matrix invN = n.inverse();
        //BTL=BT*L
        Matrix btl = bt.times(lM);
        //dx1=invN*BTL;
        Matrix dx = invN.times(btl);

        DecimalFormat df = new DecimalFormat("0.0000000");
        xOffset =Double.valueOf(df.format(dx.get(0, 0))) ;
        yOffset =Double.valueOf(df.format(dx.get(1, 0))) ;
        zOffset = Double.valueOf(df.format(dx.get(2, 0)));
        scale = Double.valueOf(df.format(dx.get(3, 0)));//(a1=m+1)
        xRotate = Double.valueOf(df.format(dx.get(4, 0)/scale));
        yRotate = Double.valueOf(df.format(dx.get(5, 0)/scale));
        zRotate = Double.valueOf(df.format(dx.get(6, 0)/scale));
    }



    public double xOffset() {
        return xOffset;
    }

    public double yOffset() {
        return yOffset;
    }

    public double zOffset() {
        return zOffset;
    }

    public double scale() {
        return scale;
    }

    public double xRotate() {
        return xRotate;
    }

    public double yRotate() {
        return yRotate;
    }

    public double zRotate() {
        return zRotate;
    }

    public int targetEllipsoid() {
        return targetEllipsoid;
    }

}
