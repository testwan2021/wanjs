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
package com.somnus.cloud.provider.model.dto.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: UacMenuStatusDto
 * @Description: The class Uac menu status dto.
 * @author Somnus
 * @date 2018年10月18日
 */
@Data
@ApiModel(value = "UacMenuStatusDto")
public class UacMenuStatusDto implements Serializable {
	private static final long serialVersionUID = 7834606418601316142L;
	/**
	 * 菜单的Id
	 */
	@ApiModelProperty(value = "菜单的Id", required = true)
	private Long id;
	/**
	 * 菜单的父Id
	 */
	@ApiModelProperty(value = "菜单的状态", required = true)
	private String status;

}
