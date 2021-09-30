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

/**
 * @ClassName: TpcMqPublishVo
 * @Description: The class Mdc mq producer vo.
 * @author Somnus
 * @date 2018年10月17日
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TpcMqPublishVo extends BaseVo {
	private static final long serialVersionUID = -5644698735373761104L;

	/**
	 * 主题编码
	 */
	private String topicCode;

	/**
	 * 主题名称
	 */
	private String topicName;

	/**
	 * 微服务名称
	 */
	private String applicationName;

	/**
	 * 城市编码
	 */
	private String producerCode;

	/**
	 * 区域编码
	 */
	private String producerName;

	/**
	 * 状态, 0生效,10,失效
	 */
	private Integer status;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 预留对账的URL
	 */
	private String queryMessageUrl;
}
