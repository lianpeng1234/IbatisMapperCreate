一：如何运行
1:运行环境
安装jdk1.6或者更高版本,window操作系统，需要对 C盘 有操作权限

2:执行命令,运行jar包
java -jar jarName.jar

3:依赖的第三方jar包
mysql-connector-java-5.1.29.jar
freemarker-2.3.18.jar
fastjson-1.1.41.jar
commons-lang3-3.3.2.jar

二：如何创建属于自己的 Controller 和 页面
1：创建属于自己的 Controller
方法com.lp.ibatis.CreateAppControlerFileUtil.createAppControllerFile(List<TableColumnInfo> tableInfo, String tableName) 的方法体是空的，
你想使用什么技术创建Controller都是可以的。

2：创建属于自己的页面
方法com.lp.ibatis.CreateViewFileUtil.createViewFile(List<TableColumnInfo> tableInfo, String tableName) 的方法体是空的，
你想使用什么技术创建页面都是可以的。

