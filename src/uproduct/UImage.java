package uproduct;

import main.Config;
import main.Constants;
import utils.AttachmentStore;

/**
 * Created by DELL on 2017/8/25.
 */
public class UImage {

    public static void start(){
        System.out.println("edit IMAGE");
        String mudi = Config.productSrc+ Constants.IMAGESRC;
        String config = UProperties.config.get("ImageSrc") ;
        String temp = Constants.APPTEMP+UImage.class.getName();

        AttachmentStore.deleteDir(temp);
        AttachmentStore.copyFolder(config, temp);
        AttachmentStore.copyFolder(temp,mudi);
        AttachmentStore.deleteDir(temp);

        System.out.println("end IMAGE");

    }

}
