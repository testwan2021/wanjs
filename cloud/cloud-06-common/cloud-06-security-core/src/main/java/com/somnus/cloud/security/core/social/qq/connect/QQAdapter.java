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
package com.somnus.cloud.security.core.social.qq.connect;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

import com.somnus.cloud.security.core.social.qq.api.QQ;
import com.somnus.cloud.security.core.social.qq.api.QQUserInfo;

/**
 * @ClassName: QQAdapter
 * @Description: The class Qq adapter.
 * @author Somnus
 * @date 2018年10月12日
 */
public class QQAdapter implements ApiAdapter<QQ> {

	/**
	 * Test boolean.
	 *
	 * @param api the api
	 *
	 * @return the boolean
	 */
	@Override
	public boolean test(QQ api) {
		return true;
	}

	/**
	 * Sets connection values.
	 *
	 * @param api    the api
	 * @param values the values
	 */
	@Override
	public void setConnectionValues(QQ api, ConnectionValues values) {
		QQUserInfo userInfo = api.getUserInfo();

		values.setDisplayName(userInfo.getNickname());
		values.setImageUrl(userInfo.getFigureUrlQq1());
		values.setProfileUrl(null);
		values.setProviderUserId(userInfo.getOpenId());
	}

	/**
	 * Fetch user profile user profile.
	 *
	 * @param api the api
	 *
	 * @return the user profile
	 */
	@Override
	public UserProfile fetchUserProfile(QQ api) {
		return null;
	}

	/**
	 * Update status.
	 *
	 * @param api     the api
	 * @param message the message
	 */
	@Override
	public void updateStatus(QQ api, String message) {
		//do noting
	}

}
