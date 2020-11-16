package com.wonderzh.gis.core.entity;

import com.wonderzh.gis.core.util.DoubleArithUtil;
import lombok.Data;


/**
 * 客户端点对象
 * @Author: wonderzh
 * @Date: 2019/8/23
 * @Version: 1.0
 */
@Data
public class PointDTO {

    private Double x;
    private Double y;

    private Double z;
    /**
     * 椭球体类型
     */
    private Integer ellipsoid;
    /**
     *坐标系
     */
    private Byte coordinateSys;
    /**
     * 带号
     */
    private Integer projNum;
    /**
     * 带类型
     */
    private Integer projType;


    public void round() {
        this.x = DoubleArithUtil.round(x, 6);
        this.y = DoubleArithUtil.round(y, 6);
        this.z = DoubleArithUtil.round(z, 6);
    }

}
