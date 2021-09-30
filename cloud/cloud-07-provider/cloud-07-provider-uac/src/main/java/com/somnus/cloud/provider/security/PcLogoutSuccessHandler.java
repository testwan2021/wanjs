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

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.somnus.cloud.common.util.wrapper.WrapMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName: PcLogoutSuccessHandler
 * @Description: 默认的退出成功处理器，如果设置了cloud.security.browser.signOutUrl，则跳到配置的地址上，
 * 如果没配置，则返回json格式的响应。
 * @author Somnus
 * @date 2018年10月17日
 */
@Slf4j
public class PcLogoutSuccessHandler implements LogoutSuccessHandler {

	private ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * On logout success.
	 *
	 * @param request        the request
	 * @param response       the response
	 * @param authentication the authentication
	 *
	 * @throws IOException the io exception
	 */
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException {
		log.info("退出成功");
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(objectMapper.writeValueAsString(WrapMapper.success("退出成功")));
	}

}
