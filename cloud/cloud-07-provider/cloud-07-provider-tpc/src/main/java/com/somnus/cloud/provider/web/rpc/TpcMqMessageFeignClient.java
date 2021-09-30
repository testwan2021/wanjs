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
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.somnus.cloud.common.util.wrapper.WrapMapper;
import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.comon.core.support.BaseController;
import com.somnus.cloud.provider.api.model.dto.TpcMqMessageDto;
import com.somnus.cloud.provider.api.service.TpcMqMessageFeignApi;
import com.somnus.cloud.provider.service.TpcMqMessageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName: TpcMqMessageFeignClient
 * @Description: The class Tpc mq message feign client.
 * @author Somnus
 * @date 2018年10月17日
 */
@RestController
@Api(value = "API - TpcMqMessageFeignClient", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TpcMqMessageFeignClient extends BaseController implements TpcMqMessageFeignApi {
	
	@Autowired
	private TpcMqMessageService tpcMqMessageService;

	@Override
	@ApiOperation(httpMethod = "POST", value = "预存储消息")
	public Wrapper<?> saveMessageWaitingConfirm(@RequestBody TpcMqMessageDto mqMessageDto) {
		logger.info("预存储消息. mqMessageDto={}", mqMessageDto);
		tpcMqMessageService.saveMessageWaitingConfirm(mqMessageDto);
		return WrapMapper.ok();
	}

	@Override
	@ApiOperation(httpMethod = "POST", value = "确认并发送消息")
	public Wrapper<?> confirmAndSendMessage(@RequestParam("messageKey") String messageKey) {
		logger.info("确认并发送消息. messageKey={}", messageKey);
		tpcMqMessageService.confirmAndSendMessage(messageKey);
		return WrapMapper.ok();
	}

	@Override
	@ApiOperation(httpMethod = "POST", value = "存储并发送消息")
	public Wrapper<?> saveAndSendMessage(@RequestBody TpcMqMessageDto message) {
		logger.info("存储并发送消息. mqMessageDto={}", message);
		tpcMqMessageService.saveAndSendMessage(message);
		return WrapMapper.ok();
	}

	@Override
	@ApiOperation(httpMethod = "POST", value = "直接发送消息")
	public Wrapper<?> directSendMessage(@RequestBody TpcMqMessageDto messageDto) {
		logger.info("直接发送消息. mqMessageDto={}", messageDto);
		tpcMqMessageService.directSendMessage(messageDto.getMessageBody(), messageDto.getMessageTopic(), messageDto.getMessageTag(), messageDto.getMessageKey(), messageDto.getProducerGroup(), messageDto.getDelayLevel());
		return WrapMapper.ok();
	}

	@Override
	@ApiOperation(httpMethod = "POST", value = "根据消息ID删除消息")
	public Wrapper<?> deleteMessageByMessageKey(@RequestParam("messageKey") String messageKey) {
		logger.info("根据消息ID删除消息. messageKey={}", messageKey);
		tpcMqMessageService.deleteMessageByMessageKey(messageKey);
		return WrapMapper.ok();
	}

	@Override
	@ApiOperation(httpMethod = "POST", value = "确认收到消息")
	public Wrapper<?> confirmReceiveMessage(@RequestParam("cid") final String cid, @RequestParam("messageKey") final String messageKey) {
		logger.info("确认收到消息. cid={}, messageKey={}", cid, messageKey);
		tpcMqMessageService.confirmReceiveMessage(cid, messageKey);
		return WrapMapper.ok();
	}

	@Override
	@ApiOperation(httpMethod = "POST", value = "确认消费消息")
	public Wrapper<?> confirmConsumedMessage(@RequestParam("cid") final String cid, @RequestParam("messageKey") final String messageKey) {
		logger.info("确认完成消费消息. cid={}, messageKey={}", cid, messageKey);
		tpcMqMessageService.confirmConsumedMessage(cid, messageKey);
		return WrapMapper.ok();
	}
}
