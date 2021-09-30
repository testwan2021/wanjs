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
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.somnus.cloud.comon.core.mybatis.MyMapper;
import com.somnus.cloud.provider.model.domain.UacUser;
import com.somnus.cloud.provider.model.dto.user.BindRoleDto;

/**
 * @ClassName: UacUserMapper
 * @Description: The interface Uac user mapper.
 * @author Somnus
 * @date 2018年10月17日
 */
@Mapper
@Component
public interface UacUserMapper extends MyMapper<UacUser> {

	/**
	 * Find by login name uac user.
	 *
	 * @param loginName the login name
	 *
	 * @return the uac user
	 */
	UacUser findByLoginName(String loginName);

	/**
	 * Find by mobile no uac user.
	 *
	 * @param mobileNo the mobile no
	 *
	 * @return the uac user
	 */
	UacUser findByMobileNo(@Param("mobileNo") String mobileNo);

	/**
	 * Find by login name and login pwd uac user.
	 *
	 * @param loginNamePwdMap the login name pwd map
	 *
	 * @return the uac user
	 */
	UacUser findByLoginNameAndLoginPwd(Map<String, String> loginNamePwdMap);

	/**
	 * Select user list list.
	 *
	 * @param uacUser the uac user
	 *
	 * @return the list
	 */
	List<UacUser> selectUserList(UacUser uacUser);

	/**
	 * Select user info by user id uac user.
	 *
	 * @param userId the user id
	 *
	 * @return the uac user
	 */
	UacUser selectUserInfoByUserId(Long userId);

	/**
	 * Update uac user int.
	 *
	 * @param user the user
	 *
	 * @return the int
	 */
	int updateUacUser(UacUser user);

	/**
	 * Select all need bind role list.
	 *
	 * @param superManagerRoleId the super manager role id
	 *
	 * @return the list
	 */
	List<BindRoleDto> selectAllNeedBindRole(@Param("superManagerRoleId") Long superManagerRoleId);

	/**
	 * Find user info by login name uac user.
	 *
	 * @param loginName the login name
	 *
	 * @return the uac user
	 */
	UacUser findUserInfoByLoginName(@Param("loginName") String loginName);
}