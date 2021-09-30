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
package com.somnus.cloud.provider.web.frontend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.somnus.cloud.common.base.dto.BaseQuery;
import com.somnus.cloud.common.base.dto.LoginAuthDto;
import com.somnus.cloud.common.util.wrapper.WrapMapper;
import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.comon.core.support.BaseController;
import com.somnus.cloud.provider.api.model.dto.OrderPageQuery;
import com.somnus.cloud.provider.api.model.vo.OrderDocVo;
import com.somnus.cloud.provider.api.model.vo.OrderProductVo;
import com.somnus.cloud.provider.api.model.vo.OrderVo;
import com.somnus.cloud.provider.service.OmcCartService;
import com.somnus.cloud.provider.service.OmcOrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName: OmcOrderController
 * @Description: The class Omc order controller.
 * @author Somnus
 * @date 2018年10月17日
 */
@RestController
@RequestMapping(value = "/order")
@Api(value = "WEB - OmcOrderController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OmcOrderController extends BaseController {

	@Autowired
	private OmcOrderService omcOrderService;
	
	@Autowired
	private OmcCartService omcCartService;

	/**
	 * 获取购物车数量.
	 *
	 * @return the cart count
	 */
	@PostMapping(value = "getCartCount")
	public Wrapper<Integer> getCartCount() {
		return WrapMapper.success(0);
	}

	/**
	 * 获取购物车商品数量.
	 *
	 * @return the order cart product
	 */
	@PostMapping("/getOrderCartProduct")
	@ApiOperation(httpMethod = "POST", value = "获取购物车商品数量")
	public Wrapper<?> getOrderCartProduct() {
		logger.info("getOrderCartProduct - 获取购物车商品数量");
		OrderProductVo orderCartProduct = omcCartService.getOrderCartProduct(getLoginAuthDto().getUserId());
		return WrapMapper.success(orderCartProduct);
	}

	/**
	 * 创建订单.
	 *
	 * @param shippingId the shipping id
	 *
	 * @return the wrapper
	 */
	@PostMapping("createOrderDoc/{shippingId}")
	@ApiOperation(httpMethod = "POST", value = "创建订单")
	public Wrapper<?> createOrderDoc(@PathVariable Long shippingId) {
		logger.info("createOrderDoc - 创建订单. shippingId={}", shippingId);
		LoginAuthDto loginAuthDto = getLoginAuthDto();
		logger.info("操作人信息. loginAuthDto={}", loginAuthDto);

		OrderVo orderDoc = omcOrderService.createOrderDoc(loginAuthDto, shippingId);
		return WrapMapper.success(orderDoc);
	}


	/**
	 * 取消订单.
	 *
	 * @param orderNo the order no
	 *
	 * @return the wrapper
	 */
	@PostMapping("cancelOrderDoc/{orderNo}")
	@ApiOperation(httpMethod = "POST", value = "取消订单")
	public Wrapper<?> cancelOrderDoc(@PathVariable String orderNo) {
		logger.info("cancelOrderDoc - 取消订单. orderNo={}", orderNo);
		LoginAuthDto loginAuthDto = getLoginAuthDto();
		logger.info("操作人信息. loginAuthDto={}", loginAuthDto);

		int result = omcOrderService.cancelOrderDoc(loginAuthDto, orderNo);
		return handleResult(result);
	}

	/**
	 * 查询订单详情.
	 *
	 * @param orderNo the order no
	 *
	 * @return the wrapper
	 */
	@PostMapping("queryUserOrderDetailList/{orderNo}")
	@ApiOperation(httpMethod = "POST", value = "查询订单详情")
	public Wrapper<?> queryUserOrderDetailList(@PathVariable String orderNo) {
		logger.info("queryUserOrderDetailList - 查询用户订单明细. orderNo={}", orderNo);

		Long userId = getLoginAuthDto().getUserId();
		logger.info("操作人信息. userId={}", userId);

		OrderVo orderVo = omcOrderService.getOrderDetail(userId, orderNo);
		return WrapMapper.success(orderVo);
	}

	@PostMapping("queryUserOrderDetail/{orderNo}")
	@ApiOperation(httpMethod = "POST", value = "查询订单详情")
	public Wrapper<?> queryUserOrderDetail(@PathVariable String orderNo) {
		logger.info("queryUserOrderDetail - 查询订单明细. orderNo={}", orderNo);

		OrderVo orderVo = omcOrderService.getOrderDetail(orderNo);
		return WrapMapper.success(orderVo);
	}

	/**
	 * Query user order list with page wrapper.
	 *
	 * @param baseQuery the base query
	 *
	 * @return the wrapper
	 */
	@PostMapping("queryUserOrderListWithPage")
	@ApiOperation(httpMethod = "POST", value = "查询用户订单列表")
	public Wrapper<?> queryUserOrderListWithPage(@RequestBody BaseQuery baseQuery) {
		logger.info("queryUserOrderListWithPage - 查询用户订单集合. baseQuery={}", baseQuery);

		Long userId = getLoginAuthDto().getUserId();
		logger.info("操作人信息. userId={}", userId);

		PageInfo<OrderVo> pageInfo = omcOrderService.queryUserOrderListWithPage(userId, baseQuery);
		return WrapMapper.success(pageInfo);
	}

	@PostMapping("queryOrderListWithPage")
	@ApiOperation(httpMethod = "POST", value = "查询用户订单列表")
	public Wrapper<?> queryOrderListWithPage(@RequestBody OrderPageQuery orderPageQuery) {
		logger.info("queryOrderListWithPage - 查询订单集合. orderPageQuery={}", orderPageQuery);
		PageInfo<OrderDocVo> pageInfo = omcOrderService.queryOrderListWithPage(orderPageQuery);
		return WrapMapper.success(pageInfo);
	}

	/**
	 * 查询订单状态.
	 *
	 * @param orderNo the order no
	 *
	 * @return the wrapper
	 */
	@PostMapping("queryOrderPayStatus/{orderNo}")
	@ApiOperation(httpMethod = "POST", value = "查询订单状态")
	public Wrapper<Boolean> queryOrderPayStatus(@PathVariable String orderNo) {
		logger.info("queryOrderPayStatus - 查询订单状态. orderNo={}", orderNo);
		boolean result = omcOrderService.queryOrderPayStatus(getLoginAuthDto().getUserId(), orderNo);
		return WrapMapper.success(result);
	}

}
