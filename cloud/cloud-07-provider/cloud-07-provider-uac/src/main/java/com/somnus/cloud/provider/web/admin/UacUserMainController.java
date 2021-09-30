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
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.somnus.cloud.common.base.dto.LoginAuthDto;
import com.somnus.cloud.common.base.enums.ErrorCodeEnum;
import com.somnus.cloud.common.util.wrapper.WrapMapper;
import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.comon.core.annotation.LogAnnotation;
import com.somnus.cloud.comon.core.support.BaseController;
import com.somnus.cloud.provider.api.exceptions.UacBizException;
import com.somnus.cloud.provider.model.domain.UacLog;
import com.somnus.cloud.provider.model.domain.UacUser;
import com.somnus.cloud.provider.model.dto.menu.UserMenuDto;
import com.somnus.cloud.provider.model.dto.user.BindUserMenusDto;
import com.somnus.cloud.provider.model.dto.user.BindUserRolesDto;
import com.somnus.cloud.provider.model.dto.user.ModifyUserStatusDto;
import com.somnus.cloud.provider.model.vo.UserBindRoleVo;
import com.somnus.cloud.provider.security.SecurityUtils;
import com.somnus.cloud.provider.service.UacUserService;
import com.somnus.cloud.security.core.SecurityUser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName: UacUserMainController
 * @Description: 用户管理主页面.
 * @author Somnus
 * @date 2018年10月17日
 */
