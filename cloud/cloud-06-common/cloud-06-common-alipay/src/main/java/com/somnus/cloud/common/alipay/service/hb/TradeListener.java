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
 * @ClassName: TradeListener
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Somnus
 * @date 2018年10月17日
 */
public abstract class TradeListener {
	public abstract void onPayTradeSuccess(String paramString, long paramLong);

	public abstract void onPayInProgress(String paramString, long paramLong);

	public abstract void onPayFailed(String paramString, long paramLong);

	public abstract void onConnectException(String paramString, long paramLong);

	public abstract void onSendException(String paramString, long paramLong);

	public abstract void onReceiveException(String paramString, long paramLong);
}
