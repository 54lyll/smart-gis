package com.wonderzh.gis.cad.dxf;

import com.smartwater.common.util.EncodingUtil;
import com.wonderzh.gis.cad.CADParser;
import com.wonderzh.gis.cad.xxf.Dxf;
import com.wonderzh.gis.cad.xxf.Line;
import com.wonderzh.gis.cad.xxf.LwPolyLine;
import com.wonderzh.gis.cad.xxf.Vertex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * dxf解析器
 * @Author: wonderzh
 * @Date: 2019/3/15
 * @Version: 1.0
 */

public class DxfReader {
    private static final Logger logger = LoggerFactory.getLogger(CADParser.class);

    private BufferedReader bufferedReader;
    //与组码对应的指针
    private String attribute, value;
    //记录线段数量,包括单线和多段线
    private int lineCount = 1;
    //格式话数据，保留5位小数
    private static final DecimalFormat df = new DecimalFormat("0.00000");

    public DxfReader(String dxfPath) {
        File f = new File(dxfPath);
        if (!f.exists()) {
            logger.error("DXF file not exist：{}", dxfPath);
        }
        try {
            //识别文件编码为GBK还是UTF-8
            String encoding = EncodingUtil.encoding(dxfPath);
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(f), encoding));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析Entities段数据
     *
     * @param
     */
    public Dxf resolveEntities() {
        ArrayList<Line> lineList = new ArrayList<>(100);
        ArrayList<LwPolyLine> lwPolyLineList = new ArrayList<>();
        ArrayList<String> circleList = new ArrayList<>();
        ArrayList<String> arcList = new ArrayList<>();
        Map<String, Vertex> vertexMap = new LinkedHashMap<>(100);

        logger.debug("Begin resolve entities");
        String title = "";
        // 文件结束标志
        while (!"EOF".equals(title = readLine())) {
            // 实体段开始
            if ("ENTITIES".equals(title)) {
                String item = "";
                while (!"ENDSEC".equals(item = readLine())) {
                    // 判断CIRCLE实体开始
                    if (item.equals("CIRCLE")) {
                        readCircle(circleList);
                    }
                    // 判断ARC实体开始
                    if (item.equals("ARC")) {
                        readArc(arcList);
                    }
                    // 判断LINE实体开始
                    if (item.equals("LINE")) {
                        Line line = readStraightLine(lineList,vertexMap);
                        lineList.add(line);
                        lineCount++;
                    }
                    // 判断LWPOLYLINE实体开始
                    if (item.equals("LWPOLYLINE")) {
                        LwPolyLine polyLine = readPolyLine(lwPolyLineList,vertexMap);
                        lwPolyLineList.add(polyLine);
                        lineCount++;
                    }
                }
            }
        }
        List<Vertex> vertices = new ArrayList<>(vertexMap.values());
        return new Dxf(lineList,lwPolyLineList,vertices);
    }

    /**
     * 解析直线
     *
     * @return
     * @param lineList
     * @param vertexMap
     */
    private Line readStraightLine(ArrayList<Line> lineList, Map<String, Vertex> vertexMap) {
        Line line = new Line();
        line.setId(lineCount);
        String aX="",aY="",zX="",zY="";
        while (true) {
            attribute = readLine();
            if (attribute.equals("  8")) {//图层
                value = readLine();
                line.setLayer(value.trim());
            }
            if (attribute.equals(" 10")) {//顶点x坐标
                aX =  df.format(Double.valueOf(readLine().trim()));
                //aX = readLine().trim();
            }
            if (attribute.equals(" 20")) {//顶点y坐标
                aY = df.format(Double.valueOf(readLine().trim()));
                //aY = readLine().trim();
            }
            if (attribute.equals(" 30")) {//顶点z坐标
                String aZ =df.format(Double.valueOf(readLine().trim()));
                String key = getKey(aX, aY);
                //配置顶点坐标
                Vertex headVertex = vertexMap.get(key);
                if (headVertex != null) {
                    line.setANode(headVertex);
                } else {
                    headVertex = new Vertex(vertexMap.size()+1, aX, aY, aZ);
                    line.setANode(headVertex);
                    vertexMap.put(key, headVertex);
                }
            }
            if (attribute.equals(" 11")) {//端点x坐标
                zX =df.format(Double.valueOf(readLine().trim()));
                //zX = readLine().trim();
            }
            if (attribute.equals(" 21")) {//端点y坐标
                zY = df.format(Double.valueOf(readLine().trim()));
                //zY = readLine().trim();
            }
            if (attribute.equals(" 31")) {//端点z坐标
                String zZ = df.format(Double.valueOf(readLine().trim()));
                //配置端点坐标
                String key = getKey(zX, zY);
                Vertex tailVertex = vertexMap.get(key);
                if (tailVertex != null) {
                    line.setZNode(tailVertex);
                } else {
                    tailVertex = new Vertex(vertexMap.size()+1, zX, zY, zZ);
                    line.setZNode(tailVertex);
                    vertexMap.put(key, tailVertex);
                }
                //计算长度
                line.calculateLength();
            }
            if (attribute.equals("  0")) {//一个line 实体结束
                break;
            }
        }
        return line;
    }

