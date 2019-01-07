# CodeX4J

CodeX4J是一款开源的Java代码生成器，整个软件就一个jar包，双击即可运行，操作非常简单。  
CodeX4J根据数据库自动生成基于Maven的SSM项目，包含SSM框架的基本配置和各层基础代码。  
其中Mapper和Model是调用MyBatis的官方代码生成器生成，并且自定义了Model注释的生成规则。  
无论是IntelliJ IDEA、Eclipse、MyEclipse，只要配置好Maven和Tomcat，都可以打开直接运行。  
默认生成的Controller中，除了首页返回页面，其它都返回JSON数据。  
所有代码都可以根据实际情况自行修改。  
CodeX4J希望能为您的工作效率加油！

![jar包](/img/jar.jpg)  
![运行界面](/img/UI.jpg)  
![生成的文件](/img/generatedFiles.jpg)  
![网站首页](/img/index.jpg)  