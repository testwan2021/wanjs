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
package com.somnus.cloud.provider.model.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

import com.somnus.cloud.comon.core.mybatis.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @ClassName: TpcMqTopic
 * @Description: The class Tpc mq topic.
 * @author Somnus
 * @date 2018年10月17日
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "pc_tpc_mq_topic")
@Alias(value = "tpcMqTopic")
public class TpcMqTopic extends BaseEntity {
	private static final long serialVersionUID = 5642946024630652202L;

	/**
	 * 城市编码
	 */
	@Column(name = "topic_code")
	private String topicCode;

	/**
	 * 区域编码
	 */
	@Column(name = "topic_name")
	private String topicName;

	/**
	 * MQ类型, 10 rocketmq 20 kafka
	 */
	@Column(name = "mq_type")
	private Integer mqType;

	/**
	 * 消息类型, 10 无序消息, 20 无序消息
	 */
	@Column(name = "msg_type")
	private Integer msgType;

	/**
	 * 状态, 0生效,10,失效
	 */
	private Integer status;

	/**
	 * 备注
	 */
	private String remarks;
}