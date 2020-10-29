package com.wonderzh.gis.shape;

//import com.smartwater.common.util.JsonUtil;
//import org.apache.commons.lang.StringUtils;
//import org.geotools.data.FeatureWriter;
//import org.geotools.data.Transaction;
//import org.geotools.data.shapefile.ShapefileDataStore;
//import org.geotools.data.shapefile.ShapefileDataStoreFactory;
//import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
//import org.geotools.referencing.crs.DefaultGeographicCRS;
//import org.locationtech.jts.geom.*;
//import org.opengis.feature.simple.SimpleFeature;
//import org.opengis.feature.simple.SimpleFeatureType;
//
//import java.io.File;
//import java.io.Serializable;
//import java.nio.charset.StandardCharsets;
//import java.util.*;
//
///**
// * @Author: wonderzh
// * @Date: 2019/10/24
// * @Version: 1.0
// */
//
//public class ShpWriter {
//
//
//    /**
//     * Json中存储坐标信息的key
//     */
//    private static final String PLOT_TYPE = "plotType";
//
//    /**
//     * Json中存储空间类型的key
//     */
//    private static final String JSON_GEOM_KEY = "pts";
//
//    /**
//     * the_geom can not be changed
//     */
//    private static final String GEOM_KEY = "the_geom";
//
//    public static final String POINT = "POINT";
//
//    public static final String LINE = "LINE";
//
//    public static final String POLYGON = "POLYGON";
//
//    public static final String SEPARATOR_PAIR = ",";
//    public static final String SEPARATOR_GROUP = "|";
//
//
//    private static List<Map<String, String>> getShpFieldDataMap(String jsonData) throws Exception {
//        /**
//         * 需要重定义的 key
//         */
//        jsonData = jsonData.replace(JSON_GEOM_KEY, GEOM_KEY);
//        List<Map<String, String>> fieldMap = new ArrayList<>();
//        List<Map> list = JsonUtil.jsonToList(jsonData, Map.class);
//        for (Map obj : list) {
//            Map<String, String> map=(Map<String, String>)obj;
//            fieldMap.add(map);
//        }
//        return fieldMap;
//    }
//
//    /**
//     * 获取集合中的 key 全集
//     *
//     * @param mapList 从json中获得的数据集
//     * @return key 全集
//     */
//    private static Set<String> getUpperCaseKeyMap(List<Map<String, String>> mapList) {
//        Set<String> keySet = new HashSet<>();
//
//        for (Map<String, String> stringMap : mapList) {
//            for (Map.Entry<String,String> entry : stringMap.entrySet()) {
//                String key = entry.getKey().toUpperCase();
//                if (!keySet.contains(key)) {
//                    keySet.add(key);
//                }
//            }
//        }
//
//        //keySet.add("ZIPCODE");
//        //keySet.add("URL");
//        //keySet.add("PRODATE");
//        keySet.remove(PLOT_TYPE.toUpperCase());
//        return keySet;
//    }
//
//    /**
//     * 字符串转成空间字段所需的数组集合
//     *
//     * @param str
//     * @return
//     */
//    private static Coordinate[] strToCoordinates(String str, String type) {
//        List<Coordinate> coordinates = new ArrayList<>();
//        String[] strings = StringUtils.split(str, SEPARATOR_GROUP);
//        for (int i = 0; i < strings.length; i++) {
//            String[] split = StringUtils.split(strings[i], SEPARATOR_PAIR);
//            if (split.length == 2) {
//                coordinates.add(new Coordinate(Double.parseDouble(split[0].trim()), Double.parseDouble(split[1].trim())));
//            }
//        }
//        //多边形尾点和首点相同
//        if (StringUtils.equals(type, POLYGON)) {
//            coordinates.add(coordinates.get(0));
//        }
//        return coordinates.toArray(new Coordinate[coordinates.size()]);
//    }
//
//    /**
//     * 生成shp文件
//     *
//     * @param filePath 文件路径
//     * @return shp文件
//     */
//    private static File createShpFile(String filePath) {
//        //创建shape文件对象
//        File root = new File(filePath);
//        filePath += File.separator + root.getName();
//        if (!filePath.endsWith(".shp")) {
//            filePath += ".shp";
//        }
//        File file = new File(filePath);
//        if (!file.getParentFile().exists()) {
//            file.getParentFile().mkdirs();
//        }
//        return file;
//    }
//
//
//    public static void write(String filePath, String jsonData, String geomType) {
//
//        try {
//            File file = createShpFile(filePath);
//
//            List<Map<String, String>> shpFieldList = getShpFieldDataMap(jsonData);
//
//            Set<String> keys = getUpperCaseKeyMap(shpFieldList);
//
//            //使用工厂函数生成datastore
//            Map<String, Serializable> params = new HashMap<String, Serializable>();
//            params.put(ShapefileDataStoreFactory.URLP.key, file.toURI().toURL());
//            ShapefileDataStore ds = (ShapefileDataStore) new ShapefileDataStoreFactory().createNewDataStore(params);
//
//            //定义图形信息和属性信息
//            // 创建shp文件的结构，也就是schema，告诉他你这个文件有哪些属性，是点线还是面。
//            SimpleFeatureTypeBuilder simpleFeatureTypeBuilder = new SimpleFeatureTypeBuilder();
//            //坐标系
//            simpleFeatureTypeBuilder.setCRS(DefaultGeographicCRS.WGS84);
//            simpleFeatureTypeBuilder.setName("shapeFile");
//
//            //几何实体类型
//            switch (geomType.toUpperCase()) {
//                case POINT:
//                    simpleFeatureTypeBuilder.add(GEOM_KEY, Point.class);
//                    break;
//                case LINE:
//                    simpleFeatureTypeBuilder.add(GEOM_KEY, LineString.class);
//                    break;
//                case POLYGON:
//                    simpleFeatureTypeBuilder.add(GEOM_KEY, Polygon.class);
//                    break;
//                default:
//                    throw new RuntimeException("空间字段类型错误");
//            }
//            //其他属性, 除the_geom小写，其余大写
//            for (String key : keys) {
//                simpleFeatureTypeBuilder.add(key, String.class);
//            }
//
//            ds.createSchema(simpleFeatureTypeBuilder.buildFeatureType());
//            ds.setCharset(StandardCharsets.UTF_8);
//
//            //设置Writer   自动提交
//            FeatureWriter<SimpleFeatureType, SimpleFeature> writer = ds.getFeatureWriter(ds.getTypeNames()[0], Transaction.AUTO_COMMIT);
//
//            //写下一条
//            SimpleFeature feature;
//            for (Map<String, String> stringMap : shpFieldList) {
//                feature = writer.next();
//
//                for (Map.Entry<String, String> entry : stringMap.entrySet()) {
//                    String keyUpper = entry.getKey().toUpperCase();
//                    //Json中存储坐标信息的key跳过导出
//                    //if (key == null || key.equals(PLOT_TYPE)) {
//                    //    continue;
//                    //}
//                    //if (key.length() > 10) {
//                    //    key = StringUtils.substring(key, 0, 10);
//                    //}
//                    String value = entry.getValue() == null ? "" : entry.getValue();
//
//                    if (StringUtils.equals(keyUpper, GEOM_KEY.toUpperCase())) {
//                        if (StringUtils.equals(geomType, POINT)) {
//                            String[] split = value.split(SEPARATOR_PAIR);
//                            if (split.length == 2) {
//                                Coordinate coordinate = new Coordinate(Double.parseDouble(split[0].trim()), Double.parseDouble(split[1].trim()));
//                                feature.setAttribute(GEOM_KEY, new GeometryFactory().createPoint(coordinate));
//                            }
//                        } else if (StringUtils.equals(geomType, LINE)) {
//                            LineString line = new GeometryFactory().createLineString(strToCoordinates(value, LINE));
//                            feature.setAttribute(GEOM_KEY, line);
//                        } else if (StringUtils.equals(geomType, POLYGON)) {
//                            Polygon polygon = new GeometryFactory().createPolygon(strToCoordinates(value, POLYGON));
//                            feature.setAttribute(GEOM_KEY, polygon);
//                        }
//                    } else {
//                        feature.setAttribute(keyUpper, value);
//                    }
//                }
//            }
//            writer.write();
//            writer.close();
//            ds.dispose();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//    public static void main(String[] args) {
//        List<Map> data = new ArrayList<>();
//        Map<String, Object> jsonData = new HashMap<>();
//        jsonData.put("the_geom", "1,1");
//        jsonData.put("id", "135");
//        jsonData.put("name", "测试");
//
//        Map<String, Object> jsonData2 = new HashMap<>();
//        jsonData2.put("the_geom", "2,2");
//        data.add(jsonData);
//        data.add(jsonData2);
//        String saveRoot = "D:\\test";
//        write(saveRoot,JsonUtil.objectToJson(data),POINT);
//
//    }
//
//
//}
