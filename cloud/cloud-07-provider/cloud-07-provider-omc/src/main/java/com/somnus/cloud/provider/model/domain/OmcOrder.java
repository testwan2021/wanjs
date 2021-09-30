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
package com.somnus.cloud.provider.model.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;

import com.somnus.cloud.comon.core.mybatis.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName: OmcOrder
 * @Description: The class Omc order.
 * @author Somnus
 * @date 2018年10月17日
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "pc_omc_order")
public class OmcOrder extends BaseEntity {

	private static final long serialVersionUID = -8434937678211570532L;
	/**
	 * 订单号
	 */
	@Column(name = "order_no")
	private String orderNo;

	/**
	 * 用户id
	 */
	@Column(name = "user_id")
	private Long userId;

	@Column(name = "shipping_id")
	private Long shippingId;

	/**
	 * 实际付款金额,单位是元,保留两位小数
	 */
	private BigDecimal payment;

	/**
	 * 支付类型,1-在线支付
	 */
	@Column(name = "payment_type")
	private Integer paymentType;

	/**
	 * 运费,单位是元
	 */
	private Integer postage;

	/**
	 * 订单状态:0-已取消-10-未付款, 20-已付款, 40-已发货, 50-交易成功, 60-交易关闭
	 */
	private Integer status;

	/**
	 * 支付时间
	 */
	@Column(name = "payment_time")
	private Date paymentTime;

	/**
	 * 发货时间
	 */
	@Column(name = "send_time")
	private Date sendTime;

	/**
	 * 交易完成时间
	 */
	@Column(name = "end_time")
	private Date endTime;

	/**
	 * 交易关闭时间
	 */
	@Column(name = "close_time")
	private Date closeTime;
}