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
package com.somnus.cloud.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @ClassName: ValidateCodeProcessor
 * @Description: 校验码处理器，封装不同校验码的处理逻辑
 * @author Somnus
 * @date 2018年10月12日
 */
public interface ValidateCodeProcessor {

	/**
	 * 创建校验码
	 *
	 * @param request the request
	 *
	 * @throws Exception the exception
	 */
	void create(ServletWebRequest request) throws Exception;

	/**
	 * 校验验证码(验证后删除)
	 *
	 * @param servletWebRequest the servlet web request
	 */
	void validate(ServletWebRequest servletWebRequest);

	/**
	 * 校验验证码(验证后不删除)
	 *
	 * @param servletWebRequest the servlet web request
	 */
	void check(ServletWebRequest servletWebRequest);

}
