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
package com.somnus.cloud.provider.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.somnus.cloud.common.util.wrapper.WrapMapper;
import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.comon.core.support.BaseController;
import com.somnus.cloud.provider.model.domain.UacMenu;
import com.somnus.cloud.provider.model.dto.menu.UacMenuCheckCodeDto;
import com.somnus.cloud.provider.model.dto.menu.UacMenuCheckNameDto;
import com.somnus.cloud.provider.model.dto.menu.UacMenuCheckUrlDto;
import com.somnus.cloud.provider.service.UacMenuService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import tk.mybatis.mapper.entity.Example;

/**
 * @ClassName: UacMenuCommonController
 * @Description: 菜单.
 * @author Somnus
 * @date 2018年10月17日
 */
@RestController
@RequestMapping(value = "/menu", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "Web - UacMenuCommonController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacMenuCommonController extends BaseController {

	@Autowired
	private UacMenuService uacMenuService;

	/**
	 * 检测菜单编码是否已存在
	 *
	 * @param uacMenuCheckCodeDto the uac menu check code dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkMenuCode")
	@ApiOperation(httpMethod = "POST", value = "检测菜单编码是否已存在")
	public Wrapper<Boolean> checkUacMenuActionCode(@ApiParam(name = "uacMenuCheckCodeDto", value = "id与url") @RequestBody UacMenuCheckCodeDto uacMenuCheckCodeDto) {
		logger.info("校验菜单编码唯一性 uacMenuCheckCodeDto={}", uacMenuCheckCodeDto);

		Long id = uacMenuCheckCodeDto.getMenuId();
		String menuCode = uacMenuCheckCodeDto.getMenuCode();

		Example example = new Example(UacMenu.class);
		Example.Criteria criteria = example.createCriteria();

		if (id != null) {
			criteria.andNotEqualTo("id", id);
		}
		criteria.andEqualTo("menuCode", menuCode);

		int result = uacMenuService.selectCountByExample(example);
		return WrapMapper.success(result < 1);
	}

	/**
	 * 检测菜单名称唯一性
	 *
	 * @param uacMenuCheckNameDto the uac menu check name dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkMenuName")
	@ApiOperation(httpMethod = "POST", value = "检测菜单名称唯一性")
	public Wrapper<Boolean> checkUacMenuName(@ApiParam(name = "uacMenuCheckNameDto", value = "id与name") @RequestBody UacMenuCheckNameDto uacMenuCheckNameDto) {
		logger.info("校验菜单名称唯一性 uacMenuCheckNameDto={}", uacMenuCheckNameDto);
		Long id = uacMenuCheckNameDto.getMenuId();
		Long pid = uacMenuCheckNameDto.getPid();
		String menuName = uacMenuCheckNameDto.getMenuName();

		Example example = new Example(UacMenu.class);
		Example.Criteria criteria = example.createCriteria();

		if (id != null) {
			criteria.andNotEqualTo("id", id);
		}
		criteria.andEqualTo("menuName", menuName);
		criteria.andEqualTo("pid", pid);

		int result = uacMenuService.selectCountByExample(example);
		return WrapMapper.success(result < 1);
	}


	/**
	 * 检测菜单URL唯一性
	 *
	 * @param uacMenuCheckUrlDto the uac menu check url dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkMenuUrl")
	@ApiOperation(httpMethod = "POST", value = "检测菜单URL唯一性")
	public Wrapper<Boolean> checkUacMenuUrl(@ApiParam(name = "uacMenuCheckUrlDto", value = "id与url") @RequestBody UacMenuCheckUrlDto uacMenuCheckUrlDto) {
		logger.info("检测菜单URL唯一性 uacMenuCheckUrlDto={}", uacMenuCheckUrlDto);

		Long id = uacMenuCheckUrlDto.getMenuId();
		String url = uacMenuCheckUrlDto.getUrl();

		Example example = new Example(UacMenu.class);
		Example.Criteria criteria = example.createCriteria();

		if (id != null) {
			criteria.andNotEqualTo("id", id);
		}
		criteria.andEqualTo("url", url);

		int result = uacMenuService.selectCountByExample(example);
		return WrapMapper.success(result < 1);
	}
}
