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
package com.somnus.cloud.provider.api.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.somnus.cloud.common.base.dto.MessageQueryDto;
import com.somnus.cloud.common.base.dto.MqMessageVo;
import com.somnus.cloud.provider.api.model.domain.MqMessageData;

import java.util.List;

/**
 * @ClassName: MqMessageDataMapper
 * @Description: The interface Mq message data mapper.
 * @author Somnus
 * @date 2018年10月16日
 */
@Component
@Mapper
public interface MqMessageDataMapper extends tk.mybatis.mapper.common.Mapper<MqMessageData> {
	/**
	 * 获取7天前消息总数.
	 *
	 * @param shardingTotalCount the sharding total count
	 * @param shardingItem       the sharding item
	 * @param messageType        the message type
	 *
	 * @return the delete total count
	 */
	int getBefore7DayTotalCount(@Param("shardingTotalCount") int shardingTotalCount, @Param("shardingItem") int shardingItem, @Param("messageType") int messageType);

	/**
	 * Gets id list before 7 day.
	 *
	 * @param shardingTotalCount the sharding total count
	 * @param shardingItem       the sharding item
	 * @param messageType        the message type
	 * @param currentPage        the current page
	 * @param pageSize           the page size
	 *
	 * @return the id list before 7 day
	 */
	List<Long> getIdListBefore7Day(@Param("shardingTotalCount") int shardingTotalCount, @Param("shardingItem") int shardingItem, @Param("messageType") int messageType, @Param("currentPage") int currentPage, @Param("pageSize") int pageSize);

	/**
	 * Batch delete by id list.
	 *
	 * @param idList the id list
	 *
	 * @return the int
	 */
	int batchDeleteByIdList(@Param("idList") List<Long> idList);

	/**
	 * Query message key list list.
	 *
	 * @param messageKeyList the message key list
	 *
	 * @return the list
	 */
	List<String> queryMessageKeyList(@Param("messageKeyList") List<String> messageKeyList);

	/**
	 * Query message list with page list.
	 *
	 * @param messageQueryDto the message query dto
	 *
	 * @return the list
	 */
	List<MqMessageVo> queryMessageListWithPage(MessageQueryDto messageQueryDto);
}