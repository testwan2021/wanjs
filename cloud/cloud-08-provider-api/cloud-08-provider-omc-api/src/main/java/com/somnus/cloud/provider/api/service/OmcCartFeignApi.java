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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.provider.api.model.vo.CartProductVo;
import com.somnus.cloud.provider.api.service.hystrix.OmcCartFeignHystrix;
import com.somnus.cloud.security.feign.OAuth2FeignAutoConfiguration;

import java.util.List;

/**
 * @ClassName: OmcCartFeignApi
 * @Description: The interface Omc cart feign api.
 * @author Somnus
 * @date 2018年10月16日
 */
@FeignClient(value = "cloud-provider-omc", configuration = OAuth2FeignAutoConfiguration.class, fallback = OmcCartFeignHystrix.class)
public interface OmcCartFeignApi {
	/**
	 * 更新购物车.
	 *
	 * @param cartProductVoList the cart product vo list
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/api/cart/updateCartList")
	Wrapper<Integer> updateCartList(@RequestBody List<CartProductVo> cartProductVoList);

	/**
	 * 添加购物车.
	 *
	 * @param userId    the user id
	 * @param productId the product id
	 * @param count     the count
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/api/cart/addProduct")
	Wrapper<Integer> addProduct(@RequestParam("userId") Long userId, @RequestParam("productId") Long productId, @RequestParam("count") Integer count);

	/**
	 * 更新商品信息.
	 *
	 * @param userId    the user id
	 * @param productId the product id
	 * @param count     the count
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/api/cart/updateProduct")
	Wrapper<Integer> updateProduct(@RequestParam("userId") Long userId, @RequestParam("productId") Long productId, @RequestParam("count") Integer count);

	/**
	 * 删除商品信息.
	 *
	 * @param userId     the user id
	 * @param productIds the product ids
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/api/cart/deleteProduct")
	Wrapper<Integer> deleteProduct(@RequestParam("userId") Long userId, @RequestParam("productIds") String productIds);

	/**
	 * 选中和反选商品.
	 *
	 * @param userId    the user id
	 * @param productId the product id
	 * @param checked   the checked
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/api/cart/selectOrUnSelect")
	Wrapper<Integer> selectOrUnSelect(@RequestParam(name = "userId") Long userId, @RequestParam(name = "productId", required = false) Long productId, @RequestParam(name = "checked") Integer checked);
}

