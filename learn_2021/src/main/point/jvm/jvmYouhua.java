package jvm;

public class jvmYouhua {
    /*
    jvm优化目标：使用较小的内存来获得最大的吞吐量和及时响应。
    场景：cpu过高 请求延迟（垃圾回收时会卡顿）  吞吐量低 垃圾回收越来越频繁 内存溢出 这些情况都需要针对性的进行调优后，进行测试观察。
    调优依据：
    1.设置相关依赖参数，可以参考：栈堆错误日志，GC日志，系统运行日志，现场快照，
    jvm 调优工具：在jdk中带了很多调优插件
    jps（jvm  process status） ->jps  -l pid
    jstat  监控虚拟机信息  jstat -gc pid  500 10 每500毫秒打印一次Java堆状况（各个区的容量、使用容量、gc时间等信息），打印10次
    jmap和jhat 配合使用  堆内存信息
    jconsole jvmsualvm 分析内存信息


    实战：
    JVM服务问题排查 https://blog.csdn.net/jacin1/article/details/44837595
    次让人难以忘怀的排查频繁Full GC过程 http://caogen81.iteye.com/blog/1513345
    线上FullGC频繁的排查 https://blog.csdn.net/wilsonpeng3/article/details/70064336/
    【JVM】线上应用故障排查 https://www.cnblogs.com/Dhouse/p/7839810.html
    一次JVM中FullGC问题排查过程 http://iamzhongyong.iteye.com/blog/1830265
    JVM内存溢出导致的CPU过高问题排查案例 https://blog.csdn.net/nielinqi520/article/details/78455614
    一个java内存泄漏的排查案例 https://blog.csdn.net/aasgis6u/article/details/54928744









     */
}
