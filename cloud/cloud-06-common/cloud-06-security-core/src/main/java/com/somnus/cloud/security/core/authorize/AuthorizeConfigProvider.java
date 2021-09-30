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
package com.somnus.cloud.security.core.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * @ClassName: AuthorizeConfigProvider
 * @Description: 授权配置提供器，各个模块和业务系统可以通过实现此接口向系统添加授权配置。
 * @author Somnus
 * @date 2018年10月12日
 */
public interface AuthorizeConfigProvider {

	/**
	 * Config boolean.
	 *
	 * @param config the config
	 *
	 * @return 返回的boolean表示配置中是否有针对anyRequest的配置 。在整个授权配置中， 应该有且仅有一个针对anyRequest的配置，如果所有的实现都没有针对anyRequest的配置， 系统会自动增加一个anyRequest().authenticated()的配置。如果有多个针对anyRequest 的配置，则会抛出异常。
	 */
	boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);

}
