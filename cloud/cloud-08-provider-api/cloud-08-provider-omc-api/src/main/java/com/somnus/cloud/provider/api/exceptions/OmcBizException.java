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
package com.somnus.cloud.provider.api.exceptions;

import com.somnus.cloud.common.base.enums.ErrorCodeEnum;
import com.somnus.cloud.common.base.exception.BusinessException;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: OmcBizException
 * @Description: The class Omc biz exception.
 * @author Somnus
 * @date 2018年10月16日
 */
@Slf4j
public class OmcBizException extends BusinessException {

	private static final long serialVersionUID = -6552248511084911254L;

	/**
	 * Instantiates a new Uac rpc exception.
	 */
	public OmcBizException() {
	}

	/**
	 * Instantiates a new Uac rpc exception.
	 *
	 * @param code      the code
	 * @param msgFormat the msg format
	 * @param args      the args
	 */
	public OmcBizException(int code, String msgFormat, Object... args) {
		super(code, msgFormat, args);
		log.info("<== OmcRpcException, code:{}, message:{}", this.code, super.getMessage());
	}

	/**
	 * Instantiates a new Uac rpc exception.
	 *
	 * @param code the code
	 * @param msg  the msg
	 */
	public OmcBizException(int code, String msg) {
		super(code, msg);
		log.info("<== OmcRpcException, code:{}, message:{}", this.code, super.getMessage());
	}

	/**
	 * Instantiates a new Omc rpc exception.
	 *
	 * @param codeEnum the code enum
	 */
	public OmcBizException(ErrorCodeEnum codeEnum) {
		super(codeEnum.getCode(), codeEnum.getMsg());
		log.info("<== OmcRpcException, code:{}, message:{}", this.code, super.getMessage());
	}

	/**
	 * Instantiates a new Omc rpc exception.
	 *
	 * @param codeEnum the code enum
	 * @param args     the args
	 */
	public OmcBizException(ErrorCodeEnum codeEnum, Object... args) {
		super(codeEnum, args);
		log.info("<== OmcRpcException, code:{}, message:{}", this.code, super.getMessage());
	}
}
