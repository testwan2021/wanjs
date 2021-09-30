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

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.comon.core.support.BaseController;
import com.somnus.cloud.provider.api.model.dto.OrderDto;
import com.somnus.cloud.provider.api.service.OmcOrderFeignApi;
import com.somnus.cloud.provider.model.domain.OmcOrder;
import com.somnus.cloud.provider.service.OmcOrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName: OmcOrderFeignClient
 * @Description: The class Omc order feign client.
 * @author Somnus
 * @date 2018年10月17日
 */
@RefreshScope
@RestController
@Api(value = "API - OmcOrderFeignClient", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OmcOrderFeignClient extends BaseController implements OmcOrderFeignApi {
	
	@Autowired
	private OmcOrderService omcOrderService;

	@Override
	@ApiOperation(httpMethod = "POST", value = "更新订单信息")
	public Wrapper<Integer> updateOrderById(@RequestBody OrderDto orderDto) {
		logger.info("updateOrderById - 更新订单信息. orderDto={}", orderDto);
		ModelMapper modelMapper = new ModelMapper();
		OmcOrder omcOrder = modelMapper.map(orderDto, OmcOrder.class);
		int updateResult = omcOrderService.update(omcOrder);
		return handleResult(updateResult);

	}
}
