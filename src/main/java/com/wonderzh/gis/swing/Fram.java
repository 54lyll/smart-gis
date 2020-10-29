package com.wonderzh.gis.swing;

import javax.swing.*;
import java.awt.*;

/**
 * @Author: wonderzh
 * @Date: 2019/3/26
 * @Version: 1.0
 */

public class Fram {

    public static void main(String[] args) {
        JFrame frame = new JFrame("CADParser");
        frame.setDefaultLookAndFeelDecorated(true);
        frame.setLocation(200, 100);
        frame.setSize(800,600);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel jp=new JPanel();    //创建一个JPanel对象
        JLabel jl=new JLabel("这是放在JPanel上的标签");    //创建一个标签
        jp.setBackground(Color.LIGHT_GRAY);    //设置背景色
        jp.add(jl);
        frame.add(jp);

    }
}
