package com.wonderzh.gis.cad.dxf;

import java.awt.geom.Point2D;
import java.io.IOException;

/**
 * @Author: wonderzh
 * @Date: 2019/3/26
 * @Version: 1.0
 */

public class DxfWriter {


    private void writePolylineAD(Point2D[] _pts, int _color, String _lineStyle, String _layerName) throws IOException {
        if (_pts.length > 2) {
            for (int i = 0; i <= _pts.length - 1; i++) {
                if (i < _pts.length - 1) {
                    Point2D[] pts = new Point2D.Double[2];
                    pts[0] = _pts[i];
                    pts[1] = _pts[i + 1];
                    writePolylineAD(pts, _color, _lineStyle, _layerName);
                }
            }
            return;
        }
        writeBufferLine("  0");
        writeBufferLine("POLYLINE");
        writeBufferLine("  8");
        writeBufferLine(_layerName);
        writeBufferLine(" 62");
        writeBufferLine(new Integer(_color).toString());
        writeBufferLine("  6");
        writeBufferLine(_lineStyle);
        writeBufferLine(" 66");
        writeBufferLine("     1");
        writeBufferLine(" 10");
        writeBufferLine("0.0");
        writeBufferLine(" 20");
        writeBufferLine("0.0");
        writeBufferLine(" 30");
        writeBufferLine("0.0");

        for (int j = 0; j < _pts.length; j++) {
            writeBufferLine("  0");
            writeBufferLine("VERTEX");
            writeBufferLine("  8");
            writeBufferLine(_layerName);
            writeBufferLine(" 10");
            writeBufferLine(new Double(_pts[j].getX()).toString());
            writeBufferLine(" 20");
            writeBufferLine(new Double(_pts[j].getY()).toString());
            writeBufferLine(" 30");
            writeBufferLine("0.0");
        }

        writeBufferLine("  0");
        writeBufferLine("SEQEND");
        writeBufferLine("  8");
        writeBufferLine(_layerName);
    }

    private void writeBufferLine(String layerName) {
    }
}
