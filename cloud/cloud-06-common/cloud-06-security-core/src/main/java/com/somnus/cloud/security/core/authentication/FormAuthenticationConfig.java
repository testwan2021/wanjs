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
package com.somnus.cloud.security.core.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.somnus.cloud.security.core.properties.SecurityConstants;

/**
 * @ClassName: FormAuthenticationConfig
 * @Description: 表单登录配置
 * @author Somnus
 * @date 2018年10月12日
 */
@Component
public class FormAuthenticationConfig {

	/**
	 * The Pc authentication success handler.
	 */
	protected final AuthenticationSuccessHandler pcAuthenticationSuccessHandler;

	/**
	 * The Pc authentication failure handler.
	 */
	protected final AuthenticationFailureHandler pcAuthenticationFailureHandler;

	/**
	 * Instantiates a new Form authentication config.
	 *
	 * @param pcAuthenticationSuccessHandler the pc authentication success handler
	 * @param pcAuthenticationFailureHandler the pc authentication failure handler
	 */
	@Autowired
	public FormAuthenticationConfig(AuthenticationSuccessHandler pcAuthenticationSuccessHandler, AuthenticationFailureHandler pcAuthenticationFailureHandler) {
		this.pcAuthenticationSuccessHandler = pcAuthenticationSuccessHandler;
		this.pcAuthenticationFailureHandler = pcAuthenticationFailureHandler;
	}

	/**
	 * Configure.
	 *
	 * @param http the http
	 *
	 * @throws Exception the exception
	 */
	public void configure(HttpSecurity http) throws Exception {
		http.formLogin()
				.loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
				.loginProcessingUrl(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM)
				.successHandler(pcAuthenticationSuccessHandler)
				.failureHandler(pcAuthenticationFailureHandler);
	}

}
