package lock;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

public class RedisLock {
    /*
    锁：主要避免由于竞争而造成的数据不一致，常用的java关键字：lock synchronized
    分布式锁过程：
    加锁 获得锁对象
    解锁
    锁超时：为了避免死锁，我们可以设置一阵风，在单位时间后刮起，将脚印自动抹去。

    分布式锁分类：基于数据库  memcached  reids   zookeeper
    java采用分布式redis客户端 Redisson ,锁的默认时间是30秒
    步骤：
    通过setnx命令来对业务的唯一标识进行加锁，value为当前的线程。setnx(key,1) 返回1 代表加锁成功（原先没有锁），
    当其他线程执行setnx返回0，说明key已经存在，该线程抢锁失败
    del(key)为解锁
    锁超时设置expire(key,30) 单位秒

    if(setnx(key,1)==1){
       expire(key,60);
       //业务逻辑
       try{
       }catch(Exception e){
       }finally{
       del(key)
       }
    }

    这个代码缺点：
    1.因为setnx和expire都是不是原子性操作，当某线程执行setnx，成功得到了锁，还未来得及执行expire指令，挂了。那么这个锁不会再释放了。
    2.假如设置了30秒超时，如果业务逻辑较为复杂，在30秒没执行完就释放了 ，其他人拿到了锁。这时候代码A自己来删除锁，就会删除b的锁。超时后使用del 导致误删其他线程的锁
    采用获取线程id来加锁，判断
    set（key，threadId ，30，NX）
    if(threadId== redisClient.get(key))

    3.出现并发的问题：虽然我们避免了线程A误删掉key的情况，但是同一时间有A，B两个线程在访问代码块
    让获得锁的线程开启一个守护线程，用来给快要过期的锁“续航”，当过去了29秒，线程A还没执行完，这时候守护线程会执行expire指令，为这把锁“续命”20秒。守护线程从第29秒开始执行，每20秒执行一次。
    当线程A执行完任务，会显式关掉守护线程

     */
    public static void main(String[] args) {
        RedissonClient redissonClient= Redisson.create(null);
        RLock  lock=redissonClient.getLock("keyword");
    }


}
