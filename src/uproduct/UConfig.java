package uproduct;

import main.Config;
import main.Constants;
import utils.AttachmentStore;

import java.io.*;

/**
 * Created by DELL on 2017/8/25.
 */
public class UConfig {
    public static void start(){
        System.out.println("edit CONFIG");
        String config = Config.productSrc+ Constants.CONFIG;
        String temp = Constants.APPTEMP+UConfig.class.getName();

        AttachmentStore.create(temp);

        AttachmentStore.replace(config, temp, new AttachmentStore.Oo() {
            @Override
            public void o(StringBuffer sb, String s) {
                int index = s.indexOf("DEBUG_MODE");
                if (index != -1) {
                    System.out.println("edit DEBUG_MODE success");

                    sb.append(s.replace(s.substring(index, s.length()), "DEBUG_MODE = " + UProperties.config.get("DEBUG_MODE") + ";") + "\r\n");
                } else {
                    sb.append(s + "\r\n");
                }
            }
        });
        //AttachmentStore.save(temp,AttachmentStore.loadAsString(config));

        AttachmentStore.save2(temp,config);
        AttachmentStore.delete(temp);
        System.out.println("end CONFIG");

    }

}
