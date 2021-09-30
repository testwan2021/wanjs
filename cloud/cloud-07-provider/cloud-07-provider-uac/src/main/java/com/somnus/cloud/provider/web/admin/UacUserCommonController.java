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

import java.util.HashSet;
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
import com.somnus.cloud.common.util.PublicUtil;
import com.somnus.cloud.common.util.wrapper.WrapMapper;
import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.comon.core.annotation.LogAnnotation;
import com.somnus.cloud.comon.core.support.BaseController;
import com.somnus.cloud.provider.api.vo.MenuVo;
import com.somnus.cloud.provider.model.domain.UacRole;
import com.somnus.cloud.provider.model.domain.UacUser;
import com.somnus.cloud.provider.model.dto.user.CheckEmailDto;
import com.somnus.cloud.provider.model.dto.user.CheckLoginNameDto;
import com.somnus.cloud.provider.model.dto.user.CheckNewPasswordDto;
import com.somnus.cloud.provider.model.dto.user.CheckUserNameDto;
import com.somnus.cloud.provider.model.dto.user.CheckUserPhoneDto;
import com.somnus.cloud.provider.model.vo.UserVo;
import com.somnus.cloud.provider.service.UacRoleService;
import com.somnus.cloud.provider.service.UacUserService;
import com.somnus.cloud.provider.utils.Md5Util;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import tk.mybatis.mapper.entity.Example;

/**
 * @ClassName: UacUserCommonController
 * @Description: 用户管理-公共方法.
 * @author Somnus
 * @date 2018年10月17日
 */
