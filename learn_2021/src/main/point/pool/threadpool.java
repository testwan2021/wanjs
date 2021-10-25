package pool;

public class threadpool {
/*
1.线程池原理
线程池就是提前创建好一些线程，等待有任务要处理，线程池就会处理，之后不会销毁，继续存着，这样就避免了创建和销毁带来的性能消耗，
对提高系统整体性能有好处。
可以根据系统的承受能力，调整线程池中线程的数量，防止消耗内存过大。
线程池的顶级接口是Executor，是一个执行线程的工具。
2.创建方式
Executors工厂方法创建
创建大小不固定的线程池，创建数量固定的线程池，创建定时线程池，创建单线程的线程池
ThreadPoolExcutor 自定义创建
参数：corePoolSize 核心线程数 一直存活，即使没有任务处理
maxPoolSize 当线程数大于或等于核心线程，且任务队列已满时，线程池会创建新的线程，直到线程数量达到maxPoolSize。
如果线程数已等于maxPoolSize，且任务队列已满，则已超出线程池的处理能力，线程池会拒绝处理任务而抛出异常。
keepAliveTime 当线程空闲时间到了 ，线程会退出
allowCoreThreadTimeout  是否允许核心线程空闲退出
queueCapacity  队列容量 需要设置为合理的大小

3.线程执行流程
int threadcount;
int corethread;
int maxPoolSize;
boolean queueCapacity;
if(threadcount<corethread){
     new Thread().start();//创建线程
}
if(threadcount<corethread && queueCapacity){//等待队列没有满
     new Thread().start();//继续创建线程
}else {
//等待队列满了
if(threadcount<maxPoolSize){
      new Thread().start();//继续创建线程
}else{
      //拒绝策略
      AbortPolicy ->拒绝并且跑出异常
      DiscardPolicy  ->丢弃任务不跑异常
      DiscardOldestPolicy ->丢弃队列最前面的，重新提交被拒绝的
      CallerRunsPolicy ->由提交任务的线程自行处理
}
}

3.线程状态
new 创建
runnable 调用start()方法后 可运行/就绪
running  获取的cpu资源 运行中
blocked  调用sleep() join（） 阻塞锁  会变成阻塞等待 ->等待结束后->runnable 可运行/就绪
dead  调用run（）方法 执行完就是 结束

4.线程池的状态
6种状态：
new thread() ->初始状态
start()->可运行
等待cpu调度选中->运行中->等cpu时间用完调用yield()线程礼让
运行中：可以调用sleep()或者join() 就变成阻塞状态，结束sleep和join ->可运行
运行中：调用wait()->等待队列->notyfi()或者等待时间到了->锁池状态->拿到对象的锁标记->可运行
run()或者main方法-结束

 */
}
