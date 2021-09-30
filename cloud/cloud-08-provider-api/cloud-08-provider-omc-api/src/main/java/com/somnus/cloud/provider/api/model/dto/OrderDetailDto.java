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
package com.somnus.cloud.provider.api.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName: OrderDetailDto
 * @Description: The class Order detail dto.
 * @author Somnus
 * @date 2018年10月16日
 */
@Data
public class OrderDetailDto implements Serializable {

	private static final long serialVersionUID = 4423448618530228839L;
	private Long id;
	/**
	 * 订单明细序列号
	 */
	private String detailNo;

	private Long userId;

	private String orderNo;

	private Long productId;

	/**
	 * 商品名称
	 */
	private String productName;

	/**
	 * 商品图片地址
	 */
	private String productImage;

	/**
	 * 生成订单时的商品单价, 单位是元,保留两位小数
	 */
	private BigDecimal currentUnitPrice;

	/**
	 * 商品数量
	 */
	private Integer quantity;

	/**
	 * 商品总价,单位是元,保留两位小数
	 */
	private BigDecimal totalPrice;
}
