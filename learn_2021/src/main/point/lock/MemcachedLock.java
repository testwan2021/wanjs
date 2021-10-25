package lock;

public class MemcachedLock {
    /*
    比如在某知名网站上的热点搜索排名，前10的数据会缓存在memcached中，当时当某个数据不在，就会有大量请求来到db库
    这样的话就需要多个请求来查询db后 再放到cached中，
    这种情况在大并发的情况，很多请求直接到了数据库是很危险的，可能会导致雪崩。
    解决方法，采用memcached锁


    public static void main(String[] args) {
        if(memcached.get(key)==null){
            //再查询数据库前，设置一个分布式锁变量，加锁成功了再去查库，如果请求没有获取到锁的，就睡眠等下，再去获取原始的cache,
            //为了防止其死锁也要加过期时间
            if(memcached.add(key_nex,3*60*1000)==true) {
                value = db.get(key);
                memcached.set("key", value);
                memcached.delete(key_nex);
            }else{
                sleep(50);
                retry()；//获取原始的cache业务逻辑
            }
        }
    }
     */
}
