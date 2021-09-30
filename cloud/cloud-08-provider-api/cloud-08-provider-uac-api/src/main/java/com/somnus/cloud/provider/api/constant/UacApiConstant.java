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
package com.somnus.cloud.provider.api.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @ClassName: UacApiConstant
 * @Description: The class Uac api constant.
 * @author Somnus
 * @date 2018年10月16日
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UacApiConstant {
	/**
	 * token变量
	 */
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Valid {
		/**
		 * The constant EMAIL.
		 */
		public static final String EMAIL = "email";
		/**
		 * The constant LOGIN_NAME.
		 */
		public static final String LOGIN_NAME = "loginName";
		/**
		 * The constant MOBILE_NO.
		 */
		public static final String MOBILE_NO = "mobileNo";
	}
}
