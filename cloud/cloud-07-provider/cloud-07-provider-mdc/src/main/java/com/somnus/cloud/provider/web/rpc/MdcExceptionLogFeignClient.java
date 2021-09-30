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
package com.somnus.cloud.provider.web.rpc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.somnus.cloud.common.util.wrapper.WrapMapper;
import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.comon.core.support.BaseController;
import com.somnus.cloud.provider.api.model.dto.GlobalExceptionLogDto;
import com.somnus.cloud.provider.api.service.MdcExceptionLogFeignApi;
import com.somnus.cloud.provider.service.MdcExceptionLogService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: MdcExceptionLogFeignClient
 * @Description: The class Mdc product feign client.
 * @author Somnus
 * @date 2018年10月17日
 */
@Slf4j
@RefreshScope
@RestController
@Api(value = "API - MdcExceptionLogFeignClient", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MdcExceptionLogFeignClient extends BaseController implements MdcExceptionLogFeignApi {
	
	@Autowired
	private MdcExceptionLogService mdcExceptionLogService;

	@Override
	@ApiOperation(httpMethod = "POST", value = "保存日志并发送钉钉消息")
	public Wrapper<?> saveAndSendExceptionLog(@RequestBody GlobalExceptionLogDto exceptionLogDto) {
		try {
			mdcExceptionLogService.saveAndSendExceptionLog(exceptionLogDto);
		} catch (Exception e) {
			log.error("saveAndSendExceptionLog={}", e.getMessage(), e);
		}
		return WrapMapper.ok();
	}
}
