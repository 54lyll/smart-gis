package com.wonderzh.gis.core;

import com.wonderzh.gis.core.converter.EllipsoidConverter;
import com.wonderzh.gis.core.coordinate.SpaceCoordinate;
import com.wonderzh.gis.core.param.SevenParam;
import com.wonderzh.gis.core.util.GeneralUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 不同椭球体，空间坐标转换服务
 * @Author: wonderzh
 * @Date: 2019/3/25
 * @Version: 1.0
 */

public class SpaceTransform {

    /**
     * 空间坐标批量转换
     * @param sources
     * @param sevenParam
     * @param targetEllipsoid
     * @return
     */
     public List<SpaceCoordinate> transformBatch(List<SpaceCoordinate> sources, SevenParam sevenParam, Integer targetEllipsoid) {
         if (GeneralUtil.isEmpty(sevenParam)) {
             return null;
         }
         if (sevenParam == null) {
             throw new NullPointerException();
         }
         List<SpaceCoordinate> targetList = new ArrayList<>(sources.size());
         for (SpaceCoordinate source : sources) {
            SpaceCoordinate target= EllipsoidConverter.transformSpaceCoordinate(source, sevenParam);
             targetList.add(target);
         }
         return targetList;
     }


}
