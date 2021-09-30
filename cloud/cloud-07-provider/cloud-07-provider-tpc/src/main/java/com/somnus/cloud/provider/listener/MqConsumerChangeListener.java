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
package com.somnus.cloud.provider.listener;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;
import com.somnus.cloud.common.base.constant.GlobalConstant;
import com.somnus.cloud.common.zk.registry.base.ReliableMessageRegisterDto;
import com.somnus.cloud.common.zk.registry.exception.RegExceptionHandler;
import com.somnus.cloud.provider.service.MqProducerBeanFactory;
import com.somnus.cloud.provider.service.TpcMqConsumerService;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName: MqConsumerChangeListener
 * @Description: The class Mq consumer change listener.
 * @author Somnus
 * @date 2018年10月17日
 */
@Slf4j
@Component
public class MqConsumerChangeListener implements TreeCacheListener {
	@Autowired
	private TpcMqConsumerService tpcMqConsumerService;

	/**
	 * Child event.
	 *
	 * @param client the client
	 * @param event  the event
	 */
	@Override
	public void childEvent(final CuratorFramework client, final TreeCacheEvent event) {
		ChildData data = event.getData();
		if (data == null) {
			return;
		}
		String path = data.getPath();
		if (GlobalConstant.ZK_REGISTRY_CONSUMER_ROOT_PATH.equals(path) || GlobalConstant.ZK_REGISTRY_CONSUMER_ROOT_PATH.equals(path)) {
			return;
		}
		String[] split = path.split(GlobalConstant.Symbol.SLASH);
		String dataStr = new String(data.getData());
		switch (event.getType()) {
			case NODE_ADDED:
				log.info("MqConsumerChangeListener CHILD_ADDED path={}, data={}", path, dataStr);
				if (split.length == GlobalConstant.Number.SIX_INT) {
					String appPath = path.substring(0, path.lastIndexOf(GlobalConstant.Symbol.SLASH));

					ReliableMessageRegisterDto dto = JSON.parseObject(getDirectly(client, appPath), ReliableMessageRegisterDto.class);
					if (this.getNumChildrenCount(client, appPath) > 0) {
						tpcMqConsumerService.updateOnLineStatusByCid(dto.getConsumerGroup());
						MqProducerBeanFactory.putCid(dto.getConsumerGroup());
					}
				}
				break;
			case NODE_REMOVED:
				log.info("MqConsumerChangeListener NODE_REMOVED path={}, data={}", path, dataStr);
				if (split.length == GlobalConstant.Number.SIX_INT) {
					String appPath = path.substring(0, path.lastIndexOf(GlobalConstant.Symbol.SLASH));

					ReliableMessageRegisterDto dto = JSON.parseObject(getDirectly(client, appPath), ReliableMessageRegisterDto.class);
					if (this.getNumChildrenCount(client, appPath) < 1) {
						tpcMqConsumerService.updateOffLineStatusByCid(dto.getConsumerGroup());
						MqProducerBeanFactory.rmCid(dto.getConsumerGroup());
					}
				}
				break;
			case NODE_UPDATED:
				log.error("MqConsumerChangeListener 本次版本不对更新做处理, path={}, data={}", path, new String(data.getData()));
				break;
			default:
				break;
		}
	}

	private int getNumChildrenCount(CuratorFramework client, final String key) {
		log.info("获取消费者节点个数 path={}", key);
		Stat stat = null;
		try {
			stat = client.checkExists().forPath(key);
		} catch (final Exception ex) {
			log.error("获取目录 key={}的子节点个数出现异常={}", key, ex.getMessage(), ex);
		}
		return stat == null ? 0 : stat.getNumChildren();

	}

	private String getDirectly(CuratorFramework client, final String key) {
		try {
			return new String(client.getData().forPath(key), Charsets.UTF_8);
		} catch (final Exception ex) {
			RegExceptionHandler.handleException(ex);
			return null;
		}
	}
}
