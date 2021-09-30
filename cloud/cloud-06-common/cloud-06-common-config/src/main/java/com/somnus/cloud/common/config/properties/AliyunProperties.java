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
package com.somnus.cloud.common.config.properties;

import lombok.Data;

/**
 * @ClassName: AliyunProperties
 * @Description: The class Aliyun properties.
 * @author Somnus
 * @date 2018年9月26日
 */
@Data
public class AliyunProperties {
	private AliyunKeyProperties key = new AliyunKeyProperties();
	private RocketMqProperties rocketMq = new RocketMqProperties();
	private AliyunSmsProperties sms = new AliyunSmsProperties();

	@Data
	public class AliyunKeyProperties {
		/**
		 * 秘钥id
		 */
		private String accessKeyId;

		/**
		 * 秘钥
		 */
		private String accessKeySecret;
	}

	@Data
	public class RocketMqProperties {
		private String consumerGroup;
		private String producerGroup;
		private String namesrvAddr;
		/**
		 * 生产者是否使用可靠消息, 默认不使用 @MqConsumerStore
		 */
		private boolean reliableMessageProducer;
		/**
		 * 消费者是否使用可靠消息, 默认不使用 @MqProducerStore
		 */
		private boolean reliableMessageConsumer;
	}

	@Data
	public class AliyunSmsProperties {

		/**
		 * 阿里云管理控制台中配置的短信签名（状态必须是验证通过）
		 */
		private String signName;

		/**
		 * 机房信息
		 */
		private String regionId;

		/**
		 * 端点
		 */
		private String endpoint;

		/**
		 * 端点名称
		 */
		private String endpointName;

		private String product;

		private String domain;
	}
}