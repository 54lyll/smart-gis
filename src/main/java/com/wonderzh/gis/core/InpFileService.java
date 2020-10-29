package com.wonderzh.gis.core;

import com.smartwater.common.constant.Constant;
import com.smartwater.common.util.FileUtil;
import com.wonderzh.gis.cad.xxf.AbstractLine;
import com.wonderzh.gis.cad.xxf.Vertex;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * inp文件写服务
 */

public class InpFileService {

    public static final String inpTemplateFile = "inp/model.inp";

    /**
     * 将CAD点线数据转换成Inp文件
     * @param nodes 节点
     * @param lines  单线、多段线
     * @return  inp文件输出path
     * @throws IOException
     */
    public String writeInp(List<Vertex> nodes, List<? extends AbstractLine> lines) throws IOException {
        String content = FileUtil.readFileInClassPath(inpTemplateFile);
        //String content = readToString(inpTemplateFile);
        StringBuilder inpSb = new StringBuilder(content);

        //格式化节点行头
        StringBuilder JUNCTIONS = new StringBuilder("[JUNCTIONS]" + Constant.LINE_SEPARATOR + ";ID               	Elev        	Demand      	Pattern     " + Constant.LINE_SEPARATOR);
        //格式化节点坐标行头
        StringBuilder COORDINATES = new StringBuilder("[COORDINATES]" + Constant.LINE_SEPARATOR + ";NodeType            	X-Coord         	Y-Coord " + Constant.LINE_SEPARATOR);
        //写节点
        for (Vertex node : nodes) {
            JUNCTIONS.append(node.getId()).append("               	0              	0                              	;").append(Constant.LINE_SEPARATOR);
            COORDINATES.append(node.getId()).append("               	").append(String.format("%.2f",Double.valueOf(node.getX()))).append("           	").append(String.format("%.2f",Double.valueOf(node.getY()))).append(Constant.LINE_SEPARATOR);
        }

        //格式化管线行头
        StringBuilder PIPE = new StringBuilder("[PIPES]" + Constant.LINE_SEPARATOR + ";ID              	Node1           	Node2           	Length      	Diameter    	Roughness   	MinorLoss   	Status" + Constant.LINE_SEPARATOR);
        //写管线
        for (AbstractLine line : lines) {
            PIPE.append(line.getId()).append("               	").append(line.getANode().getId()).append("                	").append(line.getZNode().getId()).append("               	 ").append(line.calculateLength())
                    .append("        	12           	100            	0           	Open         ;").append(Constant.LINE_SEPARATOR);
        }

        //分区空行
        JUNCTIONS.append(Constant.LINE_SEPARATOR);
        PIPE.append(Constant.LINE_SEPARATOR);
        COORDINATES.append(Constant.LINE_SEPARATOR);

        int start = inpSb.indexOf("[JUNCTIONS]");
        int end = inpSb.indexOf("[RESERVOIRS]");
        inpSb.replace(start, end, JUNCTIONS.toString());

        int pipeStart = inpSb.indexOf("[PIPES]");
        int pipeEnd = inpSb.indexOf("[PUMPS]");
        inpSb.replace(pipeStart, pipeEnd, PIPE.toString());

        int nodeStart = inpSb.indexOf("[COORDINATES]");
        int nodeSend = inpSb.indexOf("[VERTICES]");
        inpSb.replace(nodeStart, nodeSend, COORDINATES.toString());

        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMddhhmm");
        String newFileName = "user" + "-" + dataFormat.format(new Date()) + ".inp";
        File newFile = new File(newFileName);
        BufferedWriter bw_NewFile = new BufferedWriter(new FileWriter(newFile));
        bw_NewFile.write(inpSb.toString());
        bw_NewFile.close();
        return newFile.getPath();
    }


}
