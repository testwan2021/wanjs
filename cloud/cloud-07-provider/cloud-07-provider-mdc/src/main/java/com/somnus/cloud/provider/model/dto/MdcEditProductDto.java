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
package com.somnus.cloud.provider.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName: MdcEditProductDto
 * @Description: The class Mdc edit category dto.
 * @author Somnus
 * @date 2018年10月17日
 */
@Data
public class MdcEditProductDto implements Serializable {
	private static final long serialVersionUID = 8578699727403591194L;
	/**
	 * 商品ID
	 */
	private Long id;

	/**
	 * 商品名称
	 */
	@NotBlank(message = "商品名称不能为空")
	private String name;

	/**
	 * 商品编码
	 */
	@NotBlank(message = "商品编码不能为空")
	private String productCode;

	/**
	 * 品类Id集合
	 */
	@NotBlank(message = "品类不能为空")
	private List<Long> categoryIdList;

	/**
	 * 商品副标题
	 */
	@NotBlank(message = "品类不能为空")
	private String subtitle;

	/**
	 * 产品主图,url相对地址
	 */
	private String mainImage;

	/**
	 * 价格,单位-元保留两位小数
	 */
	@NotBlank(message = "品类价格不能为空")
	private BigDecimal price;

	/**
	 * 库存数量
	 */
	@NotBlank(message = "品类库存不能为空")
	private Integer stock;

	/**
	 * 商品状态.1-在售 2-下架 3-删除
	 */
	@NotBlank(message = "品类状态不能为空")
	private Integer status;

	/**
	 * 商品图片流水号集合
	 */
	private List<Long> attachmentIdList;

	/**
	 * 商品详情
	 */
	private String detail;
}
