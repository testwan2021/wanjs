package jvm;

public class JvmMemcache {
    /*
    jdk1.7 堆里的内存模型
    Young 新生代 ->Eden区+Survivior from->to ,大小默认8:1:1 ,当Eden区满，GC将存活的对象放到空闲的Survivior，经过几次后，依旧在Survivior
    存活的就会被移动到老年代，默认生命区是15次。
    老年代：主要保存生命周期长的对象和new出来的大对象
    永久代Perm：主要放class  method filed(属性)，一般不会溢出，除非一次性加载很多类。

    在jdk1.8 堆的内存模型
    年轻代 Eden+Survivior
    老年代 oldGen
    把jdk7的永久区 改成了 元空间metaspace ,元空间非jvm内存，而且本地内存。

    为何设置元空间？
    1.在jdk7时候，如方法数据，方法信息，运行的常量都在永久代，由于永久代默认的空间很小，很容易造成内存溢出OOM
    2.永久代的内存大小比例很难设定，因为 方法相关的都是不定的，大了会浪费，小了怕不够
    3.在1.8改成的元空间用的是本机的内存，这个可以最大分配内存。
     */
}
