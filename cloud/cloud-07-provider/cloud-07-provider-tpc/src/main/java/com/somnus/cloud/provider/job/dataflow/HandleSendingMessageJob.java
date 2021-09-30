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

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dangdang.elasticjob.lite.annotation.ElasticSimpleJob;
import com.google.common.collect.Lists;
import com.somnus.cloud.common.base.dto.JobParameter;
import com.somnus.cloud.provider.api.model.enums.JobTaskStatusEnum;
import com.somnus.cloud.provider.mapper.TpcMqConfirmMapper;
import com.somnus.cloud.provider.model.domain.TpcMqMessage;
import com.somnus.cloud.provider.model.dto.MessageTaskQueryDto;
import com.somnus.cloud.provider.model.enums.MqSendStatusEnum;
import com.somnus.cloud.provider.service.TpcMqMessageService;
import com.xiaoleilu.hutool.date.DateUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: HandleSendingMessageJob
 * @Description: 处理发送中的消息数据.
 * @author Somnus
 * @date 2018年10月17日
 */
@Component
@Slf4j
@ElasticSimpleJob(cron = "0/30 * * * * ?", jobParameter = "fetchNum=200")
public class HandleSendingMessageJob extends AbstractBaseDataflowJob<TpcMqMessage> {
	@Autowired
	private TpcMqMessageService tpcMqMessageService;
	
	@Value("${cloud.message.handleTimeout}")
	private int timeOutMinute;
	
	@Value("${cloud.message.maxSendTimes}")
	private int messageMaxSendTimes;

	@Value("${cloud.message.resendMultiplier}")
	private int messageResendMultiplier;
	
	@Autowired
	private TpcMqConfirmMapper tpcMqConfirmMapper;

	/**
	 * Fetch job data list.
	 *
	 * @param jobParameter the job parameter
	 *
	 * @return the list
	 */
	@Override
	protected List<TpcMqMessage> fetchJobData(JobParameter jobParameter) {
		MessageTaskQueryDto query = new MessageTaskQueryDto();
		query.setCreateTimeBefore(DateUtil.getBeforeTime(timeOutMinute));
		query.setMessageStatus(MqSendStatusEnum.SENDING.sendStatus());
		query.setFetchNum(jobParameter.getFetchNum());
		query.setShardingItem(jobParameter.getShardingItem());
		query.setShardingTotalCount(jobParameter.getShardingTotalCount());
		query.setTaskStatus(JobTaskStatusEnum.TASK_CREATE.status());
		return tpcMqMessageService.listMessageForWaitingProcess(query);
	}

	/**
	 * Process job data.
	 *
	 * @param taskList the task list
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	protected void processJobData(List<TpcMqMessage> taskList) {
		for (TpcMqMessage message : taskList) {

			Integer resendTimes = message.getResendTimes();
			if (resendTimes >= messageMaxSendTimes) {
				tpcMqMessageService.setMessageToAlreadyDead(message.getId());
				continue;
			}

			int times = (resendTimes == 0 ? 1 : resendTimes) * messageResendMultiplier;
			long currentTimeInMillis = Calendar.getInstance().getTimeInMillis();
			long needTime = currentTimeInMillis - times * 60 * 1000;
			long hasTime = message.getUpdateTime().getTime();
			// 判断是否达到了可以再次发送的时间条件
			if (hasTime > needTime) {
				log.debug("currentTime[" + com.xiaoleilu.hutool.date.DateUtil.formatDateTime(new Date()) + "],[SENDING]消息上次发送时间[" + com.xiaoleilu.hutool.date.DateUtil.formatDateTime(message.getUpdateTime()) + "],必须过了[" + times + "]分钟才可以再发送。");
				continue;
			}

			// 前置状态
			List<Integer> preStatusList = Lists.newArrayList(JobTaskStatusEnum.TASK_CREATE.status());
			// 设置任务状态为执行中
			message.setPreStatusList(preStatusList);
			message.setTaskStatus(JobTaskStatusEnum.TASK_EXETING.status());
			int updateRes = tpcMqMessageService.updateMqMessageTaskStatus(message);
			if (updateRes > 0) {
				try {
					// 查询是否全部订阅者都确认了消息 是 则更新消息状态完成, 否则重发消息
					int count = tpcMqConfirmMapper.selectUnConsumedCount(message.getMessageKey());
					int status = JobTaskStatusEnum.TASK_CREATE.status();
					if (count < 1) {
						TpcMqMessage update = new TpcMqMessage();
						update.setMessageStatus(MqSendStatusEnum.FINISH.sendStatus());
						update.setId(message.getId());
						tpcMqMessageService.updateMqMessageStatus(update);
						status = JobTaskStatusEnum.TASK_SUCCESS.status();
					} else {
						tpcMqMessageService.resendMessageByMessageId(message.getId());
					}

					// 前置状态
					preStatusList = Lists.newArrayList(JobTaskStatusEnum.TASK_EXETING.status());
					// 设置任务状态为执行中
					message.setPreStatusList(preStatusList);
					message.setTaskStatus(status);
					tpcMqMessageService.updateMqMessageTaskStatus(message);
				} catch (Exception e) {
					log.error("重发失败 ex={}", e.getMessage(), e);
					// 设置任务状态为执行中
					preStatusList = Lists.newArrayList(JobTaskStatusEnum.TASK_EXETING.status());
					message.setPreStatusList(preStatusList);
					message.setTaskStatus(JobTaskStatusEnum.TASK_SUCCESS.status());
					tpcMqMessageService.updateMqMessageTaskStatus(message);
				}
			}
		}
	}
}
