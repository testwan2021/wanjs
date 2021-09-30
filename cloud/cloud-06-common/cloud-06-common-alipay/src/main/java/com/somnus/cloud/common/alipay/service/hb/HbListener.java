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
package com.somnus.cloud.common.alipay.service.hb;

/**
 * @ClassName: HbListener
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Somnus
 * @date 2018年10月17日
 */
public class HbListener implements TradeListener {
	
	private static Log log = LogFactory.getLog(HbListener.class);

	private void offerTradeInfo(String outTradeNo, long beforeCall, HbStatus status) {
		long afterCall = System.currentTimeMillis();
		double timeConsume = (afterCall - beforeCall) / 1000.0D;
		log.debug(outTradeNo + " offer " + status + ": " + timeConsume);
		HbQueue.offer(SysTradeInfo.newInstance(outTradeNo, timeConsume, status));
	}

	public void onPayTradeSuccess(String outTradeNo, long beforeCall) {
		offerTradeInfo(outTradeNo, beforeCall, HbStatus.S);
	}

	public void onPayInProgress(String outTradeNo, long beforeCall) {
		offerTradeInfo(outTradeNo, beforeCall, HbStatus.I);
	}

	public void onPayFailed(String outTradeNo, long beforeCall) {
		offerTradeInfo(outTradeNo, beforeCall, HbStatus.F);
	}

	public void onConnectException(String outTradeNo, long beforeCall) {
		offerTradeInfo(outTradeNo, beforeCall, HbStatus.X);
	}

	public void onSendException(String outTradeNo, long beforeCall) {
		offerTradeInfo(outTradeNo, beforeCall, HbStatus.Y);
	}

	public void onReceiveException(String outTradeNo, long beforeCall) {
		offerTradeInfo(outTradeNo, beforeCall, HbStatus.Z);
	}
}
