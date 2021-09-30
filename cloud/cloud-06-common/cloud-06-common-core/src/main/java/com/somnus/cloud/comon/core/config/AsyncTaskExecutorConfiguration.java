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
package com.somnus.cloud.comon.core.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.somnus.cloud.common.config.properties.CloudProperties;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executor;

/**
 * @ClassName: AsyncTaskExecutorConfiguration
 * @Description: The class Async config
 * @author Somnus
 * @date 2018年9月27日
 */
@Configuration
@EnableAsync
@EnableScheduling
@Slf4j
public class AsyncTaskExecutorConfiguration implements AsyncConfigurer {
	@Autowired
	private CloudProperties cloudProperties;

	@Override
	@Bean(name = "taskExecutor")
	public Executor getAsyncExecutor() {
		log.debug("Creating Async Task Executor");
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(cloudProperties.getTask().getCorePoolSize());
		executor.setMaxPoolSize(cloudProperties.getTask().getMaxPoolSize());
		executor.setQueueCapacity(cloudProperties.getTask().getQueueCapacity());
		executor.setKeepAliveSeconds(cloudProperties.getTask().getKeepAliveSeconds());
		executor.setThreadNamePrefix(cloudProperties.getTask().getThreadNamePrefix());
		return new ExceptionHandlingAsyncTaskExecutor(executor);
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new SimpleAsyncUncaughtExceptionHandler();
	}
}
