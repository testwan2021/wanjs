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
package com.somnus.cloud.provider.web.frontend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.somnus.cloud.common.util.wrapper.WrapMapper;
import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.comon.core.annotation.LogAnnotation;
import com.somnus.cloud.comon.core.support.BaseController;
import com.somnus.cloud.provider.model.domain.MdcProduct;
import com.somnus.cloud.provider.model.dto.MdcEditProductDto;
import com.somnus.cloud.provider.model.vo.ProductVo;
import com.somnus.cloud.provider.service.MdcProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName: MdcProductMainController
 * @Description: The class Mdc dict main controller.
 * @author Somnus
 * @date 2018年10月17日
 */
@RestController
@RequestMapping(value = "/product", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - MdcProductMainController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MdcProductMainController extends BaseController {

	@Autowired
	private MdcProductService mdcProductService;

	/**
	 * 分页查询商品列表.
	 *
	 * @param mdcProduct the mdc product
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/queryProductListWithPage")
	@ApiOperation(httpMethod = "POST", value = "分页查询商品列表")
	public Wrapper<PageInfo<ProductVo>> queryProductListWithPage(@ApiParam(name = "mdcProduct", value = "商品信息") @RequestBody MdcProduct mdcProduct) {

		logger.info("分页查询商品列表, mdcProduct={}", mdcProduct);
		PageHelper.startPage(mdcProduct.getPageNum(), mdcProduct.getPageSize());
		mdcProduct.setOrderBy("update_time desc");
		List<ProductVo> roleVoList = mdcProductService.queryProductListWithPage(mdcProduct);
		return WrapMapper.success(new PageInfo<>(roleVoList));
	}

	/**
	 * 商品详情.
	 */
	@PostMapping(value = "/getById/{id}")
	@ApiOperation(httpMethod = "POST", value = "分页查询商品列表")
	public Wrapper<ProductVo> getById(@PathVariable Long id) {
		logger.info("查询商品详情, id={}", id);
		ProductVo productVo = mdcProductService.getProductVo(id);
		return WrapMapper.success(productVo);
	}

	@PostMapping(value = "/save")
	@ApiOperation(httpMethod = "POST", value = "编辑商品")
	@LogAnnotation
	public Wrapper<?> saveCategory(@RequestBody MdcEditProductDto mdcCategoryAddDto) {
		logger.info("编辑商品. mdcCategoryAddDto={}", mdcCategoryAddDto);
		mdcProductService.saveProduct(mdcCategoryAddDto, getLoginAuthDto());
		return WrapMapper.ok();
	}

	@PostMapping(value = "/deleteProductById/{id}")
	@ApiOperation(httpMethod = "POST", value = "删除商品信息")
	@LogAnnotation
	public Wrapper<ProductVo> deleteProductById(@PathVariable Long id) {
		logger.info("删除商品信息, id={}", id);
		mdcProductService.deleteProductById(id);
		return WrapMapper.ok();
	}
}
