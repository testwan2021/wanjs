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
package com.somnus.cloud.provider.api.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: TpcMqMessageDto
 * @Description: The class Tpc mq message dto.
 * @author Somnus
 * @date 2018年10月16日
 */
@Data
public class TpcMqMessageDto implements Serializable {

	private static final long serialVersionUID = -6980935654952282538L;

	/**
	 * 生产者组
	 */
	private String producerGroup;

	/**
	 * 业务单据号
	 */
	private String refNo;

	/**
	 * 消息key
	 */
	private String messageKey;

	/**
	 * topic
	 */
	private String messageTopic;

	/**
	 * tag
	 */
	private String messageTag;

	/**
	 * 消息类型
	 */
	private Integer messageType;

	/**
	 * 消息内容
	 */
	private String messageBody;

	/**
	 * 延时消息的延时级别
	 */
	private int delayLevel;

	/**
	 * 顺序类型, 0有序 1无序
	 */
	private int orderType;
}