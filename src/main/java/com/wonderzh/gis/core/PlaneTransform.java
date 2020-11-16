package com.wonderzh.gis.core;

import com.wonderzh.gis.core.converter.EllipsoidConverter;
import com.wonderzh.gis.core.coordinate.AbstractCoordinate;
import com.wonderzh.gis.core.coordinate.BLHCoordinate;
import com.wonderzh.gis.core.coordinate.GaussCoordinate;
import com.wonderzh.gis.core.param.FourParam;
import com.wonderzh.gis.core.util.GeneralUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 不同椭球体，平面坐标转换服务
 * @Author: wonderzh
 * @Date: 2019/3/22
 * @Version: 1.0
 */

@Service
public class PlaneTransform implements EllipsoidTransform {

    private ProjectionTransform projectionTransform=new ProjectionTransform();

    /**
     * 计算四参数
     *
     * @param blhSources   已知控制点经纬度坐标，带坐标系类型
     * @param gaussTargets 已知控制点高斯平面坐标，带坐标系类型
     * @throws ClassCastException if source not instance of BLHCoordinate.class and target not instance of GaussCoordinate.class
     * @return
     */

    @Override
    public FourParam computeParam(List<? extends AbstractCoordinate> blhSources, List<? extends AbstractCoordinate> gaussTargets, boolean desc) {
        List<GaussCoordinate> gaussSources = projectionTransform.BLHToGauss((List<BLHCoordinate>)blhSources);
        FourParam fourParam=null;
        if (desc) {
            fourParam = new FourParam(gaussSources, (List<GaussCoordinate>)gaussTargets);
        } else {
            fourParam = new FourParam((List<GaussCoordinate>)gaussTargets, gaussSources);
        }
        return fourParam;

    }

    /**
     * 高斯平面坐标转换
     *
     * @param source   需要被转换的高斯平面坐标
     * @param param  四参法参数
     * @throws ClassCastException if source not instance of GaussCoordinate.class and param not instance of FourParam.class
     * @return
     */
    @Override
    public List<GaussCoordinate> planeTransformBatch(List<GaussCoordinate> source, FourParam param) {
        if (GeneralUtil.isOneEmpty(source, param)) {
            return null;
        }
        List<GaussCoordinate> gaussTargets = new ArrayList<>(source.size());
        for (GaussCoordinate coordinate : source) {
            GaussCoordinate target =planeTransform(coordinate,param);
            gaussTargets.add(target);
        }
        return gaussTargets;
    }


    @Override
    public GaussCoordinate planeTransform(GaussCoordinate source, FourParam param) {
        if (GeneralUtil.isOneEmpty(source, param)) {
            return null;
        }
        GaussCoordinate target = EllipsoidConverter.transformPlaneCoordinate(source, param);

        return target;
    }


}
