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
import com.somnus.cloud.comon.core.annotation.LogAnnotation;
import com.somnus.cloud.comon.core.support.BaseController;
import com.somnus.cloud.provider.model.dto.role.RoleBindActionDto;
import com.somnus.cloud.provider.model.dto.role.RoleBindMenuDto;
import com.somnus.cloud.provider.service.UacRoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName: UacGrantAuth4RoleController
 * @Description: 角色授权.
 * @author Somnus
 * @date 2018年10月17日
 */
@RestController
@RequestMapping(value = "/role", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "Web - UacGrantAuth4RoleController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacGrantAuth4RoleController extends BaseController {

	@Autowired
	private UacRoleService uacRoleService;

	/**
	 * 角色分配权限.
	 *
	 * @param roleBindActionDto the role bind action dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/bindAction")
	@ApiOperation(httpMethod = "POST", value = "角色分配权限")
	@LogAnnotation
	public Wrapper<?> bindAction(@ApiParam(name = "bindAuth", value = "权限信息") @RequestBody RoleBindActionDto roleBindActionDto) {
		logger.info("角色分配权限. roleBindActionDto= {}", roleBindActionDto);
		uacRoleService.bindAction(roleBindActionDto);
		return WrapMapper.ok();
	}

	/**
	 * 角色分配权限.
	 *
	 * @param roleBindMenuDto the role bind menu dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/bindMenu")
	@ApiOperation(httpMethod = "POST", value = "角色分配权限")
	@LogAnnotation
	public Wrapper<?> bindMenu(@ApiParam(name = "bindAuth", value = "权限信息") @RequestBody RoleBindMenuDto roleBindMenuDto) {
		logger.info("角色分配权限. roleBindMenuDto= {}", roleBindMenuDto);
		uacRoleService.bindMenu(roleBindMenuDto);
		return WrapMapper.ok();
	}
}
