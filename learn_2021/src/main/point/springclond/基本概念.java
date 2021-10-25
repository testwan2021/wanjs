package springclond;

public class 基本概念 {
    /*
    NetFlix核心组件：
    1.springclound是一套微服务框架解决方案集合
      eureka  "优立卡" 服务注册中心（剔除和纳管新的服务，这个始终保持是可用服务的列表->类似教研组的老师名单）
      ribbon  "李本"   客户端负载均衡 有重试等机制  默认轮询（nginx类似）
      feign   "飞恩"   声明式服务调用，本质上就是Ribbon+Hystrix （根据注解和选择机器，拼接请求URL地址，发起请求）
      hystrix  "孩丝去可丝"  客户端容错的保护  服务降级、服务熔断、请求缓存、请求合并、依赖隔离
      zuul  "煮.."  api网关 主要有隐藏域名和路由分发 过滤非法请求  （学校的门卫）
      config  分布式配置中心 （规则制度，大家到这里获取规定的配置）
      dashbroad "答西波得" 为hystrix的仪表盘 监控一个流量的变化 类似保安监控室
      sleuth  "分布式追踪系统"
     */


    /*
     Spring cloud Alibaba核心组件
     Nacos  服务注册中心 （包含配置与注册中心）






     */

}
