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

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import com.somnus.cloud.common.base.dto.BaseVo;

/**
 * @ClassName: TpcMqTopicVo
 * @Description: The class Tpc mq topic vo.
 * @author Somnus
 * @date 2018年10月17日
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TpcMqTopicVo extends BaseVo {
	private static final long serialVersionUID = 3857060544292574468L;

	/**
	 * 生产者名称
	 */
	private String producerName;

	/**
	 * 城市编码
	 */
	private String topicCode;

	/**
	 * 区域编码
	 */
	private String topicName;

	/**
	 * MQ类型, 10 rocketmq 20 kafka
	 */
	private Integer mqType;

	/**
	 * 消息类型, 10 无序消息, 20 无序消息
	 */
	private Integer msgType;

	/**
	 * 状态, 0生效,10,失效
	 */
	private Integer status;

	/**
	 * 备注
	 */
	private String remarks;

	/**
	 * Tag 列表
	 */
	private List<TpcMqTagVo> tagVoList;
}
