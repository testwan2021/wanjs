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
package com.somnus.cloud.common.config.properties;

import lombok.Data;

/**
 * @ClassName: ZookeeperProperties
 * @Description: The class Job zookeeper properties.
 * @author Somnus
 * @date 2018年9月26日
 */
@Data
public class ZookeeperProperties {
	/**
	 * 连接Zookeeper服务器的列表
	 * 包括IP地址和端口号
	 * 多个地址用逗号分隔
	 * 如: host1:2181,host2:2181
	 */
	private String zkAddressList;

	/**
	 * Zookeeper的命名空间
	 */
	private String namespace;

	/**
	 * 等待重试的间隔时间的初始值
	 * 单位：毫秒
	 */
	private int baseSleepTimeMilliseconds = 1000;

	/**
	 * 等待重试的间隔时间的最大值
	 * 单位：毫秒
	 */
	private int maxSleepTimeMilliseconds = 3000;

	/**
	 * 最大重试次数
	 */
	private int maxRetries = 3;

	/**
	 * 连接超时时间
	 * 单位：毫秒
	 */
	private int connectionTimeoutMilliseconds = 15000;

	/**
	 * 会话超时时间
	 * 单位：毫秒
	 */
	private int sessionTimeoutMilliseconds = 60000;

	/**
	 * 连接Zookeeper的权限令牌
	 * 缺省为不需要权限验
	 */
	private String digest;
}
