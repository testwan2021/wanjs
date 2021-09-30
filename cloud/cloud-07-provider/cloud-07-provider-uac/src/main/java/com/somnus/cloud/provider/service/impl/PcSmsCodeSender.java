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
package com.somnus.cloud.provider.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.somnus.cloud.common.base.constant.AliyunSmsConstants;
import com.somnus.cloud.provider.model.dto.sms.SmsMessage;
import com.somnus.cloud.provider.service.SmsService;
import com.somnus.cloud.security.core.validate.code.sms.SmsCodeSender;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: PcSmsCodeSender
 * @Description: 默认的短信验证码发送器
 * @author Somnus
 * @date 2018年10月18日
 */
@Slf4j
public class PcSmsCodeSender implements SmsCodeSender {
	
	@Autowired
	private SmsService smsService;

	@Override
	public void send(String mobile, String code, String ip) {
		log.info("ip地址:{}向手机: {}发送短信验证码:{}", ip, mobile, code);
		SmsMessage smsMessage = new SmsMessage();
		smsMessage.setMobileNo(mobile);
		smsMessage.setSmsCode(code);
		smsMessage.setSmsTemplateCode(AliyunSmsConstants.SmsTempletEnum.UAC_PC_GLOBAL_TEMPLATE.getTempletCode());
		smsService.sendSmsCode(smsMessage, ip);
	}

}
