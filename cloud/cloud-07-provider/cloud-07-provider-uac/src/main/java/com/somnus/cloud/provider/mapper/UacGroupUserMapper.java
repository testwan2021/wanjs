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
import com.somnus.cloud.provider.model.domain.UacGroup;
import com.somnus.cloud.provider.model.domain.UacGroupUser;

/**
 * @ClassName: UacGroupUserMapper
 * @Description: The interface Uac group user mapper.
 * @author Somnus
 * @date 2018年10月17日
 */
@Mapper
@Component
public interface UacGroupUserMapper extends MyMapper<UacGroupUser> {

	/**
	 * Query by user id uac group user.
	 *
	 * @param userId the user id
	 *
	 * @return the uac group user
	 */
	UacGroupUser getByUserId(Long userId);

	/**
	 * Update by user id int.
	 *
	 * @param uacGroupUser the uac group user
	 *
	 * @return the int
	 */
	int updateByUserId(UacGroupUser uacGroupUser);

	/**
	 * Select group list by user id list.
	 *
	 * @param userId the user id
	 *
	 * @return the list
	 */
	List<UacGroup> selectGroupListByUserId(Long userId);

	/**
	 * List by group id list.
	 *
	 * @param groupId the group id
	 *
	 * @return the list
	 */
	List<UacGroupUser> listByGroupId(@Param("groupId") Long groupId);

	/**
	 * Delete exclude super mng int.
	 *
	 * @param groupId            the group id
	 * @param superManagerRoleId the super manager role id
	 *
	 * @return the int
	 */
	int deleteExcludeSuperMng(@Param("currentGroupId") Long groupId, @Param("superManagerRoleId") Long superManagerRoleId);
}