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
package com.somnus.cloud.provider.mq;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import com.somnus.cloud.common.base.constant.GlobalConstant;
import com.somnus.cloud.common.base.enums.ErrorCodeEnum;
import com.somnus.cloud.comon.core.mq.MqMessage;
import com.somnus.cloud.provider.api.exceptions.TpcBizException;
import com.somnus.cloud.provider.service.MqProducerBeanFactory;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: RocketMqProducer
 * @Description: The class Rocket mq producer.
 * @author Somnus
 * @date 2018年10月17日
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RocketMqProducer {

	private static final int PRODUCER_RETRY_TIMES = 3;

	public static SendResult sendSimpleMessage(String body, String topic, String tag, String key, String pid, Integer delayLevel) {
		if (delayLevel == null) {
			delayLevel = 0;
		}
		Message message = MqMessage.checkMessage(body, topic, tag, key);
		if (delayLevel < 0 || delayLevel > GlobalConstant.Number.EIGHTEEN_INT) {
			throw new TpcBizException(ErrorCodeEnum.TPC100500013, topic, key);
		}
		message.setDelayTimeLevel(delayLevel);
		return retrySendMessage(pid, message);
	}

	private static SendResult retrySendMessage(String pid, Message msg) {
		int iniCount = 1;
		SendResult result;
		while (true) {
			try {
				result = MqProducerBeanFactory.getBean(pid).send(msg);
				break;
			} catch (Exception e) {
				log.error("发送消息失败:", e);
				if (iniCount++ >= PRODUCER_RETRY_TIMES) {
					throw new TpcBizException(ErrorCodeEnum.TPC100500014, msg.getTopic(), msg.getKeys());
				}
			}
		}
		log.info("<== 发送MQ SendResult={}", result);
		return result;
	}

}
