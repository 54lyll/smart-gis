package com.wonderzh.gis.core.util;

import com.wonderzh.gis.cad.xxf.Vertex;
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
     * cad节点转高斯平面节点
     * 节点带有序号id，以及投影信息
     * @param vertexList  cad 节点
     * @param projection  投影信息
     * @return GaussCoordinateNode
     */
    public static List<GaussCoordinate> vertexToGauss(List<Vertex> vertexList, Projection projection) {
        if (vertexList == null || vertexList.size() == 0) {
            return null;
        }
        List<GaussCoordinate> gaussList = new ArrayList<>(vertexList.size());
        for (Vertex vertex : vertexList) {
            double x = Double.valueOf(vertex.getX());
            double y = Double.valueOf(vertex.getY());
            GaussCoordinate gauss = new GaussCoordinate();
            if (vertex.getZ() != null) {
                double h = Double.valueOf(vertex.getZ());
                gauss.setZ_H(h);
            }
            gauss.setNo(String.valueOf(vertex.getId()));
            gauss.setX_L(x);
            gauss.setY_B(y);
            gauss.setProjNum(projection.getNum());
            gauss.setProjType(projection.getZoneWide());
            gauss.setEllipsoid(projection.getEllipsoid());
            gaussList.add(gauss);
        }
        return gaussList;
    }


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
