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
package com.somnus.cloud.provider.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.somnus.cloud.provider.model.domain.UacUser;
import com.somnus.cloud.provider.service.UacUserService;
import com.somnus.cloud.security.core.SecurityUser;

/**
 * @ClassName: UacUserDetailsServiceImpl
 * @Description: The class Uac user details service.
 * @author Somnus
 * @date 2018年10月17日
 */
@Component
public class UacUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UacUserService uacUserService;

	/**
	 * Load user by username user details.
	 *
	 * @param username the username
	 *
	 * @return the user details
	 */
	@Override
	public UserDetails loadUserByUsername(String username) {
		Collection<GrantedAuthority> grantedAuthorities;
		UacUser user = uacUserService.findByLoginName(username);
		if (user == null) {
			throw new BadCredentialsException("用户名不存在或者密码错误");
		}
		user = uacUserService.findUserInfoByUserId(user.getId());
		grantedAuthorities = uacUserService.loadUserAuthorities(user.getId());
		return new SecurityUser(user.getId(), user.getLoginName(), user.getLoginPwd(),
				user.getUserName(), user.getGroupId(), user.getGroupName(), user.getStatus(), grantedAuthorities);
	}
}
