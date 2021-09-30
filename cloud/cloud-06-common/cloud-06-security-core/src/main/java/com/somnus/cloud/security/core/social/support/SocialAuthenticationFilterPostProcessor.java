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
package com.somnus.cloud.security.core.social.support;

import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * @ClassName: SocialAuthenticationFilterPostProcessor
 * @Description: SocialAuthenticationFilter后处理器，用于在不同环境下个性化社交登录的配置
 * @author Somnus
 * @date 2018年10月12日
 */
public interface SocialAuthenticationFilterPostProcessor {

	/**
	 * Process.
	 *
	 * @param socialAuthenticationFilter the social authentication filter
	 */
	void process(SocialAuthenticationFilter socialAuthenticationFilter);

}
