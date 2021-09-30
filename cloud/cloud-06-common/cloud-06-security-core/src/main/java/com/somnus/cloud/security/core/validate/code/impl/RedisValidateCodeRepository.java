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
package com.somnus.cloud.security.core.validate.code.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import com.somnus.cloud.security.core.validate.code.ValidateCode;
import com.somnus.cloud.security.core.validate.code.ValidateCodeException;
import com.somnus.cloud.security.core.validate.code.ValidateCodeRepository;
import com.somnus.cloud.security.core.validate.code.ValidateCodeType;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName: RedisValidateCodeRepository
 * @Description: 基于redis的验证码存取器，避免由于没有session导致无法存取验证码的问题
 * @author Somnus
 * @date 2018年10月12日
 */
@Component
public class RedisValidateCodeRepository implements ValidateCodeRepository {

	private final RedisTemplate<String, Object> redisTemplate;

	/**
	 * Instantiates a new Redis validate code repository.
	 *
	 * @param redisTemplate the redis template
	 */
	@Autowired
	public RedisValidateCodeRepository(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * Save.
	 *
	 * @param request the request
	 * @param code    the code
	 * @param type    the type
	 */
	@Override
	public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType type) {
		String key = buildKey(request, type);
		redisTemplate.opsForValue().set(key, code, 3, TimeUnit.MINUTES);
	}

	/**
	 * Get validate code.
	 *
	 * @param request the request
	 * @param type    the type
	 *
	 * @return the validate code
	 */
	@Override
	public ValidateCode get(ServletWebRequest request, ValidateCodeType type) {
		Object value = redisTemplate.opsForValue().get(buildKey(request, type));
		if (value == null) {
			return null;
		}
		return (ValidateCode) value;
	}

	/**
	 * Remove.
	 *
	 * @param request the request
	 * @param type    the type
	 */
	@Override
	public void remove(ServletWebRequest request, ValidateCodeType type) {
		redisTemplate.delete(buildKey(request, type));
	}

	private String buildKey(ServletWebRequest request, ValidateCodeType type) {
		String deviceId = request.getHeader("deviceId");
		if (StringUtils.isBlank(deviceId)) {
			throw new ValidateCodeException("请在请求头中携带deviceId参数");
		}
		return "code:" + type.toString().toLowerCase() + ":" + deviceId;
	}

}
