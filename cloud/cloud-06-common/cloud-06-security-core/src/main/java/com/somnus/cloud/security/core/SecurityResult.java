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
package com.somnus.cloud.security.core;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName: SecurityResult
 * @Description: The class Security result.
 * @author Somnus
 * @date 2018年10月12日
 */
@Data
@NoArgsConstructor
public class SecurityResult {

	/**
	 * 成功码.
	 */
	public static final int SUCCESS_CODE = 200;

	/**
	 * 成功信息.
	 */
	public static final String SUCCESS_MESSAGE = "操作成功";

	/**
	 * 错误码.
	 */
	public static final int ERROR_CODE = 500;

	/**
	 * 错误信息.
	 */
	public static final String ERROR_MESSAGE = "内部异常";

	/**
	 * 状态码
	 */
    private Integer code;

	/**
	 * 提示信息
	 */
	private String message;

	/**
	 * 结果
	 */
    private Object result;

	public static SecurityResult ok(Object data) {
        return new SecurityResult(data);
    }

	public static SecurityResult ok() {
        return new SecurityResult(null);
    }

	public static SecurityResult error(String message) {
		return error(message, null);
	}

	public static SecurityResult error(String message, Object data) {
		return new SecurityResult(ERROR_CODE, StringUtils.isEmpty(message) ? ERROR_MESSAGE : message, data);
	}

	public SecurityResult(Integer code, String message, Object result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

	private SecurityResult(Object result) {
        this.code = SUCCESS_CODE;
        this.message = SUCCESS_MESSAGE;
        this.result = result;
    }
}