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
package com.somnus.cloud.security.core.validate.code.sms;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: DefaultSmsCodeSender
 * @Description: 默认的短信验证码发送器
 * @author Somnus
 * @date 2018年10月12日
 */
@Slf4j
public class DefaultSmsCodeSender implements SmsCodeSender {

	/**
	 * Send.
	 *
	 * @param mobile the mobile
	 * @param code   the code
	 * @param ip     the ip
	 */
	@Override
	public void send(String mobile, String code, String ip) {
		log.warn("请配置真实的短信验证码发送器(SmsCodeSender)");
		log.info("向手机" + mobile + "发送短信验证码" + code + "ip:" + ip);
	}

}
