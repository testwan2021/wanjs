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
package com.somnus.cloud.provider.consumer.listener;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import com.somnus.cloud.common.base.constant.AliyunMqTopicConstants;
import com.somnus.cloud.comon.core.mq.MqMessage;
import com.somnus.cloud.provider.api.annotation.MqConsumerStore;
import com.somnus.cloud.provider.api.service.MqMessageService;
import com.somnus.cloud.provider.consumer.MdcTopicConsumer;
import com.somnus.cloud.provider.consumer.OptSendEmailTopicConsumer;
import com.somnus.cloud.provider.consumer.OptSendSmsTopicConsumer;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: OptPushMessageListener
 * @Description: The class Opt push message listener.
 * @author Somnus
 * @date 2018年10月17日
 */
@Slf4j
@Component
public class OptPushMessageListener implements MessageListenerConcurrently {

	@Autowired
	private OptSendSmsTopicConsumer optSendSmsTopicService;
	
	@Autowired
	private OptSendEmailTopicConsumer optSendEmailTopicService;
	
	@Autowired
	private MdcTopicConsumer mdcTopicConsumer;

	@Autowired
	private MqMessageService mqMessageService;
	
	@Autowired
	private StringRedisTemplate srt;

	/**
	 * Consume message consume concurrently status.
	 *
	 * @param messageExtList             the message ext list
	 * @param consumeConcurrentlyContext the consume concurrently context
	 *
	 * @return the consume concurrently status
	 */
	@Override
	@MqConsumerStore
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messageExtList, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
		MessageExt msg = messageExtList.get(0);
		String body = new String(msg.getBody());
		String topicName = msg.getTopic();
		String tags = msg.getTags();
		String keys = msg.getKeys();
		log.info("MQ消费Topic={},tag={},key={}", topicName, tags, keys);
		ValueOperations<String, String> ops = srt.opsForValue();
		// 控制幂等性使用的key
		try {
			MqMessage.checkMessage(body, topicName, tags, keys);
			String mqKV = null;
			if (srt.hasKey(keys)) {
				mqKV = ops.get(keys);
			}
			if (StringUtils.isNotEmpty(mqKV)) {
				log.error("MQ消费Topic={},tag={},key={}, 重复消费", topicName, tags, keys);
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
			if (AliyunMqTopicConstants.MqTopicEnum.SEND_SMS_TOPIC.getTopic().equals(topicName)) {
				optSendSmsTopicService.handlerSendSmsTopic(body, topicName, tags, keys);
			}
			if (AliyunMqTopicConstants.MqTopicEnum.SEND_EMAIL_TOPIC.getTopic().equals(topicName)) {
				optSendEmailTopicService.handlerSendEmailTopic(body, topicName, tags, keys);
			}
			if (AliyunMqTopicConstants.MqTopicEnum.TPC_TOPIC.getTopic().equals(topicName)) {
				mqMessageService.deleteMessageTopic(body, tags);
			}
			if (AliyunMqTopicConstants.MqTopicEnum.MDC_TOPIC.getTopic().equals(topicName)) {
				mdcTopicConsumer.handlerSendSmsTopic(body, topicName, tags, keys);
			} else {
				log.info("OPC订单信息消 topicName={} 不存在", topicName);
			}
		} catch (IllegalArgumentException ex) {
			log.error("校验MQ message 失败 ex={}", ex.getMessage(), ex);
		} catch (Exception e) {
			log.error("处理MQ message 失败 topicName={}, keys={}, ex={}", topicName, keys, e.getMessage(), e);
			return ConsumeConcurrentlyStatus.RECONSUME_LATER;
		}
		ops.set(keys, keys, 10, TimeUnit.DAYS);
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}
}
