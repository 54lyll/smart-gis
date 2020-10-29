package com.wonderzh.gis.cad;

import com.wonderzh.gis.cad.dxf.DxfReader;
import com.wonderzh.gis.cad.xxf.Dxf;

/**
 * CAD  .dxf 文件解析器
 *
 * @Author: wonderzh
 * @Date: 2019/3/13
 * @Version: 1.0
 */

public class CADParser {

    public static Dxf readCad(String path) {
        DxfReader dxfReader = new DxfReader(path);
        Dxf dxf=dxfReader.resolveEntities();
        return  dxf;
    }



}
