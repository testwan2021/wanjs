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
package com.somnus.cloud.provider.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import com.somnus.cloud.common.base.dto.LoginAuthDto;
import com.somnus.cloud.common.base.enums.ErrorCodeEnum;
import com.somnus.cloud.common.util.PublicUtil;
import com.somnus.cloud.comon.core.support.BaseService;
import com.somnus.cloud.provider.api.exceptions.OmcBizException;
import com.somnus.cloud.provider.api.model.constant.OmcApiConstant;
import com.somnus.cloud.provider.mapper.OmcShippingMapper;
import com.somnus.cloud.provider.model.domain.OmcShipping;
import com.somnus.cloud.provider.service.OmcShippingService;

/**
 * @ClassName: OmcShippingServiceImpl
 * @Description: The class Omc shipping service.
 * @author Somnus
 * @date 2018年10月17日
 */
@Service
public class OmcShippingServiceImpl extends BaseService<OmcShipping> implements OmcShippingService {

	@Autowired
	private OmcShippingMapper omcShippingMapper;

	@Override
	public int saveShipping(LoginAuthDto loginAuthDto, OmcShipping shipping) {
		int resultInt;
		shipping.setUpdateInfo(loginAuthDto);
		if (shipping.isNew()) {
			resultInt = omcShippingMapper.insertSelective(shipping);
		} else {
			resultInt = omcShippingMapper.updateByPrimaryKeySelective(shipping);
		}
		return resultInt;
	}

	@Override
	public int deleteShipping(Long userId, Integer shippingId) {

		return omcShippingMapper.deleteByShippingIdUserId(userId, shippingId);
	}

	@Override
	public OmcShipping selectByShippingIdUserId(Long userId, Long shippingId) {
		return omcShippingMapper.selectByShippingIdUserId(userId, shippingId);
	}

	@Override
	public List<OmcShipping> queryShippingList(OmcShipping shipping) {
		return omcShippingMapper.select(shipping);
	}

	@Override
	public PageInfo<OmcShipping> queryShippingListWithPage(OmcShipping shipping) {

		shipping.setOrderBy("update_time desc");
		PageHelper.startPage(shipping.getPageNum(), shipping.getPageSize());
		if (StringUtils.isEmpty(shipping.getReceiverName())) {
			shipping.setReceiverName(null);
		}
		if (StringUtils.isEmpty(shipping.getReceiverMobileNo())) {
			shipping.setReceiverMobileNo(null);
		}
		List<OmcShipping> omcShippingList = this.queryShippingList(shipping);
		return new PageInfo<>(omcShippingList);
	}

	@Override
	public PageInfo<OmcShipping> queryListWithPageByUserId(Long userId, int pageNum, int pageSize) {
		Preconditions.checkArgument(userId != null, ErrorCodeEnum.UAC10011001.getMsg());
		PageHelper.startPage(pageNum, pageSize);
		//TODO 写死 不维护收人列表
		List<OmcShipping> omcShippingList = this.selectByUserId(1L);
		return new PageInfo<>(omcShippingList);
	}

	@Override
	public List<OmcShipping> selectByUserId(Long userId) {
		Preconditions.checkArgument(userId != null, ErrorCodeEnum.UAC10011001.getMsg());
		return omcShippingMapper.selectByUserId(userId);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public int setDefaultAddress(LoginAuthDto loginAuthDto, Long addressId) {
		Long userId = loginAuthDto.getUserId();
		Preconditions.checkArgument(addressId != null, "地址ID不能为空");

		// 1. 查找当前默认地址
		OmcShipping omcShipping = omcShippingMapper.selectDefaultAddressByUserId(userId);
		if (PublicUtil.isEmpty(omcShipping)) {
			throw new OmcBizException(ErrorCodeEnum.OMC10031007);
		}
		// 2. 判断默认地址和当前传入地址是否相同
		if (addressId.equals(omcShipping.getId())) {
			logger.info("所选地址和当前用户默认地址相同 userId={}, addressId={}", userId, addressId);
			return 1;
		}
		// 3. 相同不处理不相同把当前改为非默认, 把当前地址改为默认地址
		setDefault(loginAuthDto, addressId, OmcApiConstant.Shipping.DEFAULT);
		setDefault(loginAuthDto, omcShipping.getId(), OmcApiConstant.Shipping.NOT_DEFAULT);

		return 1;
	}

	private void setDefault(LoginAuthDto loginAuthDto, Long addressId, int isDefault) {
		int result;
		OmcShipping updateNotDefault = new OmcShipping();
		updateNotDefault.setDefaultAddress(isDefault);
		updateNotDefault.setUpdateInfo(loginAuthDto);
		updateNotDefault.setId(addressId);
		result = omcShippingMapper.updateByPrimaryKeySelective(updateNotDefault);
		if (result < 1) {
			throw new OmcBizException(ErrorCodeEnum.OMC10031008, addressId);
		}
	}
}