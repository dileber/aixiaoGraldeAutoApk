package utils;

import org.gradle.tooling.*;
import org.gradle.tooling.model.DomainObjectSet;
import org.gradle.tooling.model.GradleProject;

import java.io.File;
import java.util.*;
import java.util.concurrent.*;

/**
 * Gradle����
 *
 * @author lizhongwen
 * @since jdk1.6
 * @version 2017��3��28�� lizhongwen
 */
public final class GradleTools {

    /** Gradle����ʵ�� */
    private static GradleTools instance;

    /** Gradle������ */
    private GradleConnector gradleConnector;

    /**
     * ���캯��
     */
    private GradleTools() {

    }

    /**
     * ���캯��
     *
     * @param gradleHome Gradle��װĿ¼
     */
    private GradleTools(String gradleHome) {
        gradleConnector = GradleConnector.newConnector();
        if (gradleHome!=null) {
            File home = new File(gradleHome);
            if (!home.exists()) {
                throw new IllegalArgumentException("Gradle��װĿ¼�����ڣ�");
            }
            gradleConnector.useInstallation(home);
        }
    }

    /**
     * ��ȡgradle����
     *
     * @param gradleHome gradle��װĿ¼
     * @return Gradle����ʵ��
     */
    public static synchronized final GradleTools getInstance(String gradleHome) {
        if (instance == null) {
            instance = new GradleTools(gradleHome);
        }
        return instance;
    }

    /**
     * ��apk��
     *
     * @param rootProjectDir ����Ŀ����Ŀ¼��
     * @param subProjectName ����Ŀ���ƣ��������Ŀ����Ϊ�գ����ʾ���й��̾���Jar��
     * @return ִ�н��
     */
    public ExecuteResult apk(String rootProjectDir) {
        return execute(rootProjectDir, "apkRelease");
    }
    public ExecuteResult upModel(String rootProjectDir,String model) {
        return execute(rootProjectDir, model+":pushImpl");
    }

    /**
     * ִ������
     *
     * @param rootProjectDir ����Ŀ����Ŀ¼��
     * @param tasks ����
     * @return ִ�н��
     */
    public ExecuteResult execute(String rootProjectDir, String... tasks) {
        if (rootProjectDir==null) {
            throw new IllegalArgumentException("\u6839\u9879\u76ee\u6240\u5728\u7684\u76ee\u5f55\u4e0d\u80fd\u4e3a\u7a7a\u0021");
        }
        final File root = new File(rootProjectDir);
        if (!root.exists()) {
            throw new IllegalArgumentException("\u6839\u9879\u76ee\u6240\u5728\u7684\u76ee\u5f55\u4e0d\u5b58\u5728\u0021");
        }
        if (tasks == null) {
            throw new IllegalArgumentException("\u6ca1\u6709\u53ef\u6267\u884c\u7684\u4efb\u52a1\u0021");
        }
        final List<String> arguments = new ArrayList<String>(1);
        arguments.add("-xTest");
        final Map<String, String> jvmDefines = new HashMap<String, String>();
        final String[] taskes = tasks;

        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<ExecuteResult> future = service.submit(new Callable<ExecuteResult>() {

            @Override
            public ExecuteResult call() throws Exception {
                return execute(root, arguments, jvmDefines, taskes);
            }

        });
        try {
            while (!future.isDone()) {
                // �ȴ����
            }
            System.out.println("{"+rootProjectDir+"}::\u6267\u884c[{"+ Arrays.toString(tasks)+"}]\u5b8c\u6210.");
            return future.get();
        } catch (Exception e) {
            System.out.println("\u4efb\u52a1\u6267\u884c\u5931\u8d25\u3002");
            return new ExecuteResult(Result.FAIL, e);
        } finally {
            service.shutdown();
        }
    }

    /**
     * ִ��Gradle����
     *
     * @param projectDir ��ĿĿ¼
     * @param arguments ִ�в���
     * @param jvmDefines jvm����
     * @param tasks ����
     * @return ִ�н��
     */
    public ExecuteResult execute(File projectDir, List<String> arguments, Map<String, String> jvmDefines, String... tasks) {
        ProjectConnection connection = gradleConnector.forProjectDirectory(projectDir).connect();
        final CountDownLatch latch = new CountDownLatch(1);
        final ExecuteResult ret = new ExecuteResult();
        try {
            BuildLauncher build = connection.newBuild().forTasks(tasks).withArguments(arguments.toArray(new String[arguments.size()]));
            String[] jvmArgs = new String[jvmDefines.size()];
            if (!jvmDefines.isEmpty()) {
                int index = 0;
                for (Map.Entry<String, String> entry : jvmDefines.entrySet()) {
                    jvmArgs[index++] = "-D" + entry.getKey() + "=" + entry.getValue();
                }
            }
            build.setJvmArguments(jvmArgs);
            build.addProgressListener(new ProgressListener() {

                @Override
                public void statusChanged(ProgressEvent event) {
                    System.out.println("execute gradle task status changed:" + event.getDescription());
                }
            });
            build.run(new ResultHandler<Void>() {

                @Override
                public void onComplete(Void result) {
                    ret.result = Result.SUCCESS;
                    latch.countDown();
                }

                @Override
                public void onFailure(GradleConnectionException exception) {
                    ret.result = Result.FAIL;
                    ret.exception = exception;

                    latch.countDown();
                }
            });
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
//            LOGGER.error("ִ��Gradle�������:" + e.getMessage(), e);
        } finally {
            connection.close();
        }
        return ret;
    }

    enum Result{
        SUCCESS,FAIL
    }

    public class ExecuteResult{
        Result result;
        Exception exception;

        public ExecuteResult(){}

        public ExecuteResult(Result result,Exception exception) {
            this.exception = exception;
            this.result = result;
        }
    }

//    /**
//     * @param args xx
//     */
//    public static void main(String[] args) {
//        getInstance("D:\\soft\\Android\\Android Studio\\gradle\\gradle-3.3").apk("D:\\svn\\product\\ZhaoShengGuanJia\\trunk");
//    }

}
