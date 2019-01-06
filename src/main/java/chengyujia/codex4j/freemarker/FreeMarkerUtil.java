package chengyujia.codex4j.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.FileWriter;
import java.util.Map;

/**
 * Created by 成宇佳.
 */
public class FreeMarkerUtil {
    private static Configuration config;

    static {
        config = new Configuration(Configuration.VERSION_2_3_28);
        config.setClassForTemplateLoading(FreeMarkerUtil.class, "/ftl");
    }

    public static void generateFile(String ftlName, Map<String, Object> data, String savePath) throws Exception {
        File file = new File(savePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileWriter out = new FileWriter(file);
        Template temp = config.getTemplate(ftlName);
        temp.process(data, out);
        out.close();
    }
}