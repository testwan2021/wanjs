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
import com.somnus.cloud.provider.model.domain.MdcDict;
import com.somnus.cloud.provider.model.dto.MdcDictCheckCodeDto;
import com.somnus.cloud.provider.model.dto.MdcDictCheckNameDto;
import com.somnus.cloud.provider.service.MdcDictService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import tk.mybatis.mapper.entity.Example;

/**
 * @ClassName: UacDictCommonController
 * @Description: The class Uac dict common controller.
 * @author Somnus
 * @date 2018年10月17日
 */
@RestController
@RequestMapping(value = "/dict", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - UacDictCommonController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacDictCommonController extends BaseController {

	@Autowired
	private MdcDictService mdcDictService;

	/**
	 * 检测菜单编码是否已存在
	 *
	 * @param mdcDictCheckCodeDto the mdc dict check code dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkDictCode")
	@ApiOperation(httpMethod = "POST", value = "检测数据字典编码是否已存在")
	public Wrapper<Boolean> checkDictCode(@ApiParam(name = "uacMenuCheckCodeDto", value = "id与url") @RequestBody MdcDictCheckCodeDto mdcDictCheckCodeDto) {
		logger.info("检测数据字典编码是否已存在 mdcDictCheckCodeDto={}", mdcDictCheckCodeDto);

		Long id = mdcDictCheckCodeDto.getDictId();
		String dictCode = mdcDictCheckCodeDto.getDictCode();

		Example example = new Example(MdcDict.class);
		Example.Criteria criteria = example.createCriteria();

		if (id != null) {
			criteria.andNotEqualTo("id", id);
		}
		criteria.andEqualTo("dictCode", dictCode);

		int result = mdcDictService.selectCountByExample(example);
		return WrapMapper.success(result < 1);
	}

	/**
	 * 检测数据字典名称是否已存在.
	 *
	 * @param mdcDictCheckNameDto the mdc dict check name dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkDictName")
	@ApiOperation(httpMethod = "POST", value = "检测数据字典名称是否已存在")
	public Wrapper<Boolean> checkDictName(@ApiParam(name = "uacMenuCheckCodeDto", value = "id与url") @RequestBody MdcDictCheckNameDto mdcDictCheckNameDto) {
		logger.info("检测数据字典名称是否已存在 mdcDictCheckNameDto={}", mdcDictCheckNameDto);

		Long id = mdcDictCheckNameDto.getDictId();
		String dictName = mdcDictCheckNameDto.getDictName();

		Example example = new Example(MdcDict.class);
		Example.Criteria criteria = example.createCriteria();

		if (id != null) {
			criteria.andNotEqualTo("id", id);
		}
		criteria.andEqualTo("dictName", dictName);

		int result = mdcDictService.selectCountByExample(example);
		return WrapMapper.success(result < 1);
	}
}
