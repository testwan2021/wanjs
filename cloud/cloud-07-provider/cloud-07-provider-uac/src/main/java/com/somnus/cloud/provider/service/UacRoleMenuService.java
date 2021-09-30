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
package com.somnus.cloud.provider.service;

import java.util.List;
import java.util.Set;

import com.somnus.cloud.comon.core.support.IService;
import com.somnus.cloud.provider.model.domain.UacRoleMenu;

/**
 * @ClassName: UacRoleMenuService
 * @Description: The interface Uac role menu service.
 * @author Somnus
 * @date 2018年10月17日
 */
public interface UacRoleMenuService extends IService<UacRoleMenu> {
	/**
	 * Del role menu list int.
	 *
	 * @param uacRoleMenus the uac role menus
	 *
	 * @return the int
	 */
	int delRoleMenuList(Set<UacRoleMenu> uacRoleMenus);

	/**
	 * Delete by role id.
	 *
	 * @param roleId the role id
	 */
	void deleteByRoleId(Long roleId);

	/**
	 * List by role id list.
	 *
	 * @param roleId the role id
	 *
	 * @return the list
	 */
	List<UacRoleMenu> listByRoleId(Long roleId);

	/**
	 * Insert.
	 *
	 * @param roleId     the role id
	 * @param menuIdList the menu id list
	 */
	void insert(Long roleId, Set<Long> menuIdList);

	/**
	 * Delete by role id list.
	 *
	 * @param roleIdList the role id list
	 */
	void deleteByRoleIdList(List<Long> roleIdList);
}
