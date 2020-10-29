package com.wonderzh.gis.core;

import com.wonderzh.gis.core.converter.MapConverter;
import com.wonderzh.gis.core.coordinate.BLHCoordinate;

import java.util.List;

/**
 * 商用地图服务
 * @Author: wonderzh
 * @Date: 2019/3/22
 * @Version: 1.0
 */

public class MapTransform {

    public List<BLHCoordinate> wgsToGcj(List<BLHCoordinate> blhSource) {
        if (blhSource == null || blhSource.size() == 0) {
            return null;
        }
        for (BLHCoordinate blhS : blhSource) {
            double[] temp=MapConverter.wgs84togcj02(blhS.L_longitude(), blhS.B_latitude());
            blhS.setL_longitude(temp[0]);
            blhS.setB_latitude(temp[1]);
        }
        return blhSource;
    }

}
