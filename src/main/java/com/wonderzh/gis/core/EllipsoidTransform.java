package com.wonderzh.gis.core;

import com.wonderzh.gis.core.coordinate.AbstractCoordinate;
import com.wonderzh.gis.core.coordinate.GaussCoordinate;
import com.wonderzh.gis.core.param.FourParam;

import java.util.List;

/**
 * @Author: wonderzh
 * @Date: 2019/3/25
 * @Version: 1.0
 */
public interface EllipsoidTransform {

    /**
     * 计算参数
     *
     * @param sources   已知控制点经纬度，带坐标系类型
     * @param targets 已知控制点高斯平面坐标，带坐标系类型
     * @return
     */
    FourParam computeParam(List<? extends AbstractCoordinate> sources, List<? extends AbstractCoordinate> targets, boolean desc);




    /**
     * 批量平面坐标转换
     * @param sources
     * @param param
     * @return
     */
    List<GaussCoordinate> planeTransformBatch(List<GaussCoordinate> sources, FourParam param);

    /**
     * 单点平面坐标转换
     * @param source
     * @param param
     * @return
     */
    GaussCoordinate planeTransform(GaussCoordinate source, FourParam param);
}
