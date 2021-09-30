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

/**
 * @ClassName: UacConstant
 * @Description: The class Uac constant.
 * @author Somnus
 * @date 2018年10月16日
 */
public class UacConstant {

	/**
	 * The constant MENU_ROOT.
	 */
	public static final String MENU_ROOT = "root";

	/**
	 * token变量
	 */
	public static final class Token {
		/**
		 * 用户名密码加密秘钥
		 */
		public static final String SEC_TOKEN = "SEC_TOKEN";
		/**
		 * 验证码
		 */
		public static final String KAPTCHA = "KAPTCHA";

		/**
		 * The constant SMS_TOKEN.
		 */
		public static final String SMS_TOKEN = "SMS_TOKEN";
		/**
		 * The constant AUTH_TOKEN_KEY.
		 */
		public static final String AUTH_TOKEN_KEY = "AUTH_TOKEN_KEY";
		/**
		 * The constant REGISTER_TOKEN_KEY.
		 */
		public static final String REGISTER_TOKEN_KEY = "REGISTER_TOKEN_KEY";
		/**
		 * The constant RESET_PWD_TOKEN_KEY.
		 */
		public static final String RESET_PWD_TOKEN_KEY = "RESET_PWD_TOKEN_KEY";

		/**
		 * 登录人信息
		 */
		public static final String TOKEN_AUTH_DTO = "TOKEN_AUTH_DTO";

		/**
		 * The class Jwt.
		 */
		public static final class Jwt {
			/**
			 * The constant VIEW.
			 */
			public static final String VIEW = "JWT_VIEW_PRIVATE";
			/**
			 * The constant API.
			 */
			public static final String API = "JWT_API_PRIVATE";
		}
	}

	/**
	 * The class User.
	 */
	public static final class User {
		/**
		 * The constant LOGIN_NAME.
		 */
		public static final String LOGIN_NAME = "LOGIN_NAME";
	}

	/**
	 * The class Cookie.
	 */
	public static final class Cookie {
		/**
		 * 用户名密码加密秘钥
		 */
		public static final String CLOUD_DOMAIN = "cloud.com";
		/**
		 * token 前缀
		 */
		public static final String CLOUD_PATH = "/ ";

	}
}
