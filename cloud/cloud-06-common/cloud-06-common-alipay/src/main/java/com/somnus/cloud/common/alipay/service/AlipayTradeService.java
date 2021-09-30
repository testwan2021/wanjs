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
package com.somnus.cloud.common.alipay.service;

/**
 * @ClassName: AlipayTradeService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Somnus
 * @date 2018年10月17日
 */
public interface AlipayTradeService {
	public abstract AlipayF2FPayResult tradePay(AlipayTradePayContentBuilder paramAlipayTradePayContentBuilder);

	public abstract AlipayF2FQueryResult queryTradeResult(String paramString);

	public abstract AlipayF2FRefundResult tradeRefund(AlipayTradeRefundContentBuilder paramAlipayTradeRefundContentBuilder);

	public abstract AlipayF2FPrecreateResult tradePrecreate(AlipayTradePrecreateContentBuilder paramAlipayTradePrecreateContentBuilder);
}
