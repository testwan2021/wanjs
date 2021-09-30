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

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: UserMenuDto
 * @Description: The class User menu dto.
 * @author Somnus
 * @date 2018年10月18日
 */
@Data
public class UserMenuDto implements Serializable {
	private static final long serialVersionUID = -5220054739321565548L;
	/**
	 * 一级菜单名称
	 */
	private String fistMenuName;
	/**
	 * 一级菜单图标
	 */
	private String fistMenuIcon;
	/**
	 * 一级菜单Id
	 */
	private String fistMenuId;
	/**
	 * 所有的子节点
	 */
	private List<UserMenuChildrenDto> children;
}
