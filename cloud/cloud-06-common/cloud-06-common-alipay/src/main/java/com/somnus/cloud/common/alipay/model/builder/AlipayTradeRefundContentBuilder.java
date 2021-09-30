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
 * @ClassName: AlipayTradeRefundContentBuilder
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Somnus
 * @date 2018年10月17日
 */
public class AlipayTradeRefundContentBuilder extends RequestBuilder {

	@SerializedName("trade_no")
	private String tradeNo;

	@SerializedName("out_trade_no")
	private String outTradeNo;

	@SerializedName("refund_amount")
	private String refundAmount;

	@SerializedName("out_request_no")
	private String outRequestNo;

	@SerializedName("refund_reason")
	private String refundReason;

	@SerializedName("store_id")
	private String storeId;

	@SerializedName("alipay_store_id")
	private String alipayStoreId;

	@SerializedName("terminal_id")
	private String terminalId;

	public boolean validate() {
		if ((StringUtils.isEmpty(this.tradeNo)) && (StringUtils.isEmpty(this.outTradeNo))) {
			throw new NullPointerException("trade_no and out_trade_no should not both be NULL!");
		}
		if (StringUtils.isEmpty(this.refundAmount)) {
			throw new NullPointerException("refund_amount should not be NULL!");
		}
		if (StringUtils.isEmpty(this.refundReason)) {
			throw new NullPointerException("refund_reson should not be NULL!");
		}
		return true;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("AlipayTradeRefundContentBuilder{");
		sb.append("outTradeNo='").append(this.outTradeNo).append('\'');
		if (StringUtils.isNotEmpty(this.tradeNo)) {
			sb.append(", tradeNo='").append(this.tradeNo).append('\'');
		}
		sb.append(", refundAmount='").append(this.refundAmount).append('\'');
		sb.append(", outRequestNo='").append(this.outRequestNo).append('\'');
		sb.append(", refundReason='").append(this.refundReason).append('\'');
		sb.append(", storeId='").append(this.storeId).append('\'');
		sb.append(", alipayStoreId='").append(this.alipayStoreId).append('\'');
		sb.append(", terminalId='").append(this.terminalId).append('\'');
		sb.append('}');
		return sb.toString();
	}

	public String getOutTradeNo() {
		return this.outTradeNo;
	}

	public AlipayTradeRefundContentBuilder setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
		return this;
	}

	public AlipayTradeRefundContentBuilder setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
		return this;
	}

	public AlipayTradeRefundContentBuilder setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
		return this;
	}

	public AlipayTradeRefundContentBuilder setOutRequestNo(String outRequestNo) {
		this.outRequestNo = outRequestNo;
		return this;
	}

	public AlipayTradeRefundContentBuilder setRefundReason(String refundReason) {
		this.refundReason = refundReason;
		return this;
	}

	public AlipayTradeRefundContentBuilder setStoreId(String storeId) {
		this.storeId = storeId;
		return this;
	}

	public AlipayTradeRefundContentBuilder setAlipayStoreId(String alipayStoreId) {
		this.alipayStoreId = alipayStoreId;
		return this;
	}

	public AlipayTradeRefundContentBuilder setTerminalId(String terminalId) {
		this.terminalId = terminalId;
		return this;
	}

	public String getTradeNo() {
		return this.tradeNo;
	}

	public String getRefundAmount() {
		return this.refundAmount;
	}

	public String getOutRequestNo() {
		return this.outRequestNo;
	}

	public String getRefundReason() {
		return this.refundReason;
	}

	public String getStoreId() {
		return this.storeId;
	}

	public String getAlipayStoreId() {
		return this.alipayStoreId;
	}

	public String getTerminalId() {
		return this.terminalId;
	}
}