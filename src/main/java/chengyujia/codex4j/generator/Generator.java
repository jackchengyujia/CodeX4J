package chengyujia.codex4j.generator;

import chengyujia.codex4j.common.FileUtil;
import chengyujia.codex4j.config.AppConfig;
import chengyujia.codex4j.config.UserConfigModel;
import chengyujia.codex4j.freemarker.FreeMarkerUtil;
import chengyujia.codex4j.mybatis.MyBatisGeneratorHandler;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 成宇佳.
 */
public class Generator {

    private String getRootPackagePath(String projectPath, String rootPackage) {
        StringBuilder sb = new StringBuilder();
        sb.append(projectPath);
        sb.append("src/main/java/");
        String[] folders = rootPackage.split("\\.");
        for (String folder : folders) {
            sb.append(folder);
            sb.append("/");
        }
        String rootPackagePath = sb.toString();
        return rootPackagePath;
    }

    public void generate(UserConfigModel userConfigModel) throws Exception {
        String projectPath = AppConfig.getGeneratedProjectPath() + userConfigModel.getDatabase() + "/";
        FileUtils.deleteDirectory(new File(projectPath));
        String rootPackagePath = getRootPackagePath(projectPath, userConfigModel.getRootPackage());
        generatePom(projectPath, userConfigModel);
        generateIndexJsp(projectPath);
        generateWebXml(projectPath);
        generateJdbcProperties(projectPath, userConfigModel);
        generateSpringXml(projectPath, userConfigModel);
        generateSpringMvcXml(projectPath, userConfigModel);
        generateSpringMyBatisXml(projectPath, userConfigModel);
        generateModelAndDao(userConfigModel, projectPath);
        //String date = getDate();
        List<String> modelNames = getModelNames(rootPackagePath);
        for (String modelName : modelNames) {
            generateService(rootPackagePath, userConfigModel, modelName);
            generateController(rootPackagePath, userConfigModel, modelName);
        }
        generateIndexController(rootPackagePath, userConfigModel);
    }

    private void generatePom(String projectPath, UserConfigModel userConfigModel) throws Exception {
        String savePath = projectPath + "pom.xml";
        Map<String, Object> data = new HashMap();
        data.put("groupId", userConfigModel.getRootPackage());
        data.put("artifactId", userConfigModel.getDatabase());
        FreeMarkerUtil.generateFile("pom.xml.ftl", data, savePath);
    }

    private void generateIndexJsp(String projectPath) throws Exception {
        String savePath = projectPath + "src/main/webapp/WEB-INF/view/index.jsp";
        FreeMarkerUtil.generateFile("index.jsp.ftl", null, savePath);
    }

    private void generateWebXml(String projectPath) throws Exception {
        String savePath = projectPath + "src/main/webapp/WEB-INF/web.xml";
        FreeMarkerUtil.generateFile("web.xml.ftl", null, savePath);
    }

    private void generateJdbcProperties(String projectPath, UserConfigModel userConfigModel) throws Exception {
        String savePath = projectPath + "src/main/resources/config/jdbc.properties";
        Map<String, Object> data = new HashMap();
        data.put("host", userConfigModel.getHost());
        data.put("port", userConfigModel.getPort());
        data.put("database", userConfigModel.getDatabase());
        data.put("username", userConfigModel.getUsername());
        data.put("password", userConfigModel.getPassword());
        FreeMarkerUtil.generateFile("jdbc.properties.ftl", data, savePath);
    }

    private void generateSpringXml(String projectPath, UserConfigModel userConfigModel) throws Exception {
        String savePath = projectPath + "src/main/resources/config/spring.xml";
        Map<String, Object> data = new HashMap();
        data.put("rootPackage", userConfigModel.getRootPackage());
        FreeMarkerUtil.generateFile("spring.xml.ftl", data, savePath);
    }

    private void generateSpringMvcXml(String projectPath, UserConfigModel userConfigModel) throws Exception {
        String savePath = projectPath + "src/main/resources/config/spring-mvc.xml";
        Map<String, Object> data = new HashMap();
        data.put("rootPackage", userConfigModel.getRootPackage());
        FreeMarkerUtil.generateFile("spring-mvc.xml.ftl", data, savePath);
    }

    private void generateSpringMyBatisXml(String projectPath, UserConfigModel userConfigModel) throws Exception {
        String savePath = projectPath + "src/main/resources/config/spring-mybatis.xml";
        Map<String, Object> data = new HashMap();
        data.put("rootPackage", userConfigModel.getRootPackage());
        FreeMarkerUtil.generateFile("spring-mybatis.xml.ftl", data, savePath);
    }

    private void generateModelAndDao(UserConfigModel userConfigModel, String projectPath) throws Exception {
        MyBatisGeneratorHandler myBatisGeneratorHandler = new MyBatisGeneratorHandler();
        myBatisGeneratorHandler.generate(userConfigModel, projectPath);
    }

    private List<String> getModelNames(String rootPackagePath) {
        List<String> fileNames = FileUtil.getFileNamesInFolder(rootPackagePath + "model");
        List<String> modelNames = new ArrayList<>();
        for (String fileName : fileNames) {
            if (fileName.endsWith(".java") && !fileName.endsWith("Example.java")) {
                String modelName = fileName.substring(0, fileName.length() - 5);
                modelNames.add(modelName);
            }
        }
        return modelNames;
    }

    private void generateService(String rootPackagePath, UserConfigModel userConfigModel, String modelName) throws Exception {
        String savePath = rootPackagePath + "service/" + modelName + "Service.java";
        Map<String, Object> data = new HashMap();
        data.put("rootPackage", userConfigModel.getRootPackage());
        data.put("modelName", modelName);
        FreeMarkerUtil.generateFile("Service.java.ftl", data, savePath);
    }

    private void generateController(String rootPackagePath, UserConfigModel userConfigModel, String modelName) throws Exception {
        String savePath = rootPackagePath + "controller/" + modelName + "Controller.java";
        Map<String, Object> data = new HashMap();
        data.put("rootPackage", userConfigModel.getRootPackage());
        data.put("modelName", modelName);
        FreeMarkerUtil.generateFile("Controller.java.ftl", data, savePath);
    }

    private void generateIndexController(String rootPackagePath, UserConfigModel userConfigModel) throws Exception {
        String savePath = rootPackagePath + "controller/IndexController.java";
        Map<String, Object> data = new HashMap();
        data.put("rootPackage", userConfigModel.getRootPackage());
        FreeMarkerUtil.generateFile("IndexController.java.ftl", data, savePath);
    }
}