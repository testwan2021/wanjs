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
import com.somnus.cloud.provider.model.dto.group.GroupBindUserDto;
import com.somnus.cloud.provider.model.dto.group.GroupBindUserReqDto;
import com.somnus.cloud.provider.service.UacGroupService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName: UacGroupBindUserController
 * @Description: 组织绑定用户.
 * @author Somnus
 * @date 2018年10月17日
 */
@RestController
@RequestMapping(value = "/group", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "Web - UacGroupBindUserController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacGroupBindUserController extends BaseController {

	@Autowired
	private UacGroupService uacGroupService;

	/**
	 * 组织绑定用户
	 *
	 * @param groupBindUserReqDto the group bind user req dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/bindUser")
	@LogAnnotation
	@ApiOperation(httpMethod = "POST", value = "组织绑定用户")
	public Wrapper<?> bindUser4Role(@ApiParam(name = "uacGroupBindUserReqDto", value = "组织绑定用户") @RequestBody GroupBindUserReqDto groupBindUserReqDto) {
		logger.info("组织绑定用户...  groupBindUserReqDto={}", groupBindUserReqDto);
		LoginAuthDto loginAuthDto = super.getLoginAuthDto();
		uacGroupService.bindUacUser4Group(groupBindUserReqDto, loginAuthDto);
		return WrapMapper.ok();
	}

	/**
	 * 组织绑定用户页面数据
	 *
	 * @param groupId the group id
	 *
	 * @return the group bind user page info
	 */
	@PostMapping(value = "/getBindUser/{groupId}")
	@ApiOperation(httpMethod = "POST", value = "获取组织绑定用户页面数据")
	public Wrapper<GroupBindUserDto> getGroupBindUserPageInfo(@ApiParam(name = "groupId", value = "组织id") @PathVariable Long groupId) {
		logger.info("查询组织绑定用户页面数据 groupId={}", groupId);
		LoginAuthDto loginAuthDto = super.getLoginAuthDto();
		Long currentUserId = loginAuthDto.getUserId();
		GroupBindUserDto bindUserDto = uacGroupService.getGroupBindUserDto(groupId, currentUserId);
		return WrapMapper.success(bindUserDto);
	}
}
