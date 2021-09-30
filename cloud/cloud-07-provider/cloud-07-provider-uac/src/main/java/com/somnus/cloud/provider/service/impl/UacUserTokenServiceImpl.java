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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.HttpHeader;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.somnus.cloud.common.base.dto.LoginAuthDto;
import com.somnus.cloud.common.base.dto.UserTokenDto;
import com.somnus.cloud.common.util.PublicUtil;
import com.somnus.cloud.common.util.RedisKeyUtil;
import com.somnus.cloud.comon.core.support.BaseService;
import com.somnus.cloud.comon.core.utils.RequestUtil;
import com.somnus.cloud.provider.mapper.UacUserTokenMapper;
import com.somnus.cloud.provider.model.domain.UacUser;
import com.somnus.cloud.provider.model.domain.UacUserToken;
import com.somnus.cloud.provider.model.dto.token.TokenMainQueryDto;
import com.somnus.cloud.provider.model.enums.UacUserTokenStatusEnum;
import com.somnus.cloud.provider.service.OpcRpcService;
import com.somnus.cloud.provider.service.UacUserService;
import com.somnus.cloud.provider.service.UacUserTokenService;
import com.somnus.cloud.security.core.properties.OAuth2ClientProperties;
import com.somnus.cloud.security.core.properties.SecurityProperties;

import eu.bitwalker.useragentutils.UserAgent;

