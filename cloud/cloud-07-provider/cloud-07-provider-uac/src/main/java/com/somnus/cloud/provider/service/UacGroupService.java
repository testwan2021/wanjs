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
import com.somnus.cloud.provider.api.dto.user.IdStatusDto;
import com.somnus.cloud.provider.api.vo.MenuVo;
import com.somnus.cloud.provider.model.domain.UacGroup;
import com.somnus.cloud.provider.model.dto.group.GroupBindUserDto;
import com.somnus.cloud.provider.model.dto.group.GroupBindUserReqDto;
import com.somnus.cloud.provider.model.vo.GroupZtreeVo;

/**
 * @ClassName: UacGroupService
 * @Description: The interface Uac group service.
 * @author Somnus
 * @date 2018年10月17日
 */
public interface UacGroupService extends IService<UacGroup> {

	/**
	 * Update uac group status by id int.
	 *
	 * @param idStatusDto  the id status dto
	 * @param loginAuthDto the login auth dto
	 *
	 * @return the int
	 */
	int updateUacGroupStatusById(IdStatusDto idStatusDto, LoginAuthDto loginAuthDto);

	/**
	 * Delete uac group by id int.
	 *
	 * @param id the id
	 *
	 * @return the int
	 */
	int deleteUacGroupById(Long id);

	/**
	 * Query by id uac group.
	 *
	 * @param groupId the group id
	 *
	 * @return the uac group
	 */
	UacGroup queryById(Long groupId);

	/**
	 * Gets group tree.
	 *
	 * @param id the id
	 *
	 * @return the group tree
	 */
	List<GroupZtreeVo> getGroupTree(Long id);

	/**
	 * Find current user have group info list.
	 *
	 * @param userId the user id
	 *
	 * @return the list
	 */
	List<MenuVo> getGroupTreeListByUserId(Long userId);

	/**
	 * Gets group bind user dto.
	 *
	 * @param groupId the group id
	 * @param userId  the user id
	 *
	 * @return the group bind user dto
	 */
	GroupBindUserDto getGroupBindUserDto(Long groupId, Long userId);

	/**
	 * Bind uac user 4 group int.
	 *
	 * @param groupBindUserReqDto the group bind user req dto
	 * @param loginAuthDto        the login auth dto
	 */
	void bindUacUser4Group(GroupBindUserReqDto groupBindUserReqDto, LoginAuthDto loginAuthDto);

	/**
	 * Save uac group int.
	 *
	 * @param group        the group
	 * @param loginAuthDto the login auth dto
	 *
	 * @return the int
	 */
	int saveUacGroup(UacGroup group, LoginAuthDto loginAuthDto);

	/**
	 * Gets by id.
	 *
	 * @param id the id
	 *
	 * @return the by id
	 */
	UacGroup getById(Long id);
}
