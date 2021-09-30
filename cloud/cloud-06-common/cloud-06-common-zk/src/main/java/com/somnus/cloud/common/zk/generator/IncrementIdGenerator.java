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
package com.somnus.cloud.common.zk.generator;

import org.apache.curator.retry.RetryNTimes;

import com.somnus.cloud.common.base.constant.GlobalConstant;
import com.somnus.cloud.common.zk.registry.base.CoordinatorRegistryCenter;
import com.somnus.cloud.common.zk.registry.base.RegisterDto;

/**
 * @ClassName: IncrementIdGenerator
 * @Description: FrameworkID 的保存器.
 * @author Somnus
 * @date 2018年9月27日
 */
public class IncrementIdGenerator implements IdGenerator {

	private static Long serviceId = null;
	private final RegisterDto registerDto;

	/**
	 * Instantiates a new Increment id generator.
	 *
	 * @param registerDto the register dto
	 */
	public IncrementIdGenerator(RegisterDto registerDto) {
		this.registerDto = registerDto;
	}

	/**
	 * Next id long.
	 *
	 * @return the long
	 */
	@Override
	public Long nextId() {
		String app = this.registerDto.getApp();
		String host = this.registerDto.getHost();
		CoordinatorRegistryCenter regCenter = this.registerDto.getCoordinatorRegistryCenter();
		String path = GlobalConstant.ZK_REGISTRY_ID_ROOT_PATH + GlobalConstant.Symbol.SLASH + app + GlobalConstant.Symbol.SLASH + host;
		if (regCenter.isExisted(path)) {
			// 如果已经有该节点，表示已经为当前的host上部署的该app分配的编号（应对某个服务重启之后编号不变的问题），直接获取该id，而无需生成
			return Long.valueOf(regCenter.getDirectly(GlobalConstant.ZK_REGISTRY_ID_ROOT_PATH + GlobalConstant.Symbol.SLASH + app + GlobalConstant.Symbol.SLASH + host));
		} else {
			// 节点不存在，那么需要生成id，利用zk节点的版本号每写一次就自增的机制来实现
			regCenter.increment(GlobalConstant.ZK_REGISTRY_SEQ, new RetryNTimes(2000, 3));
			// 生成id
			Integer id = regCenter.getAtomicValue(GlobalConstant.ZK_REGISTRY_SEQ, new RetryNTimes(2000, 3)).postValue();
			// 将数据写入节点
			regCenter.persist(path);
			regCenter.persist(path, String.valueOf(id));
			return Long.valueOf(id);
		}
	}

	/**
	 * Gets service id.
	 *
	 * @return the service id
	 */
	public static Long getServiceId() {
		return serviceId;
	}

	/**
	 * Sets service id.
	 *
	 * @param serviceId the service id
	 */
	public static void setServiceId(Long serviceId) {
		IncrementIdGenerator.serviceId = serviceId;
	}
}
