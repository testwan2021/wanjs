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
package com.somnus.cloud.common.zk.registry.base;

import com.google.common.base.Preconditions;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName: ReliableMessageRegisterDto
 * @Description: The class Reliable message register dto.
 * @author Somnus
 * @date 2018年9月27日
 */
@Data
public class ReliableMessageRegisterDto {
	private String consumerGroup;
	private String producerGroup;
	private String namesrvAddr;

	/**
	 * Sets consumer group.
	 *
	 * @param consumerGroup the consumer group
	 *
	 * @return the consumer group
	 */
	public ReliableMessageRegisterDto setConsumerGroup(final String consumerGroup) {
		Preconditions.checkArgument(StringUtils.isNotEmpty(consumerGroup), "init zk cid is null");
		this.consumerGroup = consumerGroup;
		return this;
	}

	/**
	 * Sets producer group.
	 *
	 * @param producerGroup the producer group
	 *
	 * @return the producer group
	 */
	public ReliableMessageRegisterDto setProducerGroup(final String producerGroup) {
		Preconditions.checkArgument(StringUtils.isNotEmpty(producerGroup), "init zk pid is null");
		this.producerGroup = producerGroup;
		return this;
	}

	/**
	 * Sets namesrv addr.
	 *
	 * @param namesrvAddr the namesrv addr
	 *
	 * @return the namesrv addr
	 */
	public ReliableMessageRegisterDto setNamesrvAddr(final String namesrvAddr) {
		Preconditions.checkArgument(StringUtils.isNotEmpty(namesrvAddr), "init ZK namesrvAddr is null");
		this.namesrvAddr = namesrvAddr;
		return this;
	}
}
