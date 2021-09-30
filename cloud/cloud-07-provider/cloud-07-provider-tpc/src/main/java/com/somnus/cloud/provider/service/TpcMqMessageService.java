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

import com.somnus.cloud.common.base.dto.MessageQueryDto;
import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.provider.api.model.dto.TpcMqMessageDto;
import com.somnus.cloud.provider.model.domain.TpcMqMessage;
import com.somnus.cloud.provider.model.dto.MessageTaskQueryDto;
import com.somnus.cloud.provider.model.vo.TpcMessageVo;

/**
 * @ClassName: TpcMqMessageService
 * @Description: The interface Tpc mq message service.
 * @author Somnus
 * @date 2018年10月17日
 */
public interface TpcMqMessageService {
	/**
	 * 预存储消息.
	 *
	 * @param mqMessageDto the mq message dto
	 */
	void saveMessageWaitingConfirm(TpcMqMessageDto mqMessageDto);


	/**
	 * 确认并发送消息.
	 *
	 * @param messageKey the message key
	 */
	void confirmAndSendMessage(String messageKey);


	/**
	 * 存储并发送消息.
	 *
	 * @param mqMessageDto the mq message dto
	 */
	void saveAndSendMessage(TpcMqMessageDto mqMessageDto);


	/**
	 * 直接发送消息.
	 *
	 * @param body       the body
	 * @param topic      the topic
	 * @param tag        the tag
	 * @param key        the key
	 * @param pid        the pid
	 * @param delayLevel the delay level
	 */
	void directSendMessage(String body, String topic, String tag, String key, String pid, Integer delayLevel);


	/**
	 * 重发消息.
	 *
	 * @param messageId the message id
	 */
	void resendMessageByMessageId(Long messageId);


	/**
	 * 根据messageId重发某条消息.
	 *
	 * @param messageKey the message key
	 */
	void resendMessageByMessageKey(String messageKey);


	/**
	 * 将消息标记为死亡消息.
	 *
	 * @param messageId the message id
	 */
	void setMessageToAlreadyDead(Long messageId);

	/**
	 * 根据消息ID删除消息
	 *
	 * @param messageKey the message key
	 */
	void deleteMessageByMessageKey(String messageKey);


	/**
	 * 重发某个消息队列中的全部已死亡的消息.
	 *
	 * @param topicName the topic name
	 * @param batchSize the batch size
	 */
	void resendAllDeadMessageByTopicName(String topicName, int batchSize);

	/**
	 * List message for waiting process list.
	 *
	 * @param query the query
	 *
	 * @return the list
	 */
	List<TpcMqMessage> listMessageForWaitingProcess(MessageTaskQueryDto query);

	/**
	 * 确认收到消息.
	 *
	 * @param cid        the cid
	 * @param messageKey the message key
	 */
	void confirmReceiveMessage(String cid, String messageKey);

	/**
	 * 确认消费消息.
	 *
	 * @param cid        the cid
	 * @param messageKey the message key
	 */
	void confirmConsumedMessage(String cid, String messageKey);

	/**
	 * 更新消息确认状态.
	 *
	 * @param cid        the cid
	 * @param messageKey the message key
	 *
	 * @return the int
	 */
	int updateMqConfirmStatus(String cid, String messageKey);

	/**
	 * 根据topic创建待确认的消息确认列表.
	 *
	 * @param topic      the topic
	 * @param messageId  the message id
	 * @param messageKey the message key
	 */
	void createMqConfirmListByTopic(String topic, Long messageId, String messageKey);

	/**
	 * 查询发送中且超时的消息.
	 *
	 * @param query the query
	 *
	 * @return the list
	 */
	List<String> queryWaitingConfirmMessageKeyList(MessageTaskQueryDto query);

	/**
	 * Handle waiting confirm message.
	 *
	 * @param deleteKeyList the delete key list
	 * @param resendKeyList the resend key list
	 */
	void handleWaitingConfirmMessage(List<String> deleteKeyList, List<String> resendKeyList);

	/**
	 * 更新任务状态.
	 *
	 * @param message the message
	 *
	 * @return the int
	 */
	int updateMqMessageTaskStatus(TpcMqMessage message);

	/**
	 * Update mq message status.
	 *
	 * @param update the update
	 *
	 * @return the int
	 */
	int updateMqMessageStatus(TpcMqMessage update);

	/**
	 * Query record list with page page info.
	 *
	 * @param tpcMessageQueryDto the tpc message query dto
	 *
	 * @return the page info
	 */
	Wrapper<?> queryRecordListWithPage(MessageQueryDto tpcMessageQueryDto);

	/**
	 * List reliable message vo list.
	 *
	 * @param tpcMessageQueryDto the tpc message query dto
	 *
	 * @return the list
	 */
	List<TpcMessageVo> listReliableMessageVo(MessageQueryDto tpcMessageQueryDto);

	/**
	 * List reliable message vo list.
	 *
	 * @param messageIdList the message id list
	 *
	 * @return the list
	 */
	List<TpcMessageVo> listReliableMessageVo(List<Long> messageIdList);
}
