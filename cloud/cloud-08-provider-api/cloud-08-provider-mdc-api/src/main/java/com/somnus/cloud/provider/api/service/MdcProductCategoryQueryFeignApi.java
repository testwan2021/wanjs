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
package com.somnus.cloud.provider.api.service;

import com.github.pagehelper.PageInfo;
import com.somnus.cloud.common.util.annotation.NoNeedAccessAuthentication;
import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.provider.api.model.dto.ProductCategoryDto;
import com.somnus.cloud.provider.api.model.dto.ProductReqDto;
import com.somnus.cloud.provider.api.service.hystrix.MdcProductCategoryQueryFeignHystrix;
import com.somnus.cloud.security.feign.OAuth2FeignAutoConfiguration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @ClassName: MdcProductCategoryQueryFeignApi
 * @Description: The interface Mdc product category query feign api.
 * @author Somnus
 * @date 2018年10月16日
 */
@FeignClient(value = "cloud-provider-mdc", configuration = OAuth2FeignAutoConfiguration.class, fallback = MdcProductCategoryQueryFeignHystrix.class)
public interface MdcProductCategoryQueryFeignApi {

	/**
	 * 查询分类信息.
	 *
	 * @param pid the pid
	 *
	 * @return the product category data
	 */
	@PostMapping(value = "/api/productCategory/getProductCategoryDtoByPid/{pid}")
	@NoNeedAccessAuthentication
	Wrapper<List<ProductCategoryDto>> getProductCategoryData(@PathVariable("pid") Long pid);

	/**
	 * 查询商品列表.
	 *
	 * @param productReqDto the product req dto
	 *
	 * @return the product list
	 */
	@PostMapping(value = "/api/product/getProductList")
	@NoNeedAccessAuthentication
	Wrapper<PageInfo<?>> getProductList(@RequestBody ProductReqDto productReqDto);

}
