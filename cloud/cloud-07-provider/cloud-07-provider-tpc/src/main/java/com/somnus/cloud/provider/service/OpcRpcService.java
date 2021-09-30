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
package com.somnus.cloud.provider.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.pagehelper.PageInfo;
import com.somnus.cloud.common.base.dto.MessageQueryDto;
import com.somnus.cloud.common.base.dto.MqMessageVo;
import com.somnus.cloud.common.base.enums.ErrorCodeEnum;
import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.provider.api.exceptions.TpcBizException;
import com.somnus.cloud.provider.api.model.dto.robot.ChatRobotMsgDto;
import com.somnus.cloud.provider.api.service.DingtalkFeignApi;
import com.somnus.cloud.provider.api.service.OpcMqMessageFeignApi;
import com.somnus.cloud.provider.api.service.OpcOssFeignApi;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: OpcRpcService
 * @Description: The class Opc rpc service.
 * @author Somnus
 * @date 2018年10月17日
 */
@Slf4j
@Component
public class OpcRpcService {

	@Autowired
	private DingtalkFeignApi dingtalkFeignApi;
	@Autowired
	private OpcOssFeignApi opcOssFeignApi;
	@Autowired
	private OpcMqMessageFeignApi opcMqMessageFeignApi;

	/**
	 * Send chat robot msg boolean.
	 *
	 * @param chatRobotMsgDto the chat robot msg dto
	 *
	 * @return the boolean
	 */
	public boolean sendChatRobotMsg(ChatRobotMsgDto chatRobotMsgDto) {
		Wrapper<Boolean> result = dingtalkFeignApi.sendChatRobotMsg(chatRobotMsgDto);
		return result.getResult();
	}

	/**
	 * Delete expire file.
	 */
	public void deleteExpireFile() {
		opcOssFeignApi.deleteExpireFile();
	}

	public Wrapper<PageInfo<MqMessageVo>> queryMessageListWithPage(final MessageQueryDto messageQueryDto) {
		Wrapper<PageInfo<MqMessageVo>> wrapper = opcMqMessageFeignApi.queryMessageListWithPage(messageQueryDto);
		if (wrapper == null) {
			log.error("查询消息记录 失败 result is null");
			throw new TpcBizException(ErrorCodeEnum.GL99990002);
		}
		return wrapper;
	}
}