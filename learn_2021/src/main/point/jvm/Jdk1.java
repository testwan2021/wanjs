package jvm;

public class Jdk1 {
     /*
      jdk:一套开发程序工具包+jre,lib类库，在java程序过程中使用
      jre:jdk中的bin目录下的运行环境,包含了jvm，在编译后的class进行运行
      jvm：当开发的人写的Java文件，需要进行编译成class文件，在不同的操作系统，需要有对应半的jvm，才可以运行class文件。

      类的加载流程：class文件->内存->虚拟机中->类加载器字节码验证（调用java类库验证）->准备->初始化(类变量)->使用->卸载

      jvm周期：在代码运行的时候，jvm运行，等程序结束，jvm也停止。
      jvm线程分2种，守护线程和普通线程

      类装载子系统（类加载器）提供了默认的三个类加载器：
      BootStrap ClassLoader  顶级的类加载器 负责核心类库 rt.jar resource.jar等
      Extension ClassLoader 扩展类加载器 jre/lib下面的包
      App ClassLoader 应用级别，加载程序的class文件和相关依赖的jar

      jvm组成：方法区  虚拟机栈  堆(主要发生内存溢出的，调优的主要部分)   本地方法栈   程序计数器
      总结：本地方法栈+虚拟机栈+程序计数器 都是随着线程的生命周期而结束，所以不存在垃圾回收
           方法区和堆是需要垃圾回收的

      双亲委派：
      比如建一个类名叫System,根据爸爸BootStrap先找到，儿子就没有机会加载。
      优点：1.避免重复加载 2.避免核心类被篡改，保障了类的安全性
      流程：当Application ClassLoader 接到一个请求，先会去让他的父类去加载，一直往上，如果没有找到就自己来加载。

     */
}
