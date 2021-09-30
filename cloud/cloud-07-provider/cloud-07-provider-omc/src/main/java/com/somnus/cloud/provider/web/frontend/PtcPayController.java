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

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.google.common.collect.Maps;
import com.somnus.cloud.common.base.dto.LoginAuthDto;
import com.somnus.cloud.common.util.wrapper.WrapMapper;
import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.comon.core.support.BaseController;
import com.somnus.cloud.provider.api.model.constant.PtcApiConstant;
import com.somnus.cloud.provider.service.PtcAlipayService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName: PtcPayController
 * @Description: The class Ptc pay controller.
 * @author Somnus
 * @date 2018年10月17日
 */
@RestController
@RequestMapping(value = "/pay")
@Api(value = "WEB - PtcPayController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PtcPayController extends BaseController {
	@Autowired
	private PtcAlipayService ptcAlipayService;

	/**
	 * 生成付款二维码.
	 *
	 * @param orderNo the order no
	 *
	 * @return the wrapper
	 */
	@PostMapping("/createQrCodeImage/{orderNo}")
	@ApiOperation(httpMethod = "POST", value = "生成付款二维码")
	public Wrapper<?> createQrCodeImage(@PathVariable String orderNo) {
		LoginAuthDto loginAuthDto = getLoginAuthDto();
		return ptcAlipayService.pay(orderNo, loginAuthDto);
	}

	/**
	 * 支付宝回调信息.
	 *
	 * @param request the request
	 *
	 * @return the object
	 */
	@PostMapping("/alipayCallback")
	@ApiOperation(httpMethod = "POST", value = "支付宝回调信息")
	public Object alipayCallback(HttpServletRequest request) {
		logger.info("收到支付宝回调信息");
		Map<String, String> params = Maps.newHashMap();

		Map<String, String[]> requestParams = request.getParameterMap();
		for (Object o : requestParams.keySet()) {
			String name = (String) o;
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {

				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		logger.info("支付宝回调,sign:{},trade_status:{},参数:{}", params.get("sign"), params.get("trade_status"), params.toString());

		//非常重要,验证回调的正确性,是不是支付宝发的.并且呢还要避免重复通知.

		params.remove("sign_type");
		try {
			boolean alipayRSACheckedV2 = AlipaySignature.rsaCheckV2(params, "Configs.getAlipayPublicKey()", "utf-8", "Configs.getSignType()");

			if (!alipayRSACheckedV2) {
				return WrapMapper.error("非法请求,验证不通过,再恶意请求我就报警找网警了");
			}
		} catch (AlipayApiException e) {
			logger.error("支付宝验证回调异常", e);
		}

		//todo 验证各种数据
		Wrapper<?> serverResponse = ptcAlipayService.aliPayCallback(params);
		if (serverResponse.success()) {
			return PtcApiConstant.AlipayCallback.RESPONSE_SUCCESS;
		}
		return PtcApiConstant.AlipayCallback.RESPONSE_FAILED;
	}

}
