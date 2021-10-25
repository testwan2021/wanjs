package jvm;

public class JvmSf {
    /*
    判断对象是否可以回收的标准：判断对象是否还有被引用
    规则：
    给对象添加一个引用计数器，失效-1，引用+1.当计数器=0，代表可回收（引用计数）
    可达性分析：从一个根目录节点开始，往下查找（虚拟机栈中本地变量，方法区中静态对象和常量对象）

    4种引用类型：
    强引用 Object obj = new Object() 比如数组
    软引用 还有用但不是必须的 比如缓存
    弱引用:当垃圾回收发生，不管内存是否够，对应一定会被回收
    虚引用：用于对象的销毁 如 资源释放

    垃圾回收算法：
    1.标记-清除算法
    先标记-再统一清理不可用的对象，缺点：效率低，会产生内存碎片
    2.复制算法
    新生代，在eden区不够发生垃圾回收，把存活的对象放到from或者to空间,再来清理掉eden和from或者to
    3.标记-整理算法
    老年代使用，从根节点找不存活对象，把存活的对象压缩到另外一边，优点：不产生内存碎片
     */

    public static void main(String[] args) {
        String a=new String ("333");

    }
}