/**
 * @ClassName: UacUserTokenServiceImpl
 * @Description: The class Uac user token service.
 * @author Somnus
 * @date 2018年10月18日
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UacUserTokenServiceImpl extends BaseService<UacUserToken> implements UacUserTokenService {
	@Autowired
	private UacUserTokenMapper uacUserTokenMapper;
	
	@Autowired
	private UacUserService uacUserService;
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	private OpcRpcService opcRpcService;
	
	@Value("${cloud.auth.refresh-token-url}")
	private String refreshTokenUrl;

	@Override
	public void saveUserToken(String accessToken, String refreshToken, LoginAuthDto loginAuthDto, HttpServletRequest request) {
		// 获取登录时间
		Long userId = loginAuthDto.getUserId();
		UacUser uacUser = uacUserService.selectByKey(userId);
		final UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
		//获取客户端操作系统
		final String os = userAgent.getOperatingSystem().getName();
		//获取客户端浏览器
		final String browser = userAgent.getBrowser().getName();
		final String remoteAddr = RequestUtil.getRemoteAddr(request);
		// 根据IP获取位置信息
		final String remoteLocation = opcRpcService.getLocationById(remoteAddr);

		// 存入mysql数据库
		UacUserToken uacUserToken = new UacUserToken();
		OAuth2ClientProperties[] clients = securityProperties.getOauth2().getClients();
		int accessTokenValidateSeconds = clients[0].getAccessTokenValidateSeconds();
		int refreshTokenValiditySeconds = clients[0].getRefreshTokenValiditySeconds();
		uacUserToken.setOs(os);
		uacUserToken.setBrowser(browser);
		uacUserToken.setAccessToken(accessToken);
		uacUserToken.setAccessTokenValidity(accessTokenValidateSeconds);
		uacUserToken.setLoginIp(remoteAddr);
		uacUserToken.setLoginLocation(remoteLocation);
		uacUserToken.setLoginTime(uacUser.getLastLoginTime());
		uacUserToken.setLoginName(loginAuthDto.getLoginName());
		uacUserToken.setRefreshToken(refreshToken);
		uacUserToken.setRefreshTokenValidity(refreshTokenValiditySeconds);
		uacUserToken.setStatus(UacUserTokenStatusEnum.ON_LINE.getStatus());
		uacUserToken.setUserId(userId);
		uacUserToken.setUserName(loginAuthDto.getUserName());
		uacUserToken.setUpdateInfo(loginAuthDto);
		uacUserToken.setGroupId(loginAuthDto.getGroupId());
		uacUserToken.setGroupName(loginAuthDto.getGroupName());
		uacUserToken.setId(generateId());
		uacUserTokenMapper.insertSelective(uacUserToken);
		UserTokenDto userTokenDto = new ModelMapper().map(uacUserToken, UserTokenDto.class);
		// 存入redis数据库
		updateRedisUserToken(accessToken, accessTokenValidateSeconds, userTokenDto);
	}

	private void updateRedisUserToken(String accessToken, int accessTokenValidateSeconds, UserTokenDto userTokenDto) {
		redisTemplate.opsForValue().set(RedisKeyUtil.getAccessTokenKey(accessToken), userTokenDto, accessTokenValidateSeconds, TimeUnit.SECONDS);
	}

	@Override
	public UserTokenDto getByAccessToken(String accessToken) {
		UserTokenDto userTokenDto = (UserTokenDto) redisTemplate.opsForValue().get(RedisKeyUtil.getAccessTokenKey(accessToken));
		if (userTokenDto == null) {
			UacUserToken uacUserToken = new UacUserToken();
			uacUserToken.setAccessToken(accessToken);
			uacUserToken = uacUserTokenMapper.selectOne(uacUserToken);
			userTokenDto = new ModelMapper().map(uacUserToken, UserTokenDto.class);
		}
		return userTokenDto;
	}

	@Override
	public void updateUacUserToken(UserTokenDto tokenDto, LoginAuthDto loginAuthDto) {
		UacUserToken uacUserToken = new ModelMapper().map(tokenDto, UacUserToken.class);
		uacUserToken.setUpdateInfo(loginAuthDto);
		uacUserTokenMapper.updateByPrimaryKeySelective(uacUserToken);
		OAuth2ClientProperties[] clients = securityProperties.getOauth2().getClients();
		int accessTokenValidateSeconds = clients[0].getAccessTokenValidateSeconds();
		updateRedisUserToken(uacUserToken.getAccessToken(), accessTokenValidateSeconds, tokenDto);
	}

	@Override
	public PageInfo<UacUserToken> listTokenWithPage(TokenMainQueryDto token) {
		PageHelper.startPage(token.getPageNum(), token.getPageSize());
		UacUserToken userToken = new UacUserToken();
		userToken.setStatus(token.getStatus());
		if (token.getStatus() != null) {
			userToken.setStatus(token.getStatus());
		}

		if (StringUtils.isNotBlank(token.getLoginName())) {
			userToken.setLoginName(token.getLoginName());
		}
		if (StringUtils.isNotBlank(token.getUserName())) {
			userToken.setUserName(token.getUserName());
		}
		List<UacUserToken> userTokenList = uacUserTokenMapper.selectTokenList(userToken);
		return new PageInfo<>(userTokenList);
	}

	@Override
	public String refreshToken(String accessToken, String refreshToken, HttpServletRequest request) throws HttpProcessException {
		String token;
		Map<String, Object> map = new HashMap<>(2);
		map.put("grant_type", "refresh_token");
		map.put("refresh_token", refreshToken);

		//插件式配置请求参数（网址、请求参数、编码、client）
		Header[] headers = HttpHeader.custom().contentType(HttpHeader.Headers.APP_FORM_URLENCODED).authorization(request.getHeader(HttpHeaders.AUTHORIZATION)).build();
		HttpConfig config = HttpConfig.custom().headers(headers).url(refreshTokenUrl).map(map);
		token = HttpClientUtil.post(config);
		JSONObject jsonObj = JSON.parseObject(token);
		String accessTokenNew = (String) jsonObj.get("access_token");
		String refreshTokenNew = (String) jsonObj.get("refresh_token");
		String loginName = (String) jsonObj.get("loginName");
		// 更新本次token数据
		UserTokenDto tokenDto = this.getByAccessToken(accessToken);
		tokenDto.setStatus(UacUserTokenStatusEnum.ON_REFRESH.getStatus());
		UacUser uacUser = uacUserService.findUserInfoByLoginName(loginName);

		LoginAuthDto loginAuthDto = new LoginAuthDto(uacUser.getId(), uacUser.getLoginName(), uacUser.getUserName(), uacUser.getGroupId(), uacUser.getGroupName());
		this.updateUacUserToken(tokenDto, loginAuthDto);
		// 创建刷新token
		this.saveUserToken(accessTokenNew, refreshTokenNew, loginAuthDto, request);
		return token;
	}

	@Override
	public int batchUpdateTokenOffLine() {
		List<Long> idList = uacUserTokenMapper.listOffLineTokenId();
		if (PublicUtil.isEmpty(idList)) {
			return 1;
		}
		Map<String, Object> map = Maps.newHashMap();
		map.put("status", 20);
		map.put("tokenIdList", idList);
		return uacUserTokenMapper.batchUpdateTokenOffLine(map);
	}
}