    private String getKey(String aX, String aY) {
        return aX + aY;
        //double x = Double.valueOf(aX);
        //double y = Double.valueOf(aY);
        //x=DoubleArithUtil.mul(x, 31);
        //x = DoubleArithUtil.add(x, y);
        //y = DoubleArithUtil.mul(x, 31);
        //return DoubleArithUtil.add(x , y);

    }

    /**
     * 解析多段线
     * @param lwPolyLineList
     * @param vertexMap
     */
    private LwPolyLine readPolyLine(ArrayList<LwPolyLine> lwPolyLineList, Map<String, Vertex> vertexMap) {
        LwPolyLine polyLine = new LwPolyLine();
        polyLine.setId(lineCount);
        String x="",y="";
        while (true) {
            attribute = readLine();
            if (attribute.equals("  8")) {//图层
                value = readLine();
                polyLine.setLayer(value);
            }
            if (attribute.equals(" 90")) {//顶点数
                value = readLine();
                polyLine.setVertexCount(Integer.parseInt(value.trim()));
            }
            //封装所有顶点
            while (attribute.equals(" 10")) {//顶点x坐标
                Vertex vertex ;
                x = df.format(Double.valueOf(readLine().trim()));
                attribute = readLine();
                if (attribute.equals(" 20")) {//顶点y坐标
                    y = df.format(Double.valueOf(readLine().trim()));
                    String key = getKey(x, y);
                    vertex = vertexMap.get(key);
                    if (vertex != null) {
                        polyLine.getVertices().add(vertex);
                    } else {
                        vertex=new Vertex(vertexMap.size()+1, x, y, null);
                        polyLine.getVertices().add(vertex);
                        vertexMap.put(key, vertex);
                    }
                }
                attribute = readLine();
                //if (attribute.equals(" 42")) {// 有些隐含42没有出现
                //    value = readLine();
                //    vertex.setBulge(value.trim());
                //}
            }

            if (attribute.equals("  0")) {
                break;
            }
        }
        polyLine.setANode(polyLine.getVertices().removeFirst());
        polyLine.setZNode(polyLine.getVertices().removeLast());
        //计算长度
        polyLine.calculateLength();
        return polyLine;
    }

    /**
     * 解析弧线
     * @param arclist
     */
    private void readArc(ArrayList<String> arclist) {
        while (true) {
            attribute = readLine();
            if (attribute.equals(" 10")) {
                value = readLine();
                arclist.add(value);
            }
            if (attribute.equals(" 20")) {
                value = readLine();
                arclist.add(value);
            }
            if (attribute.equals(" 30")) {
                value = readLine();
                arclist.add(value);
            }
            if (attribute.equals(" 40")) {// 半径
                value = readLine();
                arclist.add(value);
            }
            if (attribute.equals(" 50")) {// 弧的起始角度
                value = readLine();
                arclist.add(value);
            }
            if (attribute.equals(" 51")) {// 弧的终止角度
                value = readLine();
                arclist.add(value);
                break;
            }
        }
    }

    /**
     * 解析圆
     * @param circlelist
     */
    private void readCircle(ArrayList<String> circlelist) {
        while (true) {
            attribute = readLine();
            if (attribute.equals(" 10")) {
                value = readLine();
                circlelist.add(value);
            }
            if (attribute.equals(" 20")) {
                value = readLine();
                circlelist.add(value);
            }
            if (attribute.equals(" 30")) {
                value = readLine();
                circlelist.add(value);
            }
            if (attribute.equals(" 40")) {
                value = readLine();
                circlelist.add(value);
                break;
            }
        }
    }

    private String readLine() {
        String temp = null;
        try {
            temp = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return temp;
    }


}
