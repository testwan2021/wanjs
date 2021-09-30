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
package com.somnus.cloud.provider.consumer;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.somnus.cloud.common.util.JacksonUtil;
import com.somnus.cloud.comon.core.mq.MqMessage;
import com.somnus.cloud.provider.service.OptSmsService;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: OptSendSmsTopicConsumer
 * @Description: The class Opt send sms topic consumer.
 * @author Somnus
 * @date 2018年10月17日
 */
@Slf4j
@Service
public class OptSendSmsTopicConsumer {

	@Autowired
	private OptSmsService smsService;

	/**
	 * Handler send sms topic.
	 *
	 * @param body      the body
	 * @param topicName the topic name
	 * @param tags      the tags
	 * @param keys      the keys
	 */
	public void handlerSendSmsTopic(String body, String topicName, String tags, String keys) {
		MqMessage.checkMessage(body, keys, topicName);
		SendSmsRequest sendSmsRequest;
		try {
			sendSmsRequest = JacksonUtil.parseJson(body, SendSmsRequest.class);
		} catch (Exception e) {
			log.error("发送短信MQ出现异常={}", e.getMessage(), e);
			throw new IllegalArgumentException("JSON转换异常", e);
		}
		String ipAddr = sendSmsRequest.getOutId();
		if (StringUtils.isEmpty(ipAddr)) {
			throw new IllegalArgumentException("outId不能为空");
		}
		smsService.sendSms(sendSmsRequest);
	}
}
