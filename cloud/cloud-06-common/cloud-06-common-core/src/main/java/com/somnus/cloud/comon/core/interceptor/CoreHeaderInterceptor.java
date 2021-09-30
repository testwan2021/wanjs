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
package com.somnus.cloud.comon.core.interceptor;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableDefault;
import com.somnus.cloud.common.util.PublicUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName: CoreHeaderInterceptor
 * @Description: The class Core header interceptor.
 * @author Somnus
 * @date 2018年9月27日
 */
@Slf4j
public class CoreHeaderInterceptor extends HandlerInterceptorAdapter {
	/**
	 * The constant HEADER_LABEL.
	 */
	public static final String HEADER_LABEL = "x-label";
	/**
	 * The constant HEADER_LABEL_SPLIT.
	 */
	public static final String HEADER_LABEL_SPLIT = ",";

	/**
	 * The constant LABEL.
	 */
	public static final HystrixRequestVariableDefault<List<String>> LABEL = new HystrixRequestVariableDefault<>();

	private static void initHystrixRequestContext(String labels) {
		log.info("LABEL={}", labels);
		if (!HystrixRequestContext.isCurrentThreadInitialized()) {
			HystrixRequestContext.initializeContext();
		}

		if (PublicUtil.isNotEmpty(labels)) {
			CoreHeaderInterceptor.LABEL.set(Arrays.asList(labels.split(CoreHeaderInterceptor.HEADER_LABEL_SPLIT)));
		} else {
			CoreHeaderInterceptor.LABEL.set(Collections.emptyList());
		}
	}

	private static void shutdownHystrixRequestContext() {
		if (HystrixRequestContext.isCurrentThreadInitialized()) {
			HystrixRequestContext.getContextForCurrentThread().shutdown();
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		CoreHeaderInterceptor.initHystrixRequestContext(request.getHeader(CoreHeaderInterceptor.HEADER_LABEL));
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
		CoreHeaderInterceptor.shutdownHystrixRequestContext();
	}
}
