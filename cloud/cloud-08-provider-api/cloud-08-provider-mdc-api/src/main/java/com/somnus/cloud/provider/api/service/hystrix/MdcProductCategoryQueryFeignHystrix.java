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
package com.somnus.cloud.provider.api.service.hystrix;

import com.github.pagehelper.PageInfo;
import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.provider.api.model.dto.ProductCategoryDto;
import com.somnus.cloud.provider.api.model.dto.ProductReqDto;
import com.somnus.cloud.provider.api.service.MdcProductCategoryQueryFeignApi;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName: MdcProductCategoryQueryFeignHystrix
 * @Description: The class Mdc product category query feign hystrix.
 * @author Somnus
 * @date 2018年10月16日
 */
@Component
public class MdcProductCategoryQueryFeignHystrix implements MdcProductCategoryQueryFeignApi {
	@Override
	public Wrapper<List<ProductCategoryDto>> getProductCategoryData(final Long pid) {
		return null;
	}

	@Override
	public Wrapper<PageInfo<?>> getProductList(final ProductReqDto productReqDto) {
		return null;
	}
}
