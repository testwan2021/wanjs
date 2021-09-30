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
package com.somnus.cloud.comon.core.utils;

import com.somnus.cloud.common.config.properties.ZookeeperProperties;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @ClassName: SessionRegistryCenterConfiguration
 * @Description: 注册中心配置的会话声明周期.
 * @author Somnus
 * @date 2018年9月27日
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SessionRegistryCenterConfiguration {

	private static ZookeeperProperties regCenterConfig;

	/**
	 * 从当前会话范围获取注册中心配置.
	 *
	 * @return 事件追踪数据源配置
	 */
	public static ZookeeperProperties getRegistryCenterConfiguration() {
		return regCenterConfig;
	}

	/**
	 * 设置注册中心配置至当前会话范围.
	 *
	 * @param regCenterConfig 注册中心配置
	 */
	public static void setRegistryCenterConfiguration(final ZookeeperProperties regCenterConfig) {
		SessionRegistryCenterConfiguration.regCenterConfig = regCenterConfig;
	}
}
