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
package com.somnus.cloud.provider.api.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.somnus.cloud.common.base.constant.GlobalConstant;
import com.somnus.cloud.common.base.dto.LoginAuthDto;
import com.somnus.cloud.common.util.ThreadLocalMap;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Arrays;
import java.util.Date;

/**
 * @ClassName: GlobalExceptionLogDto
 * @Description: The class Global exception log dto.
 * @author Somnus
 * @date 2018年10月16日
 */
@Data
@NoArgsConstructor
@Slf4j
public class GlobalExceptionLogDto {

	/**
	 * 运行环境
	 */
	private String profile;

	/**
	 * 应用名称
	 */
	private String applicationName;

	/**
	 * 异常信息(通过exception.getMessage()获取到的内容)
	 */
	private String exceptionMessage;

	/**
	 * 异常原因(通过exception.getCause()获取到的内容)
	 */
	private String exceptionCause;

	/**
	 * 异常类型
	 */
	private String exceptionSimpleName;

	/**
	 * 异常堆栈信息
	 */
	private String exceptionStack;

	/**
	 * 创建人
	 */
	private String creator;

	/**
	 * 创建人ID
	 */
	private Long creatorId;

	/**
	 * 创建时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createdTime;

	/**
	 * Gets global exception log dto.
	 *
	 * @param ex the ex
	 *
	 * @return the global exception log dto
	 */
	public GlobalExceptionLogDto getGlobalExceptionLogDto(Exception ex, String profile, String applicationName) {
		String message = ex.getMessage();
		if (StringUtils.isNotBlank(message) && message.length() > GlobalConstant.EXCEPTION_MESSAGE_MAX_LENGTH) {
			this.exceptionMessage = StringUtils.substring(message, 0, GlobalConstant.EXCEPTION_MESSAGE_MAX_LENGTH) + "...";
		}
		this.exceptionSimpleName = ex.getClass().getSimpleName();
		String cause = ex.getCause() == null ? null : ex.getCause().toString();
		if (StringUtils.isNotBlank(cause) && cause.length() > GlobalConstant.EXCEPTION_CAUSE_MAX_LENGTH) {
			this.exceptionCause = StringUtils.substring(cause, 0, GlobalConstant.EXCEPTION_CAUSE_MAX_LENGTH) + "...";
		}
		this.exceptionStack = Arrays.toString(ex.getStackTrace());

		this.profile = profile;
		LoginAuthDto loginAuthDto = null;

		try {
			loginAuthDto = (LoginAuthDto) ThreadLocalMap.get(GlobalConstant.Sys.TOKEN_AUTH_DTO);
		} catch (Exception e) {
			log.error("获取登陆人信息, 出现异常={}", e.getMessage(), e);
		}

		if (loginAuthDto == null) {
			loginAuthDto = new LoginAuthDto(-1L, "SYSTEM_TASK", "系统任务");
		}
		this.creatorId = loginAuthDto.getUserId();
		this.creator = loginAuthDto.getUserName();
		this.applicationName = applicationName;
		return this;
	}
}