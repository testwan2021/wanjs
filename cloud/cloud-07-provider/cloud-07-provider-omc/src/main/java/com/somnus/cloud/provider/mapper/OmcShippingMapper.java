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
import com.somnus.cloud.provider.model.domain.OmcShipping;

import java.util.List;

/**
 * @ClassName: OmcShippingMapper
 * @Description: The interface Omc shipping mapper.
 * @author Somnus
 * @date 2018年10月17日
 */
@Mapper
@Component
public interface OmcShippingMapper extends MyMapper<OmcShipping> {
	/**
	 * Delete by shipping id user id int.
	 *
	 * @param userId     the user id
	 * @param shippingId the shipping id
	 *
	 * @return the int
	 */
	int deleteByShippingIdUserId(@Param("userId") Long userId, @Param("shippingId") Integer shippingId);

	/**
	 * Select by shipping id user id omc shipping.
	 *
	 * @param userId     the user id
	 * @param shippingId the shipping id
	 *
	 * @return the omc shipping
	 */
	OmcShipping selectByShippingIdUserId(@Param("userId") Long userId, @Param("shippingId") Long shippingId);

	/**
	 * Select by user id list.
	 *
	 * @param userId the user id
	 *
	 * @return the list
	 */
	List<OmcShipping> selectByUserId(@Param("userId") Long userId);

	/**
	 * Select default address by user id omc shipping.
	 *
	 * @param userId the user id
	 *
	 * @return the omc shipping
	 */
	OmcShipping selectDefaultAddressByUserId(Long userId);
}