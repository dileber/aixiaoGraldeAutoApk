package uproduct;

import main.Config;
import main.Constants;
import utils.AttachmentStore;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by DELL on 2017/8/25.
 */
public class UAndroidManifest {
    public static void start(){
        System.out.println("edit AndroidManifest");
        String config = Config.productSrc+ Constants.ANDROIDMAIN;
        String temp = Constants.APPTEMP+UAndroidManifest.class.getName();
        AttachmentStore.create(temp);

        AttachmentStore.replace(config, temp, new AttachmentStore.Oo() {
            @Override
            public void o(StringBuffer sb, String s) {
                int index = s.indexOf("wxapi.WXPayEntryActivity");
                int index2 = s.indexOf("com.baidu.lbsapi.API_KEY");
                int start = s.indexOf("name=\"");
                if (index != -1) {
                    System.out.println("edit WXPayEntryActivity success");
                    sb.append(s.replace(s.substring(start, index - 1), "name=\"" + UProperties.config.get("WXPayEntryActivity")) + "\r\n");
                }else if (index2 != -1) {
                    System.out.println("edit com.baidu.lbsapi.API_KEY success");

                    sb.append(s.replace(s.substring(index2, s.length()), "com.baidu.lbsapi.API_KEY\" android:value = \"" + UProperties.config.get("com.baidu.lbsapi.API_KEY")) + "\" /> \r\n");

                } else {
                    sb.append(s + "\r\n");
                }

            }
        });

//        try {
//            InputStreamReader isr = new InputStreamReader(new FileInputStream(config), "UTF-8");
//            AttachmentStore.
//
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

        AttachmentStore.save2(temp,config);
        AttachmentStore.delete(temp);

        System.out.println("end AndroidManifest");

    }
}
