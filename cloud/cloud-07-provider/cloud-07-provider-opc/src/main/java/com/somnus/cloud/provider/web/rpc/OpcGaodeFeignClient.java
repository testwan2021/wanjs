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

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.somnus.cloud.common.util.wrapper.WrapMapper;
import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.comon.core.support.BaseController;
import com.somnus.cloud.provider.api.model.dto.gaode.GaodeLocation;
import com.somnus.cloud.provider.api.service.OpcGaodeFeignApi;
import com.somnus.cloud.provider.utils.GaoDeUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName: OpcGaodeFeignClient
 * @Description: The class Opc attachment feign client.
 * @author Somnus
 * @date 2018年10月17日
 */
@RestController
@Api(value = "API - OpcGaodeFeignClient", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OpcGaodeFeignClient extends BaseController implements OpcGaodeFeignApi {

	@Override
	@ApiOperation(httpMethod = "POST", value = "根据IP获取位置信息")
	public Wrapper<GaodeLocation> getLocationByIpAddr(@RequestParam("ipAddr") String ipAddr) {
		String temp = "127.0.";
		String temp2 = "192.168.";
		if (ipAddr.startsWith(temp) || ipAddr.startsWith(temp2)) {
			ipAddr = "111.199.188.14";
		}
		return WrapMapper.success(GaoDeUtil.getCityByIpAddr(ipAddr));
	}
}
