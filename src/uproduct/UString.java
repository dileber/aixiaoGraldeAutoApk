package uproduct;

import main.Config;
import main.Constants;
import utils.AttachmentStore;

/**
 * Created by DELL on 2017/8/25.
 */
public class UString {
    public static void start(){
        System.out.println("edit String");
        String config = Config.productSrc+ Constants.CONFIGSTRINGS;
        String temp = Constants.APPTEMP+UString.class.getName();
        AttachmentStore.create(temp);

        AttachmentStore.replace(config, temp, new AttachmentStore.Oo() {
            @Override
            public void o(StringBuffer sb, String s) {
                int index = s.indexOf("app_name");
                int end = s.indexOf("</string>");
                if (index != -1) {
                    System.out.println("edit app_name success");

                    sb.append(s.replace(s.substring(index, end), "app_name\">" + UProperties.config.get("app_name")) + "\r\n");
                } else {
                    sb.append(s + "\r\n");
                }
            }
        });
       // AttachmentStore.save(temp,AttachmentStore.loadAsString(config));
        AttachmentStore.save2(temp, config);
        AttachmentStore.delete(temp);
        System.out.println("end String");

    }
}
