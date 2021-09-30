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

import com.somnus.cloud.provider.api.vo.MenuVo;

/**
 * @ClassName: UserMenuChildrenDto
 * @Description: The class User menu children dto.
 * @author Somnus
 * @date 2018年10月18日
 */
@Data
public class UserMenuChildrenDto implements Serializable {
	private static final long serialVersionUID = -6279523061450477189L;
	/**
	 * 叶子节点菜单名称
	 */
	private String leafMenuName;
	/**
	 * 是否被选中,true
	 */
	private boolean checked;
	/**
	 * 跳转URL
	 */
	private String url;
	/**
	 * 叶子节点菜单Id
	 */
	private Long leafMenuId;


	/**
	 * Instantiates a new User menu children dto.
	 *
	 * @param menuVo the menu vo
	 */
	public UserMenuChildrenDto(MenuVo menuVo) {
		this.leafMenuName = menuVo.getMenuName();
		this.url = menuVo.getUrl();
		this.leafMenuId = menuVo.getId();
	}
}
