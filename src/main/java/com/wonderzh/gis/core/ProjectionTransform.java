package com.wonderzh.gis.core;

import com.wonderzh.gis.core.constant.spheroid.Ellipsoid;
import com.wonderzh.gis.core.converter.ProjectionConverter;
import com.wonderzh.gis.core.coordinate.BLHCoordinate;
import com.wonderzh.gis.core.coordinate.GaussCoordinate;
import com.wonderzh.gis.core.coordinate.SpaceCoordinate;

import java.util.ArrayList;
import java.util.List;

/**
 * 同椭球体 坐标转换服务
 *
 * @Author: wonderzh
 * @Date: 2019/3/22
 * @Version: 1.0
 */

public class ProjectionTransform {

    /**
     * 同坐标系  经纬度转高斯平面
     *
     * @param blhSources
     * @return
     */
    public List<GaussCoordinate> BLHToGauss(List<BLHCoordinate> blhSources) {
        List<GaussCoordinate> gaussTargets = new ArrayList<>();
        for (BLHCoordinate blhSource : blhSources) {
            GaussCoordinate gauss = ProjectionConverter.BLToGaussNormal(blhSource);
            gaussTargets.add(gauss);
        }
        return gaussTargets;
    }

    public GaussCoordinate BLHToGauss(BLHCoordinate blhSource) {
        if (blhSource == null) {
            return null;
        }
        GaussCoordinate gauss = ProjectionConverter.BLToGaussNormal(blhSource);
        return gauss;
    }


    public List<BLHCoordinate> GaussToBLH(List<GaussCoordinate> gaussSources) {
        List<BLHCoordinate> blhCoordinates = new ArrayList<>();
        for (GaussCoordinate gaussSource : gaussSources) {
            BLHCoordinate blh = ProjectionConverter.GaussToBLNormal(gaussSource);
            blhCoordinates.add(blh);
        }
        return blhCoordinates;
    }

    /**
     * 高斯转经纬度
     * @param gaussSource
     * @return
     */
    public BLHCoordinate GaussToBLH(GaussCoordinate gaussSource) {
        if (gaussSource == null) {
            return null;
        }
        BLHCoordinate blh = ProjectionConverter.GaussToBLNormal(gaussSource);
        return blh;
    }

    /**
     * 高斯转空间坐标
     * @param gaussCoordinate
     * @return
     */
    public SpaceCoordinate GaussToXYZ(GaussCoordinate gaussCoordinate) {
        if (gaussCoordinate == null) {
            return null;
        }
        BLHCoordinate blh=GaussToBLH(gaussCoordinate);
        return ProjectionConverter.BLHtoXYZ(blh,Ellipsoid.getSpheroid(blh.ellipsoid()));
    }

    /**
     * 经纬度转空间坐标
     * @param blhCoordinate
     * @return
     */
    public SpaceCoordinate BLHToXYZ(BLHCoordinate blhCoordinate) {
        if (blhCoordinate == null) {
            return null;
        }
        return ProjectionConverter.BLHtoXYZ(blhCoordinate,Ellipsoid.getSpheroid(blhCoordinate.ellipsoid()));
    }

    /**
     *
     * @param source
     * @return
     */
    public BLHCoordinate XYZToBLH(SpaceCoordinate source) {
        if (source == null) {
            return null;
        }
        return ProjectionConverter.XYZtoBLH(source,Ellipsoid.getSpheroid(source.ellipsoid()));
    }
}
