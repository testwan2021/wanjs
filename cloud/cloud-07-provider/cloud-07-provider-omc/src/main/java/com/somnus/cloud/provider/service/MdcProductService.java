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

import com.somnus.cloud.provider.api.model.dto.ProductDto;
import com.somnus.cloud.provider.api.model.vo.ProductDetailVo;

/**
 * @ClassName: MdcProductService
 * @Description: The interface Mdc product service.
 * @author Somnus
 * @date 2018年10月17日
 */
public interface MdcProductService {
	/**
	 * Select by id product dto.
	 *
	 * @param productId the product id
	 *
	 * @return the product dto
	 */
	ProductDto selectById(Long productId);

	/**
	 * Gets product detail.
	 *
	 * @param productId the product id
	 *
	 * @return the product detail
	 */
	ProductDetailVo getProductDetail(Long productId);

	/**
	 * Update product stock by id int.
	 *
	 * @param productDto the product dto
	 *
	 * @return the int
	 */
	int updateProductStockById(ProductDto productDto);

	/**
	 * Gets main image.
	 *
	 * @param productId the product id
	 *
	 * @return the main image
	 */
	String getMainImage(Long productId);
}
