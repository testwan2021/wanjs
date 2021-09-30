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

import java.io.Serializable;

/**
 * @ClassName: CheckValidDto
 * @Description: The class Check valid dto.
 * @author Somnus
 * @date 2018年9月27日
 */
@Data
@ApiModel
public class CheckValidDto implements Serializable {
	
	private static final long serialVersionUID = 5178470476151416779L;
	
	/**
	 * 校验的参数值
	 */
	@ApiModelProperty(value = "校验参数值")
	private String validValue;

	/**
	 * 参数类型(列)
	 */
	@ApiModelProperty(value = "参数类型")
	private String type;
}
