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
package com.somnus.cloud.provider.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName: RedisHashService
 * @Description: The interface Redis hash service.
 * @author Somnus
 * @date 2018年10月17日
 */
public interface RedisHashService {

	/**
	 * 获取所有给定字段的值
	 *
	 * @param <T>    the type parameter
	 * @param key    the key
	 * @param fields the fields
	 *
	 * @return the value by fields
	 */
	<T extends Object> List<T> getValueByFields(String key, Set<String> fields);

	/**
	 * 获取所有给定字段的值
	 *
	 * @param <T>   the type parameter
	 * @param key   the key
	 * @param field the field
	 *
	 * @return the value by field
	 */
	<T extends Object> List<T> getValueByField(String key, String field);

	/**
	 * 同时将多个 field-value (域-值)对设置到哈希表 key 中
	 *
	 * @param key the key
	 * @param map the map
	 */
	void setValueByFields(String key, Map<String, Object> map);

	/**
	 * 删除一个或多个哈希表字段R
	 *
	 * @param key      the key
	 * @param hashKeys the hash keys
	 *
	 * @return the long
	 */
	Long removeFields(String key, String... hashKeys);
}
