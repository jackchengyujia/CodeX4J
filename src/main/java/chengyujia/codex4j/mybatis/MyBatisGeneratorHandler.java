package chengyujia.codex4j.mybatis;

import chengyujia.codex4j.config.UserConfigModel;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 成宇佳.
 */
public class MyBatisGeneratorHandler {

    public void generate(UserConfigModel userConfigModel, String projectPath) throws Exception {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        Configuration config = new Configuration();
        config.addContext(getContext(userConfigModel, projectPath));
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        org.mybatis.generator.api.MyBatisGenerator myBatisGenerator = new org.mybatis.generator.api.MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }

    private Context getContext(UserConfigModel userConfigModel, String projectPath) {
        String jdbcUrl = String.format("jdbc:mysql://%s:%s/%s?useInformationSchema=true", userConfigModel.getHost(), userConfigModel.getPort(), userConfigModel.getDatabase());
        Context context = new Context(ModelType.CONDITIONAL);
        context.setId("contextId");
        context.setTargetRuntime("MyBatis3");
        context.setJdbcConnectionConfiguration(getJdbcConnection(userConfigModel.getUsername(), userConfigModel.getPassword(), jdbcUrl));
        context.setJavaModelGeneratorConfiguration(getJavaModelGenerator(projectPath, userConfigModel.getRootPackage()));
        context.setSqlMapGeneratorConfiguration(getSqlMapGenerator(projectPath, userConfigModel.getRootPackage()));
        context.setJavaClientGeneratorConfiguration(getJavaClientGenerator(projectPath, userConfigModel.getRootPackage()));
        context.addTableConfiguration(getTableConfig(context, userConfigModel.isUseExample()));
        context.setCommentGeneratorConfiguration(getCommentGenerator());
        return context;
    }

    private CommentGeneratorConfiguration getCommentGenerator() {
        CommentGeneratorConfiguration commentGenerator = new CommentGeneratorConfiguration();
        commentGenerator.setConfigurationType(CustomCommentGenerator.class.getName());
        return commentGenerator;
    }

    private JDBCConnectionConfiguration getJdbcConnection(String username, String password, String jdbcUrl) {
        JDBCConnectionConfiguration jdbcConnection = new JDBCConnectionConfiguration();
        jdbcConnection.setDriverClass("com.mysql.jdbc.Driver");
        jdbcConnection.setConnectionURL(jdbcUrl);
        jdbcConnection.setUserId(username);
        jdbcConnection.setPassword(password);
        return jdbcConnection;
    }

    private JavaModelGeneratorConfiguration getJavaModelGenerator(String projectPath, String rootPackage) {
        String savePath = projectPath + "src/main/java/";
        new File(savePath).mkdirs();
        JavaModelGeneratorConfiguration javaModelGenerator = new JavaModelGeneratorConfiguration();
        javaModelGenerator.setTargetProject(savePath);
        javaModelGenerator.setTargetPackage(rootPackage + ".model");
        return javaModelGenerator;
    }

    private SqlMapGeneratorConfiguration getSqlMapGenerator(String projectPath, String rootPackage) {
        String savePath = projectPath + "src/main/resources/";
        new File(savePath).mkdirs();
        SqlMapGeneratorConfiguration sqlMapGenerator = new SqlMapGeneratorConfiguration();
        sqlMapGenerator.setTargetProject(savePath);
        sqlMapGenerator.setTargetPackage("sqlmap");
        return sqlMapGenerator;
    }

    private JavaClientGeneratorConfiguration getJavaClientGenerator(String projectPath, String rootPackage) {
        String savePath = projectPath + "src/main/java/";
        new File(savePath).mkdirs();
        JavaClientGeneratorConfiguration javaClientGenerator = new JavaClientGeneratorConfiguration();
        javaClientGenerator.setTargetProject(savePath);
        javaClientGenerator.setTargetPackage(rootPackage + ".dao");
        javaClientGenerator.setConfigurationType("XMLMAPPER");
        return javaClientGenerator;
    }

    private TableConfiguration getTableConfig(Context context, boolean useExample) {
        TableConfiguration tableConfig = new TableConfiguration(context);
        tableConfig.setTableName("%");
        tableConfig.setCountByExampleStatementEnabled(useExample);
        tableConfig.setDeleteByExampleStatementEnabled(useExample);
        tableConfig.setSelectByExampleStatementEnabled(useExample);
        tableConfig.setUpdateByExampleStatementEnabled(useExample);
        return tableConfig;
    }
}