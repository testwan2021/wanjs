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
package com.somnus.cloud.provider.web.frontend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.somnus.cloud.common.util.wrapper.WrapMapper;
import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.comon.core.support.BaseController;
import com.somnus.cloud.provider.model.domain.OmcShipping;
import com.somnus.cloud.provider.service.OmcShippingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName: OmcShippingController
 * @Description: The class Omc shipping controller.
 * @author Somnus
 * @date 2018年10月17日
 */
@RestController
@RequestMapping(value = "/shipping", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - OmcShippingController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OmcShippingController extends BaseController {

	@Autowired
	private OmcShippingService omcShippingService;

	/**
	 * 增加收货人地址.
	 *
	 * @param shipping the shipping
	 *
	 * @return the wrapper
	 */
	@PostMapping("/addShipping")
	@ApiOperation(httpMethod = "POST", value = "增加收货人地址")
	public Wrapper<?> addShipping(OmcShipping shipping) {
		logger.info("addShipping - 增加收货人地址. shipping={}", shipping);
		int result = omcShippingService.saveShipping(getLoginAuthDto(), shipping);
		return handleResult(result);

	}

	/**
	 * 删除收货人地址.
	 *
	 * @param shippingId the shipping id
	 *
	 * @return the wrapper
	 */
	@PostMapping("/deleteShipping/{shippingId}")
	@ApiOperation(httpMethod = "POST", value = "删除收货人地址")
	public Wrapper<?> deleteShipping(@PathVariable Integer shippingId) {
		Long userId = getLoginAuthDto().getUserId();
		logger.info("deleteShipping - 删除收货人地址. userId={}, shippingId={}", userId, shippingId);
		int result = omcShippingService.deleteShipping(userId, shippingId);
		return handleResult(result);
	}

	/**
	 * 编辑收货人地址.
	 *
	 * @param shipping the shipping
	 *
	 * @return the wrapper
	 */
	@PostMapping("/updateShipping")
	@ApiOperation(httpMethod = "POST", value = "编辑收货人地址")
	public Wrapper<?> updateShipping(OmcShipping shipping) {
		logger.info("updateShipping - 编辑收货人地址. shipping={}", shipping);
		int result = omcShippingService.saveShipping(getLoginAuthDto(), shipping);
		return handleResult(result);
	}

	/**
	 * 设置默认收货地址.
	 *
	 * @param addressId the address id
	 *
	 * @return the default address
	 */
	@PostMapping("/setDefaultAddress/{addressId}")
	@ApiOperation(httpMethod = "POST", value = "设置默认收货地址")
	public Wrapper<?> setDefaultAddress(@PathVariable Long addressId) {
		logger.info("updateShipping - 设置默认地址. addressId={}", addressId);
		int result = omcShippingService.setDefaultAddress(getLoginAuthDto(), addressId);
		return handleResult(result);
	}

	/**
	 * 根据Id查询收货人地址.
	 *
	 * @param shippingId the shipping id
	 *
	 * @return the wrapper
	 */
	@PostMapping("/selectShippingById/{shippingId}")
	@ApiOperation(httpMethod = "POST", value = "根据Id查询收货人地址")
	public Wrapper<OmcShipping> selectShippingById(@PathVariable Long shippingId) {
		Long userId = getLoginAuthDto().getUserId();
		logger.info("selectShippingById - 根据Id查询收货人地址. userId={}, shippingId={}", userId, shippingId);
		OmcShipping omcShipping = omcShippingService.selectByShippingIdUserId(userId, shippingId);
		return WrapMapper.success(omcShipping);
	}

	/**
	 * 分页查询当前用户收货人地址列表.
	 *
	 * @param shipping the shipping
	 *
	 * @return the wrapper
	 */
	@PostMapping("queryUserShippingListWithPage")
	@ApiOperation(httpMethod = "POST", value = "分页查询当前用户收货人地址列表")
	public Wrapper<?> queryUserShippingListWithPage(@RequestBody OmcShipping shipping) {
		Long userId = getLoginAuthDto().getUserId();
		logger.info("queryUserShippingListWithPage - 分页查询当前用户收货人地址列表.userId={} shipping={}", userId, shipping);
		PageInfo<OmcShipping> pageInfo = omcShippingService.queryListWithPageByUserId(userId, shipping.getPageNum(), shipping.getPageSize());
		return WrapMapper.success(pageInfo);
	}

	/**
	 * 分页查询收货人地址列表.
	 *
	 * @param shipping the shipping
	 *
	 * @return the wrapper
	 */
	@PostMapping("queryShippingListWithPage")
	@ApiOperation(httpMethod = "POST", value = "分页查询收货人地址列表")
	public Wrapper<?> queryShippingListWithPage(@RequestBody OmcShipping shipping) {
		logger.info("queryShippingListWithPage - 分页查询收货人地址列表. shipping={}", shipping);
		PageInfo<OmcShipping> pageInfo = omcShippingService.queryShippingListWithPage(shipping);
		return WrapMapper.success(pageInfo);
	}

}
