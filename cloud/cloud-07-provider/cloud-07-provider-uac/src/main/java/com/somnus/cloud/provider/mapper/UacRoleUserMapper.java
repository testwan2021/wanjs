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
import com.somnus.cloud.provider.model.domain.UacRoleUser;

/**
 * @ClassName: UacRoleUserMapper
 * @Description: The interface Uac role user mapper.
 * @author Somnus
 * @date 2018年10月17日
 */
@Mapper
@Component
public interface UacRoleUserMapper extends MyMapper<UacRoleUser> {

	/**
	 * Delete exclude super mng int.
	 *
	 * @param currentRoleId the current role id
	 * @param superRoleId   the super role id
	 *
	 * @return the int
	 */
	int deleteExcludeSuperMng(@Param("currentRoleId") Long currentRoleId, @Param("superRoleId") Long superRoleId);

	/**
	 * Gets by user id and role id.
	 *
	 * @param userId the user id
	 * @param roleId the role id
	 *
	 * @return the by user id and role id
	 */
	UacRoleUser getByUserIdAndRoleId(@Param("userId") Long userId, @Param("roleId") Long roleId);

	/**
	 * List by user id list.
	 *
	 * @param userId the user id
	 *
	 * @return the list
	 */
	List<UacRoleUser> listByUserId(@Param("userId") Long userId);

	/**
	 * List by role id list.
	 *
	 * @param roleId the role id
	 *
	 * @return the list
	 */
	List<UacRoleUser> listByRoleId(@Param("roleId") Long roleId);

	/**
	 * List super user list.
	 *
	 * @param roleId the role id
	 *
	 * @return the list
	 */
	List<Long> listSuperUser(@Param("roleId") Long roleId);

	/**
	 * List by role id list list.
	 *
	 * @param idList the id list
	 *
	 * @return the list
	 */
	List<UacRoleUser> listByRoleIdList(@Param("roleIds") List<Long> idList);

	/**
	 * Delete by role id int.
	 *
	 * @param roleId the role id
	 *
	 * @return the int
	 */
	int deleteByRoleId(@Param("roleId") Long roleId);

	/**
	 * Delete by role id list int.
	 *
	 * @param roleIdList the role id list
	 *
	 * @return the int
	 */
	int deleteByRoleIdList(@Param("roleIdList") List<Long> roleIdList);
}