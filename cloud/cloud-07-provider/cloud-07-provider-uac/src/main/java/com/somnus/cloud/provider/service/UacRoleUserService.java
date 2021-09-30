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

import com.somnus.cloud.comon.core.support.IService;
import com.somnus.cloud.provider.model.domain.UacRoleUser;

/**
 * @ClassName: UacRoleUserService
 * @Description: The interface Uac role user service.
 * @author Somnus
 * @date 2018年10月17日
 */
public interface UacRoleUserService extends IService<UacRoleUser> {
	/**
	 * 根据用户ID解除用户角色关联.
	 *
	 * @param userId the user id
	 *
	 * @return the int
	 */
	int deleteByUserId(Long userId);

	/**
	 * 根据用户ID查询绑定的角色ID列表.
	 *
	 * @param userId the user id
	 *
	 * @return the list
	 */
	List<UacRoleUser> queryByUserId(Long userId);

	/**
	 * 用户是否绑定该角色.
	 *
	 * @param userId the user id
	 * @param roleId the role id
	 *
	 * @return the by user id and role id
	 */
	UacRoleUser getByUserIdAndRoleId(Long userId, Long roleId);

	/**
	 * 保存用户角色中间表信息.
	 *
	 * @param userId the user id
	 * @param roleId the role id
	 *
	 * @return the int
	 */
	int saveRoleUser(Long userId, Long roleId);

	/**
	 * 根据角色信息查询用户ID集合.
	 *
	 * @param roleId the role id
	 *
	 * @return the list
	 */
	List<UacRoleUser> listByRoleId(Long roleId);

	/**
	 * 根据角色ID列表查询用户ID集合.
	 *
	 * @param idList the id list
	 *
	 * @return the list
	 */
	List<UacRoleUser> listByRoleIdList(List<Long> idList);

	/**
	 * List super user list.
	 *
	 * @param superManagerRoleId the super manager role id
	 *
	 * @return the list
	 */
	List<Long> listSuperUser(Long superManagerRoleId);

	/**
	 * Delete exclude super mng.
	 *
	 * @param roleId             the role id
	 * @param superManagerRoleId the super manager role id
	 */
	void deleteExcludeSuperMng(Long roleId, Long superManagerRoleId);

	/**
	 * 根据用户ID查询集合.
	 *
	 * @param userId the user id
	 *
	 * @return the list
	 */
	List<UacRoleUser> listByUserId(Long userId);

	/**
	 * 根据角色ID集合解除关联关系.
	 *
	 * @param roleIdList the role id list
	 */
	void deleteByRoleIdList(List<Long> roleIdList);

	/**
	 * 根据角色ID解除关联关系.
	 *
	 * @param roleId the role id
	 */
	void deleteByRoleId(Long roleId);
}
