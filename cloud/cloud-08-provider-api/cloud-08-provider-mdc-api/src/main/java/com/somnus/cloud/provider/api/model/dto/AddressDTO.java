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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 * @ClassName: AddressDTO
 * @Description: The class Address dto.
 * @author Somnus
 * @date 2018年10月16日
 */
@Data
@ApiModel
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AddressDTO implements Serializable {
	private static final long serialVersionUID = -154171216711236047L;
	/**
	 * ID
	 */
	@ApiModelProperty(value = "ID")
	private Long id;

	/**
	 * 地址名称
	 */
	@ApiModelProperty(value = "地址名称")
	private String name;

	/**
	 * 父ID
	 */
	@ApiModelProperty(value = "父ID")
	private Long pid;

	/**
	 * 城市编码
	 */
	@ApiModelProperty(value = "城市编码")
	private String cityCode;

	/**
	 * 级别（省市区县）
	 */
	@ApiModelProperty(value = "级别（省市区县）")
	private Integer level;

	/**
	 * 区域编码
	 */
	@ApiModelProperty(value = "区域编码")
	private String adCode;

	/**
	 * 行政区边界坐标点
	 */
	@ApiModelProperty(value = "行政区边界坐标点")
	private String polyline;

	/**
	 * 城市中心点
	 */
	@ApiModelProperty(value = "城市中心点")
	private String center;
}
