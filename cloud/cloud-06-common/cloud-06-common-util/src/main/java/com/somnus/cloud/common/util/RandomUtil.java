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
package com.somnus.cloud.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Random;

/**
 * @ClassName: RandomUtil
 * @Description: The class Random util.
 * @author Somnus
 * @date 2018年10月9日
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RandomUtil {

	private static final int MAX_LENGTH = 50;

	/**
	 * 生成一个随机验证码 大小写字母+数字
	 *
	 * @param length the length
	 *
	 * @return 随机验证码 string
	 */
	public static String createComplexCode(int length) {

		if (length > MAX_LENGTH) {
			length = MAX_LENGTH;
		}

		Random r = new Random();

		StringBuilder code = new StringBuilder();

		while (true) {
			if (code.length() == length) {
				break;
			}
			int tmp = r.nextInt(127);
			if (tmp < 33 || tmp == 92 || tmp == 47 || tmp == 34) {
				continue;
			}
			char x = (char) (tmp);
			if (code.toString().indexOf(x) > 0) {
				continue;
			}
			code.append(x);
		}

		return code.toString();
	}

	/**
	 * Create number code string.
	 *
	 * @param length the length
	 *
	 * @return the string
	 */
	public static String createNumberCode(int length) {
		return randomString("0123456789", length);
	}

	private static String randomString(String baseString, int length) {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		if (length < 1) {
			length = 1;
		}

		int baseLength = baseString.length();

		for (int i = 0; i < length; ++i) {
			int number = random.nextInt(baseLength);
			sb.append(baseString.charAt(number));
		}

		return sb.toString();
	}
}
