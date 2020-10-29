package com.wonderzh.gis.math.arithmetic;//package com.wonderzh.gis.math.arithmetic;
//
//import com.wonderzh.gis.math.Edge;
//import com.wonderzh.gis.math.Vector2D;
//import lombok.extern.slf4j.Slf4j;
//import org.locationtech.jts.geom.Coordinate;
//import org.locationtech.jts.geom.GeometryFactory;
//import org.locationtech.jts.geom.LineString;
//import org.locationtech.jts.geom.Polygon;
//import org.locationtech.jts.operation.polygonize.Polygonizer;
//
//import java.util.*;
//
///**
// * 离散点边界探索
// *
// * @Author: wonderzh
// * @Date: 2019/10/16
// * @Version: 1.0
// */
//@Slf4j
//public class AlphaShape {
//
//    private double radius;
//
//    private int sortCount = 0;
//
//    public AlphaShape(double radius) {
//        this.radius = radius;
//    }
//
//    public List<List<Vector2D>> boundaryPoints(List<Vector2D> points) {
//
//        if (points == null || points.size() == 0) {
//            return null;
//        }
//        int no = 1;
//        for (Vector2D point : points) {
//            point.setId(no);
//            no++;
//        }
//        //Map<Integer,Vector2D> boudary = new HashMap<>();
//        List<Vector2D> boundary = new ArrayList<>();
//        List<Edge> lines = new ArrayList<>();
//        int edgeNo = 1;
//        for (int i = 0; i < points.size(); i++) {
//
//            // k从i+1开始，减少重复计算
//            for (int k = i + 1; k < points.size(); k++) {
//                // 跳过距离大于直径的情况
//                if (points.get(i).distanceToPoint(points.get(k)) > 2 * radius) {
//                    continue;
//                }
//                // 两个圆心
//                Vector2D c1, c2;
//                // 线段中点
//                Vector2D center = points.get(i).add(points.get(k)).center();
//                // 方向向量 d = (x,y)
//                Vector2D dir = points.get(i).subtract(points.get(k));
//                // 垂直向量 n = (a,b)  a*dir.x+b*dir.y = 0; a = -(b*dir.y/dir.x)
//                Vector2D normal = new Vector2D();
//                // 因为未知数有两个，随便给y附一个值5。
//                normal.setY(5);
//
//                if (dir.getX() != 0) {
//                    normal.setX(-(normal.getY() * dir.getY()) / dir.getX());
//                } else {
//                    // 如果方向平行于y轴
//                    normal.setX(1);
//                    normal.setY(0);
//                }
//                // 法向量单位化
//                normal.normalize();
//
//                double len = Math.sqrt(radius * radius - (0.25 * dir.length() * dir.length()));
//                c1 = center.add(normal.multiply(len));
//                c2 = center.subtract(normal.multiply(len));
//
//                // b1、b2记录是否在圆C1、C2中找到其他点。
//                boolean b1 = false, b2 = false;
//                for (int m = 0; m < points.size(); m++) {
//                    if (m == i || m == k) {
//                        continue;
//                    }
//                    if (b1 != true && points.get(m).distanceToPoint(c1) < radius) {
//                        b1 = true;
//                    }
//                    if (b2 != true && points.get(m).distanceToPoint(c2) < radius) {
//                        b2 = true;
//                    }
//                    // 如果都有内部点，不必再继续检查了
//                    if (b1 == true && b2 == true) {
//                        break;
//                    }
//                }
//
//                if (b1 != true || b2 != true) {
//                    Edge edge = new Edge();
//                    edge.setId(edgeNo);
//                    edge.setA(points.get(i));
//                    edge.setB(points.get(k));
//                    edgeNo++;
//                    boundary.add(points.get(i));
//                    boundary.add(points.get(k));
//                    //boudary.put(points.get(i).getId(),points.get(i));
//                    //boudary.put(points.get(k).getId(),points.get(k));
//                    lines.add(edge);
//                }
//            }
//        }
//
//        if (lines.size() <= 1) {
//            return Collections.emptyList();
//        }
//        //cleanLine(lines);
//        //组织多边形点顺序
//        //linkedEdge(lines, polygonPoints);
//        //
//        //int count = 0;
//        //for (List<Vector2D> polygonPoint : polygonPoints) {
//        //    count = count + polygonPoint.size();
//        //}
//        List<List<Vector2D>> polygonPoints = new ArrayList<>();
//        Polygonizer polygonizer = new Polygonizer();
//        List<LineString> lineStrings = new ArrayList<>(lines.size());
//        for (Edge line : lines) {
//            Coordinate A = new Coordinate(line.getA().getX(), line.getA().getY());
//            Coordinate B = new Coordinate(line.getB().getX(), line.getB().getY());
//            LineString lineString = new GeometryFactory().createLineString(new Coordinate[]{A,B});
//            lineStrings.add(lineString);
//        }
//        //线条多边形化
//        polygonizer.add(lineStrings);
//        Collection polygonList=polygonizer.getPolygons();
//        for (Object polObj : polygonList) {
//            Polygon polygon = (Polygon) polObj;
//            Coordinate[] coordinates=polygon.getCoordinates();
//            List<Vector2D> vector2DS = new ArrayList<>();
//            for (Coordinate coordinate : coordinates) {
//                Vector2D vector2D = new Vector2D(coordinate.x, coordinate.y);
//                vector2DS.add(vector2D);
//            }
//            polygonPoints.add(vector2DS);
//        }
//        return polygonPoints;
//    }
//
//    /**
//     * 清除边界上像毛发一样的虚线
//     *
//     * @param lines
//     */
//    private void cleanLine(List<Edge> lines) {
//        Map<Integer, Vector2D> vectorMap = new HashMap<>();
//        for (Edge line : lines) {
//            vectorMap.put(line.getA().getId(), line.getA());
//            vectorMap.put(line.getB().getId(), line.getB());
//        }
//        Set<Integer> lineSol = new HashSet<>();
//        for (Vector2D vector2D : vectorMap.values()) {
//            int pointId = vector2D.getId();
//            int count = 0;
//            List<Integer> lineId = new ArrayList<>();
//            for (int i = 0; i < lines.size(); i++) {
//                Edge line = lines.get(i);
//                if (line.getA().getId() == pointId || line.getB().getId() == pointId) {
//                    count++;
//                    lineId.add(line.getId());
//                }
//            }
//            if (count == 1) {
//                lineSol.add(lineId.get(0));
//            }
//        }
//
//        if (lineSol.size() > 0) {
//            System.out.println("虚线个数" + lineSol.size());
//            for (Integer id : lineSol) {
//                System.out.println(id);
//                for (Iterator ite = lines.iterator(); ite.hasNext(); ) {
//                    Edge line = (Edge) ite.next();
//                    if (line.getId() == id) {
//                        ite.remove();
//                        break;
//                    }
//                }
//            }
//            //递归解决几个单线连接成独线的问题
//            cleanLine(lines);
//        }
//        if (lineSol.size() == 0) {
//            return;
//        }
//
//
//    }
//
//    /**
//     * 将边界线链接起来
//     * 递归遍历完成多个多边形或孤岛多边形检索
//     * 递归结束条件
//     * 1正常链接完毕 2只剩孤线 3空集合
//     *
//     * @param lines
//     */
//    private void linkedEdge(List<Edge> lines, List<List<Vector2D>> polygonPoints) {
//        sortCount++;
//        if (sortCount == 50) {
//            log.error("边界点递归探索失败，超50次");
//            return;
//        }
//        if (lines == null || lines.size() <= 1) {
//            return;
//        }
//        //链接边界，并存储边界点
//        //如果头节点是一个孤线，换下一条线开始
//        List<Vector2D> points = new ArrayList<>();
//        int solCount = 0;
//        for (Edge head : lines) {
//            boolean headIsSol = tryLink(lines, points, head);
//            if (!headIsSol) {
//                break;
//            } else {
//                solCount++;
//            }
//        }
//        //全是孤线
//        if (solCount == lines.size()) {
//            return;
//        }
//        polygonPoints.add(points);
//
//        List<Edge> residueLines = new ArrayList<>();
//        for (Edge line : lines) {
//            if (line.getNext() == null) {
//                residueLines.add(line);
//            }
//        }
//
//        if (residueLines.size() != 0) {
//            linkedEdge(residueLines, polygonPoints);
//        }
//    }
//
//    /**
//     * @param lines
//     * @param points
//     * @param head
//     * @return
//     */
//    private boolean tryLink(List<Edge> lines, List<Vector2D> points, Edge head) {
//        //虚拟头节点，类似于linkedList
//        Edge next = new Edge();
//        next.setNext(head);
//        points.add(head.getA());
//        boolean headIsSol = true;
//        for (int i = 0; next.hasNext() && i < lines.size(); i++) {
//            next = next.getNext();
//            int bId = next.getB().getId();
//            if (i > 0 && next.getId() == head.getId()) {
//                //一个多边形组合完
//                break;
//            }
//            points.add(next.getB());
//            for (int j = 0; j < lines.size(); j++) {
//                Edge match = lines.get(j);
//                if (next.getId() == match.getId() || match.hasNext()) {
//                    //跳过自己
//                    continue;
//                }
//                boolean isMatch = false;
//                if (match.getA().getId() == bId) {
//                    isMatch = true;
//                } else if (match.getB().getId() == bId) {
//                    match.reversal();
//                    isMatch = true;
//                }
//                if (isMatch) {
//                    next.setNext(match);
//                    headIsSol = false;
//                    break;
//                }
//            }
//
//
//        }
//        if (headIsSol) {
//            points.clear();
//        }
//        return headIsSol;
//    }
//
//    public static class VectorDistanceCmp implements Comparator<Vector2D> {
//
//        boolean order;
//
//        public VectorDistanceCmp(boolean order) {
//            this.order = order;
//        }
//
//        @Override
//        public int compare(Vector2D o1, Vector2D o2) {
//            if (o1.getDistance() > o2.getDistance()) {
//                if (order) {
//                    return -1;
//                } else {
//                    return 1;
//                }
//            } else if (o1.getDistance() < o2.getDistance()) {
//                if (order) {
//                    return 1;
//                } else {
//                    return -1;
//                }
//            } else {
//                return 0;
//            }
//        }
//    }
//
//}
