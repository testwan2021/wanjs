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
 * @ClassName: UacEditMenuDto
 * @Description: The class Uac menu add dto.
 * @author Somnus
 * @date 2018年10月18日
 */
@Data
@ApiModel(value = "菜单新增Dto")
public class UacEditMenuDto implements Serializable {
	private static final long serialVersionUID = -9219271060355200235L;

	/**
	 * 菜单的Id
	 */
	@ApiModelProperty(value = "菜单的Id", required = true)
	private Long id;
	/**
	 * 菜单的父Id
	 */
	@ApiModelProperty(value = "菜单的父Id", required = true)
	@NotBlank(message = "上级菜单不能为空")
	private Long pid;
	/**
	 * 菜单编码
	 */
	@ApiModelProperty(value = "菜单编码", required = true)
	@NotBlank(message = "菜单编码不能为空")
	private String menuCode;
	/**
	 * Icon编码
	 */
	@ApiModelProperty(value = "Icon编码")
	private String icon;
	/**
	 * 菜单名称
	 */
	@ApiModelProperty(value = "菜单名称", required = true)
	@NotBlank(message = "菜单名称不能为空")
	private String menuName;
	/**
	 * 菜单排序
	 */
	@ApiModelProperty(value = "菜单排序", required = true)
	private Integer number;
	/**
	 * 菜单地址
	 */
	@ApiModelProperty(value = "菜单地址", required = true)
	@NotBlank(message = "菜单地址不能为空")
	private String url;
	/**
	 * 备注说明
	 */
	@ApiModelProperty(value = "备注说明")
	private String remark;
}
