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
package com.somnus.cloud.provider.web.mall;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.somnus.cloud.common.base.dto.LoginAuthDto;
import com.somnus.cloud.common.util.PublicUtil;
import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.comon.core.support.BaseController;
import com.somnus.cloud.provider.api.model.constant.OmcApiConstant;
import com.somnus.cloud.provider.api.model.vo.CartProductVo;
import com.somnus.cloud.provider.api.model.vo.CartVo;
import com.somnus.cloud.provider.api.service.OmcCartFeignApi;
import com.somnus.cloud.provider.api.service.OmcCartQueryFeignApi;
import com.somnus.cloud.provider.model.dto.omc.CartListQuery;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName: MallCartController
 * @Description: 购物车管理.
 * @author Somnus
 * @date 2018年10月17日
 */
@RestController
@RequestMapping(value = "/cart", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - MallCartController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MallCartController extends BaseController {

	@Autowired
	private OmcCartQueryFeignApi mallCartQueryFeignApi;
	
	@Autowired
	private OmcCartFeignApi mallCartFeignApi;


	/**
	 * 登录成功合并购物车.
	 *
	 * @param cartListQuery the cart list query
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "mergeUserCart")
	@ApiOperation(httpMethod = "POST", value = "登录成功合并购物车")
	public Wrapper<CartVo> mergeUserCart(@RequestBody CartListQuery cartListQuery) {
		List<CartProductVo> cartProductVoList = cartListQuery.getCartProductVoList();
		LoginAuthDto loginAuthDto = getLoginAuthDto();
		// 1.更新购物车数据
		if (PublicUtil.isNotEmpty(cartProductVoList)) {
			for (CartProductVo cartProductVo : cartProductVoList) {
				cartProductVo.setUserId(loginAuthDto.getUserId());
			}
			mallCartFeignApi.updateCartList(cartProductVoList);
		}
		return mallCartQueryFeignApi.getCartVo(loginAuthDto.getUserId());
	}

	/**
	 * 购物车添加商品.
	 *
	 * @param productId the product id
	 * @param count     the count
	 *
	 * @return the wrapper
	 */
	@PostMapping("addProduct/{productId}/{count}")
	@ApiOperation(httpMethod = "POST", value = "购物车添加商品")
	public Wrapper<?> addProduct(@PathVariable Long productId, @PathVariable Integer count) {
		LoginAuthDto loginAuthDto = getLoginAuthDto();
		Long userId = loginAuthDto.getUserId();
		return mallCartFeignApi.addProduct(userId, productId, count);
	}


	/**
	 * 购物车更新商品.
	 *
	 * @param productId the product id
	 * @param count     the count
	 *
	 * @return the wrapper
	 */
	@ApiOperation(httpMethod = "POST", value = "购物车更新商品")
	@PostMapping("updateProduct/{productId}/{count}")
	public Wrapper<?> updateProduct(@PathVariable Long productId, @PathVariable Integer count) {
		LoginAuthDto loginAuthDto = getLoginAuthDto();
		Long userId = loginAuthDto.getUserId();
		return mallCartFeignApi.updateProduct(userId, productId, count);
	}

	/**
	 * 购物车删除商品.
	 *
	 * @param productIds the product ids
	 *
	 * @return the wrapper
	 */
	@ApiOperation(httpMethod = "POST", value = "购物车删除商品")
	@PostMapping("deleteProduct/{productIds}")
	public Wrapper<?> deleteProduct(@PathVariable String productIds) {
		LoginAuthDto loginAuthDto = getLoginAuthDto();
		Long userId = loginAuthDto.getUserId();
		return mallCartFeignApi.deleteProduct(userId, productIds);
	}


	/**
	 * 购物车全选商品.
	 *
	 * @return the wrapper
	 */
	@PostMapping("selectAllProduct")
	@ApiOperation(httpMethod = "POST", value = "购物车全选商品")
	public Wrapper<?> selectAll() {
		LoginAuthDto loginAuthDto = getLoginAuthDto();
		Long userId = loginAuthDto.getUserId();
		return mallCartFeignApi.selectOrUnSelect(userId, null, OmcApiConstant.Cart.CHECKED);
	}

	/**
	 * 购物车反选全部商品.
	 *
	 * @return the wrapper
	 */
	@PostMapping("unSelectAllProduct")
	@ApiOperation(httpMethod = "POST", value = "购物车反选全部商品")
	public Wrapper<?> unSelectAll() {
		LoginAuthDto loginAuthDto = getLoginAuthDto();
		Long userId = loginAuthDto.getUserId();
		return mallCartFeignApi.selectOrUnSelect(userId, null, OmcApiConstant.Cart.UN_CHECKED);
	}


	/**
	 * 选中商品.
	 *
	 * @param productId the product id
	 *
	 * @return the wrapper
	 */
	@PostMapping("selectProduct/{productId}")
	@ApiOperation(httpMethod = "POST", value = "选中商品")
	public Wrapper<?> select(@PathVariable Long productId) {
		LoginAuthDto loginAuthDto = getLoginAuthDto();
		Long userId = loginAuthDto.getUserId();
		return mallCartFeignApi.selectOrUnSelect(userId, productId, OmcApiConstant.Cart.CHECKED);
	}

	/**
	 * 反选商品.
	 *
	 * @param productId the product id
	 *
	 * @return the wrapper
	 */
	@PostMapping("unSelectProduct/{productId}")
	@ApiOperation(httpMethod = "POST", value = "反选商品")
	public Wrapper<?> unSelect(@PathVariable Long productId) {
		LoginAuthDto loginAuthDto = getLoginAuthDto();
		Long userId = loginAuthDto.getUserId();
		return mallCartFeignApi.selectOrUnSelect(userId, productId, OmcApiConstant.Cart.UN_CHECKED);
	}
}
