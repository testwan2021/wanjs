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

import com.somnus.cloud.common.base.dto.LoginAuthDto;
import com.somnus.cloud.comon.core.support.IService;
import com.somnus.cloud.provider.api.vo.MenuVo;
import com.somnus.cloud.provider.model.domain.UacRole;
import com.somnus.cloud.provider.model.dto.role.RoleBindActionDto;
import com.somnus.cloud.provider.model.dto.role.RoleBindMenuDto;
import com.somnus.cloud.provider.model.dto.role.RoleBindUserDto;
import com.somnus.cloud.provider.model.dto.role.RoleBindUserReqDto;
import com.somnus.cloud.provider.model.vo.BindAuthVo;
import com.somnus.cloud.provider.model.vo.RoleVo;

/**
 * @ClassName: UacRoleService
 * @Description: The interface Uac role service.
 * @author Somnus
 * @date 2018年10月17日
 */
public interface UacRoleService extends IService<UacRole> {

	/**
	 * Find by role code uac role.
	 *
	 * @param roleCode the role code
	 *
	 * @return the uac role
	 */
	UacRole findByRoleCode(String roleCode);

	/**
	 * Query role list with page list.
	 *
	 * @param role the role
	 *
	 * @return the list
	 */
	List<RoleVo> queryRoleListWithPage(UacRole role);

	/**
	 * 根据角色删除用户.
	 *
	 * @param id the id
	 *
	 * @return the int
	 */
	int deleteRoleById(Long id);

	/**
	 * 保存用户.
	 *
	 * @param role         the role
	 * @param loginAuthDto the login auth dto
	 *
	 * @return the int
	 */
	int saveRole(UacRole role, LoginAuthDto loginAuthDto);

	/**
	 * Gets auth tree no check menu.
	 *
	 * @param roleId the role id
	 *
	 * @return the auth tree no check menu
	 */
	List<Long> getAuthTreeNoCheckMenu(Long roleId);

	/**
	 * Grant auth 4 role int.
	 *
	 * @param grantAuthRole the grant auth role
	 */
	void bindAction(RoleBindActionDto grantAuthRole);

	/**
	 * 根据角色Id获取角色信息.
	 *
	 * @param roleId the role id
	 *
	 * @return the role by id
	 */
	UacRole getRoleById(Long roleId);

	/**
	 * 角色绑定用户获取穿梭框信息.
	 *
	 * @param roleId        the role id
	 * @param currentUserId the current user id
	 *
	 * @return the role bind user dto by role id
	 */
	RoleBindUserDto getRoleBindUserDto(Long roleId, Long currentUserId);

	/**
	 * 角色绑定用户.
	 *
	 * @param roleBindUserReqDto the role bind user req dto
	 * @param loginAuthDto       the login auth dto
	 */
	void bindUser4Role(RoleBindUserReqDto roleBindUserReqDto, LoginAuthDto loginAuthDto);

	/**
	 * Find all role info by user id list.
	 *
	 * @param userId the user id
	 *
	 * @return the list
	 */
	List<UacRole> findAllRoleInfoByUserId(Long userId);

	/**
	 * Gets auth tree by role id.
	 *
	 * @param roleId the role id
	 *
	 * @return the auth tree by role id
	 */
	BindAuthVo getActionTreeByRoleId(Long roleId);

	/**
	 * Batch delete by id list int.
	 *
	 * @param idList the id list
	 */
	void batchDeleteByIdList(List<Long> idList);

	/**
	 * Gets own auth tree by role id.
	 *
	 * @param userId the user id
	 *
	 * @return the own auth tree by role id
	 */
	List<MenuVo> getOwnAuthTree(Long userId);

	/**
	 * Bind menu.
	 *
	 * @param roleBindMenuDto the role bind menu dto
	 */
	void bindMenu(RoleBindMenuDto roleBindMenuDto);

	/**
	 * Gets menu tree by role id.
	 *
	 * @param roleId the role id
	 *
	 * @return the menu tree by role id
	 */
	BindAuthVo getMenuTreeByRoleId(Long roleId);
}
