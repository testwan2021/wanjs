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
package com.somnus.cloud.provider.web.mall;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.comon.core.support.BaseController;
import com.somnus.cloud.provider.api.model.dto.ProductCategoryDto;
import com.somnus.cloud.provider.api.model.dto.ProductReqDto;
import com.somnus.cloud.provider.api.model.vo.ProductDetailVo;
import com.somnus.cloud.provider.api.service.MdcProductCategoryQueryFeignApi;
import com.somnus.cloud.provider.api.service.MdcProductQueryFeignApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName: MallAuthRestController
 * @Description: The class Mall auth rest controller.
 * @author Somnus
 * @date 2018年10月17日
 */
@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - MallAuthRestController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MallAuthRestController extends BaseController {

	@Autowired
	private MdcProductQueryFeignApi mdcProductQueryFeignApi;

	@Autowired
	private MdcProductCategoryQueryFeignApi mdcProductCategoryQueryFeignApi;

	/**
	 * 查询商品列表.
	 *
	 * @param productReqDto the product req dto
	 *
	 * @return the product category dto by pid
	 */
	@PostMapping(value = "/product/queryProductList")
	@ApiOperation(httpMethod = "POST", value = "查询商品列表")
	public Wrapper<PageInfo<?>> queryProductList(@RequestBody ProductReqDto productReqDto) {
		logger.info("getProductCategoryDtoByPid - 查询分类信息 productReqDto={}", productReqDto);
		return mdcProductCategoryQueryFeignApi.getProductList(productReqDto);
	}

	/**
	 * 查询商品详情信息.
	 *
	 * @param productId the product id
	 *
	 * @return the wrapper
	 */
	@GetMapping(value = "/product/queryProductDetail/{productId}")
	@ApiOperation(httpMethod = "POST", value = "查询商品详情信息")
	public Wrapper<ProductDetailVo> queryProductDetail(@PathVariable Long productId) {
		logger.info("getProductCategoryDtoByPid - 查询商品详情信息 productId={}", productId);
		return mdcProductQueryFeignApi.getProductDetail(productId);
	}

	/**
	 * 查询分类信息.
	 *
	 * @param pid the pid
	 *
	 * @return the product category dto by pid
	 */
	@GetMapping(value = "/category/getProductCategoryDtoByPid/{pid}")
	public Wrapper<List<ProductCategoryDto>> getProductCategoryDtoByPid(@PathVariable Long pid) {
		logger.info("getProductCategoryDtoByPid - 查询分类信息 pid={}", pid);
		Wrapper<List<ProductCategoryDto>> productCategoryData = mdcProductCategoryQueryFeignApi.getProductCategoryData(pid);
		logger.info("productCategoryData={}", productCategoryData);
		return productCategoryData;
	}
}
