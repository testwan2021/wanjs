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
package com.somnus.cloud.provider.service.impl;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import com.google.common.base.Joiner;
import com.somnus.cloud.common.base.constant.GlobalConstant;
import com.somnus.cloud.provider.security.SecurityUtils;
import com.somnus.cloud.provider.service.UacPermissionService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: UacPermissionServiceImpl
 * @Description: The class Uac permission service.
 * @author Somnus
 * @date 2018年10月18日
 */
@Slf4j
@Component("permissionService")
public class UacPermissionServiceImpl implements UacPermissionService {
	private AntPathMatcher antPathMatcher = new AntPathMatcher();
	private static final String OAUTH2_CLIENT_PREFIX = "cloud-client-";

	@Autowired
	private ClientDetailsService clientDetailsService;

	@Override
	public boolean hasPermission(Authentication authentication, HttpServletRequest request) {
		String currentLoginName = SecurityUtils.getCurrentLoginName();
		Set<String> currentAuthorityUrl = SecurityUtils.getCurrentAuthorityUrl();
		String requestURI = request.getRequestURI();
		log.info("验证权限loginName={}, requestURI={}, hasAuthorityUrl={}", currentLoginName, requestURI, Joiner.on(GlobalConstant.Symbol.COMMA).join(currentAuthorityUrl));
		// 超级管理员 全部都可以访问
		if (StringUtils.equals(currentLoginName, GlobalConstant.Sys.SUPER_MANAGER_LOGIN_NAME)) {
			return true;
		}

		// DEMO项目Feign客户端具有所有权限, 如果需要则在角色权限中控制
		if (currentLoginName.contains(OAUTH2_CLIENT_PREFIX)) {
			ClientDetails clientDetails = clientDetailsService.loadClientByClientId(currentLoginName);
			return clientDetails != null;
		}

		for (final String authority : currentAuthorityUrl) {
			// DEMO项目放过查询权限
			if (requestURI.contains("query") || requestURI.contains("get") || requestURI.contains("check") || requestURI.contains("select")) {
				return true;
			}
			if (antPathMatcher.match(authority, requestURI)) {
				return true;
			}
		}
		return false;
	}
}
