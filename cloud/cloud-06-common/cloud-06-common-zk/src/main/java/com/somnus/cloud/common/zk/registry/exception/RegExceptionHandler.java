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
package com.somnus.cloud.common.zk.registry.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.KeeperException.NoNodeException;
import org.apache.zookeeper.KeeperException.NodeExistsException;

/**
 * @ClassName: RegExceptionHandler
 * @Description: 注册中心异常处理类.
 * @author Somnus
 * @date 2018年9月27日
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RegExceptionHandler {

	/**
	 * 处理异常.
	 * <p>
	 * <p>处理掉中断和连接失效异常并继续抛注册中心.</p>
	 *
	 * @param cause 待处理异常.
	 */
	public static void handleException(final Exception cause) {
		if (null == cause) {
			return;
		}
		boolean flag = isIgnoredException(cause) || null != cause.getCause() && isIgnoredException(cause.getCause());
		if (flag) {
			log.debug("Elastic job: ignored exception for: {}", cause.getMessage());
		} else if (cause instanceof InterruptedException) {
			Thread.currentThread().interrupt();
		} else {
			throw new RegException(cause);
		}
	}

	private static boolean isIgnoredException(final Throwable cause) {
		return cause instanceof NoNodeException || cause instanceof NodeExistsException;
	}
}
