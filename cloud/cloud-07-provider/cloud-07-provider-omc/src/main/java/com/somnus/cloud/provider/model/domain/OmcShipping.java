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
 * @ClassName: OmcShipping
 * @Description: The class Omc shipping.
 * @author Somnus
 * @date 2018年10月17日
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "pc_omc_shipping")
public class OmcShipping extends BaseEntity {

	private static final long serialVersionUID = 7337074530378267740L;
	/**
	 * 用户id
	 */
	@Column(name = "user_id")
	private Long userId;

	/**
	 * 收货姓名
	 */
	@Column(name = "receiver_name")
	private String receiverName;

	/**
	 * 收货固定电话
	 */
	@Column(name = "receiver_phone_no")
	private String receiverPhoneNo;

	/**
	 * 收货移动电话
	 */
	@Column(name = "receiver_mobile_no")
	private String receiverMobileNo;

	/**
	 * 收货人省ID
	 */
	@Column(name = "province_id")
	private Long provinceId;

	/**
	 * 省份
	 */
	@Column(name = "province_name")
	private String provinceName;

	/**
	 * 收货人城市ID
	 */
	@Column(name = "city_id")
	private Long cityId;

	/**
	 * 收货人城市名称
	 */
	@Column(name = "city_name")
	private String cityName;

	/**
	 * 区/县
	 */
	@Column(name = "district_name")
	private String districtName;

	/**
	 * 区/县 编码
	 */
	@Column(name = "district_id")
	private Long districtId;

	/**
	 * 街道ID
	 */
	@Column(name = "street_id")
	private Long streetId;

	/**
	 * 接到名称
	 */
	@Column(name = "street_name")
	private String streetName;

	/**
	 * 详细地址
	 */
	@Column(name = "detail_address")
	private String detailAddress;

	/**
	 * 邮编
	 */
	@Column(name = "receiver_zip_code")
	private String receiverZipCode;

	/**
	 * 邮编
	 */
	@Column(name = "default_address")
	private Integer defaultAddress;
}