package com.wonderzh.gis.cad.xxf;


/**
 * @Author: wonderzh
 * @Date: 2019/3/15
 * @Version: 1.0
 */

public abstract class AbstractLine {
    //id
    protected int id;
    //起点
    protected Vertex aNode;
    //端点
    protected Vertex zNode;
    //图层
    protected String layer;
    //长度
    protected String length;

    public abstract double calculateLength();

    public abstract Vertex getANode();

    public abstract Vertex getZNode();

    public abstract int getId();

    protected double computeDistance(Vertex head, Vertex tail) {
        if (null == head || null == tail) {
            return .0;
        }
        if (head.equals(tail)) {
            return .0;
        }

        double a = Math.abs(Double.valueOf(head.getX()) - Double.valueOf(tail.getX())); // 直角三角形的直边a
        double b = Math.abs(Double.valueOf(head.getY()) - Double.valueOf(tail.getY())); // 直角三角形的直边b

        double min = Math.min(a, b); // 短直边
        double max = Math.max(a, b); // 长直边

        /**
         * 为防止计算平方时float溢出，做如下转换
         * √(min²+max²) = √((min/max)²+1) * abs(max)
         */
        double inner = min / max;
        return Math.sqrt(inner * inner + 1.0) * max;
    }

}
