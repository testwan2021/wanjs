package jvm;

import java.util.stream.Stream;

public class Jdk8Info {
    /*
    一.jdk的新特性：
    lambda表达式->匿名内部类
    优点：代码简洁和减少匿名内部类的使用

    函数式接口 @FunctionalInterface


    Stream API
    简单的遍历，stream串行api效率低于迭代，但是并行stream发挥多核特性
    stream不是数据结构，是一种算法，可以过滤，查找,排序，限制第几个元素 等

    Map的改变：数据结构变化，之前采用的是数组+链表，默认大小是16，存储：先调用元素的hashcode方法，得到hash值做为元素的索引位置，如果没有
    直接存放，如果有，对比元素的内容是否一样，如果一样，直接value覆盖，如果不一样，1.7的时候,直接是一个链表，在这地方一直存下去，遍历的时候效率低。
    1.8->数组+链表+红黑树，当碰撞元素>8 and 容量大于64 ，就会转红黑树，效率高

    */

    public static void main(String[] args) {
       // new Thread( () -> System.out.println("无参数无返回值表达") ).start();

        Stream<Integer>  stream=Stream.of(1,2,3,4,5,6);
        System.out.println(stream.findFirst());
    }
}
