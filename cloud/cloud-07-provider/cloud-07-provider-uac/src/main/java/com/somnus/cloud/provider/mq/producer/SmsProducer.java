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
package com.somnus.cloud.provider.mq.producer;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.somnus.cloud.common.base.constant.AliyunMqTopicConstants;
import com.somnus.cloud.common.base.constant.AliyunSmsConstants;
import com.somnus.cloud.common.base.enums.ErrorCodeEnum;
import com.somnus.cloud.common.util.RedisKeyUtil;
import com.somnus.cloud.provider.api.exceptions.UacBizException;
import com.somnus.cloud.provider.api.model.domain.MqMessageData;
import com.somnus.cloud.provider.api.model.dto.PcSendSmsRequest;
import com.somnus.cloud.provider.model.dto.sms.SmsMessage;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: SmsProducer
 * @Description: The class Sms producer.
 * @author Somnus
 * @date 2018年10月17日
 */
@Slf4j
@Component
public class SmsProducer {

	public MqMessageData sendSmsCodeMq(SmsMessage smsMessage, AliyunSmsConstants.SmsTempletEnum templetEnum) {
		log.info("sendSmsCodeMq - 发送短信验证码. smsMessage={}", smsMessage);
		String msgBody;
		try {

			PcSendSmsRequest request = new PcSendSmsRequest();
			Map<String, String> map = Maps.newHashMap();
			// 模板参数
			String smsParamName = templetEnum.getSmsParamName();
			// 模板编码
			String templetCode = templetEnum.getTempletCode();
			//替换模板验证码
			map.put(smsParamName, smsMessage.getSmsCode());
			String param = JSON.toJSONString(map);

			request.setPhoneNumbers(smsMessage.getMobileNo());
			request.setTemplateCode(templetCode);
			request.setTemplateParam(param);
			request.setOutId(smsMessage.getOutId());

			msgBody = JSON.toJSONString(request);
		} catch (Exception e) {
			log.error("发送短信验证码 smsMessage转换为json字符串失败", e);
			throw new UacBizException(ErrorCodeEnum.UAC10011022);
		}
		String topic = AliyunMqTopicConstants.MqTopicEnum.SEND_SMS_TOPIC.getTopic();
		String tag = AliyunMqTopicConstants.MqTagEnum.REGISTER_USER_AUTH_CODE.getTag();
		String key = RedisKeyUtil.createMqKey(topic, tag, smsMessage.getMobileNo(), msgBody);
		return new MqMessageData(msgBody, topic, tag, key);
	}
}
