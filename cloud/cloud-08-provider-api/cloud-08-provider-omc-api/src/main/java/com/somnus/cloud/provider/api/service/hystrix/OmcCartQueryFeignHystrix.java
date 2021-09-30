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
package com.somnus.cloud.provider.api.service.hystrix;

import org.springframework.stereotype.Component;

import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.provider.api.model.vo.CartVo;
import com.somnus.cloud.provider.api.service.OmcCartQueryFeignApi;

/**
 * @ClassName: OmcCartQueryFeignHystrix
 * @Description: The class Omc cart query feign hystrix.
 * @author Somnus
 * @date 2018年10月16日
 */
@Component
public class OmcCartQueryFeignHystrix implements OmcCartQueryFeignApi {

	@Override
	public Wrapper<CartVo> getCartVo(final Long userId) {
		return null;
	}
}
