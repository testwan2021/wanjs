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

import java.util.List;

/**
 * @ClassName: CoordinatorRegistryCenter
 * @Description: 用于协调分布式服务的注册中心.
 * @author Somnus
 * @date 2018年9月27日
 */
public interface CoordinatorRegistryCenter extends RegistryCenter {

	/**
	 * 直接从注册中心而非本地缓存获取数据.
	 *
	 * @param key 键
	 *
	 * @return 值 directly
	 */
	String getDirectly(String key);

	/**
	 * 获取子节点名称集合.
	 *
	 * @param key 键
	 *
	 * @return 子节点名称集合 children keys
	 */
	List<String> getChildrenKeys(String key);

	/**
	 * 获取子节点数量.
	 *
	 * @param key 键
	 *
	 * @return 子节点数量 num children
	 */
	int getNumChildren(String key);

	/**
	 * 持久化临时注册数据.
	 *
	 * @param key   键
	 * @param value 值
	 */
	void persistEphemeral(String key, String value);

	/**
	 * 持久化顺序注册数据.
	 *
	 * @param key   键
	 * @param value 值
	 *
	 * @return 包含10位顺序数字的znode名称 string
	 */
	String persistSequential(String key, String value);

	/**
	 * 持久化临时顺序注册数据.
	 *
	 * @param key 键
	 */
	void persistEphemeralSequential(String key);

	/**
	 * 添加本地缓存.
	 *
	 * @param cachePath 需加入缓存的路径
	 */
	void addCacheData(String cachePath);

	/**
	 * 释放本地缓存.
	 *
	 * @param cachePath 需释放缓存的路径
	 */
	void evictCacheData(String cachePath);

	/**
	 * 获取注册中心数据缓存对象.
	 *
	 * @param cachePath 缓存的节点路径
	 *
	 * @return 注册中心数据缓存对象 raw cache
	 */
	Object getRawCache(String cachePath);

	/**
	 * 向注册中心进行注册，生成该服务的编号并返回
	 *
	 * @param app           the app
	 * @param host          the host
	 * @param producerGroup the producer group
	 * @param consumerGroup the consumer group
	 * @param namesrvAddr   the namesrv addr
	 */
	void registerMq(String app, String host, String producerGroup, String consumerGroup, String namesrvAddr);
}
