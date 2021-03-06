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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Preconditions;
import com.somnus.cloud.common.base.dto.CheckValidDto;
import com.somnus.cloud.common.util.PublicUtil;
import com.somnus.cloud.common.util.wrapper.WrapMapper;
import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.comon.core.annotation.OperationLogDto;
import com.somnus.cloud.comon.core.support.BaseController;
import com.somnus.cloud.provider.api.constant.UacApiConstant;
import com.somnus.cloud.provider.api.dto.user.UserRegisterDto;
import com.somnus.cloud.provider.model.domain.UacUser;
import com.somnus.cloud.provider.model.dto.user.ResetLoginPwdDto;
import com.somnus.cloud.provider.model.enums.UacUserStatusEnum;
import com.somnus.cloud.provider.service.EmailService;
import com.somnus.cloud.provider.service.SmsService;
import com.somnus.cloud.provider.service.UacLogService;
import com.somnus.cloud.provider.service.UacUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName: AuthRestController
 * @Description: ????????????URL??????.
 * @author Somnus
 * @date 2018???10???17???
 */
@RestController
@RequestMapping(value = "/auth")
@Api(value = "Web-AuthRestController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AuthRestController extends BaseController {
	@Autowired
	private UacUserService uacUserService;
	
	@Autowired
	private SmsService smsService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UacLogService uacLogService;

	/**
	 * ??????????????????.
	 *
	 * @param mobileNo the mobile no
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkPhoneActive/{mobileNo}")
	@ApiOperation(httpMethod = "POST", value = "??????????????????")
	public Wrapper<Boolean> checkPhoneActive(@PathVariable String mobileNo) {
		UacUser uacUser = new UacUser();
		uacUser.setStatus(UacUserStatusEnum.ENABLE.getKey());
		uacUser.setMobileNo(mobileNo);
		int count = uacUserService.selectCount(uacUser);
		return WrapMapper.success(count > 0);
	}

	/**
	 * ????????????.
	 *
	 * @param email the email
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkEmailActive/{email:.+}")
	@ApiOperation(httpMethod = "POST", value = "????????????")
	public Wrapper<Boolean> checkEmailActive(@PathVariable("email") String email) {
		UacUser uacUser = new UacUser();
		uacUser.setStatus(UacUserStatusEnum.ENABLE.getKey());
		uacUser.setEmail(email);
		int count = uacUserService.selectCount(uacUser);
		return WrapMapper.success(count > 0);
	}

	/**
	 * ????????????.
	 *
	 * @param checkValidDto the check valid dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkValid")
	@ApiOperation(httpMethod = "POST", value = "????????????")
	public Wrapper<?> checkValid(@RequestBody CheckValidDto checkValidDto) {
		String type = checkValidDto.getType();
		String validValue = checkValidDto.getValidValue();

		Preconditions.checkArgument(PublicUtil.isNotEmpty(validValue), "????????????");
		String message = null;
		boolean result = false;
		//????????????
		if (UacApiConstant.Valid.LOGIN_NAME.equals(type)) {
			result = uacUserService.checkLoginName(validValue);
			if (!result) {
				message = "??????????????????";
			}
		}
		if (UacApiConstant.Valid.EMAIL.equals(type)) {
			result = uacUserService.checkEmail(validValue);
			if (!result) {
				message = "???????????????";
			}
		}

		if (UacApiConstant.Valid.MOBILE_NO.equals(type)) {
			result = uacUserService.checkMobileNo(validValue);
			if (!result) {
				message = "?????????????????????";
			}
		}

		return WrapMapper.wrap(Wrapper.SUCCESS_CODE, message, result);
	}


	/**
	 * ????????????-??????-??????.
	 *
	 * @param email the email
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/submitResetPwdEmail")
	@ApiOperation(httpMethod = "POST", value = "????????????-??????-??????")
	public Wrapper<String> submitResetPwdEmail(@RequestParam("email") String email) {
		logger.info("????????????-??????-??????, email={}", email);
		emailService.submitResetPwdEmail(email);
		return WrapMapper.ok();
	}


	/**
	 * ????????????-??????-??????.
	 *
	 * @param mobile   the mobile
	 * @param response the response
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/submitResetPwdPhone")
	@ApiOperation(httpMethod = "POST", value = "????????????-??????-??????")
	public Wrapper<String> submitResetPwdPhone(@RequestParam("mobile") String mobile, HttpServletResponse response) {
		logger.info("????????????-??????-??????, mobile={}", mobile);
		String tsuccessen = smsService.submitResetPwdPhone(mobile, response);
		return WrapMapper.success(tsuccessen);
	}

	/**
	 * ????????????-????????????.
	 *
	 * @param resetLoginPwdDto the reset login pwd dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/resetLoginPwd")
	@ApiOperation(httpMethod = "POST", value = "????????????-????????????")
	public Wrapper<Boolean> checkResetSmsCode(ResetLoginPwdDto resetLoginPwdDto) {
		uacUserService.resetLoginPwd(resetLoginPwdDto);
		return WrapMapper.ok();
	}

	/**
	 * ????????????.
	 *
	 * @param user the user
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/register")
	@ApiOperation(httpMethod = "POST", value = "????????????")
	public Wrapper<?> registerUser(UserRegisterDto user) {
		uacUserService.register(user);
		return WrapMapper.ok();
	}

	/**
	 * ????????????.
	 *
	 * @param activeUserTsuccessen the active user successen
	 *
	 * @return the wrapper
	 */
	@GetMapping(value = "/activeUser/{activeUserTsuccessen}")
	@ApiOperation(httpMethod = "POST", value = "????????????")
	public Wrapper<?> activeUser(@PathVariable String activeUserTsuccessen) {
		uacUserService.activeUser(activeUserTsuccessen);
		return WrapMapper.success("????????????");
	}

	/**
	 * ????????????.
	 *
	 * @param operationLogDto the operation log dto
	 *
	 * @return the integer
	 */
	@PostMapping(value = "/saveLog")
	@ApiOperation(httpMethod = "POST", value = "????????????")
	public Integer saveLog(@RequestBody OperationLogDto operationLogDto) {
		logger.info("saveLog - ??????????????????. operationLogDto={}", operationLogDto);
		return uacLogService.saveOperationLog(operationLogDto);
	}

	@PostMapping(value = "/callback/qq")
	public void callbackQQ(HttpServletRequest request) {
		logger.info("callback - callback qq. request={}", request);
	}
}
