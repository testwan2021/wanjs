package springclond;

public class 基本概念 {
    /*
    netFlix核心组件：
    1.springclound是一套微服务框架解决方案集合:
      eureka  "优立卡" 服务注册中心（剔除和纳管新的服务，这个始终保持是可用服务的列表->类似教研组的老师名单）
      ribbon  "李本"   客户端负载均衡 有重试等机制  默认轮询（nginx类似）
      feign   "飞恩"   声明式服务调用，本质上就是Ribbon+Hystrix （用注解的方式进行服务调用，代码更好看了）
      hystrix  "孩丝去可丝"  客户端容错的保护  服务降级、服务熔断、请求缓存、请求合并、依赖隔离
      zuul  "煮.."  api网关 主要有隐藏域名和路由分发 过滤非法请求  （学校的门卫）
      config  分布式配置中心 （规则制度，大家到这里获取规定的配置）
      dashbroad "答西波得" 为hystrix的仪表盘 监控一个流量的变化 类似保安监控室
      turbine  ""
      sleuth  "分布式追踪系统"



      流程思路：1.服务端->eureka去注册->eureka客户端去发现->去调用ribbon(负载均衡)->feign(服务调用)->hystrix (隔离 熔断 降级) ->网关路由->调用之前注册的服务端


     */


}
