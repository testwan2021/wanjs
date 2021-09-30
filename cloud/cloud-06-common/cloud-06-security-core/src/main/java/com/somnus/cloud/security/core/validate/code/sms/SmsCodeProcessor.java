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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.somnus.cloud.common.util.RedisKeyUtil;
import com.somnus.cloud.security.core.SecurityResult;
import com.somnus.cloud.security.core.properties.SecurityConstants;
import com.somnus.cloud.security.core.properties.SecurityProperties;
import com.somnus.cloud.security.core.properties.SmsCodeProperties;
import com.somnus.cloud.security.core.validate.code.ValidateCode;
import com.somnus.cloud.security.core.validate.code.ValidateCodeException;
import com.somnus.cloud.security.core.validate.code.ValidateCodeGenerator;
import com.somnus.cloud.security.core.validate.code.ValidateCodeRepository;
import com.somnus.cloud.security.core.validate.code.impl.AbstractValidateCodeProcessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: SmsCodeProcessor
 * @Description: 短信验证码处理器
 * @author Somnus
 * @date 2018年10月12日
 */
@Component("smsValidateCodeProcessor")
@Slf4j
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

	private static final String X_FORWARDED_FOR = "x-forwarded-for";
	private static final String PROXY_CLIENT_IP = "Proxy-Client-IP";
	private static final String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";

	private static final String LOCALHOST_IP = "127.0.0.1";
	private static final String LOCALHOST_IP_16 = "0:0:0:0:0:0:0:1";
	private static final String UNKNOWN = "unknown";
	private static final String COMMA = ",";
	private static final int MAX_IP_LENGTH = 15;

	@Autowired
	private SmsCodeSender smsCodeSender;
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * Instantiates a new Abstract validate code processor.
	 *
	 * @param validateCodeGenerators the validate code generators
	 * @param validateCodeRepository the validate code repository
	 */
	public SmsCodeProcessor(Map<String, ValidateCodeGenerator> validateCodeGenerators, ValidateCodeRepository validateCodeRepository) {
		super(validateCodeGenerators, validateCodeRepository);
	}

	/**
	 * Send.
	 *
	 * @param request      the request
	 * @param validateCode the validate code
	 *
	 * @throws Exception the exception
	 */
	@Override
	protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
		String paramName = SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE;
		String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), paramName);
		String ipAddr = this.getRemoteAddr(request.getRequest());
		SecurityResult result = new SecurityResult(SecurityResult.SUCCESS_CODE, "校验成功", true);
		// 统一处理短信流量
		try {
			this.checkSendSmsCount(mobile, ipAddr);
			smsCodeSender.send(mobile, validateCode.getCode(), ipAddr);
		} catch (ValidateCodeException e) {
			log.error("校验短信数量, e={}", e.getMessage(), e);
			result = SecurityResult.error(e.getMessage(), false);
		} catch (Exception e) {
			log.error("校验短信数量, e={}", e.getMessage(), e);
			result = SecurityResult.error("内部异常", false);
		}
		String json = objectMapper.writeValueAsString(result);
		HttpServletResponse response = request.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}

	private void checkSendSmsCount(String mobile, String ipAddr) {
		String mobileSmsCountKey = RedisKeyUtil.getSendSmsCountKey(mobile, "mobile");
		String ipSmsCountKey = RedisKeyUtil.getSendSmsCountKey(ipAddr, "ip");
		String totalSmsCountKey = RedisKeyUtil.getSendSmsCountKey("total", "total");
		String sendSmsRateKey = RedisKeyUtil.getSendSmsRateKey(ipAddr);
		SmsCodeProperties sms = securityProperties.getCode().getSms();

		Integer sendSmsRateCount = (Integer) redisTemplate.opsForValue().get(sendSmsRateKey);
		if (sendSmsRateCount != null) {
			log.error("操作频率过快 ipAddr={}, mobile={}", ipAddr, mobile);
			throw new ValidateCodeException("操作频率过快");
		} else {
			redisTemplate.opsForValue().set(sendSmsRateKey, 1, 1, TimeUnit.MINUTES);
		}

		Integer mobileSmsCount = (Integer) redisTemplate.opsForValue().get(mobileSmsCountKey);
		if (mobileSmsCount != null && mobileSmsCount > sms.getMobileMaxSendCount()) {
			log.error("Mobile当天短信发送数上限 ipAddr={}, mobile={}", ipAddr, mobile);
			throw new ValidateCodeException("Mobile当天短信发送数上限");
		} else {
			redisTemplate.opsForValue().set(mobileSmsCountKey, mobileSmsCount == null ? 1 : mobileSmsCount + 1, 1, TimeUnit.DAYS);
		}
		Integer ipSmsCount = (Integer) redisTemplate.opsForValue().get(ipSmsCountKey);
		if (ipSmsCount != null && ipSmsCount > sms.getIpMaxSendCount()) {
			log.error("IP当天短信发送数上限 ipAddr={}, mobile={}", ipAddr, mobile);
			throw new ValidateCodeException("IP当天短信发送数上限");
		} else {
			redisTemplate.opsForValue().set(ipSmsCountKey, ipSmsCount == null ? 1 : ipSmsCount + 1, 1, TimeUnit.DAYS);
		}
		Integer totalSmsCount = (Integer) redisTemplate.opsForValue().get(totalSmsCountKey);
		if (totalSmsCount != null && totalSmsCount > sms.getTotalMaxSendCount()) {
			log.error("当天短信发送数上限 ipAddr={}, mobile={}", ipAddr, mobile);
			throw new ValidateCodeException("当天短信发送数上限");
		} else {
			redisTemplate.opsForValue().set(totalSmsCountKey, totalSmsCount == null ? 1 : totalSmsCount + 1, 1, TimeUnit.DAYS);
		}
	}

	private String getRemoteAddr(HttpServletRequest request) {
		String ipAddress = request.getHeader(X_FORWARDED_FOR);
		if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader(PROXY_CLIENT_IP);
		}
		if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader(WL_PROXY_CLIENT_IP);
		}
		if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (LOCALHOST_IP.equals(ipAddress) || LOCALHOST_IP_16.equals(ipAddress)) {
				//根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					log.error("获取IP地址, 出现异常={}", e.getMessage(), e);
				}
				assert inet != null;
				ipAddress = inet.getHostAddress();
			}
		}
		// 对于通过多个代理的情况, 第一个IP为客户端真实IP,多个IP按照','分割 //"***.***.***.***".length() = 15
		if (ipAddress != null && ipAddress.length() > MAX_IP_LENGTH && ipAddress.indexOf(COMMA) > 0) {
			ipAddress = ipAddress.substring(0, ipAddress.indexOf(COMMA));
		}
		return ipAddress;
	}
}
