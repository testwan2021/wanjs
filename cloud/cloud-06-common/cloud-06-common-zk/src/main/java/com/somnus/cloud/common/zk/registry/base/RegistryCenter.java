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
package com.somnus.cloud.common.zk.registry.base;

import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.retry.RetryNTimes;

/**
 * @ClassName: RegistryCenter
 * @Description: 注册中心.
 * @author Somnus
 * @date 2018年9月27日
 */
public interface RegistryCenter {

	/**
	 * 初始化注册中心.
	 */
	void init();

	/**
	 * 关闭注册中心.
	 */
	void close();

	/**
	 * 获取注册数据.
	 *
	 * @param key 键
	 *
	 * @return 值 string
	 */
	String get(String key);

	/**
	 * 获取数据是否存在.
	 *
	 * @param key 键
	 *
	 * @return 数据是否存在 boolean
	 */
	boolean isExisted(String key);

	/**
	 * 持久化注册数据.
	 *
	 * @param key   键
	 * @param value 值
	 */
	void persist(String key, String value);

	/**
	 * 创建一个持久化节点，初始内容为空.
	 *
	 * @param key the key
	 */
	void persist(String key);

	/**
	 * 更新注册数据.
	 *
	 * @param key   键
	 * @param value 值
	 */
	void update(String key, String value);

	/**
	 * 删除注册数据.
	 *
	 * @param key 键
	 */
	void remove(String key);

	/**
	 * 获取注册中心当前时间.
	 *
	 * @param key 用于获取时间的键
	 *
	 * @return 注册中心当前时间 registry center time
	 */
	long getRegistryCenterTime(String key);

	/**
	 * 直接获取操作注册中心的原生客户端.
	 * 如：Zookeeper或Redis等原生客户端.
	 *
	 * @return 注册中心的原生客户端 raw client
	 */
	Object getRawClient();

	/**
	 * Instantiates a new Increment.
	 *
	 * @param path        the path
	 * @param retryNTimes the retry n times
	 */
	void increment(String path, RetryNTimes retryNTimes);

	/**
	 * Instantiates a new Get atomic value.
	 *
	 * @param path        the path
	 * @param retryNTimes the retry n times
	 *
	 * @return the atomic value
	 */
	AtomicValue<Integer> getAtomicValue(String path, RetryNTimes retryNTimes);
}
