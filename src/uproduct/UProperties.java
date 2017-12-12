package uproduct;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * Created by DELL on 2017/8/25.
 */
public class UProperties {

    public static Map<String,String> config = null;

    public static Map<String,String> readProperties(){
        System.out.println("loding config");
        config = new HashMap<>();
        Properties prop = new Properties();
        try{
            //读取属性文件a.properties
            InputStreamReader in = new InputStreamReader(new FileInputStream("config.properties"));
            prop.load(in);     ///加载属性列表
            Iterator<String> it=prop.stringPropertyNames().iterator();
            while(it.hasNext()){
                String key = it.next();
                config.put(key,prop.getProperty(key));
                //System.out.println(key+":"+prop.getProperty(key));
            }
            in.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        System.out.println("end loding config");
        return config;

    }

}
