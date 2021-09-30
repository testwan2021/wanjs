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

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName: ProductDto
 * @Description: The class Product dto.
 * @author Somnus
 * @date 2018年10月16日
 */
@Data
@ApiModel
public class ProductDto implements Serializable {
	private static final long serialVersionUID = 6932649538854879183L;
	@ApiModelProperty("货品ID")
	private Long id;
	@ApiModelProperty("分类ID")
	private Long categoryId;
	private String name;
	@ApiModelProperty("子标题")
	private String subtitle;
	@ApiModelProperty("首图")
	private String mainImage;
	@ApiModelProperty("价格")
	private BigDecimal price;
	@ApiModelProperty("状态")
	private Integer status;
	@ApiModelProperty("图片服务器前缀")
	private String imageHost;
	@ApiModelProperty("库存数量")
	private Integer stock;
	@ApiModelProperty("商品序列号")
	private String productCode;
	@ApiModelProperty("变化的库存数量")
	private Integer changeStock;
}
