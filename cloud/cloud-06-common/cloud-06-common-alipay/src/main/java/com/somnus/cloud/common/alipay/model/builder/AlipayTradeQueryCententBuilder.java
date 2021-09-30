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
package com.somnus.cloud.common.alipay.model.builder;

/**
 * @ClassName: AlipayTradeQueryCententBuilder
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Somnus
 * @date 2018年10月17日
 */
public class AlipayTradeQueryCententBuilder extends RequestBuilder {

	@SerializedName("trade_no")
	private String tradeNo;

	@SerializedName("out_trade_no")
	private String outTradeNo;

	public boolean validate() {
		if ((StringUtils.isEmpty(this.tradeNo)) && (StringUtils.isEmpty(this.outTradeNo))) {
			throw new IllegalStateException("tradeNo and outTradeNo can not both be NULL!");
		}
		return true;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("AlipayTradeQueryCententBuilder{");
		sb.append("tradeNo='").append(this.tradeNo).append('\'');
		sb.append(", outTradeNo='").append(this.outTradeNo).append('\'');
		sb.append('}');
		return sb.toString();
	}

	public String getTradeNo() {
		return this.tradeNo;
	}

	public AlipayTradeQueryCententBuilder setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
		return this;
	}

	public String getOutTradeNo() {
		return this.outTradeNo;
	}

	public AlipayTradeQueryCententBuilder setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
		return this;
	}
}