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
package com.somnus.cloud.provider.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;

import com.somnus.cloud.common.base.constant.AliyunMqTopicConstants;
import com.somnus.cloud.common.base.constant.GlobalConstant;
import com.somnus.cloud.common.config.properties.CloudProperties;
import com.somnus.cloud.common.util.PublicUtil;
import com.somnus.cloud.provider.consumer.listener.OptPushMessageListener;

/**
 * @ClassName: AliyunMqConfiguration
 * @Description: The class Aliyun mq configuration.
 * @author Somnus
 * @date 2018年10月17日
 */
@Slf4j
@Configuration
public class AliyunMqConfiguration {

	@Autowired
	private CloudProperties cloudProperties;

	@Autowired
	private OptPushMessageListener optPushConsumer;

	@Autowired
	private TaskExecutor taskExecutor;

	/**
	 * Default mq push consumer default mq push consumer.
	 *
	 * @return the default mq push consumer
	 *
	 * @throws MQClientException the mq client exception
	 */
	@Bean
	public DefaultMQPushConsumer defaultMQPushConsumer() throws MQClientException {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(cloudProperties.getAliyun().getRocketMq().getConsumerGroup());
		consumer.setNamesrvAddr(cloudProperties.getAliyun().getRocketMq().getNamesrvAddr());
		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);

		String[] strArray = AliyunMqTopicConstants.ConsumerTopics.OPT.split(GlobalConstant.Symbol.COMMA);
		for (String aStrArray : strArray) {
			String[] topicArray = aStrArray.split(GlobalConstant.Symbol.AT);
			String topic = topicArray[0];
			String tags = topicArray[1];
			if (PublicUtil.isEmpty(tags)) {
				tags = "*";
			}
			consumer.subscribe(topic, tags);
			log.info("RocketMq OpcPushConsumer topic = {}, tags={}", topic, tags);
		}

		consumer.registerMessageListener(optPushConsumer);
		consumer.setConsumeThreadMax(2);
		consumer.setConsumeThreadMin(2);

		taskExecutor.execute(() -> {
			try {
				Thread.sleep(5000);
				consumer.start();
				log.info("RocketMq OpcPushConsumer OK.");
			} catch (InterruptedException | MQClientException e) {
				log.error("RocketMq OpcPushConsumer, 出现异常={}", e.getMessage(), e);
			}
		});
		return consumer;
	}

}
