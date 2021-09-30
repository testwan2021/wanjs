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

import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.provider.api.model.dto.oss.ElementImgUrlDto;
import com.somnus.cloud.provider.api.model.dto.oss.OptBatchGetUrlRequest;
import com.somnus.cloud.provider.api.model.dto.oss.OptGetUrlRequest;
import com.somnus.cloud.provider.api.model.dto.robot.ChatRobotMsgDto;
import com.somnus.cloud.provider.api.service.DingtalkFeignApi;
import com.somnus.cloud.provider.api.service.OpcOssFeignApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName: OpcRpcService
 * @Description: The class Opc rpc service.
 * @author Somnus
 * @date 2018年10月17日
 */
@Component
public class OpcRpcService {

	@Autowired
	private DingtalkFeignApi dingtalkFeignApi;
	
	@Autowired
	private OpcOssFeignApi opcOssFeignApi;

	public boolean sendChatRobotMsg(ChatRobotMsgDto chatRobotMsgDto) {
		Wrapper<Boolean> result = dingtalkFeignApi.sendChatRobotMsg(chatRobotMsgDto);
		return result.getResult();
	}

	public List<ElementImgUrlDto> listFileUrl(OptBatchGetUrlRequest urlRequest) {
		Wrapper<List<ElementImgUrlDto>> result = opcOssFeignApi.listFileUrl(urlRequest);
		return result.getResult();
	}

	public String getFileUrl(final OptGetUrlRequest request) {
		Wrapper<String> result = opcOssFeignApi.getFileUrl(request);
		return result.getResult();
	}
}