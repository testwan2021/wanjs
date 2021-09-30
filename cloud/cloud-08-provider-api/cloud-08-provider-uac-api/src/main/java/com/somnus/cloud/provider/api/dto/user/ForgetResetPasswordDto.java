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
 * @ClassName: ForgetResetPasswordDto
 * @Description: 忘记密码.
 * @author Somnus
 * @date 2018年10月16日
 */
@Data
@ApiModel
public class ForgetResetPasswordDto implements Serializable {
	private static final long serialVersionUID = 5478700873789068921L;
	@ApiModelProperty(value = "登录名")
	private String loginName;
	@ApiModelProperty(value = "密码")
	private String loginPwd;
	@ApiModelProperty(value = "邮箱")
	private String email;
	@ApiModelProperty(value = "验证码")
	private String emailCode;
	@ApiModelProperty(value = "token")
	private String forgetToken;
}
