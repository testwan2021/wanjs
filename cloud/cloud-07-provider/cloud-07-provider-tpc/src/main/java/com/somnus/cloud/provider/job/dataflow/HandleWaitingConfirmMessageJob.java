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
package com.somnus.cloud.provider.job.dataflow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dangdang.elasticjob.lite.annotation.ElasticSimpleJob;
import com.google.common.collect.Lists;
import com.somnus.cloud.common.base.dto.JobParameter;
import com.somnus.cloud.provider.api.model.enums.JobTaskStatusEnum;
import com.somnus.cloud.provider.model.dto.MessageTaskQueryDto;
import com.somnus.cloud.provider.model.enums.MqSendStatusEnum;
import com.somnus.cloud.provider.service.TpcMqMessageService;
import com.somnus.cloud.provider.service.UacRpcService;
import com.xiaoleilu.hutool.date.DateUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: HandleWaitingConfirmMessageJob
 * @Description: 处理待确认的消息数据.
 * @author Somnus
 * @date 2018年10月17日
 */
@Slf4j
@Component
@ElasticSimpleJob(cron = "0 0/10 * * * ?", jobParameter = "fetchNum=1000")
public class HandleWaitingConfirmMessageJob extends AbstractBaseDataflowJob<String> {
	@Autowired
	private TpcMqMessageService tpcMqMessageService;
	
	@Autowired
	private UacRpcService uacRpcService;
	
	@Value("${cloud.message.handleTimeout}")
	private int timeOutMinute;
	
	private static final String PID_UAC = "PID_UAC";

	/**
	 * Fetch job data list.
	 *
	 * @param jobParameter the job parameter
	 *
	 * @return the list
	 */
	@Override
	protected List<String> fetchJobData(JobParameter jobParameter) {
		MessageTaskQueryDto query = new MessageTaskQueryDto();
		query.setCreateTimeBefore(DateUtil.getBeforeTime(timeOutMinute));
		query.setMessageStatus(MqSendStatusEnum.WAIT_SEND.sendStatus());
		query.setFetchNum(jobParameter.getFetchNum());
		query.setShardingItem(jobParameter.getShardingItem());
		query.setShardingTotalCount(jobParameter.getShardingTotalCount());
		query.setTaskStatus(JobTaskStatusEnum.TASK_CREATE.status());
		query.setProducerGroup(PID_UAC);
		return tpcMqMessageService.queryWaitingConfirmMessageKeyList(query);
	}

	/**
	 * Process job data.
	 *
	 * @param messageKeyList the message key list
	 */
	@Override
	protected void processJobData(List<String> messageKeyList) {
		if (messageKeyList == null) {
			return;
		}
		List<String> resendMessageList = uacRpcService.queryWaitingConfirmMessageKeyList(messageKeyList);
		if (resendMessageList == null) {
			resendMessageList = Lists.newArrayList();
		}
		messageKeyList.removeAll(resendMessageList);
		tpcMqMessageService.handleWaitingConfirmMessage(messageKeyList, resendMessageList);
	}
}
