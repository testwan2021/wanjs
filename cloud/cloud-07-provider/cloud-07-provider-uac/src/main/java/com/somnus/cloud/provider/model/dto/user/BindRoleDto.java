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
package com.somnus.cloud.provider.model.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: BindRoleDto
 * @Description: The class Bind role dto.
 * @author Somnus
 * @date 2018年10月18日
 */
@Data
@ApiModel(value = "绑定的用户信息")
public class BindRoleDto implements Serializable {

	private static final long serialVersionUID = -3385971785265488527L;
	/**
	 * 用户ID
	 */
	@ApiModelProperty(value = "用户ID")
	private Long roleId;
	/**
	 * 手机号
	 */
	@ApiModelProperty(value = "角色编码")
	private String roleCode;
	/**
	 * 用户名
	 */
	@ApiModelProperty(value = "角色名称")
	private String roleName;
	/**
	 * 是否可以操作
	 */
	@ApiModelProperty(value = "是否可以操作")
	private boolean disabled;

}
