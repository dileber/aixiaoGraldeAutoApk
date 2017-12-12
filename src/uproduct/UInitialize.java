package uproduct;

import main.Config;
import main.Constants;
import utils.AttachmentStore;

/**
 * Created by DELL on 2017/8/25.
 */
public class UInitialize {
    public static void start(){
        System.out.println("edit Initialize");
        String config = Config.productSrc+ Constants.INITIALIZE;
        String temp = Constants.APPTEMP+UInitialize.class.getName();
        AttachmentStore.create(temp);

        AttachmentStore.replace(config, temp, new AttachmentStore.Oo() {
            @Override
            public void o(StringBuffer sb, String s) {

                int index = s.indexOf("(Application) context, AppConfigStatus.");
                if (index != -1) {
                    System.out.println("edit AppConfigStatus success");

                    sb.append(s.replace(s.substring(index, s.length()), "(Application) context, AppConfigStatus." + UProperties.config.get("AppConfigStatus") + ");") + "\r\n");
                } else {
                    sb.append(s + "\r\n");
                }
            }
        });
        //AttachmentStore.save(temp,AttachmentStore.loadAsString(config));

        AttachmentStore.save2(temp,config);
        AttachmentStore.delete(temp);
        System.out.println("end Initialize");

    }
}
