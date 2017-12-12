package uproduct.upackage;

import main.Config;
import main.Constants;
import uproduct.UProperties;
import utils.AttachmentStore;
import utils.GradleTools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by DELL on 2017/8/28.
 */
public class UPackage {

    public static void toPackage(){
        System.out.println("start package... wait");
        //MyThread myThread = new MyThread();
        //myThread.run();
        String open =  UProperties.config.get("GRADLE_UPDATE_OPEN");
        String gradleSrc = UProperties.config.get("GRADLE_SRC");
        String productSrc = UProperties.config.get("dir")+UProperties.config.get("src");
        boolean o = Boolean.valueOf(open);
        if(o){
            final String config = Config.productSrc+Constants.GRADLE_SETTING ;
            String temp = Constants.APPTEMP+UPackage.class.getName()+".bat";
            AttachmentStore.create(temp);

            String sz = "@echo off\r\n"+UProperties.config.get("dir")+"\r\ncd"+UProperties.config.get("src")+"\r\n";
            String gradleUpdate =  UProperties.config.get("GRADLE_UPDATE");
            if(gradleUpdate!=null&&!gradleUpdate.equals("")){
                String[] str = gradleUpdate.split(",");
                StringBuffer[] sbs = new StringBuffer[str.length];
                for(int i=0;i<sbs.length;i++){
                    sbs[i] = new StringBuffer(sz);
                    sbs[i].append("gradle "+str[i]+":pushImpl");
                }

                final String old = AttachmentStore.loadAsString(config);

                for(int i=0;i<str.length;i++){
                    String s = "include ':"+str[i]+"'";
                    System.out.println(str[i] + "\u4e0a\u4f20\u672c\u5730\u5e93\u4e2d");
                    AttachmentStore.save(config, s);
                    AttachmentStore.save(temp, sbs[i].toString() + "\r\nexit");

                    if(gradleSrc!=null){
                        GradleTools.getInstance(gradleSrc).upModel(productSrc, str[i]);
                        AttachmentStore.save(config, old);
                    }else{
                        startCmd(temp, new M() {
                            @Override
                            public void myFinally() {
                                AttachmentStore.save(config, old);
                            }
                        });
                    }



                }

            }

            String build = sz+"gradle apkRelease";
            if(gradleSrc!=null){
                GradleTools.getInstance(gradleSrc).apk(productSrc);
            }else{
                AttachmentStore.save(temp, build);
                startCmd(temp, null);
            }
            AttachmentStore.delete(temp);


            String start = Config.productSrc+ Constants.ARCHIVES;
            String tempPackage = Constants.APPTEMP+UPackage.class.getName();

            AttachmentStore.deleteDir(tempPackage);
            AttachmentStore.copyFolder(start, tempPackage);

        }
        //thread = false;
        System.out.println("end package...");

    }

    interface M{
        void myFinally();
    }

    public synchronized static void startCmd(String path,M m){
        StringBuilder sb = new StringBuilder();
        String line = null;

        try {
            Process ps = Runtime.getRuntime().exec(path);
            InputStream in = ps.getInputStream();
            InputStreamReader isr = new InputStreamReader(in);

            BufferedReader  bufferedReader = new BufferedReader(isr);
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line + "\n");
                System.out.println(line);
            }

            in.close();
            isr.close();
            bufferedReader.close();
            ps.waitFor();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            if(m!=null){
                m.myFinally();
            }
        }
        //System.out.println(sb.toString());

    }

    public static boolean thread = true;

    static class MyThread extends Thread {

        @Override
        public void run() {
            int i = 0;
            while (thread){
                System.out.print("*");
                i++;
                if(i%50==0){
                    System.out.print("\u52aa\u529b\u6253\u5305\u4e2d");
                }
                if(i%100==0){
                    System.out.print("\r\n");
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
