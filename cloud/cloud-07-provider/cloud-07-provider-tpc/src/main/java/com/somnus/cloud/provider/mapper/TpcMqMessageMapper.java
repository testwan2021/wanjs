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

import com.somnus.cloud.common.base.dto.MessageQueryDto;
import com.somnus.cloud.comon.core.mybatis.MyMapper;
import com.somnus.cloud.provider.model.domain.TpcMqMessage;
import com.somnus.cloud.provider.model.dto.MessageTaskQueryDto;
import com.somnus.cloud.provider.model.vo.TpcMessageVo;

/**
 * @ClassName: TpcMqMessageMapper
 * @Description: The interface Tpc mq message mapper.
 * @author Somnus
 * @date 2018年10月17日
 */
@Component
@Mapper
public interface TpcMqMessageMapper extends MyMapper<TpcMqMessage> {
	/**
	 * Gets message by message key.
	 *
	 * @param messageKey the message key
	 *
	 * @return the message by message key
	 */
	TpcMqMessage getByMessageKey(@Param("messageKey") String messageKey);

	/**
	 * Delete message by message key int.
	 *
	 * @param messageKey the message key
	 *
	 * @return the int
	 */
	int deleteMessageByMessageKey(@Param("messageKey") String messageKey);

	/**
	 * Update already dead by task id.
	 *
	 * @param messageId the message id
	 *
	 * @return the int
	 */
	int updateAlreadyDeadByMessageId(@Param("messageId") Long messageId);

	/**
	 * Add task exe count by id int.
	 *
	 * @param messageId the message id
	 *
	 * @return the int
	 */
	int addTaskExeCountById(@Param("messageId") Long messageId);

	/**
	 * Query waiting confirm message key list list.
	 *
	 * @param query the query
	 *
	 * @return the list
	 */
	List<String> queryWaitingConfirmMessageKeyList(MessageTaskQueryDto query);

	/**
	 * Batch delete message int.
	 *
	 * @param deleteKeyList the delete key list
	 *
	 * @return the int
	 */
	int batchDeleteMessage(@Param("messageKeyList") List<String> deleteKeyList);

	/**
	 * Update mq task message status int.
	 *
	 * @param message the message
	 *
	 * @return the int
	 */
	int updateMqMessageTaskStatus(TpcMqMessage message);

	/**
	 * List message for waiting process list.
	 *
	 * @param query the query
	 *
	 * @return the list
	 */
	List<TpcMqMessage> listMessageForWaitingProcess(MessageTaskQueryDto query);

	/**
	 * List reliable message vo with page list.
	 *
	 * @param messageQueryDto the message query dto
	 *
	 * @return the list
	 */
	List<TpcMessageVo> listReliableMessageVoWithPage(MessageQueryDto messageQueryDto);

	/**
	 * List reliable message vo list.
	 *
	 * @param messageIdList the message id list
	 *
	 * @return the list
	 */
	List<TpcMessageVo> listReliableMessageVo(@Param("messageIdList") List<Long> messageIdList);
}