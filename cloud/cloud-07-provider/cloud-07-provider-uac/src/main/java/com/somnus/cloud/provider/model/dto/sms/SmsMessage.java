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
package com.somnus.cloud.provider.model.dto.sms;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: SmsMessage
 * @Description: The class Sms message.
 * @author Somnus
 * @date 2018年10月18日
 */
@Data
@NoArgsConstructor
public class SmsMessage implements Serializable {
	private static final long serialVersionUID = -8708881656765856624L;
	/**
	 * 手机号码
	 */
	private String mobileNo;

	/**
	 * 加密token
	 */
	private String smsToken;

	/**
	 * 验证码
	 */
	private String smsCode;

	/**
	 * 短信模板Code
	 */
	private String smsTemplateCode;

	/**
	 * 业务单号(这里使用ip地址)
	 */
	private String outId;
}
