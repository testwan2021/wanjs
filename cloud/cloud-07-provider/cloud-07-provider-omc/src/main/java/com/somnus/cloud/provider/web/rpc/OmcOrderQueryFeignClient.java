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
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.somnus.cloud.common.util.wrapper.WrapMapper;
import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.comon.core.support.BaseController;
import com.somnus.cloud.provider.api.model.dto.OrderDto;
import com.somnus.cloud.provider.api.service.OmcOrderQueryFeignApi;
import com.somnus.cloud.provider.service.OmcOrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName: OmcOrderQueryFeignClient
 * @Description: The class Omc order query feign client.
 * @author Somnus
 * @date 2018年10月17日
 */
@RefreshScope
@RestController
@Api(value = "API - OmcOrderQueryFeignClient", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OmcOrderQueryFeignClient extends BaseController implements OmcOrderQueryFeignApi {
	
	@Autowired
	private OmcOrderService omcOrderService;

	@Override
	@ApiOperation(httpMethod = "POST", value = "根据订单号查询订单信息")
	public Wrapper<OrderDto> queryByOrderNo(@PathVariable("orderNo") String orderNo) {
		logger.info("selectByOrderNo - 根据订单号查询订单信息. orderNo={}", orderNo);
		OrderDto orderDto = omcOrderService.queryOrderDtoByOrderNo(orderNo);
		return WrapMapper.success(orderDto);
	}

	@Override
	@ApiOperation(httpMethod = "POST", value = "根据订单号查询用户订单信息")
	public Wrapper<OrderDto> queryByUserIdAndOrderNo(@PathVariable("userId") Long userId, @PathVariable("orderNo") String orderNo) {
		logger.info("selectByUserIdAndOrderNo - 根据订单号查询用户订单信息. userId={}, orderNo={}", userId, orderNo);
		OrderDto orderDto = omcOrderService.queryOrderDtoByUserIdAndOrderNo(userId, orderNo);
		return WrapMapper.success(orderDto);
	}
}
