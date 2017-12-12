package main;

import uproduct.*;
import uproduct.upackage.UPackage;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        JFrame jf = new JFrame("\u6253\u5305"); // ʵ����һ��JFrame����
        Container container = jf.getContentPane(); // ��ȡһ������
        JLabel jl=new JLabel("\u8fd0\u884c\u4e2d");
        jl.setHorizontalAlignment(SwingConstants.CENTER);
        container.add(jl);
        container.setBackground(Color.white);//���������ı�����ɫ
        jf.setVisible(true); // ʹ�������
        jf.setSize(200, 150); // ���ô����С
        // ���ô���رշ�ʽ
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Map<String,String> config = UProperties.readProperties();
        Config.config(config.get("dir")+config.get("src"));
        //System.out.print(Config.productSrc + Constants.ANDROIDMAIN);
        UConfig.start();
        UGradle.start();
        UInitialize.start();
        UString.start();
        UAndroidManifest.start();
        UImage.start();
        UPackage.toPackage();
        jl.setText("\u6253\u5305\u5b8c\u6210");

        jf.dispose();
    }


}
