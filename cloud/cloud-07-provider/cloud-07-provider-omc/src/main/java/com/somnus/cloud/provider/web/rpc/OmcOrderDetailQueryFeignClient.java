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

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.somnus.cloud.common.util.wrapper.WrapMapper;
import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.comon.core.support.BaseController;
import com.somnus.cloud.provider.api.model.dto.OrderDetailDto;
import com.somnus.cloud.provider.api.service.OmcOrderDetailQueryFeignApi;
import com.somnus.cloud.provider.model.domain.OmcOrderDetail;
import com.somnus.cloud.provider.service.OmcOrderDetailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName: OmcOrderDetailQueryFeignClient
 * @Description: The class Omc order detail query feign client.
 * @author Somnus
 * @date 2018年10月17日
 */
@RefreshScope
@RestController
@Api(value = "API - MallCartQueryFeignClient", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OmcOrderDetailQueryFeignClient extends BaseController implements OmcOrderDetailQueryFeignApi {
	
	@Autowired
	private OmcOrderDetailService omcOrderDetailService;

	@Override
	@ApiOperation(httpMethod = "POST", value = "获取用户订单详情")
	public Wrapper<List<OrderDetailDto>> getListByOrderNoUserId(@PathVariable("orderNo") String orderNo, @PathVariable("userId") Long userId) {
		logger.info("getListByOrderNoUserId - 获取用户订单详情. orderNo={}, userId={}", orderNo, userId);
		List<OmcOrderDetail> list = omcOrderDetailService.getListByOrderNoUserId(orderNo, userId);

		List<OrderDetailDto> orderDetailDtoList = Lists.newArrayList();

		for (OmcOrderDetail orderDetail : list) {

			OrderDetailDto orderDetailDto = new OrderDetailDto();
			BeanUtils.copyProperties(orderDetail, orderDetailDto);
			orderDetailDtoList.add(orderDetailDto);
		}

		return WrapMapper.success(orderDetailDtoList);
	}
}
