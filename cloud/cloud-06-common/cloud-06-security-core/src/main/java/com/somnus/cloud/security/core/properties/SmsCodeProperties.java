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
package com.somnus.cloud.security.core.properties;

import lombok.Data;

/**
 * @ClassName: SmsCodeProperties
 * @Description: The class Sms code properties.
 * @author Somnus
 * @date 2018年10月12日
 */
@Data
public class SmsCodeProperties {

	/**
	 * 验证码长度
	 */
	private int length = 6;
	/**
	 * 过期时间
	 */
	private int expireIn = 60;
	/**
	 * 要拦截的url，多个url用逗号隔开，ant pattern
	 */
	private String url;
	/**
	 * 每天每个手机号最大送送短信数量
	 */
	private int mobileMaxSendCount;
	/**
	 * 每天每个IP最大送送短信数量
	 */
	private int ipMaxSendCount;
	/**
	 * 每天最大送送短信数量
	 */
	private int totalMaxSendCount;


}
