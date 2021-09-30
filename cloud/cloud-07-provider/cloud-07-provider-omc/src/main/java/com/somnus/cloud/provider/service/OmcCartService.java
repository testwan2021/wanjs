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

import java.util.List;

import com.somnus.cloud.common.base.dto.LoginAuthDto;
import com.somnus.cloud.comon.core.support.IService;
import com.somnus.cloud.provider.api.model.vo.CartProductVo;
import com.somnus.cloud.provider.api.model.vo.CartVo;
import com.somnus.cloud.provider.api.model.vo.OrderProductVo;
import com.somnus.cloud.provider.model.domain.OmcCart;
import com.somnus.cloud.provider.model.domain.OmcOrderDetail;

/**
 * @ClassName: OmcCartService
 * @Description: The interface Omc cart service.
 * @author Somnus
 * @date 2018年10月17日
 */
public interface OmcCartService extends IService<OmcCart> {
	/**
	 * 获取购物车信息.
	 *
	 * @param userId the user id
	 *
	 * @return the car vo
	 */
	CartVo getCarVo(Long userId);

	/**
	 * Select cart list by user id list.
	 *
	 * @param userId the user id
	 *
	 * @return the list
	 */
	List<OmcCart> selectCartListByUserId(Long userId);

	/**
	 * 更新购物车.
	 *
	 * @param cartProductVoList the cart product vo list
	 *
	 * @return the int
	 */
	int updateCartList(List<CartProductVo> cartProductVoList);

	/**
	 * Save cart.
	 *
	 * @param omcCart the omc cart
	 * @param authDto the auth dto
	 */
	void saveCart(OmcCart omcCart, LoginAuthDto authDto);

	/**
	 * 保存购物车信息.
	 *
	 * @param userId    the user id
	 * @param productId the product id
	 * @param count     the count
	 *
	 * @return the int
	 */
	int saveCart(Long userId, Long productId, int count);

	/**
	 * Gets cart by user id and product id.
	 *
	 * @param userId    the user id
	 * @param productId the product id
	 *
	 * @return the cart by user id and product id
	 */
	OmcCart getCartByUserIdAndProductId(Long userId, Long productId);

	/**
	 * 删除购物车商品信息.
	 *
	 * @param userId     the user id
	 * @param productIds the product ids
	 *
	 * @return the int
	 */
	int deleteProduct(Long userId, String productIds);

	/**
	 * 选中或者反选商品信息.
	 *
	 * @param userId    the user id
	 * @param productId the product id
	 * @param checked   the checked
	 *
	 * @return the int
	 */
	int selectOrUnSelect(Long userId, Long productId, int checked);

	/**
	 * 更新购物车信息.
	 *
	 * @param userId    the user id
	 * @param productId the product id
	 * @param count     the count
	 *
	 * @return the int
	 */
	int updateCart(Long userId, Long productId, int count);

	/**
	 * 获取购物车商品数量.
	 *
	 * @param userId the user id
	 *
	 * @return the or cart product
	 */
	OrderProductVo getOrderCartProduct(Long userId);

	/**
	 * Gets cart order item.
	 *
	 * @param userId   the user id
	 * @param cartList the cart list
	 *
	 * @return the cart order item
	 */
	List<OmcOrderDetail> getCartOrderItem(Long userId, List<OmcCart> cartList);
}
