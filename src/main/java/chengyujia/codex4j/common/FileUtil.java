package chengyujia.codex4j.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 成宇佳.
 */
public class FileUtil {

    public static List<String> getFileNamesInFolder(String folderPath) {
        List<String> fileNames = new ArrayList<>();
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        for (File f : files) {
            if (f.isFile()) {
                fileNames.add(f.getName());
            }
        }
        return fileNames;
    }
}