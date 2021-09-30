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

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.somnus.cloud.comon.core.mybatis.MyMapper;
import com.somnus.cloud.provider.api.model.dto.OrderPageQuery;
import com.somnus.cloud.provider.api.model.vo.OrderDocVo;
import com.somnus.cloud.provider.model.domain.OmcOrder;

import java.util.List;

/**
 * @ClassName: OmcOrderMapper
 * @Description: The interface Omc order mapper.
 * @author Somnus
 * @date 2018年10月17日
 */
@Mapper
@Component
public interface OmcOrderMapper extends MyMapper<OmcOrder> {
	/**
	 * Select by user id and order no omc order.
	 *
	 * @param userId  the user id
	 * @param orderNo the order no
	 *
	 * @return the omc order
	 */
	OmcOrder selectByUserIdAndOrderNo(@Param("userId") Long userId, @Param("orderNo") String orderNo);

	/**
	 * Select by order no omc order.
	 *
	 * @param orderNo the order no
	 *
	 * @return the omc order
	 */
	OmcOrder selectByOrderNo(String orderNo);

	/**
	 * Select by user id list.
	 *
	 * @param userId the user id
	 *
	 * @return the list
	 */
	List<OmcOrder> selectByUserId(Long userId);

	/**
	 * Select all order list.
	 *
	 * @return the list
	 */
	List<OmcOrder> selectAllOrder();

	/**
	 * Query order list with page list.
	 *
	 * @param orderPageQuery the order page query
	 *
	 * @return the list
	 */
	List<OrderDocVo> queryOrderListWithPage(OrderPageQuery orderPageQuery);
}