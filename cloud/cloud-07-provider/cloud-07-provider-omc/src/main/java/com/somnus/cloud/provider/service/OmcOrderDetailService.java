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
import com.somnus.cloud.provider.model.domain.OmcOrderDetail;

/**
 * @ClassName: OmcOrderDetailService
 * @Description: The interface Omc order detail service.
 * @author Somnus
 * @date 2018年10月17日
 */
public interface OmcOrderDetailService extends IService<OmcOrderDetail> {
	/**
	 * 获取用户订单详情.
	 *
	 * @param orderNo the order no
	 * @param userId  the user id
	 *
	 * @return the list by order no user id
	 */
	List<OmcOrderDetail> getListByOrderNoUserId(String orderNo, Long userId);

	/**
	 * Gets list by order no.
	 *
	 * @param orderNo the order no
	 *
	 * @return the list by order no
	 */
	List<OmcOrderDetail> getListByOrderNo(String orderNo);

	/**
	 * Batch insert order detail.
	 *
	 * @param omcOrderDetailList the omc order detail list
	 */
	void batchInsertOrderDetail(List<OmcOrderDetail> omcOrderDetailList);
}