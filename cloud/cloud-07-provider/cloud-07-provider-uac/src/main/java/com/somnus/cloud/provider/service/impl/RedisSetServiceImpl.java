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

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.somnus.cloud.provider.service.RedisSetService;

import java.util.Set;

/**
 * @ClassName: RedisSetServiceImpl
 * @Description: The class Redis set service.
 * @author Somnus
 * @date 2018年10月18日
 */
@Slf4j
@Service
public class RedisSetServiceImpl implements RedisSetService {
	
	@Autowired
	private StringRedisTemplate rt;

	@Override
	public Set<String> getAllValue(String key) {
		Set<String> result;
		SetOperations<String, String> setOps = rt.opsForSet();
		result = setOps.members(key);
		log.info("getAllValue - 根据key获取元素. [OK] key={}, value={}", key, result);
		return result;
	}

	@Override
	public Long add(String key, String... value) {
		SetOperations<String, String> setOps = rt.opsForSet();
		Long result = setOps.add(key, value);
		log.info("add - 向key里面添加元素, key={}, value={}, result={}", key, value, result);
		return result;
	}

	@Override
	public Long remove(String key, String... value) {
		SetOperations<String, String> setOps = rt.opsForSet();
		Long result = setOps.remove(key, (Object) value);
		log.info("remove - 根据key移除元素, key={}, value={}, result={}", key, value, result);
		return result;
	}
}
