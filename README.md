<img src="/image/logo.png" width="40%" height="40%" />

# jpa mapper只是spring  data jpa 的搬运工,无任何侵入
[![Maven central](https://maven-badges.herokuapp.com/maven-central/cn.xphsc/spring-data-jpamapper/badge.svg)]
[![APACHE 2 License](https://img.shields.io/badge/license-Apache2-blue.svg?style=flat)](LICENSE)
 <a target="_blank" href="https://search.maven.org/search?q=spring-data-jpamapper%20spring-data-jpamapper">
        <img src="https://img.shields.io/maven-central/v/cn.xphsc/parent?label=Maven%20Central" alt="Maven" />
    </a>
<a target="_blank" href="http://www.javadoc.io/badge/cn.xphsc/spring-data-jpamapper.svg">
<img src="http://www.javadoc.io/badge/cn.xphsc/spring-data-jpamapper.svg" />
</a>
<a target="_blank" href="https://img.shields.io/maven-central/v/cn.xphsc/spring-data-jpamapper.svg">
<img src="https://img.shields.io/maven-central/v/cn.xphsc/spring-data-jpamapper.svg" />
</a>
    <a target="_blank" href="https://www.apache.org/licenses/LICENSE-2.0.txt">
		<img src="https://img.shields.io/:license-Apache2-blue.svg" alt="Apache 2" />
	</a>
    <a target="_blank" href="https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html">
		<img src="https://img.shields.io/badge/JDK-8-green.svg" alt="jdk-8" />
	</a>
    <a target="_blank" href="https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html">
		<img src="https://img.shields.io/badge/JDK-11-green.svg" alt="jdk-11" />
	</a>
    <a target="_blank" href="https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html">
		<img src="https://img.shields.io/badge/JDK-17-green.svg" alt="jdk-17" />
	</a>
#### 项目介绍
jpa mapper在spring  data jpa之上进行了一些包装，使得经过注解的实体可以像mybatis 一样进行SQL 增、删、改和获取。SQL构造工具、SQL注解、链式API等让查询操作更为灵活
> 函数表达式， SQL注解 Optional返回 ,Criteria条件函数表达式

- Please choose the _Spring data jpamapper JPA_ version appropriate with your spring version.

  | Spring Boot version | Spring data  jpamapper version |
  |:-------------------:|:--------------------------:    |
  |    2.0.x.RELEASE    |           1.2.5                |
  |    2.2.x-2.7.18     |           2.x.x                |
  |        3.x.x        |           3.0.x                |

**功能简介：**

1：原生JPA以及 spring data jpa 支持。

2：简化的批处理操作。

4：简化的分页操作。

5：灵活的链式查询API和SQL构造器以及SQL注解。

6：实体属性动态映射。

7：支持多种数据库（mysql,mariadb,oracle,sqlserver,postgresql,db2,sqlite,hsqldb）。
#### 安装教程
spring
~~~
 <dependency>
       <groupId>cn.xphsc</groupId>
       <artifactId>spring-data-jpamapper</artifactId>
      <version>2.0.6</version>
</dependency>
~~~
spring boot版本
~~~
<dependency>
   <groupId>cn.xphsc.boot</groupId>
    <artifactId>jpamapper-spring-boot-starter</artifactId>
    <version>2.0.6</version>
</dependency>
~~~
spring boot 3版本
~~~
<dependency>
   <groupId>cn.xphsc.boot</groupId>
    <artifactId>jpamapper-spring-boot-starter</artifactId>
    <version>3.0.3</version>
</dependency>
~~~
#### 使用说明
#### [集成文档 - gitee](https://gitee.com/xphsc/jpa-mapper/wikis/Home)
#### [更新日志 - gitee](https://gitee.com/xphsc/jpa-mapper/wikis/changelog)
#### [QQ技术交流群：593802274]
