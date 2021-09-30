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
package com.somnus.cloud.provider.api.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.somnus.cloud.provider.api.model.dto.TpcMqMessageDto;

import java.util.Date;

/**
 * @ClassName: MqMessageData
 * @Description: The class Mq message data.
 * @author Somnus
 * @date 2018年10月16日
 */
@Table(name = "pc_mq_message_data")
@Data
@NoArgsConstructor
public class MqMessageData {
	/**
	 * ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 版本号
	 */
	private Integer version;

	/**
	 * 消息key
	 */
	@Column(name = "message_key")
	private String messageKey;

	/**
	 * topic
	 */
	@Column(name = "message_topic")
	private String messageTopic;

	/**
	 * tag
	 */
	@Column(name = "message_tag")
	private String messageTag;

	/**
	 * 消息内容
	 */
	@Column(name = "message_body")
	private String messageBody;

	/**
	 * 消息类型: 10 - 生产者 ; 20 - 消费者
	 */
	@Column(name = "message_type")
	private Integer messageType;

	/**
	 * 顺序类型, 0有序 1无序
	 */
	private int orderType;

	/**
	 * 消息状态
	 */
	private Integer status;

	/**
	 * 延时级别
	 */
	@Column(name = "delay_level")
	private int delayLevel;

	/**
	 * 创建人
	 */
	private String creator;

	/**
	 * 创建人ID
	 */
	@Column(name = "creator_id")
	private Long creatorId;

	/**
	 * 创建时间
	 */
	@Column(name = "created_time")
	private Date createdTime;

	/**
	 * 最近操作人
	 */
	@Column(name = "last_operator")
	private String lastOperator;

	/**
	 * 最后操作人ID
	 */
	@Column(name = "last_operator_id")
	private Long lastOperatorId;

	/**
	 * 更新时间
	 */
	@Column(name = "update_time")
	private Date updateTime;

	/**
	 * 是否删除 -0 未删除 -1 已删除
	 */
	private Integer yn;

	/**
	 * producer group name
	 */
	@Transient
	private String producerGroup;

	public MqMessageData(final String msgBody, final String topic, final String tag, final String key) {
		this.messageBody = msgBody;
		this.messageTopic = topic;
		this.messageTag = tag;
		this.messageKey = key;
	}

	/**
	 * Gets tpc mq message dto.
	 *
	 * @return the tpc mq message dto
	 */
	@Transient
	public TpcMqMessageDto getTpcMqMessageDto() {
		TpcMqMessageDto tpcMqMessageDto = new TpcMqMessageDto();
		tpcMqMessageDto.setMessageBody(this.messageBody);
		tpcMqMessageDto.setMessageKey(this.messageKey);
		tpcMqMessageDto.setMessageTag(this.messageTag);
		tpcMqMessageDto.setMessageTopic(this.messageTopic);
		tpcMqMessageDto.setMessageType(this.messageType);
		tpcMqMessageDto.setRefNo(this.messageKey);
		tpcMqMessageDto.setDelayLevel(this.delayLevel);
		tpcMqMessageDto.setOrderType(this.orderType);
		tpcMqMessageDto.setProducerGroup(this.producerGroup);
		return tpcMqMessageDto;
	}
}