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
package com.somnus.cloud.provider.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;

import com.somnus.cloud.common.base.dto.BaseVo;
import com.somnus.cloud.provider.api.model.dto.oss.ElementImgUrlDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName: ProductVo
 * @Description: The class Product vo.
 * @author Somnus
 * @date 2018年10月17日
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProductVo extends BaseVo {
	private static final long serialVersionUID = 5719854343599455196L;

	/**
	 * 商品名称
	 */
	private String name;

	/**
	 * 商品编码
	 */
	private String productCode;

	/**
	 * 商品副标题
	 */
	private String subtitle;

	/**
	 * 产品主图,url相对地址
	 */
	@Column(name = "main_image")
	private String mainImage;

	/**
	 * 价格,单位-元保留两位小数
	 */
	private BigDecimal price;

	/**
	 * 库存数量
	 */
	private Integer stock;

	/**
	 * 商品状态.1-在售 2-下架 3-删除
	 */
	private Integer status;

	/**
	 * 图片地址,json格式,扩展用
	 */
	@Column(name = "sub_images")
	private String subImages;

	/**
	 * 商品详情
	 */
	private String detail;

	/**
	 * 分类ID
	 */
	private List<Long> categoryIdList;

	/**
	 * 图片集合
	 */
	private List<ElementImgUrlDto> imgUrlList;
}
