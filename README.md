# README

本项目采用Java程序设计语言，所选择的JDK为17.0.2版本，在Apache Maven 3.8.7包管理工具的帮助下，搭配MySQL 8.0.28的数据库后端开发完成。本文档将帮助您复现项目的运行环境并再现程序的编译和运行过程。

首先，本项目中所有的包依赖都是采用Maven来进行管理的，如果您的环境中有安装Maven的Java工具链，那么我非常建议您采用该方式来管理本项目。当然，Visual Studio Code中自带的Java相关插件也是可以完成类似的功能的。

如果您不确定您的环境中是否有安装Maven，您可以采用以下命令来查看：

~~~shell
mvn --version
~~~



如果您的环境中已经安装好了Maven，那么您会看见类似下面的输出，下面这是在我的环境中的运行结果。

~~~shell
Apache Maven 3.8.7 (b89d5959fcde851dcb1c8946a785a163f14e1e29)
Maven home: D:\apache-maven-3.8.7
Java version: 17.0.2, vendor: Oracle Corporation, runtime: D:\Java\jdk-17.0.2
Default locale: zh_CN, platform encoding: GBK
OS name: "windows 10", version: "10.0", arch: "amd64", family: "windows"
~~~



在本目录中，已经配置好了相应的POM文件，它位于与本文档相同的目录下，并命名为`pom.xml`。如果您想要修改相应的配置以适应您的环境的话，您可以在其中直接进行修改。如果您对Maven的POM文件还不是特别熟悉的话，我非常建议您可以在Maven的官网的[教程](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html)中学习了解一下。

为了运行程序，我们需要首先对整个项目中的所有Java程序进行打包编译。您可以在当前目录的命令行中输入如下命令实现该操作：

~~~shell
mvn package
~~~



随后，你会在当前目录下看到新建的一个`target`目录，其中包含了我们整个项目打包之后的产物。

在我们的程序中，一共有两个类是包含有main函数的主类，其中一个来自服务器的主运行程序`Main.java`，另一个来自客户端的主运行程序`App.java`，您可以分别采用以下命令来运行客户端和服务器程序。

服务器：

~~~
java -cp target/art-1.0-SNAPSHOT.jar edu.rihong.Main
~~~



客户端：

~~~shell
java -cp target/art-1.0-SNAPSHOT.jar edu.rihong.App
~~~



由于在我们的程序中使用了MySQL作为数据库后端，并且使用了`lib`目录下的`mysql-connector-j-8.0.31.jar`库。因此，如果您想要在运行时尝试相应的数据库功能，您需要首先将`lib`目录下的第三方MySQL连接库加入到您的库之中。

我们把测试时使用的数据库及其相应的数据放在了`data`目录下，您可以将`data`目录下的`cherryforum.sql`文件导入到您的MySQL数据库`cherryforum`之中。

当然，您也可以使用以下的MySQL命令来创建相应的数据库：

~~~mysql
DROP DATABASE IF EXISTS cherryforum;

CREATE DATABASE cherryforum;
USE cherryforum

CREATE TABLE user(
    uid varchar(10) primary key,
    name varchar(64),
    gender enum('M', 'F'),
    fortune int default 5000,
    password varchar(64)
);
~~~



此外，您可能还需要修改`src\main\java\edu\rihong\DB\Database.java`中的`MYSQL_USER`变量和`MYSQL_PASSWORD`变量，将其改为您在您的MYSQL数据库中的用户名和密码。

这些都完成之后，您便可以体验数据库相关的注册和登录以及游戏记录等了。当然，如果您觉得无需使用数据库相关功能，程序依然也是可以正确运行并实现Tic-Tac-Toe游戏等功能的正常游玩的。



当然，如果您有Visual Studio Code并且实现了正确配置的话，您也可以先运行`Main.java`启动服务器，随后可以再运行多个`App.java`启动客户端游玩。



祝您玩得愉快 :D

