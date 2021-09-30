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
package com.somnus.cloud.provider.job.simple;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.elasticjob.lite.annotation.ElasticSimpleJob;
import com.somnus.cloud.common.base.constant.AliyunMqTopicConstants;
import com.somnus.cloud.common.base.dto.ShardingContextDto;
import com.somnus.cloud.common.config.properties.CloudProperties;
import com.somnus.cloud.common.zk.generator.UniqueIdGenerator;
import com.somnus.cloud.provider.api.model.dto.TpcMqMessageDto;
import com.somnus.cloud.provider.service.TpcMqMessageService;

/**
 * @ClassName: DeleteRpcConsumerMessageJob
 * @Description: 定时清理所有订阅者消费成功的消息数据.
 * @author Somnus
 * @date 2018年10月17日
 */
@ElasticSimpleJob(cron = "0 0 0 1/1 * ?")
public class DeleteRpcConsumerMessageJob implements SimpleJob {
	
	@Autowired
	private CloudProperties cloudProperties;
	
	@Autowired
	private TpcMqMessageService tpcMqMessageService;

	/**
	 * Execute.
	 *
	 * @param shardingContext the sharding context
	 */
	@Override
	public void execute(final ShardingContext shardingContext) {
		ShardingContextDto shardingContextDto = new ShardingContextDto(shardingContext.getShardingTotalCount(), shardingContext.getShardingItem());
		final TpcMqMessageDto message = new TpcMqMessageDto();
		message.setMessageBody(JSON.toJSONString(shardingContextDto));
		message.setMessageTag(AliyunMqTopicConstants.MqTagEnum.DELETE_CONSUMER_MESSAGE.getTag());
		message.setMessageTopic(AliyunMqTopicConstants.MqTopicEnum.TPC_TOPIC.getTopic());
		message.setProducerGroup(cloudProperties.getAliyun().getRocketMq().getProducerGroup());
		String refNo = Long.toString(UniqueIdGenerator.generateId());
		message.setRefNo(refNo);
		message.setMessageKey(refNo);
		tpcMqMessageService.saveAndSendMessage(message);
	}
}
