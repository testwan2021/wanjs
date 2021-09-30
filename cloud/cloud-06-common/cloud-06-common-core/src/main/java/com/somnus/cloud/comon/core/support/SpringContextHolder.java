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
package com.somnus.cloud.comon.core.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @ClassName: SpringContextHolder
 * @Description: The class Spring context holder.
 * @author Somnus
 * @date 2018年10月16日
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	/**
	 * Sets application context.
	 *
	 * @param applicationContext the application context
	 *
	 * @throws BeansException the beans exception
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(SpringContextHolder.applicationContext == null) {
        	SpringContextHolder.applicationContext = applicationContext;
        }
    }

	/**
	 * Gets application context.
	 *
	 * @return the application context
	 */
	public static ApplicationContext getApplicationContext() {
		assertApplicationContext();
		return applicationContext;
	}

	/**
	 * Gets bean.
	 *
	 * @param <T>      the type parameter
	 * @param beanName the bean name
	 *
	 * @return the bean
	 */
	public static Object getBean(String beanName) {
		assertApplicationContext();
		return applicationContext.getBean(beanName);
	}

	/**
	 * Gets bean.
	 *
	 * @param <T>          the type parameter
	 * @param requiredType the required type
	 *
	 * @return the bean
	 */
	public static <T> T getBean(Class<T> clazz) {
		assertApplicationContext();
		return applicationContext.getBean(clazz);
	}
	
	/**
	 * Gets bean.
	 *
	 * @param <T>          the type parameter
	 * @param requiredType the required type
	 *
	 * @return the bean
	 */
	public static <T> T getBean(String name,Class<T> clazz){
		assertApplicationContext();
		return applicationContext.getBean(name, clazz);
    }

	public static DefaultListableBeanFactory getDefaultListableBeanFactory() {
		assertApplicationContext();
		return (DefaultListableBeanFactory) ((ConfigurableApplicationContext) applicationContext).getBeanFactory();
	}

	private static void assertApplicationContext() {
		if (SpringContextHolder.applicationContext == null) {
			throw new IllegalArgumentException("applicationContext属性为null,请检查是否注入了SpringContextHolder!");
		}
	}

}
