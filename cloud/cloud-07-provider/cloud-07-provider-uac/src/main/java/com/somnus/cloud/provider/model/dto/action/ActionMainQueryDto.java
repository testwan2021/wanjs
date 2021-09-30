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
package com.somnus.cloud.provider.model.dto.action;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import com.somnus.cloud.common.base.dto.BaseQuery;

/**
 * @ClassName: ActionMainQueryDto
 * @Description: The class Action main query dto.
 * @author Somnus
 * @date 2018年10月18日
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
public class ActionMainQueryDto extends BaseQuery {
	private static final long serialVersionUID = -1755881173841393763L;
	/**
	 * 资源路径
	 */
	private String url;

	/**
	 * 权限名称
	 */
	private String actionName;

	/**
	 * 权限编码
	 */
	private String actionCode;

	/**
	 * 状态
	 */
	private String status;

	/**
	 * 菜单ID
	 */
	private List<Long> menuIdList;
}
