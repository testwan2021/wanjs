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
package com.somnus.cloud.provider.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.somnus.cloud.comon.core.mybatis.MyMapper;
import com.somnus.cloud.provider.api.dto.user.Perm;
import com.somnus.cloud.provider.api.vo.MenuVo;
import com.somnus.cloud.provider.model.domain.UacAction;
import com.somnus.cloud.provider.model.domain.UacMenu;
import com.somnus.cloud.provider.model.vo.ActionVo;

/**
 * @ClassName: UacActionMapper
 * @Description: The interface Uac action mapper.
 * @author Somnus
 * @date 2018年10月17日
 */
@Mapper
@Component
public interface UacActionMapper extends MyMapper<UacAction> {
	/**
	 * Find all perms list.
	 *
	 * @return the list
	 */
	List<Perm> findAllPerms();

	/**
	 * Find action code list by user id list.
	 *
	 * @param userId the user id
	 *
	 * @return the list
	 */
	List<String> findActionCodeListByUserId(Long userId);

	/**
	 * Batch delete by id list int.
	 *
	 * @param deleteIdList the delete id list
	 *
	 * @return the int
	 */
	int batchDeleteByIdList(@Param("idList") List<Long> deleteIdList);

	/**
	 * Query action list with page list.
	 *
	 * @param uacAction the uac action
	 *
	 * @return the list
	 */
	List<ActionVo> queryActionListWithPage(UacAction uacAction);

	/**
	 * Delete by menu id int.
	 *
	 * @param id the id
	 *
	 * @return the int
	 */
	int deleteByMenuId(@Param("menuId") Long id);

	/**
	 * Gets checked action list.
	 *
	 * @param roleId the role id
	 *
	 * @return the checked action list
	 */
	List<Long> getCheckedActionList(@Param("roleId") Long roleId);

	/**
	 * Gets own auth list.
	 *
	 * @param userId the user id
	 *
	 * @return the own auth list
	 */
	List<MenuVo> getOwnAuthList(@Param("userId") Long userId);

	/**
	 * Gets checked menu list.
	 *
	 * @param roleId the role id
	 *
	 * @return the checked menu list
	 */
	List<Long> getCheckedMenuList(@Param("roleId") Long roleId);

	/**
	 * Gets own uac action list by user id.
	 *
	 * @param userId the user id
	 *
	 * @return the own uac action list by user id
	 */
	List<UacAction> getOwnUacActionListByUserId(Long userId);

	/**
	 * 根据角色ID查询权限列表.
	 *
	 * @param roleId the role id
	 *
	 * @return the list
	 */
	List<UacAction> listActionListByRoleId(@Param("roleId") Long roleId);

	/**
	 * List action list list.
	 *
	 * @param menuList the menu list
	 *
	 * @return the list
	 */
	List<UacAction> listActionList(@Param("menuList") List<UacMenu> menuList);
}