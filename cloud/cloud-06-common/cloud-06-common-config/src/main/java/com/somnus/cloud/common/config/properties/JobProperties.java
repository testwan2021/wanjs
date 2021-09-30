/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.somnus.cloud.common.config.properties;

import lombok.Data;

/**
 * @ClassName: JobProperties
 * @Description: The class Job core properties.
 * @author Somnus
 * @date 2018年9月26日
 */
@Data
public class JobProperties {
    private JobTask task = new JobTask();

    @Data
    public class JobTask {
        private JobCoreProperties dingTalk = new JobCoreProperties();
        private JobCoreProperties sendingMessage = new JobCoreProperties();
        private JobCoreProperties waitingMessage = new JobCoreProperties();

        @Data
        public class JobCoreProperties {
            /**
             * 作业名称
             */
            private String jobName;
            /**
             * cron表达式，用于控制作业触发时间
             */
            private String cron;
            /**
             * 作业分片总数
             */
            private int shardingTotalCount;
            /**
             * 分片序列号和参数用等号分隔，多个键值对用逗号分隔,分片序列号从0开始，不可大于或等于作业分片总数如：0=a,1=b,2=c
             */
            private String shardingItemParameters;

            /**
             * 作业自定义参数，可通过传递该参数为作业调度的业务方法传参，用于实现带参数的作业例：每次获取的数据量、作业实例从数据库读取的主键等
             */
            private String jobParameter;
            /**
             * 是否开启任务执行失效转移，开启表示如果作业在一次任务执行中途宕机，允许将该次未完成的任务在另一作业节点上补偿执行
             */
            private boolean failover;
            /**
             * 是否开启错过任务重新执行
             */
            private boolean misfire = true;
            /**
             * 作业描述信息
             */
            private String description;

            /**
             * 配置jobProperties定义的枚举控制Elastic-Job的实现细节JOB_EXCEPTION_HANDLER用于扩展异常处理类EXECUTOR_SERVICE_HANDLER用于扩展作业处理线程池类
             */
            private Enum<?> jobProperties;
        }
    }
}
