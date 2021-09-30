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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.google.common.base.Preconditions;
import com.somnus.cloud.common.base.constant.GlobalConstant;
import com.somnus.cloud.common.base.enums.ErrorCodeEnum;
import com.somnus.cloud.common.config.properties.CloudProperties;
import com.somnus.cloud.provider.api.exceptions.OpcBizException;
import com.somnus.cloud.provider.service.OptSmsService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: OptSmsServiceImpl
 * @Description: The class Opt sms service.
 * @author Somnus
 * @date 2018年10月17日
 */
@Slf4j
@Service
public class OptSmsServiceImpl implements OptSmsService {
	@Autowired
	private IAcsClient iAcsClient;
	
	@Autowired
	private CloudProperties cloudProperties;
	
	@Value("${spring.profiles.active}")
	private String profile;

	@Override
	public SendSmsResponse sendSms(SendSmsRequest sendSmsRequest) {
		checkParam(sendSmsRequest);
		SendSmsResponse acsResponse;
		try {
			if (GlobalConstant.DEV_PROFILE.equals(profile)) {
				log.error("dev环境不发送短信");
				return new SendSmsResponse();
			}
			if (GlobalConstant.TEST_PROFILE.equals(profile)) {
				log.error("test环境不发送短信");
				return new SendSmsResponse();
			}
			acsResponse = iAcsClient.getAcsResponse(sendSmsRequest);
		} catch (ClientException e) {
			log.error("send sms message error={}", e.getMessage(), e);
			throw new OpcBizException(ErrorCodeEnum.OPC10040004, e);
		}
		log.info("send sms message OK acsResponse={}", acsResponse);
		return acsResponse;
	}

	private void checkParam(SendSmsRequest sendSmsRequest) {
		String templateCode = sendSmsRequest.getTemplateCode();
		String signName = sendSmsRequest.getSignName();
		if (StringUtils.isBlank(signName)) {
			sendSmsRequest.setSignName(cloudProperties.getAliyun().getSms().getSignName());
		}

		String templateParam = sendSmsRequest.getTemplateParam();
		String phoneNumbers = sendSmsRequest.getPhoneNumbers();

		Preconditions.checkArgument(StringUtils.isNotEmpty(templateCode), "短信模板不能为空");
		Preconditions.checkArgument(StringUtils.isNotEmpty(phoneNumbers), "手机号码不能为空");
		Preconditions.checkArgument(StringUtils.isNotEmpty(templateParam), "短信内容不能为空");
	}
}
