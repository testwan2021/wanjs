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
package com.somnus.cloud.provider.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.somnus.cloud.common.base.enums.ErrorCodeEnum;
import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.provider.api.exceptions.MdcBizException;
import com.somnus.cloud.provider.api.model.dto.ProductDto;
import com.somnus.cloud.provider.api.model.vo.ProductDetailVo;
import com.somnus.cloud.provider.api.service.MdcProductFeignApi;
import com.somnus.cloud.provider.api.service.MdcProductQueryFeignApi;
import com.somnus.cloud.provider.service.MdcProductService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: MdcProductServiceImpl
 * @Description: The class Mdc product service.
 * @author Somnus
 * @date 2018年10月17日
 */
@Slf4j
@Service
public class MdcProductServiceImpl implements MdcProductService {
	@Autowired
	private MdcProductQueryFeignApi mdcProductQueryFeignApi;
	
	@Autowired
	private MdcProductFeignApi mdcProductFeignApi;

	@Override
	public ProductDto selectById(Long productId) {
		log.info("查询商品信息. productId={}", productId);
		Preconditions.checkArgument(productId != null, ErrorCodeEnum.MDC10021021.getMsg());
		Wrapper<ProductDto> productDtoWrapper = mdcProductQueryFeignApi.selectById(productId);

		if (productDtoWrapper == null) {
			throw new MdcBizException(ErrorCodeEnum.MDC10021003);
		}
		if (productDtoWrapper.error()) {
			throw new MdcBizException(ErrorCodeEnum.MDC10021004, productId);
		}
		return productDtoWrapper.getResult();

	}

	@Override
	public ProductDetailVo getProductDetail(Long productId) {
		log.info("获取商品详情. productId={}", productId);
		Preconditions.checkArgument(productId != null, ErrorCodeEnum.MDC10021021.getMsg());

		Wrapper<ProductDetailVo> wrapper = mdcProductQueryFeignApi.getProductDetail(productId);

		if (wrapper == null) {
			throw new MdcBizException(ErrorCodeEnum.GL99990002);
		}
		if (wrapper.error()) {
			throw new MdcBizException(ErrorCodeEnum.MDC10021004, productId);
		}
		return wrapper.getResult();
	}

	@Override
	public int updateProductStockById(ProductDto productDto) {
		Preconditions.checkArgument(productDto.getId() != null, ErrorCodeEnum.MDC10021021.getMsg());
		Wrapper<Integer> wrapper = mdcProductFeignApi.updateProductStockById(productDto);
		if (wrapper == null) {
			throw new MdcBizException(ErrorCodeEnum.GL99990002);
		}
		if (wrapper.error()) {
			throw new MdcBizException(ErrorCodeEnum.MDC10021022, productDto.getId());
		}
		return wrapper.getResult();
	}

	@Override
	public String getMainImage(final Long productId) {
		Wrapper<String> wrapper = mdcProductFeignApi.getMainImage(productId);
		if (wrapper == null) {
			throw new MdcBizException(ErrorCodeEnum.GL99990002);
		}
		return wrapper.getResult();
	}
}
