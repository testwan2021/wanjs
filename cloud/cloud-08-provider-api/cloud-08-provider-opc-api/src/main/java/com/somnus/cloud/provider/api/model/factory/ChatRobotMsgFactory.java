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
package com.somnus.cloud.provider.api.model.factory;

import com.somnus.cloud.provider.api.model.dto.robot.AtDto;
import com.somnus.cloud.provider.api.model.dto.robot.ChatRobotMsgDto;
import com.somnus.cloud.provider.api.model.dto.robot.LinkDto;
import com.somnus.cloud.provider.api.model.dto.robot.MarkdownDto;
import com.somnus.cloud.provider.api.model.dto.robot.TextDto;
import com.somnus.cloud.provider.api.model.enums.RobotMsgTypeEnum;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @ClassName: ChatRobotMsgFactory
 * @Description: 钉钉消息对象创建工厂.
 * @author Somnus
 * @date 2018年10月16日
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatRobotMsgFactory {

	/**
	 * 创建钉钉机器人Markdown消息
	 *
	 * @param webhookToken token
	 * @param text         消息内容
	 * @param title        标题
	 * @param isAtAll      是否@所有人:true,否则为:false
	 * @param atMobiles    被@人的手机号(在text内容里要有@手机号)
	 *
	 * @return chat robot msg dto
	 */
	public static ChatRobotMsgDto createChatRobotMarkdownMsg(String webhookToken, String text, String title,
	                                                         boolean isAtAll, String[] atMobiles) {
		ChatRobotMsgDto entity = new ChatRobotMsgDto();
		entity.setMsgType(RobotMsgTypeEnum.MARKDOWN.getType());
		entity.setWebhookToken(webhookToken);

		MarkdownDto markdownDto = new MarkdownDto();
		markdownDto.setTitle(title);
		markdownDto.setText(text);
		entity.setMarkdown(markdownDto);

		AtDto atDto = new AtDto();
		atDto.setAtAll(isAtAll);
		if (atMobiles != null && atMobiles.length > 0) {
			atDto.setAtMobiles(atMobiles);
		}
		entity.setAt(atDto);
		return entity;
	}

	/**
	 * 创建钉钉机器人Link消息
	 *
	 * @param webhookToken token
	 * @param text         消息内容。如果太长只会部分展示
	 * @param title        消息标题
	 * @param messageUrl   点击消息跳转的URL
	 * @param picUrl       图片URL
	 *
	 * @return chat robot msg dto
	 */
	public static ChatRobotMsgDto createChatRobotLinkMsg(String webhookToken, String text, String title,
	                                                     String messageUrl, String picUrl) {
		ChatRobotMsgDto entity = new ChatRobotMsgDto();
		entity.setMsgType(RobotMsgTypeEnum.LINK.getType());
		entity.setWebhookToken(webhookToken);
		LinkDto linkDto = new LinkDto();
		linkDto.setText(text);
		linkDto.setTitle(title);
		linkDto.setPicUrl(picUrl);
		linkDto.setMessageUrl(messageUrl);
		entity.setLink(linkDto);
		return entity;
	}

	/**
	 * 创建钉钉机器人文本消息
	 *
	 * @param webhookToken token
	 * @param text         消息内容
	 * @param isAtAll      是否@所有人:true,否则为:false
	 * @param atMobiles    被@人的手机号(在text内容里要有@手机号)
	 *
	 * @return chat robot msg dto
	 */
	public static ChatRobotMsgDto createChatRobotTextMsg(String webhookToken, String text,
	                                                     boolean isAtAll, String[] atMobiles) {
		ChatRobotMsgDto entity = new ChatRobotMsgDto();
		entity.setMsgType(RobotMsgTypeEnum.TEXT.getType());
		entity.setWebhookToken(webhookToken);

		TextDto textDto = new TextDto();
		textDto.setContent(text);
		entity.setText(textDto);

		AtDto atDto = new AtDto();
		atDto.setAtAll(isAtAll);
		if (atMobiles != null && atMobiles.length > 0) {
			atDto.setAtMobiles(atMobiles);
		}
		entity.setAt(atDto);
		return entity;
	}

}
