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
package com.somnus.cloud.provider.web.rpc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;

import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.comon.core.support.BaseController;
import com.somnus.cloud.provider.api.service.UacUserTokenFeignApi;
import com.somnus.cloud.provider.service.UacUserTokenService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName: UacUserTokenFeignClient
 * @Description: 用户token.
 * @author Somnus
 * @date 2018年10月17日
 */
@RestController
@Api(value = "API - UacUserTokenFeignClient", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacUserTokenFeignClient extends BaseController implements UacUserTokenFeignApi {
	
	@Autowired
	private UacUserTokenService uacUserTokenService;

	@Override
	@ApiOperation(httpMethod = "POST", value = "更新token离线状态")
	public Wrapper<Integer> updateTokenOffLine() {
		int result = uacUserTokenService.batchUpdateTokenOffLine();
		return handleResult(result);
	}
}
