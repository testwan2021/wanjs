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

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.somnus.cloud.common.base.constant.GlobalConstant;
import com.somnus.cloud.provider.service.RedisService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: RedisServiceImpl
 * @Description: The class Redis service.
 * @author Somnus
 * @date 2018年10月18日
 */
@Slf4j
@Service
public class RedisServiceImpl implements RedisService {
	
	@Autowired
	private StringRedisTemplate rt;

	@Override
	public String getKey(String key) {
		String value = null;
		ValueOperations<String, String> ops = rt.opsForValue();
		if (rt.hasKey(key)) {
			value = ops.get(key);
		}
		log.info("getKey. [OK] key={}, value={}", key, value);
		return value;
	}

	@Override
	public void deleteKey(String key) {
		rt.delete(key);
		log.info("deleteKey. [OK] key={}", key);

	}

	@Override
	public void setKey(String key, String value) {
		Preconditions.checkArgument(StringUtils.isNotEmpty(key), "Redis key is not null");

		ValueOperations<String, String> ops = rt.opsForValue();
		ops.set(key, value);
		rt.expire(key, GlobalConstant.Sys.REDIS_DEFAULT_EXPIRE, TimeUnit.MINUTES);
		log.info("setKey. [OK] key={}, value={}, expire=默认超时时间", key, value);


	}

	@Override
	public void setKey(String key, String value, long timeout, TimeUnit unit) {
		Preconditions.checkArgument(StringUtils.isNotEmpty(key), "Redis key is not null");
		Preconditions.checkArgument(unit != null, "TimeUnit is not null");
		ValueOperations<String, String> ops = rt.opsForValue();
		ops.set(key, value);
		rt.expire(key, timeout, unit);
		log.info("setKey. [OK] key={}, value={}, timeout={}, unit={}", key, value, timeout, unit);

	}
}
