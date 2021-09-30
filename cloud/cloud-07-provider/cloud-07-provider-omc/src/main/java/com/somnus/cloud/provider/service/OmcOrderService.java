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
package com.somnus.cloud.provider.service;

import com.github.pagehelper.PageInfo;
import com.somnus.cloud.common.base.dto.BaseQuery;
import com.somnus.cloud.common.base.dto.LoginAuthDto;
import com.somnus.cloud.comon.core.support.IService;
import com.somnus.cloud.provider.api.model.dto.OrderDto;
import com.somnus.cloud.provider.api.model.dto.OrderPageQuery;
import com.somnus.cloud.provider.api.model.vo.OrderDocVo;
import com.somnus.cloud.provider.api.model.vo.OrderVo;
import com.somnus.cloud.provider.model.domain.OmcOrder;

/**
 * @ClassName: OmcOrderService
 * @Description: The interface Omc order service.
 * @author Somnus
 * @date 2018年10月17日
 */
public interface OmcOrderService extends IService<OmcOrder> {

	/**
	 * 创建订单.
	 *
	 * @param loginAuthDto the login auth dto
	 * @param shippingId   the shipping id
	 *
	 * @return the order vo
	 */
	OrderVo createOrderDoc(LoginAuthDto loginAuthDto, Long shippingId);

	/**
	 * 取消订单.
	 *
	 * @param loginAuthDto the login auth dto
	 * @param orderNo      the order no
	 *
	 * @return the int
	 */
	int cancelOrderDoc(LoginAuthDto loginAuthDto, String orderNo);

	/**
	 * 查询用户订单列表.
	 *
	 * @param userId    the user id
	 * @param baseQuery the base query
	 *
	 * @return the page info
	 */
	PageInfo<OrderVo> queryUserOrderListWithPage(Long userId, BaseQuery baseQuery);

	/**
	 * 查询订单状态.
	 *
	 * @param userId  the user id
	 * @param orderNo the order no
	 *
	 * @return the boolean
	 */
	boolean queryOrderPayStatus(Long userId, String orderNo);

	/**
	 * Query by order no omc order.
	 *
	 * @param orderNo the order no
	 *
	 * @return the omc order
	 */
	OmcOrder queryByOrderNo(String orderNo);

	/**
	 * Query by user id and order no omc order.
	 *
	 * @param userId  the user id
	 * @param orderNo the order no
	 *
	 * @return the omc order
	 */
	OmcOrder queryByUserIdAndOrderNo(Long userId, String orderNo);

	/**
	 * 根据订单号查询订单信息.
	 *
	 * @param orderNo the order no
	 *
	 * @return the order dto
	 */
	OrderDto queryOrderDtoByOrderNo(String orderNo);

	/**
	 * 根据订单号查询用户订单信息.
	 *
	 * @param userId  the user id
	 * @param orderNo the order no
	 *
	 * @return the order dto
	 */
	OrderDto queryOrderDtoByUserIdAndOrderNo(Long userId, String orderNo);

	/**
	 * 查询用户订单详情.
	 *
	 * @param userId  the user id
	 * @param orderNo the order no
	 *
	 * @return the order detail
	 */
	OrderVo getOrderDetail(Long userId, String orderNo);

	/**
	 * 查询订单详情.
	 *
	 * @param orderNo the order no
	 *
	 * @return the order detail
	 */
	OrderVo getOrderDetail(String orderNo);

	/**
	 * 分页查询订单列表.
	 *
	 * @param orderPageQuery the order page query
	 *
	 * @return the page info
	 */
	PageInfo<OrderDocVo> queryOrderListWithPage(OrderPageQuery orderPageQuery);
}