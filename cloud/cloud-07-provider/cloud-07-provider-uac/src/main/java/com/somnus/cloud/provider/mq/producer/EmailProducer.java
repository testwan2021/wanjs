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
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.somnus.cloud.common.base.constant.AliyunMqTopicConstants;
import com.somnus.cloud.common.base.enums.ErrorCodeEnum;
import com.somnus.cloud.common.util.RedisKeyUtil;
import com.somnus.cloud.provider.api.exceptions.UacBizException;
import com.somnus.cloud.provider.api.model.domain.MqMessageData;
import com.somnus.cloud.provider.api.model.dto.PcSendEmailRequest;
import com.somnus.cloud.provider.model.enums.UacEmailTemplateEnum;
import com.somnus.cloud.provider.service.UacFreeMarkerService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: EmailProducer
 * @Description: The class Email producer.
 * @author Somnus
 * @date 2018年10月17日
 */
@Component
@Slf4j
public class EmailProducer {
	@Autowired
	private UacFreeMarkerService uacFreeMarkerService;

	/**
	 * Send email mq.
	 *
	 * @param emailSet          the email set
	 * @param emailTemplateEnum the email template enum
	 * @param param             the param
	 */
	public MqMessageData sendEmailMq(Set<String> emailSet, UacEmailTemplateEnum emailTemplateEnum, AliyunMqTopicConstants.MqTagEnum tagEnum, Map<String, Object> param) {
		log.info("pcSendEmailRequest - 发送邮件MQ. emailSet={}, param={}", emailSet, param);
		String msgBody;
		try {

			Preconditions.checkArgument(emailTemplateEnum != null, "邮箱模板信息不存在");
			PcSendEmailRequest request = new PcSendEmailRequest();

			String templateLocation = emailTemplateEnum.getLocation();
			String text = uacFreeMarkerService.getTemplate(param, templateLocation);

			request.setText(text);
			request.setTo(emailSet);
			request.setSubject(emailTemplateEnum.getSubject());

			msgBody = JSON.toJSONString(request);
		} catch (Exception e) {
			log.error("发送邮件验证码 smsMessage转换为json字符串失败", e);
			throw new UacBizException(ErrorCodeEnum.UAC10011021);
		}
		String topic = tagEnum.getTopic();
		String tag = tagEnum.getTag();
		String key = RedisKeyUtil.createMqKey(topic, tag, emailSet.toString(), msgBody);
		return new MqMessageData(msgBody, topic, tag, key);
	}
}
