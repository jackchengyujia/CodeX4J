package chengyujia.codex4j.config;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by 成宇佳.
 */
public class UserConfigUtil {
    private static final String CHARSET = AppConfig.CHARSET;
    private static String userConfigFilePath;

    static {
        userConfigFilePath = AppConfig.getWorkFolderPath() + "user-config";
    }

    public static UserConfigModel getUserConfig() {
        try {
            String jsonString = FileUtils.readFileToString(new File(userConfigFilePath), CHARSET);
            UserConfigModel userConfigModel = JSON.parseObject(jsonString, UserConfigModel.class);
            return userConfigModel;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void saveUserConfig(UserConfigModel userConfigModel) {
        File file = new File(userConfigFilePath);
        if (file.exists()) {
            file.delete();
        }
        String jsonString = JSON.toJSONString(userConfigModel);
        try {
            FileUtils.writeStringToFile(file, jsonString, CHARSET, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}