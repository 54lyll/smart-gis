package com.wonderzh.gis.core.entity;

import lombok.Data;

/**
 * 投影信息
 * @Author: wonderzh
 * @Date: 2019/3/22
 * @Version: 1.0
 */

@Data
public class Projection {
    /**
     * 投影带号
     */
    private int num;
    /**
     * 投影带宽
     */
    private int zoneWide;

    /**
     * 椭球体类型
     */
    private Integer ellipsoid;

    public Projection(int num, int zoneWide, int ellipsoid) {
        this.num = num;
        this.zoneWide = zoneWide;
        this.ellipsoid = ellipsoid;
    }


}
