package com.wonderzh.gis.core.util;

import com.wonderzh.gis.core.converter.ProjectionConverter;
import com.wonderzh.gis.core.coordinate.GaussCoordinate;
import com.wonderzh.gis.core.entity.Projection;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wonderzh
 * @Date: 2019/3/22
 * @Version: 1.0
 */

public class GisUtil {



    /**
     * 计算带号
     * @param lng 经度
     * @param projType 带类型 3度或6度
     * @return
     */
    public static int countProjNumber(double lng ,int projType) {
        if (projType == ProjectionConverter.THREE_DEGREE) {
            return (int)(lng+1.5) / 3;
        } else if (projType == ProjectionConverter.SIX_DEGREE) {
            return (int)(lng + 6) / 6;
        }
        return -1;
    }

    /**
     * 计算中央经线
     * @param lng 经度
     * @param projType 带类型
     * @return
     */
    public static double countCentralMeridian(double lng ,int projType) {
        int proNum = countProjNumber(lng, projType);
        return countCentralMeridian(proNum, projType);
    }

    /**
     * 计算中央经线
     * @param projNum 带号
     * @param projType 带类型
     * @return
     */
    public static double countCentralMeridian(int projNum ,int projType) {
        if (projType == ProjectionConverter.THREE_DEGREE) {
            return projNum * 3;
        } else if (projType == ProjectionConverter.SIX_DEGREE) {
            return projNum * 6-3;
        }
        return -1D;
    }

    public static void main(String[] args) {
        //System.out.println(countProjNumber(104.112031,3));
        //System.out.println(countCentralMeridian(104.112031,3));
        System.out.println(countProjNumber(3, 3));
    }

}
