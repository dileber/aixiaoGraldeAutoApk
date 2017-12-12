package uproduct;

import main.Config;
import main.Constants;
import utils.AttachmentStore;

/**
 * Created by DELL on 2017/8/25.
 */
public class UGradle {
    public static void start(){
        System.out.println("edit Gradle");
        String config = Config.productSrc+ Constants.CONFIGGRADLE;
        String temp = Constants.APPTEMP+UGradle.class.getName();
        AttachmentStore.create(temp);

        AttachmentStore.replace(config, temp, new AttachmentStore.Oo() {
            @Override
            public void o(StringBuffer sb, String s) {
//                if(!(gradleConfig(sb,s,"BUGLY_APPID")&&gradleConfig(sb,s,"BUGLY_APPKEY")
//                        &&gradleConfig(sb,s,"KEY_STORE_FILE")&&gradleConfig(sb,s,"KEY_ALISA")
//                        &&gradleConfig(sb,s,"KEY_PASSWORD")&&gradleConfig(sb,s,"STORE_PASSWORD")&&gradleConfig(sb,s,"APPLICATION_ID"))){
//                    sb.append(s + "\r\n");
//                }
                int index = s.indexOf("BUGLY_APPID = ");
                int index2 = s.indexOf("BUGLY_APPKEY = ");
                int index3 = s.indexOf("KEY_STORE_FILE = ");
                int index4 = s.indexOf("KEY_ALISA = ");
                int index5 = s.indexOf("KEY_PASSWORD = ");
                int index6 = s.indexOf("STORE_PASSWORD = ");
                int index7 = s.indexOf("APPLICATION_ID = ");
                int index8 = s.indexOf("JPUSH_APPKEY = ");

                if (index != -1) {
                    System.out.println("edit BUGLY_APPID success");

                    sb.append(s.replace(s.substring(index, s.length()), "BUGLY_APPID = " + UProperties.config.get("BUGLY_APPID")) + "\r\n");
                } else if (index2 != -1) {
                    System.out.println("edit BUGLY_APPKEY success");

                    sb.append(s.replace(s.substring(index2, s.length()), "BUGLY_APPKEY = " + UProperties.config.get("BUGLY_APPKEY")) + "\r\n");
                } else if (index3 != -1) {
                    System.out.println("edit KEY_STORE_FILE success");

                    sb.append(s.replace(s.substring(index3, s.length()), "KEY_STORE_FILE = " + UProperties.config.get("KEY_STORE_FILE")) + "\r\n");
                } else if (index4 != -1) {
                    System.out.println("edit KEY_ALISA success");

                    sb.append(s.replace(s.substring(index4, s.length()), "KEY_ALISA = " + UProperties.config.get("KEY_ALISA")) + "\r\n");
                } else if (index5 != -1) {
                    System.out.println("edit KEY_PASSWORD success");

                    sb.append(s.replace(s.substring(index5, s.length()), "KEY_PASSWORD = " + UProperties.config.get("KEY_PASSWORD")) + "\r\n");
                } else if (index6 != -1) {
                    System.out.println("edit STORE_PASSWORD success");

                    sb.append(s.replace(s.substring(index6, s.length()), "STORE_PASSWORD = " + UProperties.config.get("STORE_PASSWORD")) + "\r\n");
                } else if (index7 != -1) {
                    System.out.println("edit APPLICATION_ID success");

                    sb.append(s.replace(s.substring(index7, s.length()), "APPLICATION_ID = " + UProperties.config.get("APPLICATION_ID")) + "\r\n");
                }else if (index8 != -1) {
                    System.out.println("edit JPUSH_APPKEY success");
                    sb.append(s.replace(s.substring(index8, s.length()), "JPUSH_APPKEY = " + UProperties.config.get("JPUSH_APPKEY")) + "\r\n");
                }
                else {
                    sb.append(s + "\r\n");
                }
            }
        });
        //AttachmentStore.save(temp,AttachmentStore.loadAsString(config));

        AttachmentStore.save2(temp,config);
        AttachmentStore.delete(temp);
        System.out.println("end Gradle");

    }

    public static boolean gradleConfig(StringBuffer sb,String s,String key){
        int index = s.indexOf(key+" = ");
        if (index != -1) {
            sb.append(s.replace(s.substring(index, s.length()), key+" = " + UProperties.config.get(key)) + "\r\n");
            return true;
        }
        return false;
    }
}
