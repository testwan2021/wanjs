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

 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import com.somnus.cloud.security.core.properties.SecurityProperties;

/**
 * @ClassName: TokenStoreConfig
 * @Description: The class Token store config.
 * @author Somnus
 * @date 2018年10月15日
 */
@Configuration
public class TokenStoreConfig {

	/**
	 * 使用redis存储token的配置，只有在somnus.cloud.security.oauth2.tokenStore配置为redis时生效
	 */
	@Configuration
	@ConditionalOnProperty(prefix = "somnus.cloud.security.oauth2", name = "tokenStore", havingValue = "redis")
	public static class RedisConfig {

		@Autowired
		private RedisConnectionFactory redisConnectionFactory;

		/**
		 * Redis token store token store.
		 *
		 * @return token store
		 */
		@Bean
		public TokenStore redisTokenStore() {
			return new RedisTokenStore(redisConnectionFactory);
		}

	}

	/**
	 * 使用jwt时的配置，默认生效
	 *
	 * @author somnus.cloud.net @gmail.com
	 */
	@Configuration
	@ConditionalOnProperty(prefix = "somnus.cloud.security.oauth2", name = "tokenStore", havingValue = "jwt", matchIfMissing = true)
	public static class JwtConfig {

		@Autowired
		private SecurityProperties securityProperties;

		/**
		 * Jwt token store token store.
		 *
		 * @return the token store
		 */
		@Bean
		public TokenStore jwtTokenStore() {
			return new JwtTokenStore(jwtAccessTokenConverter());
		}

		/**
		 * Jwt access token converter jwt access token converter.
		 *
		 * @return the jwt access token converter
		 */
		@Bean
		public JwtAccessTokenConverter jwtAccessTokenConverter() {
			JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
			converter.setSigningKey(securityProperties.getOauth2().getJwtSigningKey());
			return converter;
		}

		/**
		 * Jwt token enhancer token enhancer.
		 *
		 * @return the token enhancer
		 */
		@Bean
		@ConditionalOnBean(TokenEnhancer.class)
		public TokenEnhancer jwtTokenEnhancer() {
			return new TokenJwtEnhancer();
		}

	}


}
