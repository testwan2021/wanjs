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
package com.somnus.cloud.security.app.social;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import com.somnus.cloud.security.core.properties.SecurityConstants;
import com.somnus.cloud.security.core.social.support.PcSpringSocialConfigurer;

/**
 * @ClassName: SpringSocialConfigurerPostProcessor
 * @Description: The class Spring social configurer post processor.
 * @author Somnus
 * @date 2018年10月15日
 */
@Component
public class SpringSocialConfigurerPostProcessor implements BeanPostProcessor {

	/**
	 * Post process before initialization object.
	 *
	 * @param bean     the bean
	 * @param beanName the bean name
	 *
	 * @return the object
	 *
	 * @throws BeansException the beans exception
	 */
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	/**
	 * Post process after initialization object.
	 *
	 * @param bean     the bean
	 * @param beanName the bean name
	 *
	 * @return the object
	 *
	 * @throws BeansException the beans exception
	 */
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		String pcSocialSecurityConfig = "pcSocialSecurityConfig";
		if (StringUtils.equals(beanName, pcSocialSecurityConfig)) {
			PcSpringSocialConfigurer config = (PcSpringSocialConfigurer) bean;
			config.signupUrl(SecurityConstants.DEFAULT_SOCIAL_USER_INFO_URL);
			return config;
		}
		return bean;
	}

}
