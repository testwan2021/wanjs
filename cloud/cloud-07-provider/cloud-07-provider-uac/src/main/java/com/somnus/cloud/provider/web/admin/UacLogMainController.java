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

import com.github.pagehelper.PageInfo;
import com.somnus.cloud.common.util.wrapper.WrapMapper;
import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.comon.core.support.BaseController;
import com.somnus.cloud.provider.model.domain.UacLog;
import com.somnus.cloud.provider.model.dto.log.UacLogMainDto;
import com.somnus.cloud.provider.service.UacLogService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName: UacLogMainController
 * @Description: 日志管理.
 * @author Somnus
 * @date 2018年10月17日
 */
@RestController
@RequestMapping(value = "/log", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "Web - UacLogMainController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacLogMainController extends BaseController {
	
	@Autowired
	private UacLogService uacLogService;

	/**
	 * 查询日志列表.
	 *
	 * @param uacLogQueryDtoPage the uac log query dto page
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/queryListWithPage")
	@ApiOperation(httpMethod = "POST", value = "查询日志列表")
	public Wrapper<?> queryLogListWithPage(@ApiParam(name = "uacLogQueryDtoPage", value = "日志查询条件") @RequestBody UacLogMainDto uacLogQueryDtoPage) {
		logger.info("查询日志处理列表 uacLogQueryDtoPage={}", uacLogQueryDtoPage);
		PageInfo<UacLog> pageInfo = uacLogService.queryLogListWithPage(uacLogQueryDtoPage);
		return WrapMapper.success(pageInfo);
	}
}
