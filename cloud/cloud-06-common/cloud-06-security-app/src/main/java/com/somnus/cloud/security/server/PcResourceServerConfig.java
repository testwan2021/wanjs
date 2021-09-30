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
package com.somnus.cloud.security.server;

import com.somnus.cloud.security.app.authentication.openid.OpenIdAuthenticationSecurityConfig;
import com.somnus.cloud.security.core.authentication.FormAuthenticationConfig;
import com.somnus.cloud.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.somnus.cloud.security.core.authorize.AuthorizeConfigManager;
import com.somnus.cloud.security.core.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * @ClassName: PcResourceServerConfig
 * @Description: 资源服务器配置
 * @author Somnus
 * @date 2018年10月15日
 */
@Configuration
@EnableResourceServer
public class PcResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	private OAuth2WebSecurityExpressionHandler pcSecurityExpressionHandler;

	@Autowired
	private AccessDeniedHandler pcAccessDeniedHandler;

	@Autowired
	protected AuthenticationSuccessHandler pcAuthenticationSuccessHandler;

	@Autowired
	protected AuthenticationFailureHandler pcAuthenticationFailureHandler;

	@Autowired
	private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

	@Autowired
	private ValidateCodeSecurityConfig validateCodeSecurityConfig;

	@Autowired
	private SpringSocialConfigurer pcSocialSecurityConfig;

	@Autowired
	private AuthorizeConfigManager authorizeConfigManager;

	@Autowired
	private FormAuthenticationConfig formAuthenticationConfig;

	@Autowired
	private OpenIdAuthenticationSecurityConfig openIdAuthenticationSecurityConfig;

	@Autowired
	private DataSource dataSource;

	/**
	 * 记住我功能的token存取器配置
	 *
	 * @return the persistent token repository
	 */
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		tokenRepository.setDataSource(dataSource);
//		tokenRepository.setCreateTableOnStartup(true); // 第一次启动创建
		return tokenRepository;
	}

	/**
	 * Configure.
	 *
	 * @param http the http
	 *
	 * @throws Exception the exception
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception {
		formAuthenticationConfig.configure(http);
		http.headers().frameOptions().disable();
		http.apply(validateCodeSecurityConfig)
				.and()
				.apply(smsCodeAuthenticationSecurityConfig)
				.and()
				.apply(pcSocialSecurityConfig)
				.and()
				.apply(openIdAuthenticationSecurityConfig)
				.and()
				.headers().frameOptions().disable()
				.and()
				.exceptionHandling().accessDeniedHandler(pcAccessDeniedHandler)
				.and()
				.csrf().disable();

		authorizeConfigManager.config(http.authorizeRequests());
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.expressionHandler(pcSecurityExpressionHandler);
	}
}