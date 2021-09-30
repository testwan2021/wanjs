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
package com.somnus.cloud.provider.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.somnus.cloud.comon.core.mybatis.MyMapper;
import com.somnus.cloud.provider.model.domain.TpcMqConsumer;
import com.somnus.cloud.provider.model.vo.TpcMqConsumerVo;
import com.somnus.cloud.provider.model.vo.TpcMqSubscribeVo;

/**
 * @ClassName: TpcMqConsumerMapper
 * @Description: The interface Tpc mq consumer mapper.
 * @author Somnus
 * @date 2018年10月17日
 */
@Mapper
@Component
public interface TpcMqConsumerMapper extends MyMapper<TpcMqConsumer> {
	/**
	 * 查询消费者列表.
	 *
	 * @param tpcMqConsumer the tpc mq consumer
	 *
	 * @return the list
	 */
	List<TpcMqConsumerVo> listTpcMqConsumerVoWithPage(TpcMqConsumer tpcMqConsumer);

	/**
	 * 分页查询MQ订阅列表.
	 *
	 * @param tpcMqConsumer the tpc mq consumer
	 *
	 * @return the list
	 */
	List<TpcMqSubscribeVo> listTpcMqSubscribeVoWithPage(TpcMqConsumer tpcMqConsumer);

	/**
	 * Delete subscribe tag by tag id int.
	 *
	 * @param tagId the tag id
	 *
	 * @return the int
	 */
	int deleteSubscribeTagByTagId(@Param("tagId") Long tagId);

	/**
	 * List subscribe id by consumer id list.
	 *
	 * @param consumerId the consumer id
	 *
	 * @return the list
	 */
	List<Long> listSubscribeIdByConsumerId(@Param("consumerId") Long consumerId);

	/**
	 * Delete subscribe by consumer id.
	 *
	 * @param consumerId the consumer id
	 *
	 * @return the int
	 */
	int deleteSubscribeByConsumerId(@Param("consumerId") Long consumerId);

	/**
	 * Delete subscribe tag by subscribe id list.
	 *
	 * @param subscribeIdList the subscribe id list
	 *
	 * @return the int
	 */
	int deleteSubscribeTagBySubscribeIdList(@Param("subscribeIdList") List<Long> subscribeIdList);

	/**
	 * List subscribe vo list.
	 *
	 * @param subscribeIdList the subscribe id list
	 *
	 * @return the list
	 */
	List<TpcMqSubscribeVo> listSubscribeVo(@Param("subscribeIdList") List<Long> subscribeIdList);

	/**
	 * List consumer group by topic list.
	 *
	 * @param topic the topic
	 *
	 * @return the list
	 */
	List<String> listConsumerGroupByTopic(@Param("topic") String topic);

	/**
	 * Gets by cid.
	 *
	 * @param consumerGroup the consumer group
	 *
	 * @return the by cid
	 */
	TpcMqConsumer getByCid(String consumerGroup);
}