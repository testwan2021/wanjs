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
import java.util.List;

/**
 * @ClassName: BindUserRolesDto
 * @Description: The class Bind user roles dto.
 * @author Somnus
 * @date 2018年10月18日
 */
@ApiModel
@Data
public class BindUserRolesDto implements Serializable {

	private static final long serialVersionUID = -9149237379943908522L;
	@ApiModelProperty(value = "角色ID")
	private Long userId;

	@ApiModelProperty(value = "需要绑定的角色ID集合")
	private List<Long> roleIdList;
}
