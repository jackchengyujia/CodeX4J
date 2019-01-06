package chengyujia.codex4j;

import chengyujia.codex4j.config.AppConfig;
import chengyujia.codex4j.config.UserConfigModel;
import chengyujia.codex4j.config.UserConfigUtil;
import chengyujia.codex4j.generator.Generator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;

/**
 * Created by 成宇佳.
 */
public class MainClass {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Java代码生成器CodeX4J");
        frame.setSize(400, 300);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);
        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel hostLabel = new JLabel("数据库地址:");
        hostLabel.setBounds(10, 10, 70, 25);
        panel.add(hostLabel);

        JTextField hostText = new JTextField(20);
        hostText.setBounds(90, 10, 300, 25);
        panel.add(hostText);

        JLabel portLabel = new JLabel("端口:");
        portLabel.setBounds(10, 40, 70, 25);
        panel.add(portLabel);

        JTextField portText = new JTextField(20);
        portText.setBounds(90, 40, 300, 25);
        panel.add(portText);

        JLabel usernameLabel = new JLabel("用户名:");
        usernameLabel.setBounds(10, 70, 70, 25);
        panel.add(usernameLabel);

        JTextField usernameText = new JTextField(20);
        usernameText.setBounds(90, 70, 300, 25);
        panel.add(usernameText);

        JLabel passwordLabel = new JLabel("密码:");
        passwordLabel.setBounds(10, 100, 70, 25);
        panel.add(passwordLabel);

        JTextField passwordText = new JTextField(20);
        passwordText.setBounds(90, 100, 300, 25);
        panel.add(passwordText);

        JLabel databaseLabel = new JLabel("数据库名:");
        databaseLabel.setBounds(10, 130, 70, 25);
        panel.add(databaseLabel);

        JTextField databaseText = new JTextField(20);
        databaseText.setBounds(90, 130, 300, 25);
        panel.add(databaseText);

        JLabel rootPackageLabel = new JLabel("根包名:");
        rootPackageLabel.setBounds(10, 160, 70, 25);
        panel.add(rootPackageLabel);

        JTextField rootPackageText = new JTextField(20);
        rootPackageText.setBounds(90, 160, 300, 25);
        panel.add(rootPackageText);

        JCheckBox useExampleCheckBox = new JCheckBox();
        useExampleCheckBox.setBounds(86, 190, 200, 25);
        useExampleCheckBox.setText("使用Example");
        panel.add(useExampleCheckBox);

        UserConfigModel userConfigModel = UserConfigUtil.getUserConfig();
        if (userConfigModel != null) {
            hostText.setText(userConfigModel.getHost());
            portText.setText(userConfigModel.getPort());
            usernameText.setText(userConfigModel.getUsername());
            passwordText.setText(userConfigModel.getPassword());
            databaseText.setText(userConfigModel.getDatabase());
            rootPackageText.setText(userConfigModel.getRootPackage());
            useExampleCheckBox.setSelected(userConfigModel.isUseExample());
        } else {
            hostText.setText("127.0.0.1");
            portText.setText("3306");
        }

        JButton generateButton = new JButton("一键生成");
        generateButton.setBounds(50, 220, 100, 25);
        panel.add(generateButton);
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String host = hostText.getText().trim();
                String port = portText.getText().trim();
                String username = usernameText.getText().trim();
                String password = passwordText.getText().trim();
                String database = databaseText.getText().trim();
                String rootPackage = rootPackageText.getText().trim();
                boolean useExample = useExampleCheckBox.isSelected();

                if ("".equals(host) || "".equals(port) || "".equals(username) || "".equals(password) || "".equals(database) || "".equals(rootPackage)) {
                    JOptionPane.showMessageDialog(panel, "所有文本框必填！");
                    return;
                }

                UserConfigModel userConfigModel = new UserConfigModel();
                userConfigModel.setHost(host);
                userConfigModel.setPort(port);
                userConfigModel.setUsername(username);
                userConfigModel.setPassword(password);
                userConfigModel.setDatabase(database);
                userConfigModel.setRootPackage(rootPackage);
                userConfigModel.setUseExample(useExample);
                UserConfigUtil.saveUserConfig(userConfigModel);

                Generator generator = new Generator();
                try {
                    generator.generate(userConfigModel);
                    JOptionPane.showMessageDialog(panel, "生成完毕。");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(panel, ex.getMessage());
                }
            }
        });

        JButton openButton = new JButton("打开生成文件夹");
        openButton.setBounds(200, 220, 150, 25);
        panel.add(openButton);
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().open(new File(AppConfig.getGeneratedProjectPath()));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        JLabel linkLabel = new JLabel("<html><a href='https://github.com/jackchengyujia'>https://github.com/jackchengyujia</a></html>");
        linkLabel.setBounds(10, 250, 300, 25);
        panel.add(linkLabel);
        linkLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    Desktop desktop = Desktop.getDesktop();
                    URI uri = new URI("https://github.com/jackchengyujia");
                    desktop.browse(uri);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }
}