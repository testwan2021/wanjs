package lock;

public class zookeeperLock {
    /*
    zookeeper存储像一棵树，每个内容存放的叫节点znode,
    默认是持久性节点
    顺序持久性
    临时节点
    顺序临时节点
    思路：
    Zookeeper分布式锁恰恰应用了临时顺序节点
    创建lock节点成功，持有锁，没有成功，会设置一个watcher等待通知，等之前的锁释放（删除临时节点），下一个就会得到锁了。

     */
}
