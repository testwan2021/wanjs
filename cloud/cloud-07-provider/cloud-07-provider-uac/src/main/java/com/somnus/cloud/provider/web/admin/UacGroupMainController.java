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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.somnus.cloud.common.base.dto.LoginAuthDto;
import com.somnus.cloud.common.util.wrapper.WrapMapper;
import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.comon.core.annotation.LogAnnotation;
import com.somnus.cloud.comon.core.support.BaseController;
import com.somnus.cloud.provider.api.dto.user.IdStatusDto;
import com.somnus.cloud.provider.api.vo.MenuVo;
import com.somnus.cloud.provider.model.domain.UacGroup;
import com.somnus.cloud.provider.service.UacGroupService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName: UacGroupMainController
 * @Description: 组织管理主页面
 * @author Somnus
 * @date 2018年10月17日
 */
@RestController
@RequestMapping(value = "/group", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "Web - UacGroupMainController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacGroupMainController extends BaseController {

	@Autowired
	private UacGroupService uacGroupService;

	/**
	 * 根据id删除组织
	 *
	 * @param id the id
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/deleteById/{id}")
	@LogAnnotation
	@ApiOperation(httpMethod = "POST", value = "根据id删除组织")
	public Wrapper<?> deleteGroupById(@ApiParam(name = "id", value = "组织id") @PathVariable Long id) {
		logger.info(" 根据id删除组织 id={}", id);
		int result = uacGroupService.deleteUacGroupById(id);
		if (result < 1) {
			return WrapMapper.wrap(Wrapper.ERROR_CODE, "操作失败");
		} else {
			return WrapMapper.wrap(Wrapper.SUCCESS_CODE, "操作成功");
		}
	}

	/**
	 * 根据id修改组织状态
	 *
	 * @param idStatusDto the id status dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/modifyStatus")
	@LogAnnotation
	@ApiOperation(httpMethod = "POST", value = "根据id修改组织状态")
	public Wrapper<?> modifyGroupStatus(@ApiParam(name = "modifyGroupStatus", value = "修改状态") @RequestBody IdStatusDto idStatusDto) {
		logger.info("根据id修改组织状态 idStatusDto={}", idStatusDto);
		UacGroup uacGroup = new UacGroup();
		uacGroup.setId(idStatusDto.getId());
		LoginAuthDto loginAuthDto = super.getLoginAuthDto();
		Integer status = idStatusDto.getStatus();
		uacGroup.setStatus(status);
		int result = uacGroupService.updateUacGroupStatusById(idStatusDto, loginAuthDto);
		if (result < 1) {
			return WrapMapper.wrap(Wrapper.ERROR_CODE, "操作失败");
		} else {
			return WrapMapper.wrap(Wrapper.SUCCESS_CODE, "操作成功");
		}
	}


	/**
	 * 获取主页面数据
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/getTree")
	@ApiOperation(httpMethod = "POST", value = "获取菜单树")
	public Wrapper<List<MenuVo>> getTree() {
		Long userId = super.getLoginAuthDto().getUserId();
		List<MenuVo> tree = uacGroupService.getGroupTreeListByUserId(userId);
		return WrapMapper.success(tree);
	}

	/**
	 * 编辑组织
	 *
	 * @param group the group
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/save")
	@LogAnnotation
	@ApiOperation(httpMethod = "POST", value = "修改组织信息")
	public Wrapper<?> editGroup(@ApiParam(name = "group", value = "组织信息") @RequestBody UacGroup group) {
		LoginAuthDto loginAuthDto = super.getLoginAuthDto();
		uacGroupService.saveUacGroup(group, loginAuthDto);
		return WrapMapper.ok();
	}


	/**
	 * 获取编辑页面数据
	 *
	 * @param id the id
	 *
	 * @return the edit group page info
	 */
	@PostMapping(value = "/queryById/{id}")
	@ApiOperation(httpMethod = "POST", value = "获取编辑页面数据")
	public Wrapper<UacGroup> getEditGroupPageInfo(@ApiParam(name = "id", value = "组织id") @PathVariable Long id) {
		UacGroup uacGroup = uacGroupService.getById(id);
		return WrapMapper.success(uacGroup);
	}
}
