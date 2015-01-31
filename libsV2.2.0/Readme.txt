**各种第三方库工具集合**
一、--compile-libs\androidannotations-3.0.1.jar
   --libs\androidannotations-api-3.0.1.jar
   ----这两个jar包是做注入用的，用于简化代码
   ------1、项目中使用的话需要配置一下。右键安卓项目项目Properties依次选择Java Compiler > Annotation Processing > Factory Path-> addjars… 
                                     把compile-libs文件夹下的androidannotations-x.x.x.jar添加入内！
         2、如何使用的demo在src>com>opensky>androidannotationsDemos下
         3、需要注意的是AndroidManifest.xml中activity的定义,在后面都需要加上“_”
         4、项目中会生成.apt_generated这样一个根目录，其中保存的就是它所生成的java文件(不用修改,会根据你的定义自行编译生成)
二、--libs\ksoap2-android-assembly-3.1.1-jar-with-dependencies.jar
       这个jar包用于调用webservice相关处理
三、--src\de..\..
       这个目录是greenDao第三方框架的源代码。用于便捷的操作sqlite数据库