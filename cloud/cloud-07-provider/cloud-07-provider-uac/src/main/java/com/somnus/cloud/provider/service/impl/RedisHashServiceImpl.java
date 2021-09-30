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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.somnus.cloud.common.util.PublicUtil;
import com.somnus.cloud.provider.service.RedisHashService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: RedisHashServiceImpl
 * @Description: The class Redis hash service.
 * @author Somnus
 * @date 2018年10月18日
 */
@Slf4j
@Service
public class RedisHashServiceImpl implements RedisHashService {
	
	@Autowired
	private StringRedisTemplate rt;

	@Override
	public <T> List<T> getValueByFields(String key, Set<String> fields) {
		HashOperations<String, String, T> hash = rt.opsForHash();
		if (!rt.hasKey(key)) {
			return Collections.emptyList();
		}
		List<T> values = hash.multiGet(key, fields);
		if (PublicUtil.isEmpty(values)) {
			return Collections.emptyList();
		}
		log.info("getValueByFields - 根据key获取所有给定字段的值. [OK] key={}, fields={}, values={}", key, fields, values);
		return values;

	}

	@Override
	public <T> List<T> getValueByField(String key, String field) {
		HashOperations<String, String, T> hash = rt.opsForHash();
		if (!rt.hasKey(key)) {
			return Collections.emptyList();
		}
		T value = hash.get(key, field);
		if (PublicUtil.isEmpty(value)) {
			return Collections.emptyList();
		}
		List<T> values = Lists.newArrayList();
		values.add(value);
		log.info("getValueByField - 根据key获取给定字段的值. [OK] key={}, field={}, values={}", key, field, values);
		return values;
	}

	@Override
	public void setValueByFields(String key, Map<String, Object> map) {
		HashOperations<String, String, Object> hash = rt.opsForHash();
		hash.putAll(key, map);
		log.info("setValueByFields - 同时将多个 field-value (域-值)对设置到哈希表 key 中. [ok] key={}, map={}", key, map);
	}

	@Override
	public Long removeFields(String key, String... hashKeys) {
		HashOperations<String, String, Object> hash = rt.opsForHash();
		Long result = hash.delete(key, (Object) hashKeys);
		log.info("removeFields- 删除一个或多个哈希表字段. [OK] key={}, hashKeys={}, result={}", key, hashKeys, result);
		return result;
	}
}
