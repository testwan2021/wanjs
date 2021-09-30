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

import com.somnus.cloud.common.util.wrapper.WrapMapper;
import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.comon.core.support.BaseController;
import com.somnus.cloud.provider.model.domain.UacRole;
import com.somnus.cloud.provider.model.dto.role.CheckRoleCodeDto;
import com.somnus.cloud.provider.model.vo.BindAuthVo;
import com.somnus.cloud.provider.service.UacRoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import tk.mybatis.mapper.entity.Example;

/**
 * @ClassName: UacRoleCommonController
 * @Description: 角色管理-公共方法.
 * @author Somnus
 * @date 2018年10月17日
 */
@RestController
@RequestMapping(value = "/role", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "Web - UacRoleCommonController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacRoleCommonController extends BaseController {

	@Autowired
	private UacRoleService uacRoleService;

	/**
	 * 查看角色信息.
	 *
	 * @param id the id
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/queryRoleInfoById/{id}")
	@ApiOperation(httpMethod = "POST", value = "查看角色信息")
	public Wrapper<UacRole> queryRoleInfo(@PathVariable Long id) {
		UacRole role = uacRoleService.selectByKey(id);
		return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, role);
	}

	/**
	 * 验证角色编码是否存在.
	 *
	 * @param checkRoleCodeDto the check role code dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkRoleCode")
	@ApiOperation(httpMethod = "POST", value = "验证角色编码是否存在")
	public Wrapper<Boolean> checkUacRoleCode(@ApiParam(name = "roleCode", value = "角色编码") @RequestBody CheckRoleCodeDto checkRoleCodeDto) {

		logger.info("校验角色编码唯一性 checkRoleCodeDto={}", checkRoleCodeDto);

		Long id = checkRoleCodeDto.getRoleId();
		String roleCode = checkRoleCodeDto.getRoleCode();

		Example example = new Example(UacRole.class);
		Example.Criteria criteria = example.createCriteria();

		if (id != null) {
			criteria.andNotEqualTo("id", id);
		}
		criteria.andEqualTo("roleCode", roleCode);

		int result = uacRoleService.selectCountByExample(example);
		return WrapMapper.success(result < 1);
	}

	/**
	 * 获取权限树
	 *
	 * @param roleId the role id
	 *
	 * @return the auth tree by role id
	 */
	@PostMapping(value = "/getActionTreeByRoleId/{roleId}")
	@ApiOperation(httpMethod = "POST", value = "获取权限树")
	public Wrapper<BindAuthVo> getActionTreeByRoleId(@ApiParam(name = "roleId", value = "角色id") @PathVariable Long roleId) {
		logger.info("roleId={}", roleId);
		BindAuthVo bindAuthVo = uacRoleService.getActionTreeByRoleId(roleId);
		return WrapMapper.success(bindAuthVo);
	}

	/**
	 * 获取菜单树.
	 *
	 * @param roleId the role id
	 *
	 * @return the menu tree by role id
	 */
	@PostMapping(value = "/getMenuTreeByRoleId/{roleId}")
	@ApiOperation(httpMethod = "POST", value = "获取菜单树")
	public Wrapper<BindAuthVo> getMenuTreeByRoleId(@ApiParam(name = "roleId", value = "角色id") @PathVariable Long roleId) {
		logger.info("roleId={}", roleId);
		BindAuthVo bindAuthVo = uacRoleService.getMenuTreeByRoleId(roleId);
		return WrapMapper.success(bindAuthVo);
	}

}
