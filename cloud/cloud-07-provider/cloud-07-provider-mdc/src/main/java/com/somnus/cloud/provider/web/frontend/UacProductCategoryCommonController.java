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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.somnus.cloud.common.util.wrapper.WrapMapper;
import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.comon.core.support.BaseController;
import com.somnus.cloud.provider.model.domain.MdcProductCategory;
import com.somnus.cloud.provider.model.dto.MdcCategoryCheckNameDto;
import com.somnus.cloud.provider.service.MdcProductCategoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import tk.mybatis.mapper.entity.Example;

/**
 * @ClassName: UacProductCategoryCommonController
 * @Description: The class Uac dict common controller.
 * @author Somnus
 * @date 2018年10月17日
 */
@RestController
@RequestMapping(value = "/dict", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - UacDictCommonController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacProductCategoryCommonController extends BaseController {

	@Autowired
	private MdcProductCategoryService mdcProductCategoryService;

	/**
	 * 检测数据分类名称是否已存在.
	 *
	 * @param categoryCheckNameDto the category check name dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkCategoryName")
	@ApiOperation(httpMethod = "POST", value = "检测数据分类名称是否已存在")
	public Wrapper<Boolean> checkCategoryName(@RequestBody MdcCategoryCheckNameDto categoryCheckNameDto) {
		logger.info("检测数据分类名称是否已存在 categoryCheckNameDto={}", categoryCheckNameDto);

		Long id = categoryCheckNameDto.getCategoryId();
		String categoryName = categoryCheckNameDto.getCategoryName();

		Example example = new Example(MdcProductCategory.class);
		Example.Criteria criteria = example.createCriteria();

		if (id != null) {
			criteria.andNotEqualTo("id", id);
		}
		criteria.andEqualTo("name", categoryName);

		int result = mdcProductCategoryService.selectCountByExample(example);
		return WrapMapper.success(result < 1);
	}
}
