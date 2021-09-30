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
package com.somnus.cloud.provider.job.listener;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.somnus.cloud.provider.api.model.dto.robot.ChatRobotMsgDto;
import com.somnus.cloud.provider.api.model.factory.ChatRobotMsgFactory;
import com.somnus.cloud.provider.service.OpcRpcService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: HandleUserTokenJobListener
 * @Description: The class Handle user token job listener.
 * @author Somnus
 * @date 2018年10月17日
 */
@Slf4j
public class HandleUserTokenJobListener implements ElasticJobListener {

	@Autowired
	private OpcRpcService opcRpcService;

	private AtomicLong startTime = new AtomicLong();
	private AtomicLong endTimestamp = new AtomicLong();
	@Value("cloud.dingTalk.webhookToken.jobListener")
	private String webhookToken;

	/**
	 * Before job executed.
	 *
	 * @param shardingContexts the sharding contexts
	 */
	@Override
	public void beforeJobExecuted(ShardingContexts shardingContexts) {
		log.info("beforeJobExecuted - shardingContexts={}", shardingContexts);
		startTime.set(System.currentTimeMillis());
	}

	/**
	 * After job executed.
	 *
	 * @param shardingContexts the sharding contexts
	 */
	@Override
	public void afterJobExecuted(ShardingContexts shardingContexts) {
		log.info("afterJobExecuted - shardingContexts={}", shardingContexts);
		endTimestamp.set(System.currentTimeMillis());
		long exeTime = endTimestamp.get() - startTime.get();
		String message = String.format("jobName: %s | 执行完毕, 总耗时: %s", shardingContexts.getJobName(), exeTime + "毫秒");
		this.sendDingTalk(message);
	}

	private void sendDingTalk(String message) {
		log.info("开始发送消息. message={}", message);
		ChatRobotMsgDto chatRobotMsgDto = ChatRobotMsgFactory.createChatRobotTextMsg(webhookToken, message, false, null);
		boolean result = opcRpcService.sendChatRobotMsg(chatRobotMsgDto);
		if (result) {
			log.info("发送消息成功. message={}", message);
		} else {
			log.error("发送消息失败. message={}", message);
		}
	}
}