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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.comon.core.support.BaseController;
import com.somnus.cloud.provider.api.model.vo.CartProductVo;
import com.somnus.cloud.provider.api.service.OmcCartFeignApi;
import com.somnus.cloud.provider.service.OmcCartService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName: MallCartFeignClient
 * @Description: The class Mall cart feign client.
 * @author Somnus
 * @date 2018年10月17日
 */
@RefreshScope
@RestController
@Api(value = "API - MallCartFeignClient", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MallCartFeignClient extends BaseController implements OmcCartFeignApi {

	@Autowired
	private OmcCartService omcCartService;

	@Override
	@ApiOperation(httpMethod = "POST", value = "更新购物车")
	public Wrapper<Integer> updateCartList(@RequestBody List<CartProductVo> cartProductVoList) {
		logger.info("updateCartList - 更新购物车. cartProductVoList={}", cartProductVoList);
		int result = omcCartService.updateCartList(cartProductVoList);
		return handleResult(result);
	}

	@Override
	@ApiOperation(httpMethod = "POST", value = "保存购物车信息")
	public Wrapper<Integer> addProduct(@RequestParam("userId") Long userId, @RequestParam("productId") Long productId, @RequestParam(value = "count") Integer count) {
		logger.info("updateCartList - 保存购物车信息. productId={}, count={}", productId, count);
		int result = omcCartService.saveCart(userId, productId, count);
		return handleResult(result);
	}

	@Override
	@ApiOperation(httpMethod = "POST", value = "更新购物车信息")
	public Wrapper<Integer> updateProduct(@RequestParam("userId") Long userId, @RequestParam("productId") Long productId, @RequestParam("count") Integer count) {
		logger.info("updateProduct - 更新购物车信息. productId={}, count={}", productId, count);
		int result = omcCartService.updateCart(userId, productId, count);
		return handleResult(result);
	}

	@Override
	@ApiOperation(httpMethod = "POST", value = "删除购物车商品信息")
	public Wrapper<Integer> deleteProduct(@RequestParam("userId") Long userId, @RequestParam("productIds") String productIds) {
		logger.info("deleteProduct - 删除购物车商品信息. productIds={}, userId={}", productIds, userId);
		int result = omcCartService.deleteProduct(userId, productIds);
		return handleResult(result);
	}

	@Override
	@ApiOperation(httpMethod = "POST", value = "选中或者反选商品信息")
	public Wrapper<Integer> selectOrUnSelect(@RequestParam(name = "userId") Long userId, @RequestParam(name = "productId", required = false) Long productId, @RequestParam(name = "checked") Integer checked) {
		logger.info("selectOrUnSelect - 选中或者反选商品信息. productId={}, userId={}, checked={}", productId, userId, checked);
		int result = omcCartService.selectOrUnSelect(userId, productId, checked);
		return handleResult(result);
	}
}
