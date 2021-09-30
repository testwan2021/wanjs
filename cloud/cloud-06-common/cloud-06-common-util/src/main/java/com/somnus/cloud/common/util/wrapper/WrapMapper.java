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
package com.somnus.cloud.common.util.wrapper;

import org.apache.commons.lang3.StringUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @ClassName: WrapMapper
 * @Description: The class Wrap mapper.
 * @author Somnus
 * @date 2018年10月9日
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WrapMapper {

	/**
	 * Wrap.
	 *
	 * @param <E>     the element type
	 * @param code    the code
	 * @param message the message
	 *
	 * @return the wrapper
	 */
	public static <E> Wrapper<E> wrap(int code, String message, E result) {
		return new Wrapper<>(code, message, result);
	}

	/**
	 * Wrap SUCCESS. code=200
	 *
	 * @param <E> the element type
	 *
	 * @return the wrapper
	 */
	public static <E> Wrapper<E> ok() {
		return new Wrapper<>();
	}

	/**
	 * Ok wrapper.
	 *
	 * @param <E> the type parameter
	 *
	 * @return the wrapper
	 */
	public static <E> Wrapper<E> success(E result) {
		return new Wrapper<>(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
	}

	/**
	 * @param <E> the element type
	 *
	 * @return the wrapper
	 */
	public static <E> Wrapper<E> illegalArgument(E result) {
		return wrap(Wrapper.ILLEGAL_ARGUMENT_CODE, Wrapper.ILLEGAL_ARGUMENT_MESSAGE, result);
	}
	/******************************************************************************************/
	/**
	 * Wrap.
	 *
	 * @param code    the code
	 * @param message the message
	 *
	 * @return the wrapper
	 */
	public static <E> Wrapper<E> wrap(int code, String message) {
		return wrap(code, message, null);
	}

	/**
	 * Wrap.
	 *
	 * @param message the message
	 *
	 * @return the wrapper
	 */
	public static <E> Wrapper<E> fail(String message) {
		return wrap(Wrapper.FAIL_CODE, message);
	}

	/**
	 * Error wrapper.
	 *
	 * @param <E>     the type parameter
	 * @param message the message
	 *
	 * @return the wrapper
	 */
	public static <E> Wrapper<E> error(String message) {
		return wrap(Wrapper.ERROR_CODE, StringUtils.isBlank(message) ? Wrapper.ERROR_MESSAGE : message);
	}
	/******************************************************************************************/
	/**
	 * Wrap WAIT.
	 *
	 * @param <E> the element type
	 *
	 * @return the wrapper
	 */
	public static <E> Wrapper<E> waiting() {
		return wrap(Wrapper.WAIT_CODE, Wrapper.WAIT_MESSAGE);
	}
	/**
	 * Wrap ERROR. code=500
	 *
	 * @param <E> the element type
	 *
	 * @return the wrapper
	 */
	public static <E> Wrapper<E> error() {
		return wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
	}

	/**
	 * Wrap.
	 *
	 * @param <E> the element type
	 * @param e   the e
	 *
	 * @return the wrapper
	 */
	public static <E> Wrapper<E> wrap(Exception e) {
		return new Wrapper<>(Wrapper.ERROR_CODE, e.getMessage());
	}

	/**
	 * Un wrapper.
	 *
	 * @param <E>     the element type
	 * @param wrapper the wrapper
	 *
	 * @return the e
	 */
	public static <E> E unWrap(Wrapper<E> wrapper) {
		return wrapper.getResult();
	}


}
