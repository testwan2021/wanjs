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
package com.somnus.cloud.provider.service;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;

import com.google.common.base.Preconditions;
import com.somnus.cloud.common.zk.registry.base.ReliableMessageRegisterDto;
import com.somnus.cloud.comon.core.support.SpringContextHolder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @ClassName: MqProducerBeanFactory
 * @Description: The class Mq producer bean factory.
 * @author Somnus
 * @date 2018年10月17日
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MqProducerBeanFactory {

	private static final ConcurrentHashMap<String, DefaultMQProducer> DEFAULT_MQ_PRODUCER_MAP = new ConcurrentHashMap<>();
	private static final ConcurrentHashMap<String, String> CONSUMER_STATUS_MAP = new ConcurrentHashMap<>();
	private static final ConcurrentHashMap<String, String> PRODUCER_STATUS_MAP = new ConcurrentHashMap<>();

	/**
	 * Gets bean.
	 *
	 * @param pid the pid
	 *
	 * @return the bean
	 */
	public static DefaultMQProducer getBean(String pid) {
		Preconditions.checkArgument(StringUtils.isNotEmpty(pid), "getBean() pid is null");
		return DEFAULT_MQ_PRODUCER_MAP.get(pid);
	}

	/**
	 * Build producer bean.
	 *
	 * @param producerDto the producer dto
	 */
	public static void buildProducerBean(ReliableMessageRegisterDto producerDto) {

		String pid = producerDto.getProducerGroup();
		DefaultMQProducer mQProducer = DEFAULT_MQ_PRODUCER_MAP.get(pid);
		if (mQProducer == null) {
			String simpleName = producerDto.getProducerGroup();
			BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(DefaultMQProducer.class);
			beanDefinitionBuilder.setScope(BeanDefinition.SCOPE_SINGLETON);
			beanDefinitionBuilder.addPropertyValue("producerGroup", producerDto.getProducerGroup());
			beanDefinitionBuilder.addPropertyValue("namesrvAddr", producerDto.getNamesrvAddr());
			beanDefinitionBuilder.setInitMethodName("start");
			beanDefinitionBuilder.setDestroyMethodName("shutdown");
			SpringContextHolder.getDefaultListableBeanFactory().registerBeanDefinition(simpleName, beanDefinitionBuilder.getBeanDefinition());
			DEFAULT_MQ_PRODUCER_MAP.put(simpleName, (DefaultMQProducer)SpringContextHolder.getBean(simpleName));
		}
	}

	public static void putCid(String cid) {
		CONSUMER_STATUS_MAP.put(cid, cid);
	}

	public static void rmCid(String cid) {
		CONSUMER_STATUS_MAP.remove(cid);
	}

	public static void putPid(final String pid) {
		PRODUCER_STATUS_MAP.put(pid, pid);
	}

	public static void rmPid(String pid) {
		PRODUCER_STATUS_MAP.remove(pid);
	}
}
