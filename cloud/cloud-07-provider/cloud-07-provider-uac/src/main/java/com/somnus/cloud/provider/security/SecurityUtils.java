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

import com.google.common.collect.Sets;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.Collection;
import java.util.Set;

/**
 * @ClassName: SecurityUtils
 * @Description: The class Security utils.
 * @author Somnus
 * @date 2018年10月17日
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SecurityUtils {

	private static final String AUTH_LOGIN_AFTER_URL = "/user/loginAfter/*";
	private static final String AUTH_LOGOUT_URL = "/user/logout";

	/**
	 * Gets current login name.
	 *
	 * @return the current login name
	 */
	public static String getCurrentLoginName() {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {

			return ((UserDetails) principal).getUsername();

		}

		if (principal instanceof Principal) {

			return ((Principal) principal).getName();

		}

		return String.valueOf(principal);

	}

	public static Set<String> getCurrentAuthorityUrl() {
		Set<String> path = Sets.newHashSet();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (final GrantedAuthority authority : authorities) {
			String url = authority.getAuthority();
			if (StringUtils.isNotEmpty(url)) {
				path.add(url);
			}
		}
		path.add(AUTH_LOGIN_AFTER_URL);
		path.add(AUTH_LOGOUT_URL);
		return path;
	}
}
