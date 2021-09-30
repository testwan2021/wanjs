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
import com.somnus.cloud.provider.api.model.vo.CartProductVo;
import com.somnus.cloud.provider.api.service.OmcCartFeignApi;

import java.util.List;

/**
 * @ClassName: OmcCartFeignHystrix
 * @Description: The class Omc cart feign hystrix.
 * @author Somnus
 * @date 2018年10月16日
 */
@Component
public class OmcCartFeignHystrix implements OmcCartFeignApi {
	@Override
	public Wrapper<Integer> updateCartList(final List<CartProductVo> cartProductVoList) {
		return null;
	}

	@Override
	public Wrapper<Integer> addProduct(final Long userId, final Long productId, final Integer count) {
		return null;
	}

	@Override
	public Wrapper<Integer> updateProduct(final Long userId, final Long productId, final Integer count) {
		return null;
	}

	@Override
	public Wrapper<Integer> deleteProduct(final Long userId, final String productIds) {
		return null;
	}

	@Override
	public Wrapper<Integer> selectOrUnSelect(final Long userId, final Long productId, final Integer checked) {
		return null;
	}

}
