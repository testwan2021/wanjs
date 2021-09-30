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
import com.somnus.cloud.provider.model.domain.MdcExceptionLog;
import com.somnus.cloud.provider.model.dto.MdcExceptionQueryDto;
import com.somnus.cloud.provider.service.MdcExceptionLogService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName: MdcExceptionMainController
 * @Description: 异常管理.
 * @author Somnus
 * @date 2018年10月17日
 */
@RestController
@RequestMapping(value = "/exception", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "Web - MdcExceptionMainController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MdcExceptionMainController extends BaseController {
	
	@Autowired
	private MdcExceptionLogService mdcExceptionLogService;

	/**
	 * 异常日志列表.
	 *
	 * @param mdcExceptionQueryDto the mdc exception query dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/queryListWithPage")
	@ApiOperation(httpMethod = "POST", value = "查询日志列表")
	public Wrapper<?> queryLogListWithPage(@ApiParam(name = "mdcExceptionQueryDto", value = "异常查询条件") @RequestBody MdcExceptionQueryDto mdcExceptionQueryDto) {
		logger.info("查询日志处理列表 mdcExceptionQueryDto={}", mdcExceptionQueryDto);
		PageInfo<MdcExceptionLog> pageInfo = mdcExceptionLogService.queryExceptionListWithPage(mdcExceptionQueryDto);
		return WrapMapper.success(pageInfo);
	}
}
