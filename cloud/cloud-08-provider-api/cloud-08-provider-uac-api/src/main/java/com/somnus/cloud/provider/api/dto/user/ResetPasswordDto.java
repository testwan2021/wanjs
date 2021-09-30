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
package com.somnus.cloud.provider.api.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: ResetPasswordDto
 * @Description: 重置密码.
 * @author Somnus
 * @date 2018年10月16日
 */
@Data
@ApiModel
public class ResetPasswordDto implements Serializable {
	private static final long serialVersionUID = 4762153981220090958L;
	@ApiModelProperty("登录名")
	private String loginName;
	@ApiModelProperty(value = "旧密码")
	private String passwordOld;
	@ApiModelProperty(value = "新密码")
	private String passwordNew;
}
