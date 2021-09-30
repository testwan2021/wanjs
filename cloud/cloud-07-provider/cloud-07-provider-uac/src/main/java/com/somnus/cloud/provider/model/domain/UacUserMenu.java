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
package com.somnus.cloud.provider.model.domain;

import com.somnus.cloud.comon.core.mybatis.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName: UacUserMenu
 * @Description: The class Uac user menu.
 * @author Somnus
 * @date 2018年10月17日
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UacUserMenu extends BaseEntity {
	private static final long serialVersionUID = 71826276587527887L;
	/**
	 * pc_uac_user_menu.user_id;用户id
	 */
	private Long userId;

	/**
	 * pc_uac_user_menu.menu_id;菜单id
	 */
	private Long menuId;

	/**
	 * pc_uac_user_menu.number;序号
	 */
	private Integer number;
}