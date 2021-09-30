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
package com.somnus.cloud.provider.web.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Preconditions;
import com.somnus.cloud.common.base.dto.UserTokenDto;
import com.somnus.cloud.common.util.wrapper.WrapMapper;
import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.comon.core.support.BaseController;
import com.somnus.cloud.comon.core.utils.RequestUtil;
import com.somnus.cloud.provider.model.dto.user.LoginRespDto;
import com.somnus.cloud.provider.model.enums.UacUserTokenStatusEnum;
import com.somnus.cloud.provider.service.UacLoginService;
import com.somnus.cloud.provider.service.UacUserTokenService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName: UacUserLoginController
 * @Description: 登录相关.
 * @author Somnus
 * @date 2018年10月17日
 */
@RestController
@RequestMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "Web - UacUserLoginController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacUserLoginController extends BaseController {

	@Autowired
	private UacLoginService uacLoginService;
	
	@Autowired
	private UacUserTokenService uacUserTokenService;
	
	@Autowired
	private ClientDetailsService clientDetailsService;
	
	private static final String BEARER_TOKEN_TYPE = "Basic ";


	/**
	 * 登录成功获取菜单信息和用户信息.
	 *
	 * @param applicationId the application id
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/user/loginAfter/{applicationId}")
	@ApiOperation(httpMethod = "POST", value = "登录成功获取用户菜单")
	public Wrapper<LoginRespDto> loginAfter(@PathVariable Long applicationId) {
		logger.info("登录成功获取用户菜单. applicationId={}", applicationId);
		LoginRespDto result = uacLoginService.loginAfter(applicationId);
		return WrapMapper.success(result);
	}

	/**
	 * 登出.
	 *
	 * @param accessToken the access token
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/user/logout")
	@ApiOperation(httpMethod = "POST", value = "登出")
	public Wrapper<?> loginAfter(String accessToken) {
		if (!StringUtils.isEmpty(accessToken)) {
			// 修改用户在线状态
			UserTokenDto userTokenDto = uacUserTokenService.getByAccessToken(accessToken);
			userTokenDto.setStatus(UacUserTokenStatusEnum.OFF_LINE.getStatus());
			uacUserTokenService.updateUacUserToken(userTokenDto, getLoginAuthDto());
		}
		return WrapMapper.ok();
	}

	/**
	 * 刷新token.
	 *
	 * @param request      the request
	 * @param refreshToken the refresh token
	 * @param accessToken  the access token
	 *
	 * @return the wrapper
	 */
	@GetMapping(value = "/auth/user/refreshToken")
	@ApiOperation(httpMethod = "POST", value = "刷新token")
	public Wrapper<String> refreshToken(HttpServletRequest request, @RequestParam(value = "refreshToken") String refreshToken, @RequestParam(value = "accessToken") String accessToken) {
		String token;
		try {
			Preconditions.checkArgument(org.apache.commons.lang3.StringUtils.isNotEmpty(accessToken), "accessToken is null");
			Preconditions.checkArgument(org.apache.commons.lang3.StringUtils.isNotEmpty(refreshToken), "refreshToken is null");
			String header = request.getHeader(HttpHeaders.AUTHORIZATION);
			if (header == null || !header.startsWith(BEARER_TOKEN_TYPE)) {
				throw new UnapprovedClientAuthenticationException("请求头中无client信息");
			}
			String[] tokens = RequestUtil.extractAndDecodeHeader(header);
			assert tokens.length == 2;

			String clientId = tokens[0];
			String clientSecret = tokens[1];

			ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

			if (clientDetails == null) {
				throw new UnapprovedClientAuthenticationException("clientId对应的配置信息不存在:" + clientId);
			} else if (!StringUtils.equals(clientDetails.getClientSecret(), clientSecret)) {
				throw new UnapprovedClientAuthenticationException("clientSecret不匹配:" + clientId);
			}

			token = uacUserTokenService.refreshToken(accessToken, refreshToken, request);
		} catch (Exception e) {
			logger.error("refreshToken={}", e.getMessage(), e);
			return WrapMapper.error();
		}
		return WrapMapper.success(token);
	}

}