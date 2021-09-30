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
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.somnus.cloud.comon.core.mybatis.MyMapper;
import com.somnus.cloud.provider.api.vo.MenuVo;
import com.somnus.cloud.provider.model.domain.UacMenu;

/**
 * @ClassName: UacMenuMapper
 * @Description: The interface Uac menu mapper.
 * @author Somnus
 * @date 2018年10月17日
 */
@Mapper
@Component
public interface UacMenuMapper extends MyMapper<UacMenu> {

	/**
	 * Find menu vo list by user id list.
	 *
	 * @param userId the user id
	 *
	 * @return the list
	 */
	List<MenuVo> findMenuVoListByUserId(Long userId);

	/**
	 * Find menu code list by user id list.
	 *
	 * @param userId the user id
	 *
	 * @return the list
	 */
	List<String> findMenuCodeListByUserId(Long userId);

	/**
	 * Select menu list list.
	 *
	 * @param uacMenu the uac menu
	 *
	 * @return the list
	 */
	List<UacMenu> selectMenuList(UacMenu uacMenu);

	/**
	 * Select menu child count by pid int.
	 *
	 * @param pid the pid
	 *
	 * @return the int
	 */
	int selectMenuChildCountByPid(Long pid);

	/**
	 * Select by url uac menu.
	 *
	 * @param url the url
	 *
	 * @return the uac menu
	 */
	UacMenu selectByUrl(String url);

	/**
	 * Update menu int.
	 *
	 * @param uacMenu the uac menu
	 *
	 * @return the int
	 */
	int updateMenu(UacMenu uacMenu);

	/**
	 * 根据角色ID查询菜单列表.
	 *
	 * @param roleId the role id
	 *
	 * @return the list
	 */
	List<UacMenu> listMenuListByRoleId(@Param("roleId") Long roleId);

	/**
	 * List menu list.
	 *
	 * @param menuIdList the menu id list
	 *
	 * @return the list
	 */
	List<UacMenu> listMenu(@Param("menuIdList") Set<Long> menuIdList);
}