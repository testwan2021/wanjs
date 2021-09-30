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

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(value=Include.NON_NULL)
public class Wrapper<T> implements Serializable {

	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = 4893280118017319089L;

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
	public static final int FAIL_CODE = 300;

	/**
	 * 业务异常信息.
	 */
	public static final String FAIL_MESSAGE = "业务异常";

	/**
	 * 错误码.
	 */
	public static final int ERROR_CODE = 500;

	/**
	 * 系统异常信息.
	 */
	public static final String ERROR_MESSAGE = "内部异常";

	/**
	 * 错误码：参数非法
	 */
	public static final int ILLEGAL_ARGUMENT_CODE = 100;

	/**
	 * 错误信息：参数非法
	 */
	public static final String ILLEGAL_ARGUMENT_MESSAGE = "参数非法";

	/**
	 * 错误码.
	 */
	public static final int WAIT_CODE = 400;

	/**
	 * 错误信息.
	 */
	public static final String WAIT_MESSAGE = "网络故障";

	/**
	 * 编号.
	 */
	@Builder.Default
	private int code = Wrapper.SUCCESS_CODE;

	/**
	 * 信息.
	 */
	@Builder.Default
	private String message = Wrapper.SUCCESS_MESSAGE;

	/**
	 * 结果数据
	 */
	private T result;

	/**
	 * Instantiates a new wrapper. default code=200
	 */
	Wrapper() {
		this(SUCCESS_CODE, SUCCESS_MESSAGE);
	}

	/**
	 * Instantiates a new wrapper.
	 *
	 * @param code    the code
	 * @param message the message
	 */
	Wrapper(int code, String message) {
		this(code, message, null);
	}

	/**
	 * Instantiates a new wrapper.
	 *
	 * @param code    the code
	 * @param message the message
	 * @param result  the result
	 */
	Wrapper(int code, String message, T result) {
		super();
		this.code(code).message(message).result(result);
	}

	/**
	 * Sets the 编号 , 返回自身的引用.
	 *
	 * @param code the new 编号
	 *
	 * @return the wrapper
	 */
	private Wrapper<T> code(int code) {
		this.code = code;
		return this;
	}

	/**
	 * Sets the 信息 , 返回自身的引用.
	 *
	 * @param message the new 信息
	 *
	 * @return the wrapper
	 */
	private Wrapper<T> message(String message) {
		this.message = message;
		return this;
	}

	/**
	 * Sets the 结果数据 , 返回自身的引用.
	 *
	 * @param result the new 结果数据
	 *
	 * @return the wrapper
	 */
	public Wrapper<T> result(T result) {
		this.result = result;
		return this;
	}

	/**
	 * 判断是否成功： 依据 Wrapper.SUCCESS_CODE == this.code
	 *
	 * @return code =200,true;否则 false.
	 */
	@JsonIgnore
	public boolean success() {
		return Wrapper.SUCCESS_CODE == this.code;
	}

	/**
	 * 判断是否成功： 依据 Wrapper.SUCCESS_CODE != this.code
	 *
	 * @return code !=200,true;否则 false.
	 */
	@JsonIgnore
	public boolean error() {
		return !success();
	}

}
