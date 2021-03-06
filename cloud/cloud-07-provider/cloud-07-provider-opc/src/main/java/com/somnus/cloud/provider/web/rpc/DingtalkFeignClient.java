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

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.somnus.cloud.common.util.PublicUtil;
import com.somnus.cloud.common.util.ValidateUtil;
import com.somnus.cloud.common.util.wrapper.WrapMapper;
import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.comon.core.support.BaseController;
import com.somnus.cloud.provider.api.model.dto.robot.AtDto;
import com.somnus.cloud.provider.api.model.dto.robot.ChatRobotMsgDto;
import com.somnus.cloud.provider.api.model.dto.robot.LinkDto;
import com.somnus.cloud.provider.api.model.dto.robot.MarkdownDto;
import com.somnus.cloud.provider.api.model.dto.robot.TextDto;
import com.somnus.cloud.provider.api.model.enums.RobotMsgTypeEnum;
import com.somnus.cloud.provider.api.service.DingtalkFeignApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName: DingtalkFeignClient
 * @Description: The class Opc attachment feign client.
 * @author Somnus
 * @date 2018???10???17???
 */
@RestController
@Api(value = "API - DingtalkFeignClient", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DingtalkFeignClient extends BaseController implements DingtalkFeignApi {

	@Override
	@ApiOperation(httpMethod = "POST", value = "??????????????????")
	public Wrapper<Boolean> sendChatRobotMsg(@RequestBody ChatRobotMsgDto chatRobotMsgDto) {
		logger.info("sendChatRobotMsg - ?????????????????????????????????. chatRobotMsgDto = {}", chatRobotMsgDto);
		boolean result;
		try {
			checkChatReBotMsg(chatRobotMsgDto);
			HttpClient httpclient = HttpClients.createDefault();
			String webhookToken = "https://oapi.dingtalk.com/robot/send?access_token=" + chatRobotMsgDto.getWebhookToken();
			HttpPost httpPost = new HttpPost(webhookToken);
			ObjectMapper mapper = new ObjectMapper();
			String robotJson = mapper.writeValueAsString(chatRobotMsgDto);
			logger.info("robotJson = {}", robotJson);
			httpPost.addHeader("Content-Type", "application/json; charset=utf-8");
			StringEntity se = new StringEntity(robotJson, "utf-8");
			httpPost.setEntity(se);
			logger.info("robotJson={}", robotJson);
			logger.info("httpPost={}", httpPost);
			HttpResponse response;
			response = httpclient.execute(httpPost);
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				logger.info("????????????????????????????????? response={}", response);
				result = true;
			} else {
				logger.error("????????????????????????????????? response={}", response);
				result = false;
			}
		} catch (IOException e) {
			logger.error("??????????????????,???????????? ex={}", e.getMessage(), e);
			return WrapMapper.error("????????????????????????");
		}
		return WrapMapper.success(result);
	}

	/**
	 * ???????????????
	 */
	private void checkChatReBotMsg(ChatRobotMsgDto chatRobotMsgDto) {

		String webhookToken = chatRobotMsgDto.getWebhookToken();
		String msgType = chatRobotMsgDto.getMsgType();
		Preconditions.checkArgument(PublicUtil.isNotEmpty(webhookToken), "???????????????token??????");
		Preconditions.checkArgument(PublicUtil.isNotEmpty(msgType), "?????????????????????????????????");

		if (RobotMsgTypeEnum.MARKDOWN.getName().equals(msgType)) {
			MarkdownDto markdown = chatRobotMsgDto.getMarkdown();
			String text = markdown.getText();
			String title = markdown.getTitle();

			Preconditions.checkArgument(PublicUtil.isNotEmpty(markdown), "markdown?????????????????????");
			Preconditions.checkArgument(PublicUtil.isNotEmpty(text), "markdown??????????????????");
			Preconditions.checkArgument(PublicUtil.isNotEmpty(title), "markdown??????????????????");
		} else if (RobotMsgTypeEnum.LINK.getName().equals(msgType)) {
			LinkDto link = chatRobotMsgDto.getLink();
			String text = link.getText();
			String title = link.getTitle();
			String messageUrl = link.getMessageUrl();

			Preconditions.checkArgument(PublicUtil.isNotEmpty(link), "link??????????????????");
			Preconditions.checkArgument(PublicUtil.isNotEmpty(text), "link??????????????????");
			Preconditions.checkArgument(PublicUtil.isNotEmpty(title), "link??????????????????");
			Preconditions.checkArgument(PublicUtil.isNotEmpty(messageUrl), "link???????????????????????????URL???");
		} else if (RobotMsgTypeEnum.TEXT.getName().equals(msgType)) {
			TextDto text = chatRobotMsgDto.getText();
			AtDto at = chatRobotMsgDto.getAt();
			String content = text.getContent();

			Preconditions.checkArgument(PublicUtil.isNotEmpty(text), "text??????????????????");
			Preconditions.checkArgument(PublicUtil.isNotEmpty(content), "text???????????????????????????");

			if (PublicUtil.isNotEmpty(at)) {
				String[] atMobiles = at.getAtMobiles();
				if (PublicUtil.isNotEmpty(atMobiles)) {
					for (String atMobile : atMobiles) {
						Preconditions.checkArgument(ValidateUtil.isMobileNumber(atMobile), "????????????:" + atMobile + "????????????");
					}
				}
			}

		} else {
			throw new IllegalArgumentException("?????????????????????????????????");
		}
	}
}
