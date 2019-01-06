package chengyujia.codex4j.config;

import java.io.File;

/**
 * Created by 成宇佳.
 */
public class AppConfig {
    public static final String CHARSET = "UTF-8";
    private static String jarFolderPath;
    private static String workFolderPath;
    private static String generatedProjectPath;

    static {
        String jarPath = System.getProperty("java.class.path");
        int firstIndex = jarPath.lastIndexOf(System.getProperty("path.separator")) + 1;
        int lastIndex = jarPath.lastIndexOf(File.separator) + 1;
        jarFolderPath = jarPath.substring(firstIndex, lastIndex);
        workFolderPath = jarFolderPath + "Codex4jWork/";
        generatedProjectPath = workFolderPath + "GeneratedProject/";
        File file = new File(generatedProjectPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static String getWorkFolderPath() {
        return workFolderPath;
    }

    public static String getGeneratedProjectPath() {
        return generatedProjectPath;
    }
}