@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "Web - UacUserMainController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacUserMainController extends BaseController {
	
	@Autowired
	private UacUserService uacUserService;

	/**
	 * 查询角色列表.
	 *
	 * @param uacUser the uac user
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/queryListWithPage")
	@ApiOperation(httpMethod = "POST", value = "查询角色列表")
	public Wrapper<?> queryUserListWithPage(@ApiParam(name = "role", value = "角色信息") @RequestBody UacUser uacUser) {

		logger.info("查询用户列表uacUser={}", uacUser);
		PageInfo<UacUser> pageInfo = uacUserService.queryUserListWithPage(uacUser);
		return WrapMapper.success(pageInfo);
	}

	/**
	 * 新增用户
	 *
	 * @param user the user
	 *
	 * @return the wrapper
	 */
	@LogAnnotation
	@PostMapping(value = "/save")
	@ApiOperation(httpMethod = "POST", value = "新增用户")
	public Wrapper<Integer> addUacUser(@ApiParam(name = "user", value = "新增用户Dto") @RequestBody UacUser user) {
		logger.info(" 新增用户 user={}", user);
		LoginAuthDto loginAuthDto = getLoginAuthDto();
		uacUserService.saveUacUser(user, loginAuthDto);
		return WrapMapper.ok();
	}

	/**
	 * 分页查询用户操作日志列表.
	 *
	 * @param log the log
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/queryUserLogListWithPage")
	@ApiOperation(httpMethod = "POST", value = "分页查询用户操作日志列表")
	public Wrapper<PageInfo<UacLog>> queryUserLogListWithPage(@ApiParam(name = "user", value = "用户信息") @RequestBody UacLog log) {

		logger.info("分页查询用户操作日志列表");
		PageHelper.startPage(log.getPageNum(), log.getPageSize());
		List<UacLog> list = uacUserService.queryUserLogListWithUserId(getLoginAuthDto().getUserId());
		PageInfo<UacLog> pageInfo = new PageInfo<>(list);
		return WrapMapper.success(pageInfo);
	}

	/**
	 * 根据Id修改用户状态.
	 *
	 * @param modifyUserStatusDto the modify user status dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/modifyUserStatusById")
	@LogAnnotation
	@ApiOperation(httpMethod = "POST", value = "根据Id修改用户状态")
	public Wrapper<Integer> modifyUserStatusById(@ApiParam(name = "modifyUserStatusDto", value = "用户禁用/启用Dto") @RequestBody ModifyUserStatusDto modifyUserStatusDto) {
		logger.info(" 根据Id修改用户状态 modifyUserStatusDto={}", modifyUserStatusDto);
		LoginAuthDto loginAuthDto = getLoginAuthDto();
		UacUser uacUser = new UacUser();
		uacUser.setId(modifyUserStatusDto.getUserId());
		uacUser.setStatus(modifyUserStatusDto.getStatus());

		int result = uacUserService.modifyUserStatusById(uacUser, loginAuthDto);
		return handleResult(result);
	}

	/**
	 * 通过Id删除用户.
	 *
	 * @param userId the user id
	 *
	 * @return the wrapper
	 */
	@LogAnnotation
	@PostMapping(value = "/deleteUserById/{userId}")
	@ApiOperation(httpMethod = "POST", value = "通过Id删除用户")
	public Wrapper<Integer> deleteUserById(@ApiParam(name = "userId", value = "用户ID") @PathVariable Long userId) {
		logger.info(" 通过Id删除用户 userId={}", userId);
		int result = uacUserService.deleteUserById(userId);
		return handleResult(result);
	}

	/**
	 * 获取用户绑定角色页面数据.
	 *
	 * @param userId the user id
	 *
	 * @return the bind role
	 */
	@PostMapping(value = "/getBindRole/{userId}")
	@ApiOperation(httpMethod = "POST", value = "获取用户绑定角色页面数据")
	public Wrapper<UserBindRoleVo> getBindRole(@ApiParam(name = "userId", value = "角色id") @PathVariable Long userId) {
		logger.info("获取用户绑定角色页面数据. userId={}", userId);
		LoginAuthDto loginAuthDto = super.getLoginAuthDto();
		Long currentUserId = loginAuthDto.getUserId();
		if (Objects.equals(userId, currentUserId)) {
			throw new UacBizException(ErrorCodeEnum.UAC10011023);
		}

		UserBindRoleVo bindUserDto = uacUserService.getUserBindRoleDto(userId);
		return WrapMapper.success(bindUserDto);
	}

	/**
	 * 用户绑定角色.
	 *
	 * @param bindUserRolesDto the bind user roles dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/bindRole")
	@LogAnnotation
	@ApiOperation(httpMethod = "POST", value = "用户绑定角色")
	public Wrapper<Integer> bindUserRoles(@ApiParam(name = "bindUserRolesDto", value = "用户绑定角色Dto") @RequestBody BindUserRolesDto bindUserRolesDto) {
		logger.info("用户绑定角色 bindUserRolesDto={}", bindUserRolesDto);
		LoginAuthDto loginAuthDto = getLoginAuthDto();
		uacUserService.bindUserRoles(bindUserRolesDto, loginAuthDto);
		return WrapMapper.ok();
	}

	/**
	 * 查询用户常用功能数据.
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/queryUserMenuDtoData")
	@ApiOperation(httpMethod = "POST", value = "查询用户常用功能数据")
	public Wrapper<List<UserMenuDto>> queryUserMenuDtoData() {
		logger.info("查询用户常用功能数据");

		LoginAuthDto loginAuthDto = getLoginAuthDto();
		List<UserMenuDto> userMenuDtoList = uacUserService.queryUserMenuDtoData(loginAuthDto);
		return WrapMapper.success(userMenuDtoList);
	}

	/**
	 * 绑定用户常用菜单.
	 *
	 * @param bindUserMenusDto the bind user menus dto
	 *
	 * @return the wrapper
	 */
	@LogAnnotation
	@PostMapping(value = "/bindUserMenus")
	@ApiOperation(httpMethod = "POST", value = "绑定用户常用菜单")
	public Wrapper<Integer> bindUserMenus(@ApiParam(name = "bindUserMenusDto", value = "绑定用户常用菜单Dto") @RequestBody BindUserMenusDto bindUserMenusDto) {
		logger.info("绑定用户常用菜单");
		List<Long> menuIdList = bindUserMenusDto.getMenuIdList();
		logger.info("menuIdList = {}", menuIdList);

		int result = uacUserService.bindUserMenus(menuIdList, getLoginAuthDto());

		return handleResult(result);

	}

	/**
	 * 根据用户Id查询用户信息.
	 *
	 * @param userId the user id
	 *
	 * @return the uac user by id
	 */
	@PostMapping(value = "/getUacUserById/{userId}")
	@ApiOperation(httpMethod = "POST", value = "根据用户Id查询用户信息")
	public Wrapper<UacUser> getUacUserById(@ApiParam(name = "userId", value = "用户ID") @PathVariable Long userId) {
		logger.info("getUacUserById - 根据用户Id查询用户信息. userId={}", userId);
		UacUser uacUser = uacUserService.queryByUserId(userId);
		logger.info("getUacUserById - 根据用户Id查询用户信息. [OK] uacUser={}", uacUser);
		return WrapMapper.success(uacUser);
	}

	/**
	 * 根据用户Id重置密码.
	 *
	 * @param userId the user id
	 *
	 * @return the wrapper
	 */
	@LogAnnotation
	@PostMapping(value = "/resetLoginPwd/{userId}")
	@ApiOperation(httpMethod = "POST", value = "根据用户Id重置密码")
	public Wrapper<UacUser> resetLoginPwd(@ApiParam(name = "userId", value = "用户ID") @PathVariable Long userId) {
		logger.info("resetLoginPwd - 根据用户Id重置密码. userId={}", userId);
		uacUserService.resetLoginPwd(userId, getLoginAuthDto());
		return WrapMapper.ok();
	}

	/**
	 * User security user.
	 *
	 * @return the security user
	 */
	@GetMapping("/user")
	public SecurityUser user() {
		String loginName = SecurityUtils.getCurrentLoginName();
		logger.info("{}", loginName);
		UacUser user = uacUserService.findByLoginName(loginName);
		return user == null ? null : new SecurityUser(user.getId(), user.getLoginName(), user.getLoginPwd(), user.getUserName(), user.getGroupId(), user.getGroupName());
	}

}
