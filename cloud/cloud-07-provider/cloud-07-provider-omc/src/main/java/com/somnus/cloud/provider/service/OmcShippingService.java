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

import com.github.pagehelper.PageInfo;
import com.somnus.cloud.common.base.dto.LoginAuthDto;
import com.somnus.cloud.comon.core.support.IService;
import com.somnus.cloud.provider.model.domain.OmcShipping;

import java.util.List;

/**
 * @ClassName: OmcShippingService
 * @Description: The interface Omc shipping service.
 * @author Somnus
 * @date 2018年10月17日
 */
public interface OmcShippingService extends IService<OmcShipping> {
	/**
	 * 编辑收货人地址.
	 *
	 * @param loginAuthDto the login auth dto
	 * @param shipping     the shipping
	 *
	 * @return the int
	 */
	int saveShipping(LoginAuthDto loginAuthDto, OmcShipping shipping);

	/**
	 * 删除收货人地址.
	 *
	 * @param userId     the user id
	 * @param shippingId the shipping id
	 *
	 * @return the int
	 */
	int deleteShipping(Long userId, Integer shippingId);

	/**
	 * 根据Id查询收货人地址.
	 *
	 * @param userId     the user id
	 * @param shippingId the shipping id
	 *
	 * @return the omc shipping
	 */
	OmcShipping selectByShippingIdUserId(Long userId, Long shippingId);

	/**
	 * Query shipping list list.
	 *
	 * @param omcShipping the omc shipping
	 *
	 * @return the list
	 */
	List<OmcShipping> queryShippingList(OmcShipping omcShipping);

	/**
	 * 分页查询收货人地址列表.
	 *
	 * @param omcShipping the omc shipping
	 *
	 * @return the page info
	 */
	PageInfo<OmcShipping> queryShippingListWithPage(OmcShipping omcShipping);

	/**
	 * 分页查询当前用户收货人地址列表.
	 *
	 * @param userId   the user id
	 * @param pageNum  the page num
	 * @param pageSize the page size
	 *
	 * @return the page info
	 */
	PageInfo<OmcShipping> queryListWithPageByUserId(Long userId, int pageNum, int pageSize);

	/**
	 * Select by user id list.
	 *
	 * @param userId the user id
	 *
	 * @return the list
	 */
	List<OmcShipping> selectByUserId(Long userId);

	/**
	 * 设置默认收货地址.
	 *
	 * @param loginAuthDto the login auth dto
	 * @param addressId    the address id
	 *
	 * @return the default address
	 */
	int setDefaultAddress(LoginAuthDto loginAuthDto, Long addressId);
}