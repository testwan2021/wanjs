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
package com.somnus.cloud.provider.model.vo;

import com.somnus.cloud.common.base.dto.BaseVo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName: TpcMqConfirmVo
 * @Description: The class Tpc mq confirm.
 * @author Somnus
 * @date 2018年10月17日
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class TpcMqConfirmVo extends BaseVo {
	private static final long serialVersionUID = 8497361355200965902L;

	/**
	 * 任务ID
	 */
	private Long messageId;

	/**
	 * 消息唯一标识
	 */
	private String messageKey;

	/**
	 * 消费者组编码
	 */
	private String consumerCode;

	/**
	 * 消费的数次
	 */
	private Integer consumeCount;

	/**
	 * 状态, 10 - 未确认 ; 20 - 已确认; 30 已消费
	 */
	private Integer status;
}