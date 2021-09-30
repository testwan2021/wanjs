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
package com.somnus.cloud.common.zk.registry.exception;

/**
 * @ClassName: RegException
 * @Description: 注册中心异常.
 * @author Somnus
 * @date 2018年9月27日
 */
public final class RegException extends RuntimeException {

	private static final long serialVersionUID = -6417179023552012152L;

	/**
	 * Instantiates a new Reg exception.
	 *
	 * @param errorMessage the error message
	 * @param args         the args
	 */
	public RegException(final String errorMessage, final Object... args) {
		super(String.format(errorMessage, args));
	}

	/**
	 * Instantiates a new Reg exception.
	 *
	 * @param cause the cause
	 */
	public RegException(final Exception cause) {
		super(cause);
	}
}
