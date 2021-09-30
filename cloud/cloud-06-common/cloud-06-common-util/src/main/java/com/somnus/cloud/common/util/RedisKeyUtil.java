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
package com.somnus.cloud.common.util;

import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName: RedisKeyUtil
 * @Description: The class Redis key util.
 * @author Somnus
 * @date 2018年10月9日
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RedisKeyUtil {

	/**
	 * The constant RESET_PWD_TOKEN_KEY.
	 */
	private static final String RESET_PWD_TOKEN_KEY = "cloud:restPwd";
	private static final String ACTIVE_USER = "cloud:activeUser";
	private static final String SEND_SMS_COUNT = "cloud:sms:count";
	private static final String SEND_EMAIL_CODE = "cloud:email:code";
	private static final String ACCESS_TOKEN = "cloud:token:accessToken";
	private static final String UPLOAD_FILE_SIZE = "cloud:file:upload_file_size";
	private static final int REF_NO_MAX_LENGTH = 100;

	/**
	 * Gets reset pwd token key.
	 *
	 * @param resetPwdKey the rest pwd key
	 *
	 * @return the reset pwd token key
	 */
	public static String getResetPwdTokenKey(String resetPwdKey) {
		Preconditions.checkArgument(StringUtils.isNotEmpty(resetPwdKey), "参数不能为空");
		return RESET_PWD_TOKEN_KEY + ":" + resetPwdKey;

	}

	public static String getSendEmailCodeKey(String loginName, String email) {
		Preconditions.checkArgument(StringUtils.isNotEmpty(loginName), "Email不能为空");
		Preconditions.checkArgument(StringUtils.isNotEmpty(email), "用户名不能为空");
		return SEND_EMAIL_CODE + ":" + loginName + email;

	}

	/**
	 * Gets active user key.
	 *
	 * @param activeToken the active token
	 *
	 * @return the active user key
	 */
	public static String getActiveUserKey(String activeToken) {
		Preconditions.checkArgument(StringUtils.isNotEmpty(activeToken), "参数不能为空");
		return ACTIVE_USER + ":" + activeToken;

	}

	/**
	 * Gets send sms count key.
	 *
	 * @param ipAddr the ip addr
	 * @param type   mobile;ip;total
	 *
	 * @return the send sms count key
	 */
	public static String getSendSmsCountKey(String ipAddr, String type) {
		Preconditions.checkArgument(StringUtils.isNotEmpty(ipAddr), "请不要篡改IP地址");
		return SEND_SMS_COUNT + ":" + type + ":" + ipAddr;

	}


	/**
	 * Gets send sms rate key.
	 *
	 * @param ipAddr the ip addr
	 *
	 * @return the send sms rate key
	 */
	public static String getSendSmsRateKey(String ipAddr) {
		Preconditions.checkArgument(StringUtils.isNotEmpty(ipAddr), "请不要篡改IP地址");
		return SEND_SMS_COUNT + ":" + ipAddr;

	}

	public static String getAccessTokenKey(String token) {
		Preconditions.checkArgument(StringUtils.isNotEmpty(token), "非法请求token参数不存在");
		return ACCESS_TOKEN + ":" + token;
	}

	public static String createMqKey(String topic, String tag, String refNo, String body) {
		String temp = refNo;
		Preconditions.checkArgument(StringUtils.isNotEmpty(topic), "topic is null");
		Preconditions.checkArgument(StringUtils.isNotEmpty(tag), "tag is null");
		Preconditions.checkArgument(StringUtils.isNotEmpty(refNo), "refNo is null");
		Preconditions.checkArgument(StringUtils.isNotEmpty(body), "body is null");

		if (refNo.length() > REF_NO_MAX_LENGTH) {
			temp = StringUtils.substring(refNo, 0, REF_NO_MAX_LENGTH) + "...";
		}
		return topic + "_" + tag + "_" + temp + "-" + body.hashCode();
	}

	public static String getFileSizeKey() {
		return UPLOAD_FILE_SIZE;
	}
}
