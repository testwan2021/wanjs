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
package com.somnus.cloud.common.base.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @ClassName: MessageQueryDto
 * @Description: The class Tpc message query dto.
 * @author Somnus
 * @date 2018年10月16日
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
public class MessageQueryDto extends BaseQuery {

	private static final long serialVersionUID = 3967075132487249652L;
	
	/**
	 * messageKey
	 */
	@ApiModelProperty(value = "messageKey")
	private String messageKey;
	/**
	 * messageTopic
	 */
	@ApiModelProperty(value = "messageTopic")
	private String messageTopic;

	/**
	 * messageTag
	 */
	@ApiModelProperty(value = "messageTag")
	private String messageTag;
	/**
	 * 发送状态
	 */
	@ApiModelProperty(value = "发送状态")
	private String messageStatus;

	/**
	 * PID
	 */
	@ApiModelProperty(value = "PID")
	private String producerGroup;

	/**
	 * 消息类型
	 */
	@ApiModelProperty(value = "消息类型")
	private String messageType;

	/**
	 * 开始时间
	 */
	@ApiModelProperty(value = "开始时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date startQueryTime;

	/**
	 * 结束时间
	 */
	@ApiModelProperty(value = "结束时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date endQueryTime;
}
