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
package com.somnus.cloud.provider.api.model.dto.robot;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: ChatRobotMsgDto
 * @Description: 自定义机器人消息.
 * @author Somnus
 * @date 2018年10月16日
 */
@Data
@ApiModel(value = "自定义机器人消息体")
public class ChatRobotMsgDto implements Serializable {

	private static final long serialVersionUID = -9132398786027223009L;

	/**
	 * 机器人URL
	 */
	@ApiModelProperty(value = "机器人URL")
	private String webhookToken;

	/**
	 * 此消息类型为固定markdown
	 * {@link com.somnus.cloud.provider.api.model.enums.RobotMsgTypeEnum}
	 */
	@ApiModelProperty(value = "首屏会话透出的展示内容", required = true)
	@JsonProperty(value = "msgtype")
	private String msgType;

	/**
	 * markdown消息体
	 */
	@ApiModelProperty(value = "markdown消息体")
	private MarkdownDto markdown;

	/**
	 * link类型
	 */
	@ApiModelProperty(value = "link类型")
	private LinkDto link;
	/**
	 * link类型
	 */
	@ApiModelProperty(value = "link类型")
	private TextDto text;
	/**
	 * link类型
	 */
	@ApiModelProperty(value = "link类型")
	private AtDto at;

}
