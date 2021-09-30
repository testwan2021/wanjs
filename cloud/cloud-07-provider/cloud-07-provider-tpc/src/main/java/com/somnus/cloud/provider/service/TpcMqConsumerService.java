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

import java.util.List;

import com.somnus.cloud.comon.core.support.IService;
import com.somnus.cloud.provider.model.domain.TpcMqConsumer;
import com.somnus.cloud.provider.model.vo.TpcMqConsumerVo;
import com.somnus.cloud.provider.model.vo.TpcMqSubscribeVo;

/**
 * @ClassName: TpcMqConsumerService
 * @Description: The interface Tpc mq consumer service.
 * @author Somnus
 * @date 2018年10月17日
 */
public interface TpcMqConsumerService extends IService<TpcMqConsumer> {
	/**
	 * 查询Mq消费者列表.
	 *
	 * @param tpcMqConsumer the tpc mq consumer
	 *
	 * @return the list
	 */
	List<TpcMqConsumerVo> listConsumerVoWithPage(TpcMqConsumer tpcMqConsumer);

	/**
	 * 查询订阅者列表.
	 *
	 * @param tpcMqConsumer the tpc mq consumer
	 *
	 * @return the list
	 */
	List<TpcMqSubscribeVo> listSubscribeVoWithPage(TpcMqConsumer tpcMqConsumer);

	/**
	 * Delete by tag id.
	 *
	 * @param tagId the tag id
	 *
	 * @return the int
	 */
	int deleteSubscribeTagByTagId(Long tagId);

	/**
	 * 根据消费者ID删除消费者.
	 *
	 * @param id the id
	 *
	 * @return the int
	 */
	int deleteConsumerById(Long id);

	/**
	 * List subscribe vo list.
	 *
	 * @param subscribeIdList the subscribe id list
	 *
	 * @return the list
	 */
	List<TpcMqSubscribeVo> listSubscribeVo(List<Long> subscribeIdList);

	/**
	 * List consumer group by topic list.
	 *
	 * @param topic the topic
	 *
	 * @return the list
	 */
	List<String> listConsumerGroupByTopic(String topic);

	/**
	 * 根据cid更新生产者状态为在线.
	 *
	 * @param consumerGroup the consumer group
	 */
	void updateOnLineStatusByCid(String consumerGroup);

	/**
	 * 根据cid更新生产者状态为离线.
	 *
	 * @param consumerGroup the consumer group
	 */
	void updateOffLineStatusByCid(String consumerGroup);
}
