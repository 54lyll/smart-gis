package com.wonderzh.gis.core.converter;


import com.wonderzh.gis.core.coordinate.GaussCoordinate;
import com.wonderzh.gis.core.coordinate.SpaceCoordinate;
import com.wonderzh.gis.core.param.FourParam;
import com.wonderzh.gis.core.param.SevenParam;

/**
 * 不同椭球体空间直角坐标系转换工具
 *
 * @Author: wonderzh
 * @Date: 2019/3/18
 * @Version: 1.0
 */

public class EllipsoidConverter {



    /**
     * 空间直角坐标七参转换
     *
     * @param sourceCoordinate
     * @return
     */

    public static SpaceCoordinate transformSpaceCoordinate(SpaceCoordinate sourceCoordinate,SevenParam sevenParam) {
        double X = sourceCoordinate.X(), Y = sourceCoordinate.Y(), Z = sourceCoordinate.Z();
        double Ex, Ey, Ez;
        Ex = sevenParam.xRotate() *sevenParam.scale();
        Ey = sevenParam.yRotate()*sevenParam.scale() ;
        Ez = sevenParam.zRotate()*sevenParam.scale() ;

        double targetX = sevenParam.xOffset() + sevenParam.scale() * X + Y * Ez - Z * Ey ;
        double targetY = sevenParam.yOffset() + sevenParam.scale() * Y - X * Ez + Z * Ex ;
        double targetZ = sevenParam.zOffset() + sevenParam.scale() * Z + X * Ey - Y * Ex ;
        //椭球体参数使用目标椭球体
        return new SpaceCoordinate(sourceCoordinate.getNo(),targetX, targetY, targetZ,sevenParam.getTargetProjNum(),
                sevenParam.getTargetProjType(),sevenParam.targetEllipsoid());
    }

    /**
     * 高斯平面坐标四参转换
     *
     * @param sourceCoordinate
     */
    public static GaussCoordinate transformPlaneCoordinate(GaussCoordinate sourceCoordinate, FourParam fourParam) {
        double x = sourceCoordinate.x_B(), y = sourceCoordinate.y_L();
        double a = fourParam.getScale() * Math.cos(fourParam.getRotate());
        double b = fourParam.getScale() * Math.sin(fourParam.getRotate());

        double targetX = fourParam.getDx() + a * x - b * y;
        double targetY = fourParam.getDy() + b * x + a * y;
        //椭球体参数使用目标椭球体
        GaussCoordinate gaussTarget= new GaussCoordinate(sourceCoordinate.getNo(),targetX, targetY,
                sourceCoordinate.z_H(),fourParam.getTargetProjNum(),fourParam.getTargetProjType(),fourParam.targetEllipsoid());
        return gaussTarget;
    }
}
