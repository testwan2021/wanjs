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

import javax.validation.constraints.NotBlank;

/**
 * @ClassName: UacMenuCheckUrlDto
 * @Description: The class Uac menu check url dto.
 * @author Somnus
 * @date 2018年10月18日
 */
@Data
@ApiModel(value = "UacMenuCheckUrlDto")
public class UacMenuCheckUrlDto implements Serializable {
	private static final long serialVersionUID = 839437721293973234L;
	/**
	 * 菜单的id
	 */
	@ApiModelProperty(value = "菜单的id")
	private Long menuId;
	/**
	 * 菜单的url
	 */
	@ApiModelProperty(value = "菜单地址")
	@NotBlank(message = "菜单地址不能为空")
	private String url;


}
