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

/**
 * @ClassName: PtcPayInfo
 * @Description: The class Ptc pay info.
 * @author Somnus
 * @date 2018年10月17日
 */
@EqualsAndHashCode(callSuper = true)
@Table(name = "pc_ptc_pay_info")
@Data
public class PtcPayInfo extends BaseEntity {

	private static final long serialVersionUID = 7949091072343450552L;
	/**
	 * 用户id
	 */
	@Column(name = "user_id")
	private Long userId;

	/**
	 * 订单号
	 */
	@Column(name = "order_no")
	private String orderNo;

	/**
	 * 支付平台:1-支付宝,2-微信
	 */
	@Column(name = "pay_platform")
	private Integer payPlatform;

	/**
	 * 支付宝支付流水号
	 */
	@Column(name = "platform_number")
	private String platformNumber;

	/**
	 * 支付宝支付状态
	 */
	@Column(name = "platform_status")
	private String platformStatus;

}