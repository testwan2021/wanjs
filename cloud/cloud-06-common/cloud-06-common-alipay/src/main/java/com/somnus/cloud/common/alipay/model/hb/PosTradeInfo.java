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
 * @ClassName: PosTradeInfo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Somnus
 * @date 2018年10月17日
 */
public class PosTradeInfo implements TradeInfo {
	
	private HbStatus status;
	private String time;
	private int timeConsume;

	public static PosTradeInfo newInstance(HbStatus status, String time, int timeConsume) {
		PosTradeInfo info = new PosTradeInfo();
		if ((timeConsume > 99) || (timeConsume < 0)) {
			timeConsume = 99;
		}
		info.setTimeConsume(timeConsume);
		info.setStatus(status);
		info.setTime(time);
		return info;
	}

	public String toString() {
		return this.status.name() + this.time
				+ String.format("%02d", new Object[] { Integer.valueOf(this.timeConsume) });
	}

	public HbStatus getStatus() {
		return this.status;
	}

	public void setStatus(HbStatus status) {
		this.status = status;
	}

	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public double getTimeConsume() {
		return this.timeConsume;
	}

	public void setTimeConsume(int timeConsume) {
		this.timeConsume = timeConsume;
	}
}
