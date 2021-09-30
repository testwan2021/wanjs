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
package com.somnus.cloud.provider.api.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.provider.api.model.dto.OrderDetailDto;
import com.somnus.cloud.provider.api.service.hystrix.OmcOrderDetailQueryFeignHystrix;
import com.somnus.cloud.security.feign.OAuth2FeignAutoConfiguration;

import java.util.List;

/**
 * @ClassName: OmcOrderDetailQueryFeignApi
 * @Description: The interface Omc order detail query feign api.
 * @author Somnus
 * @date 2018年10月16日
 */
@FeignClient(value = "cloud-provider-omc", configuration = OAuth2FeignAutoConfiguration.class, fallback = OmcOrderDetailQueryFeignHystrix.class)
public interface OmcOrderDetailQueryFeignApi {
	/**
	 * Gets list by order no user id.
	 *
	 * @param orderNo the order no
	 * @param userId  the user id
	 *
	 * @return the list by order no user id
	 */
	@PostMapping(value = "/api/orderDetail/getListByOrderNoUserId/{userId}/{orderNo}")
	Wrapper<List<OrderDetailDto>> getListByOrderNoUserId(@PathVariable("orderNo") String orderNo, @PathVariable("userId") Long userId);
}
