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
package com.somnus.cloud.provider.config;

import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.somnus.cloud.common.base.constant.GlobalConstant;
import com.somnus.cloud.common.config.properties.CloudProperties;
import com.somnus.cloud.common.zk.registry.RegistryCenterFactory;
import com.somnus.cloud.common.zk.registry.base.CoordinatorRegistryCenter;
import com.somnus.cloud.common.zk.registry.base.ReliableMessageRegisterDto;
import com.somnus.cloud.provider.listener.MqConsumerChangeListener;
import com.somnus.cloud.provider.listener.MqProducerChangeListener;
import com.somnus.cloud.provider.service.MqProducerBeanFactory;
import com.somnus.cloud.provider.service.TpcMqProducerService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: MqBeanInitRunner
 * @Description: The class Redis init runner.
 * @author Somnus
 * @date 2018年10月17日
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class MqBeanInitRunner implements CommandLineRunner {
	@Autowired
	private CloudProperties cloudProperties;
	
	@Autowired
	private MqProducerChangeListener producerChangeListener;
	
	@Autowired
	private MqConsumerChangeListener consumerChangeListener;
	
	@Autowired
	private TpcMqProducerService tpcMqProducerService;

	/**
	 * Run.
	 *
	 * @param args the args
	 */
	@Override
	public void run(String... args) throws Exception {
		CoordinatorRegistryCenter coordinatorRegistryCenter = RegistryCenterFactory.createCoordinatorRegistryCenter(cloudProperties.getZk());
		List<String> childrenKeys = coordinatorRegistryCenter.getChildrenKeys(GlobalConstant.ZK_REGISTRY_PRODUCER_ROOT_PATH);
		this.initMqListener(coordinatorRegistryCenter);
		for (final String childrenKey : childrenKeys) {
			int count = coordinatorRegistryCenter.getNumChildren(GlobalConstant.ZK_REGISTRY_PRODUCER_ROOT_PATH + GlobalConstant.Symbol.SLASH + childrenKey);
			if (count == 0) {
				continue;
			}
			String producerString = coordinatorRegistryCenter.getDirectly(GlobalConstant.ZK_REGISTRY_PRODUCER_ROOT_PATH + GlobalConstant.Symbol.SLASH + childrenKey);
			ReliableMessageRegisterDto producerDto = JSON.parseObject(producerString, ReliableMessageRegisterDto.class);
			MqProducerBeanFactory.buildProducerBean(producerDto);
			try {
				tpcMqProducerService.updateOnLineStatusByPid(producerDto.getProducerGroup());
			} catch (Exception e) {
				log.error("更新生产者状态为离线出现异常, ex={}", e.getMessage(), e);
			}
		}
	}

	private void initMqListener(CoordinatorRegistryCenter coordinatorRegistryCenter) throws Exception {
		CuratorFramework cf = (CuratorFramework) coordinatorRegistryCenter.getRawClient();
		initProducerListener(cf);
		initConsumerListener(cf);
	}


	@SuppressWarnings("resource")
	private void initProducerListener(CuratorFramework cf) throws Exception {
		TreeCache treeCache = new TreeCache(cf, GlobalConstant.ZK_REGISTRY_PRODUCER_ROOT_PATH);
		treeCache.getListenable().addListener(producerChangeListener);
		treeCache.start();
	}

	@SuppressWarnings("resource")
	private void initConsumerListener(CuratorFramework cf) throws Exception {
		TreeCache treeCache = new TreeCache(cf, GlobalConstant.ZK_REGISTRY_CONSUMER_ROOT_PATH);
		treeCache.getListenable().addListener(consumerChangeListener);
		treeCache.start();
	}

}