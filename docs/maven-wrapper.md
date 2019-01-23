### maven wrapper
    
     下载项目, 切换的项目根目录
       ```bash
       Linux/Mac:
       
       > cd miaosha/
       > ./mvnw clean install
       
       Windows:
       > mvnw.cmd clean install
       
       ```
        运行以上命令相关依赖便会安装完毕
        启动GeekQMainApplication主类即可
       若有对于./mvnw 不了解的请点击下方链接介绍
       
> 传统maven的使用流程
    
    1. 传统使用maven需要先到官网上下载.
    2. 配置环境变量把mvn可执行文件路径加入到环境变量，以便之后使用直接使用mvn命令.
    3. 另外项目pom.xml文件描述的依赖文件默认是下载在用户目录下的.m2文件下的repository目录下.
    4. 再次，如果需要更换maven的版本，需要重新下载maven并替换环境变量path中的maven路径.
    
    
> maven-wrapper，会获得以下特性

    执行mvnw比如./mvnw clean ，如果本地没有匹配的maven版本，直接会去下载maven，放在用户目录下的.m2/wrapper中
    并且项目的依赖的jar包会直接放在项目目录下的repository目录，这样可以很清晰看到当前项目的依赖文件。
    如果需要更换maven的版本，只需要更改项目当前目录下.mvn/wrapper/maven-wrapper.properties的distributionUrl属性值，更换对应版本的maven下载地址。mvnw命令就会自动重新下载maven。
    可以说带有mvnw文件的项目，除了额外需要配置 java环境外，只需要使用本项目的mvnw脚本就可以完成编译，打包，发布等一系列操作。

> 如何使用呢？

    通常我们在使用maven的时候会执行如下一些命令：
    mvn clean
    mvn install
    mvn package
    ...
    
    使用maven wrapper之后只需打开terminal 执行如下等价命令即可：
    linux:
        ./mvnw clean
        ./mvnw install
        ./mvnw package
        ...
        
    windows:
        mvnw.cmd clean
        mvnw.cmd install
        mvnw.cmd package
        ...
        
> Get Started! 
        
    因此大家在下载项目代码之后, 只需要执行 ./mvn clean install 
    maven warpper 便会自动为该项目构建maven环境.
    当然如果大家用的IDE是idea 那么依然也可以使用右上方的MavenProject Panel 鼠标触发各个Task