@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "Web - UacUserCommonController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacUserCommonController extends BaseController {

	@Autowired
	private UacUserService uacUserService;
	
	@Autowired
	private UacRoleService uacRoleService;


	/**
	 * 根据userId查询用户详细信息（连表查询）.
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/queryUserInfo/{loginName}")
	@ApiOperation(httpMethod = "POST", value = "根据userId查询用户详细信息")
	public Wrapper<UserVo> queryUserInfo(@PathVariable String loginName) {
		logger.info("根据userId查询用户详细信息");
		UserVo userVo = new UserVo();
		UacUser uacUser = uacUserService.findByLoginName(loginName);
		uacUser = uacUserService.findUserInfoByUserId(uacUser.getId());
		List<UacRole> roleList = uacRoleService.findAllRoleInfoByUserId(uacUser.getId());
		List<MenuVo> authTree = uacRoleService.getOwnAuthTree(uacUser.getId());
		BeanUtils.copyProperties(uacUser, userVo);
		if (PublicUtil.isNotEmpty(roleList)) {
			userVo.setRoles(new HashSet<>(roleList));
		}
		userVo.setAuthTree(authTree);
		return WrapMapper.success(userVo);
	}


	/**
	 * 校验登录名唯一性.
	 *
	 * @param checkLoginNameDto the check login name dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkLoginName")
	@ApiOperation(httpMethod = "POST", value = "校验登录名唯一性")
	public Wrapper<Boolean> checkLoginName(@ApiParam(name = "loginName", value = "登录名") @RequestBody CheckLoginNameDto checkLoginNameDto) {
		logger.info("校验登录名唯一性 checkLoginNameDto={}", checkLoginNameDto);

		Long id = checkLoginNameDto.getUserId();
		String loginName = checkLoginNameDto.getLoginName();

		Example example = new Example(UacUser.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("loginName", loginName);
		if (id != null) {
			criteria.andNotEqualTo("id", id);
		}

		int result = uacUserService.selectCountByExample(example);
		return WrapMapper.success(result < 1);
	}

	/**
	 * 校验登录名唯一性.
	 *
	 * @param checkEmailDto the check email dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkEmail")
	@ApiOperation(httpMethod = "POST", value = "校验登录名唯一性")
	public Wrapper<Boolean> checkEmail(@RequestBody CheckEmailDto checkEmailDto) {
		logger.info("校验邮箱唯一性 checkEmailDto={}", checkEmailDto);

		Long id = checkEmailDto.getUserId();
		String email = checkEmailDto.getEmail();

		Example example = new Example(UacUser.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("email", email);
		if (id != null) {
			criteria.andNotEqualTo("id", id);
		}

		int result = uacUserService.selectCountByExample(example);
		return WrapMapper.success(result < 1);
	}


	/**
	 * 校验真实姓名唯一性.
	 *
	 * @param checkUserNameDto the check user name dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkUserName")
	@ApiOperation(httpMethod = "POST", value = "校验真实姓名唯一性")
	public Wrapper<Boolean> checkUserName(@ApiParam(name = "checkUserNameDto", value = "校验真实姓名唯一性Dto") @RequestBody CheckUserNameDto checkUserNameDto) {
		logger.info(" 校验真实姓名唯一性 checkUserNameDto={}", checkUserNameDto);
		Long id = checkUserNameDto.getUserId();
		String name = checkUserNameDto.getUserName();

		Example example = new Example(UacUser.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("userName", name);
		if (id != null) {
			criteria.andNotEqualTo("id", id);
		}

		int result = uacUserService.selectCountByExample(example);
		return WrapMapper.success(result < 1);
	}


	/**
	 * 校验用户电话号码唯一性.
	 *
	 * @param checkUserPhoneDto the check user phone dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkUserPhone")
	@ApiOperation(httpMethod = "POST", value = "校验用户电话号码唯一性")
	public Wrapper<Boolean> checkUserPhone(@ApiParam(name = "checkUserPhoneDto", value = "校验用户电话号码唯一性Dto") @RequestBody CheckUserPhoneDto checkUserPhoneDto) {
		logger.info(" 校验用户电话号码唯一性 checkUserPhoneDto={}", checkUserPhoneDto);
		Long id = checkUserPhoneDto.getUserId();
		String mobileNo = checkUserPhoneDto.getMobileNo();
		Example example = new Example(UacUser.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("mobileNo", mobileNo);

		if (id != null) {
			criteria.andNotEqualTo("id", id);
		}

		int result = uacUserService.selectCountByExample(example);
		return WrapMapper.success(result < 1);
	}


	/**
	 * 校验新密码是否与原始密码相同.
	 *
	 * @param checkNewPasswordDto 修改密码实体
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkNewPassword")
	@ApiOperation(httpMethod = "POST", value = "校验新密码是否与原始密码相同")
	public Wrapper<Boolean> checkNewPassword(@ApiParam(name = "checkNewPasswordDto", value = "校验新密码是否与原始密码相同Dto") @RequestBody CheckNewPasswordDto checkNewPasswordDto) {
		logger.info(" 校验新密码是否与原始密码相同 checkNewPasswordDto={}", checkNewPasswordDto);
		String loginName = checkNewPasswordDto.getLoginName();
		String newPassword = checkNewPasswordDto.getNewPassword();
		UacUser uacUser = new UacUser();
		uacUser.setLoginName(loginName);
		int result = 0;
		UacUser user = uacUserService.findByLoginName(loginName);
		if (user == null) {
			logger.error("找不到用户. loginName={}", loginName);
		} else {
			uacUser.setLoginPwd(Md5Util.encrypt(newPassword));
			result = uacUserService.selectCount(uacUser);
		}
		return WrapMapper.success(result < 1);
	}

	/**
	 * 修改用户邮箱
	 *
	 * @param email     the email
	 * @param emailCode the email code
	 *
	 * @return the wrapper
	 */
	@LogAnnotation
	@PostMapping(value = "/modifyUserEmail/{email}/{emailCode}")
	@ApiOperation(httpMethod = "POST", value = "修改用户信息")
	public Wrapper<Integer> modifyUserEmail(@PathVariable String email, @PathVariable String emailCode) {
		logger.info(" 修改用户信息 email={}, emailCode={}", email, emailCode);
		LoginAuthDto loginAuthDto = getLoginAuthDto();
		uacUserService.modifyUserEmail(email, emailCode, loginAuthDto);
		return WrapMapper.ok();
	}

	/**
	 * 获取已有权限树
	 *
	 * @return the auth tree by role id
	 */
	@PostMapping(value = "/getOwnAuthTree")
	@ApiOperation(httpMethod = "POST", value = "获取权限树")
	public Wrapper<List<MenuVo>> getOwnAuthTree() {
		List<MenuVo> tree = uacRoleService.getOwnAuthTree(getLoginAuthDto().getUserId());
		return WrapMapper.success(tree);
	}
}
