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

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.somnus.cloud.common.base.dto.LoginAuthDto;
import com.somnus.cloud.common.base.dto.UpdateStatusDto;
import com.somnus.cloud.common.util.wrapper.WrapMapper;
import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.comon.core.support.BaseController;
import com.somnus.cloud.provider.model.domain.MdcDict;
import com.somnus.cloud.provider.model.dto.MdcEditDictDto;
import com.somnus.cloud.provider.model.vo.MdcDictVo;
import com.somnus.cloud.provider.service.MdcDictService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName: MdcDictMainController
 * @Description: The class Mdc dict main controller.
 * @author Somnus
 * @date 2018年10月17日
 */
@RestController
@RequestMapping(value = "/dict", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - MdcDictMainController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MdcDictMainController extends BaseController {

	@Autowired
	private MdcDictService mdcDictService;

	/**
	 * 获取字典列表数据
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/getTree")
	@ApiOperation(httpMethod = "POST", value = "获取字典树")
	public Wrapper<List<MdcDictVo>> queryDictTreeList() {
		List<MdcDictVo> dictVoList = mdcDictService.getDictTreeList();
		return WrapMapper.success(dictVoList);
	}

	/**
	 * 根据ID获取字典信息.
	 *
	 * @param id the id
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/queryById/{id}")
	@ApiOperation(httpMethod = "POST", value = "根据ID获取字典信息")
	public Wrapper<MdcDictVo> queryDictVoById(@ApiParam(name = "id", value = "字典id") @PathVariable Long id) {
		logger.info("根据Id查询字典信息, dictId={}", id);
		MdcDictVo mdcDictVo = mdcDictService.getMdcDictVoById(id);
		return WrapMapper.success(mdcDictVo);
	}


	/**
	 * 根据id修改字典的禁用状态
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/modifyStatus")
	@ApiOperation(httpMethod = "POST", value = "根据id修改字典的禁用状态")
	public Wrapper<?> updateMdcDictStatusById(@ApiParam(name = "mdcDictStatusDto", value = "修改字典状态Dto") @RequestBody UpdateStatusDto updateStatusDto) {
		logger.info("根据id修改字典的禁用状态 updateStatusDto={}", updateStatusDto);
		LoginAuthDto loginAuthDto = getLoginAuthDto();
		mdcDictService.updateMdcDictStatusById(updateStatusDto, loginAuthDto);
		return WrapMapper.ok();
	}

	@PostMapping(value = "/save")
	@ApiOperation(httpMethod = "POST", value = "编辑字典")
	public Wrapper<?> saveDict(@ApiParam(name = "saveDict", value = "编辑字典") @RequestBody MdcEditDictDto mdcDictAddDto) {
		MdcDict mdcDict = new MdcDict();
		LoginAuthDto loginAuthDto = getLoginAuthDto();
		BeanUtils.copyProperties(mdcDictAddDto, mdcDict);
		mdcDictService.saveMdcDict(mdcDict, loginAuthDto);
		return WrapMapper.ok();
	}

	/**
	 * 根据id删除字典
	 *
	 * @param id the id
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/deleteById/{id}")
	@ApiOperation(httpMethod = "POST", value = "根据id删除字典")
	public Wrapper<Integer> deleteMdcDictById(@ApiParam(name = "id", value = "字典id") @PathVariable Long id) {
		logger.info(" 根据id删除字典 id={}", id);
		// 判断此字典是否有子节点
		boolean hasChild = mdcDictService.checkDictHasChildDict(id);
		if (hasChild) {
			return WrapMapper.wrap(Wrapper.ERROR_CODE, "此字典含有子字典, 请先删除子字典");
		}
		int result = mdcDictService.deleteByKey(id);
		return super.handleResult(result);
	}
}