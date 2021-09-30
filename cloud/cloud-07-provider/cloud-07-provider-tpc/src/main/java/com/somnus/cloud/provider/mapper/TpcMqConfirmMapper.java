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
import com.somnus.cloud.provider.model.domain.TpcMqConfirm;

/**
 * @ClassName: TpcMqConfirmMapper
 * @Description: The interface Tpc mq confirm mapper.
 * @author Somnus
 * @date 2018年10月17日
 */
@Component
@Mapper
public interface TpcMqConfirmMapper extends MyMapper<TpcMqConfirm> {
	/**
	 * Confirm receive message.
	 *
	 * @param confirmId the confirm id
	 */
	void confirmReceiveMessage(@Param("cid") Long confirmId);

	/**
	 * Confirm consumed message.
	 *
	 * @param confirmId the confirm id
	 */
	void confirmConsumedMessage(@Param("cid") Long confirmId);

	/**
	 * Gets id mq confirm.
	 *
	 * @param cid        the cid
	 * @param messageKey the message key
	 *
	 * @return the id mq confirm
	 */
	Long getIdMqConfirm(@Param("cid") String cid, @Param("messageKey") String messageKey);

	/**
	 * Batch create mq confirm int.
	 *
	 * @param list the list
	 *
	 * @return the int
	 */
	int batchCreateMqConfirm(@Param("tpcMqConfirmList") List<TpcMqConfirm> list);

	/**
	 * 查询没有消费的数量.
	 *
	 * @param messageKey the message key
	 *
	 * @return the int
	 */
	int selectUnConsumedCount(@Param("messageKey") String messageKey);
}