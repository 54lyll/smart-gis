package com.wonderzh.gis.core.util;

import com.wonderzh.gis.core.entity.Box;
import com.wonderzh.gis.core.entity.Point2D;

import java.util.List;

/**
 * 图形工具类
 *
 * @Author: wonderzh
 * @Date: 2019/10/22
 * @Version: 1.0
 */

public class GeoUtil {


    /**
     * <p>
     * <b>查找离散点集中的(min_x, min_Y) (max_x, max_Y)</b>
     * <p>
     * <pre>
     * 查找离散点集中的(min_x, min_Y) (max_x, max_Y)
     * </pre>
     *
     * @author  ManerFan 2015年4月9日
     * @param   Point2Ds    离散点集
     * @return  [(min_x, min_Y), (max_x, max_Y)]
     */
    public static Point2D[] calMinMaxDots(final List<Point2D> Point2Ds) {
        if (null == Point2Ds || Point2Ds.isEmpty()) {
            return null;
        }

        double min_x = Point2Ds.get(0).getX(), max_x = Point2Ds.get(0).getX();
        double min_y = Point2Ds.get(0).getY(), max_y = Point2Ds.get(0).getY();

        /* 这里存在优化空间，可以使用并行计算 */
        for (Point2D Point2D : Point2Ds) {
            if (min_x > Point2D.getX()) {
                min_x = Point2D.getX();
            }

            if (max_x < Point2D.getX()) {
                max_x = Point2D.getX();
            }

            if (min_y > Point2D.getY()) {
                min_y = Point2D.getY();
            }

            if (max_y < Point2D.getY()) {
                max_y = Point2D.getY();
            }
        }

        Point2D ws = new Point2D(min_x, min_y);
        Point2D en = new Point2D(max_x, max_y);

        return new Point2D[] { ws, en };
    }

    /**
     * <p>
     * <b>求矩形面积平方根</b>
     * <p>
     * <pre>
     * 以两个点作为矩形的对角线上的两点，计算其面积的平方根
     * </pre>
     *
     * @author  ManerFan 2015年4月9日
     * @param   ws  西南点
     * @param   en  东北点
     * @return  矩形面积平方根
     */
    public static double calRectAreaSquare(Point2D ws, Point2D en) {
        if (null == ws || null == en) {
            return .0;
        }

        /* 为防止计算面积时float溢出，先计算各边平方根，再相乘 */
        return Math.sqrt(Math.abs(ws.getX() - en.getX()))
                * Math.sqrt(Math.abs(ws.getY() - en.getY()));
    }

    /**
     * <p>
     * <b>求两点之间的长度</b>
     * <p>
     * <pre>
     * 求两点之间的长度
     * </pre>
     *
     * @author  ManerFan 2015年4月10日
     * @param   ws  西南点
     * @param   en  东北点
     * @return  两点之间的长度
     */
    public static double calLineLen(Point2D ws, Point2D en) {
        if (null == ws || null == en) {
            return .0;
        }

        if (ws.equals(en)) {
            return .0;
        }

        double a = Math.abs(ws.getX() - en.getX()); // 直角三角形的直边a
        double b = Math.abs(ws.getY() - en.getY()); // 直角三角形的直边b

        double min = Math.min(a, b); // 短直边
        double max = Math.max(a, b); // 长直边

        /**
         * 为防止计算平方时float溢出，做如下转换
         * √(min²+max²) = √((min/max)²+1) * abs(max)
         */
        double inner = min / max;
        return Math.sqrt(inner * inner + 1.0) * max;
    }

    /**
     * <p>
     * <b>求两点间的中心点</b>
     * <p>
     * <pre>
     * 求两点间的中心点
     * </pre>
     *
     * @author  ManerFan 2015年4月10日
     * @param   ws  西南点
     * @param   en  东北点
     * @return  两点间的中心点
     */
    public static Point2D calCerter(Point2D ws, Point2D en) {
        if (null == ws || null == en) {
            return null;
        }

        return new Point2D(ws.getX() + (en.getX() - ws.getX()) / 2.0, ws.getY()
                + (en.getY() - ws.getY()) / 2.0);
    }

