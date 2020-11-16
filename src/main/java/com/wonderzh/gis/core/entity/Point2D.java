package com.wonderzh.gis.core.entity;

import com.wonderzh.gis.core.util.DoubleArithUtil;
import lombok.Data;

/**
 * @Author: wonderzh
 * @Date: 2019/4/3
 * @Version: 1.0
 */
@Data
public class Point2D {
    private Double x;
    private Double y;

    public Point2D() {

    }

    public Point2D(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public Point2D(double[] coord) {
        if (coord.length != 2) {
            throw new IllegalArgumentException();
        }
        this.x = coord[0];
        this.y = coord[1];
    }

    public void round() {
        this.x = DoubleArithUtil.round(x, 6);
        this.y = DoubleArithUtil.round(y, 6);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Point2D other = (Point2D) obj;
        if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
            return false;
        if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
            return false;
        return true;
    }

}
