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
import com.somnus.cloud.provider.api.model.dto.ProductDto;
import com.somnus.cloud.provider.api.model.vo.ProductDetailVo;
import com.somnus.cloud.provider.model.domain.MdcProduct;
import com.somnus.cloud.provider.model.dto.MdcEditProductDto;
import com.somnus.cloud.provider.model.vo.ProductVo;

/**
 * @ClassName: MdcProductService
 * @Description: The interface Mdc product service.
 * @author Somnus
 * @date 2018年10月17日
 */
public interface MdcProductService extends IService<MdcProduct> {

	/**
	 * Select by name and category ids list.
	 *
	 * @param productName    the product name
	 * @param categoryIdList the category id list
	 * @param orderBy        the order by
	 *
	 * @return the list
	 */
	List<MdcProduct> selectByNameAndCategoryIds(String productName, List<Long> categoryIdList, String orderBy);

	/**
	 * 根据商品ID查询商品详细信息.
	 *
	 * @param productId the product id
	 *
	 * @return the product detail
	 */
	ProductDetailVo getProductDetail(Long productId);

	/**
	 * 更新商品库存.
	 *
	 * @param productDto the product dto
	 *
	 * @return the int
	 */
	int updateProductStockById(ProductDto productDto);

	/**
	 * 查询商品集合.
	 *
	 * @param mdcProduct the mdc product
	 *
	 * @return the list
	 */
	List<ProductVo> queryProductListWithPage(MdcProduct mdcProduct);

	/**
	 * 新增商品信息.
	 *
	 * @param mdcEditProductDto the mdc edit product dto
	 * @param loginAuthDto      the login auth dto
	 */
	void saveProduct(MdcEditProductDto mdcEditProductDto, LoginAuthDto loginAuthDto);

	/**
	 * 删除商品信息.
	 *
	 * @param id the id
	 */
	void deleteProductById(Long id);

	/**
	 * Gets product vo.
	 *
	 * @param id the id
	 *
	 * @return the product vo
	 */
	ProductVo getProductVo(Long id);

	/**
	 * Gets main image.
	 *
	 * @param productId the product id
	 *
	 * @return the main image
	 */
	String getMainImage(Long productId);
}