    /**
     * <p>
     * <b>计算向量角</b>
     * <p>
     * <pre>
     * 计算两点组成的向量与x轴正方向的向量角
     * </pre>
     *
     * @author  ManerFan 2015年4月17日
     * @param   s   向量起点
     * @param   d   向量终点
     * @return  向量角
     */
    public static double angleOf(Point2D s, Point2D d) {
        double dist = calLineLen(s, d);

        if (dist <= 0) {
            return .0;
        }

        double x = d.getX() - s.getX(); // 直角三角形的直边a
        double y = d.getY() - s.getY(); // 直角三角形的直边b

        if (y >= 0.) { /* 1 2 象限 */
            return Math.acos(x / dist);
        } else { /* 3 4 象限 */
            return Math.acos(-x / dist) + Math.PI;
        }
    }

    /**
     * <p>
     * <b>修正角度</b>
     * <p>
     * <pre>
     * 修正角度到 [0, 2PI]
     * </pre>
     *
     * @author  ManerFan 2015年4月17日
     * @param   angle   原始角度
     * @return  修正后的角度
     */
    public static double reviseAngle(double angle) {
        while (angle < 0.) {
            angle += 2 * Math.PI;
        }
        while (angle >= 2 * Math.PI) {
            angle -= 2 * Math.PI;
        }

        return angle;
    }

    /**
     * 获取多边形的矩形外包边界
     * @param polygon
     * @return
     */
    public static Box getRectangleBoundary(List<Point2D> polygon) {
        Point2D head = polygon.get(0);
        //计算矩形外界
        double xMin = head.getX(), xMax = head.getX(),
                yMin = head.getY(), yMax = head.getY();
        for (int i = 1; i < polygon.size(); i++) {
            double x = polygon.get(i).getX();
            double y = polygon.get(i).getY();
            if (x < xMin) {
                xMin = x;
            } else if (x > xMax) {
                xMax = x;
            }
            if (y < yMin) {
                yMin = y;
            } else if (y > yMax) {
                yMax = y;
            }
        }
        return new Box(xMin, xMax, yMin, yMax);
    }

    /**
     * 判断该地理坐标是否在最大范围区域内
     * 不包含在边界上的点
     *
     * @param polygon
     * @param point
     * @return
     */
    public static boolean isInMaxArea(List<Point2D> polygon, Point2D point) {
        if (polygon == null || polygon.size() == 0) {
            return false;
        }
        //计算矩形外界
        Box boundary = getRectangleBoundary(polygon);
        double xMin = boundary.getxMin(), xMax = boundary.getxMax(),
                yMin = boundary.getyMin(), yMax = boundary.getyMax();

        return (point.getX() < xMin || point.getX() > xMax ||
                point.getY() < yMin || point.getY() > yMax);
    }

    /**
     * 判断坐标是否在多边形区域内
     * 不包含在边界上的点
     * @param polygon
     * @param point
     * @return
     */
    public static boolean isInAccurateArea(List<Point2D> polygon, Point2D point) {
        double pointLon = point.getX();
        double pointLat = point.getY();

        double[] lon=new double[polygon.size()];
        double[] lat=new double[5];
        for (int i = 0; i < polygon.size(); i++) {
            lon[i] =polygon.get(i).getX();
            lat[i] = polygon.get(i).getY();
        }
        return isInAccurateArea(pointLon, pointLat, lon, lat);
    }

    /**
     * 判断坐标是否在多边形区域内
     * 不包含在边界上的点
     *
     * @param pointLon 要判断的点的纵坐标
     * @param pointLat 要判断的点的横坐标
     * @param lon      指定区域的纵坐标组成的数组
     * @param lat      指定区域的横坐标组成的数组
     * @return
     */
    public static boolean isInAccurateArea(double pointLon, double pointLat, double[] lon, double[] lat) {
        // 代表有几个点
        int vertexNum = lon.length;
        boolean result = false;
        for (int i = 0, j = vertexNum - 1; i < vertexNum; j = i++) {
            // 满足条件，与多边形相交一次，result布尔值取反一次，奇数个则在区域内
            if ((lon[i] > pointLon) != (lon[j] > pointLon) &&
                    (pointLat < (lat[j] - lat[i]) * (pointLon - lon[i]) / (lon[j] - lon[i]) + lat[i])) {
                result = !result;
            }
        }
        return result;
    }




}
