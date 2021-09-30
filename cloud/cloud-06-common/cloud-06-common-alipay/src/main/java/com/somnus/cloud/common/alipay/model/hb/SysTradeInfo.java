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
package com.somnus.cloud.common.alipay.model.hb;

/**
 * @ClassName: SysTradeInfo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Somnus
 * @date 2018年10月17日
 */
public class SysTradeInfo implements TradeInfo {

	@SerializedName("OTN")
	private String outTradeNo;

	@SerializedName("TC")
	private double timeConsume;

	@SerializedName("STAT")
	private HbStatus status;

	public static SysTradeInfo newInstance(String outTradeNo, double timeConsume, HbStatus status) {
		SysTradeInfo info = new SysTradeInfo();
		info.setOutTradeNo(outTradeNo);
		if ((timeConsume > 99.0D) || (timeConsume < 0.0D)) {
			timeConsume = 99.0D;
		}
		info.setTimeConsume(timeConsume);
		info.setStatus(status);
		return info;
	}

	public String getOutTradeNo() {
		return this.outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public HbStatus getStatus() {
		return this.status;
	}

	public void setStatus(HbStatus status) {
		this.status = status;
	}

	public double getTimeConsume() {
		return this.timeConsume;
	}

	public void setTimeConsume(double timeConsume) {
		this.timeConsume = timeConsume;
	}
}