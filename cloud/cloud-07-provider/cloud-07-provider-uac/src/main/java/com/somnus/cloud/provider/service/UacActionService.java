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

import com.github.pagehelper.PageInfo;
import com.somnus.cloud.common.base.dto.LoginAuthDto;
import com.somnus.cloud.comon.core.support.IService;
import com.somnus.cloud.provider.api.vo.MenuVo;
import com.somnus.cloud.provider.model.domain.UacAction;
import com.somnus.cloud.provider.model.domain.UacMenu;
import com.somnus.cloud.provider.model.dto.action.ActionMainQueryDto;
import com.somnus.cloud.provider.model.vo.ActionVo;

/**
 * @ClassName: UacActionService
 * @Description: The interface Uac action service.
 * @author Somnus
 * @date 2018年10月17日
 */
public interface UacActionService extends IService<UacAction> {
	/**
	 * Query action list with page page info.
	 *
	 * @param actionMainQueryDto the action main query dto
	 *
	 * @return the page info
	 */
	PageInfo<ActionVo> queryActionListWithPage(ActionMainQueryDto actionMainQueryDto);

	/**
	 * Delete action by id int.
	 *
	 * @param actionId the action id
	 *
	 * @return the int
	 */
	int deleteActionById(Long actionId);

	/**
	 * Batch delete by id list.
	 *
	 * @param deleteIdList the delete id list
	 */
	void batchDeleteByIdList(List<Long> deleteIdList);

	/**
	 * Save action.
	 *
	 * @param action       the action
	 * @param loginAuthDto the login auth dto
	 */
	void saveAction(UacAction action, LoginAuthDto loginAuthDto);

	/**
	 * Delete by menu id int.
	 *
	 * @param id the id
	 *
	 * @return the int
	 */
	int deleteByMenuId(Long id);

	/**
	 * Gets checked auth list.
	 *
	 * @param roleId the role id
	 *
	 * @return the checked auth list
	 */
	List<Long> getCheckedActionList(Long roleId);

	/**
	 * Gets own auth list.
	 *
	 * @param userId the user id
	 *
	 * @return the own auth list
	 */
	List<MenuVo> getOwnAuthList(Long userId);

	/**
	 * Gets checked menu list.
	 *
	 * @param roleId the role id
	 *
	 * @return the checked menu list
	 */
	List<Long> getCheckedMenuList(Long roleId);

	/**
	 * 根据用户Id查询拥有的按钮权限.
	 *
	 * @param userId the user id
	 *
	 * @return the own uac action list
	 */
	List<UacAction> getOwnActionListByUserId(Long userId);

	/**
	 * 根据角色ID查询权限列表.
	 *
	 * @param roleId the role id
	 *
	 * @return the list
	 */
	List<UacAction> listActionListByRoleId(Long roleId);

	/**
	 * 根据菜单ID List 查询权限列表.
	 *
	 * @param uacMenus the uac menus
	 *
	 * @return the list
	 */
	List<UacAction> listActionList(List<UacMenu> uacMenus);

	/**
	 * Matches by url uac action.
	 *
	 * @param requestUrl the request url
	 *
	 * @return the uac action
	 */
	UacAction matchesByUrl(String requestUrl);
}
