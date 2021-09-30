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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.somnus.cloud.common.base.constant.GlobalConstant;
import com.somnus.cloud.common.base.enums.ErrorCodeEnum;
import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.provider.api.exceptions.MdcBizException;
import com.somnus.cloud.provider.api.model.dto.gaode.GaodeLocation;
import com.somnus.cloud.provider.api.service.OpcGaodeFeignApi;
import com.somnus.cloud.provider.service.OpcRpcService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: OpcRpcServiceImpl
 * @Description: The class Opc rpc service.
 * @author Somnus
 * @date 2018年10月18日
 */
@Slf4j
@Service
public class OpcRpcServiceImpl implements OpcRpcService {
	
	@Autowired
	private OpcGaodeFeignApi opcGaodeFeignApi;

	@Override
	public String getLocationById(String addressId) {
		try {
			Wrapper<GaodeLocation> wrapper = opcGaodeFeignApi.getLocationByIpAddr(addressId);
			if (wrapper == null) {
				throw new MdcBizException(ErrorCodeEnum.GL99990002);
			}
			if (wrapper.error()) {
				throw new MdcBizException(ErrorCodeEnum.MDC10021002);
			}

			GaodeLocation result = wrapper.getResult();

			assert result != null;
			return result.getProvince().contains("市") ? result.getCity() : result.getProvince() + GlobalConstant.Symbol.SHORT_LINE + result.getCity();
		} catch (Exception e) {
			log.error("getLocationById={}", e.getMessage(), e);
		}
		return null;
	}
}
