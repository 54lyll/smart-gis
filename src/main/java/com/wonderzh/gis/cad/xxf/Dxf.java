package com.wonderzh.gis.cad.xxf;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * dxf 对象
 *
 * @Author: wonderzh
 * @Date: 2019/3/26
 * @Version: 1.0
 */
@Data
public class Dxf {
    private List<Line> lines;
    private List<LwPolyLine> lwPolyLines;
    private List<String> circleList;
    private List<String> arcList;
    private List<Vertex> vertexes;

    public Dxf(List<Line> lines, List<LwPolyLine> lwPolyLines, List<Vertex> vertexes) {
        this.lines = lines;
        this.lwPolyLines = lwPolyLines;
        this.vertexes = vertexes;
    }

    public void display() {
        System.out.println(lines);
        for (Line line : lines) {
            System.out.println(line);
        }
        System.out.println(lwPolyLines);
        for (LwPolyLine polyLine : lwPolyLines) {
            System.out.println(polyLine);
        }
    }

    public List<AbstractLine> getAllLines() {
        List<AbstractLine> allLines = new ArrayList<>();
        if (lines != null) {
            allLines.addAll(lines);
        }
        if (lwPolyLines != null) {
            allLines.addAll(lwPolyLines);
        }
        return allLines;
    }
}
