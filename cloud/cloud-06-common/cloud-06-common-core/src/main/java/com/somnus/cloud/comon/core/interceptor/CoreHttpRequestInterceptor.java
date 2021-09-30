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

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * @ClassName: CoreHttpRequestInterceptor
 * @Description: The class Core http request interceptor.
 * @author Somnus
 * @date 2018年9月27日
 */
@Slf4j
public class CoreHttpRequestInterceptor implements ClientHttpRequestInterceptor {
	/**
	 * Intercept client http response.
	 *
	 * @param request   the request
	 * @param body      the body
	 * @param execution the execution
	 *
	 * @return the client http response
	 *
	 * @throws IOException the io exception
	 */
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body,
	                                    ClientHttpRequestExecution execution) throws IOException {
		HttpRequestWrapper requestWrapper = new HttpRequestWrapper(request);

		String header = StringUtils.collectionToDelimitedString(
				CoreHeaderInterceptor.LABEL.get(),
				CoreHeaderInterceptor.HEADER_LABEL_SPLIT);
		log.info("header={} ", header);
		requestWrapper.getHeaders().add(CoreHeaderInterceptor.HEADER_LABEL, header);

		return execution.execute(requestWrapper, body);
	}
}
