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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: LoginAuthDto
 * @Description: The class Login auth dto.
 * @author Somnus
 * @date 2018年9月27日
 */
@Data
@ApiModel(value = "登录人信息")
@NoArgsConstructor
@AllArgsConstructor
public class LoginAuthDto implements Serializable {
	
	private static final long serialVersionUID = -1137852221455042256L;
	
	@ApiModelProperty(value = "用户ID")
	private Long userId;
	
	@ApiModelProperty(value = "登录名")
	private String loginName;
	
	@ApiModelProperty(value = "用户名")
	private String userName;
	
	@ApiModelProperty(value = "组织ID")
	private Long groupId;
	
	@ApiModelProperty(value = "组织名称")
	private String groupName;

	public LoginAuthDto(Long userId, String loginName, String userName) {
		this.userId = userId;
		this.loginName = loginName;
		this.userName = userName;
	}
}
