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
package com.somnus.cloud.security.core.social.weixin.connect;

import org.springframework.social.oauth2.AccessGrant;

/**
 * @ClassName: WeixinAccessGrant
 * 微信的access_token信息。与标准OAuth2协议不同，微信在获取access_token时会同时返回openId,并没有单独的通过accessToke换取openId的服务
 * <p>
 * 所以在这里继承了标准AccessGrant，添加了openId字段，作为对微信access_token信息的封装。
 * @author Somnus
 * @date 2018年10月12日
 */
public class WeixinAccessGrant extends AccessGrant {


	private static final long serialVersionUID = -7243374526633186782L;

	private String openId;

	/**
	 * Instantiates a new Weixin access grant.
	 */
	public WeixinAccessGrant() {
		super("");
	}

	/**
	 * Instantiates a new Weixin access grant.
	 *
	 * @param accessToken  the access token
	 * @param scope        the scope
	 * @param refreshToken the refresh token
	 * @param expiresIn    the expires in
	 */
	public WeixinAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn) {
		super(accessToken, scope, refreshToken, expiresIn);
	}

	/**
	 * Gets open id.
	 *
	 * @return the openId
	 */
	public String getOpenId() {
		return openId;
	}

	/**
	 * Sets open id.
	 *
	 * @param openId the openId to set
	 */
	public void setOpenId(String openId) {
		this.openId = openId;
	}

}
