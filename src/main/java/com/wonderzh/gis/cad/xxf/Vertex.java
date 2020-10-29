package com.wonderzh.gis.cad.xxf;

/**
 * @Author: wonderzh
 * @Date: 2019/3/15
 * @Version: 1.0
 */

public  class Vertex {

    protected int id;
    protected String x;
    protected String y;
    private String z;

    public Vertex() {
    }

    public Vertex(int id, String x, String y, String z) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String getZ() {
        return z;
    }

    public void setZ(String z) {
        this.z = z;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    @Override
    public int hashCode() {
        return this.x.hashCode()+this.y.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (null==obj) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof Vertex) {
            Vertex vertex = (Vertex)obj;
            return this.x.equals(vertex.x)&&this.y.equals(vertex.y);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "id=" + id +
                ", x='" + x + '\'' +
                ", y='" + y + '\'' +
                ", z='" + z + '\'' +
                '}';
    }
}
