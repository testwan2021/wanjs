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

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.somnus.cloud.common.base.constant.AliyunMqTopicConstants;
import com.somnus.cloud.common.base.enums.ErrorCodeEnum;
import com.somnus.cloud.common.util.HttpAesUtil;
import com.somnus.cloud.common.util.PubUtils;
import com.somnus.cloud.common.util.RandomUtil;
import com.somnus.cloud.common.util.RedisKeyUtil;
import com.somnus.cloud.common.zk.generator.UniqueIdGenerator;
import com.somnus.cloud.provider.api.exceptions.UacBizException;
import com.somnus.cloud.provider.api.model.domain.MqMessageData;
import com.somnus.cloud.provider.manager.UserManager;
import com.somnus.cloud.provider.model.domain.UacUser;
import com.somnus.cloud.provider.model.dto.email.SendEmailMessage;
import com.somnus.cloud.provider.model.enums.UacEmailTemplateEnum;
import com.somnus.cloud.provider.mq.producer.EmailProducer;
import com.somnus.cloud.provider.service.EmailService;
import com.somnus.cloud.provider.service.RedisService;
import com.somnus.cloud.provider.service.UacUserService;
import com.xiaoleilu.hutool.date.DateUtil;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * @ClassName: EmailServiceImpl
 * @Description: The class Email service.
 * @author Somnus
 * @date 2018年10月18日
 */
@Service
@Slf4j
public class EmailServiceImpl implements EmailService {
	
	@Autowired
	private EmailProducer emailProducer;
	
	@Autowired
	private UacUserService uacUserService;
	
	@Value("${cloud.auth.rest-pwd-url}")
	private String resetPwdUrl;
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	private UserManager userManager;
	
	@Autowired
	private RedisService redisService;

	private static final String KEY_STR = "om8q6fq#A0Yl@qJy";
	private static final String IV_STR = "0#86gzOcsv1bXyIx";

	@Override
	public void submitResetPwdEmail(String email) {
		Preconditions.checkArgument(StringUtils.isNotEmpty(email), ErrorCodeEnum.UAC10011018.getMsg());

		// 获取用户名
		UacUser uacUser = new UacUser();
		uacUser.setEmail(email);
		uacUser = uacUserService.selectOne(uacUser);
		if (uacUser == null) {
			throw new UacBizException(ErrorCodeEnum.UAC10011004, email);
		}

		String resetPwdKey = PubUtils.uuid() + UniqueIdGenerator.generateId();
		redisTemplate.opsForValue().set(RedisKeyUtil.getResetPwdTokenKey(resetPwdKey), uacUser, 7 * 24, TimeUnit.HOURS);

		Map<String, Object> param = Maps.newHashMap();
		param.put("loginName", uacUser.getLoginName());
		param.put("email", email);
		param.put("resetPwdUrl", resetPwdUrl + resetPwdKey);
		param.put("dateTime", DateUtil.formatDateTime(new Date()));

		Set<String> to = Sets.newHashSet();
		to.add(email);
		MqMessageData messageData = emailProducer.sendEmailMq(to, UacEmailTemplateEnum.RESET_PWD_SEND_MAIL, AliyunMqTopicConstants.MqTagEnum.FORGOT_PASSWORD_AUTH_CODE, param);
		userManager.submitResetPwdEmail(messageData);
	}

	@Override
	public void sendEmailCode(SendEmailMessage sendEmailMessage, String loginName) {
		Preconditions.checkArgument(StringUtils.isNotEmpty(loginName), "用户名不能为空");
		String email = sendEmailMessage.getEmail();

		Preconditions.checkArgument(StringUtils.isNotEmpty(email), ErrorCodeEnum.UAC10011018.getMsg());
		Preconditions.checkArgument(StringUtils.isNotEmpty(loginName), ErrorCodeEnum.UAC10011007.getMsg());

		// 解密
		email = decryptEmail(loginName, email);

		Example example = new Example(UacUser.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("email", email);
		criteria.andNotEqualTo("loginName", loginName);
		int result = uacUserService.selectCountByExample(example);
		if (result > 0) {
			throw new UacBizException(ErrorCodeEnum.UAC10011019);
		}

		String emailCode = RandomUtil.createNumberCode(6);
		String key = RedisKeyUtil.getSendEmailCodeKey(loginName, email);
		// 在redis中绑定验证码
		redisService.setKey(key, emailCode, 7 * 24, TimeUnit.HOURS);

		// 先写死 类型多了再抽方法
		Map<String, Object> param = Maps.newHashMap();
		param.put("loginName", loginName);
		param.put("email", email);
		param.put("emailCode", emailCode);
		param.put("dateTime", DateUtil.formatDateTime(new Date()));

		Set<String> to = Sets.newHashSet();
		to.add(email);

		MqMessageData mqMessageData = emailProducer.sendEmailMq(to, UacEmailTemplateEnum.RESET_USER_EMAIL, AliyunMqTopicConstants.MqTagEnum.RESET_LOGIN_PWD, param);
		userManager.sendEmailCode(mqMessageData);
	}

	@Override
	public void checkEmailCode(SendEmailMessage sendEmailMessage, String loginName) {

		String email = sendEmailMessage.getEmail();
		String emailCode = sendEmailMessage.getEmailCode();

		Preconditions.checkArgument(StringUtils.isNotEmpty(email), ErrorCodeEnum.UAC10011018.getMsg());
		Preconditions.checkArgument(StringUtils.isNotEmpty(emailCode), "验证码不能为空");

		// 解密用户名密码
		email = decryptEmail(loginName, email);
		String key = RedisKeyUtil.getSendEmailCodeKey(loginName, email);
		String emailCodeRedis = redisService.getKey(key);
		Preconditions.checkArgument(StringUtils.isNotEmpty(emailCodeRedis), "验证码已过期");
		Preconditions.checkArgument(StringUtils.equals(emailCode, emailCodeRedis), "验证码错误");
	}

	private String decryptEmail(final String loginName, String email) {
		try {
			email = HttpAesUtil.decrypt(email, KEY_STR, false, IV_STR);
			log.info("发送短信 解密loginName={}", loginName);
			log.info("发送短信 解密email={}", email);
		} catch (Exception ex) {
			log.info("发送短信 解密手机号码失败 密文loginName={}, email={}", loginName, email);
			throw new UacBizException(ErrorCodeEnum.UAC10011031);
		}
		return email;
	}
}
