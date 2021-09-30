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
package com.somnus.cloud.common.alipay.model.result;

/**
 * @ClassName: AlipayF2FRefundResult
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Somnus
 * @date 2018年10月17日
 */
public class AlipayF2FRefundResult implements Result {
	
	private TradeStatus tradeStatus;
	private AlipayTradeRefundResponse response;

	public AlipayF2FRefundResult(AlipayTradeRefundResponse response) {
		this.response = response;
	}

	public void setTradeStatus(TradeStatus tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public void setResponse(AlipayTradeRefundResponse response) {
		this.response = response;
	}

	public TradeStatus getTradeStatus() {
		return this.tradeStatus;
	}

	public AlipayTradeRefundResponse getResponse() {
		return this.response;
	}

	public boolean isTradeSuccess() {
		return (this.response != null) && (TradeStatus.SUCCESS.equals(this.tradeStatus));
	}
}