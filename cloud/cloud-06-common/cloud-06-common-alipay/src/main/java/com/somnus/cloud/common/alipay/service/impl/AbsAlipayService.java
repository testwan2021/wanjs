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
package com.somnus.cloud.common.alipay.service.impl;

/**
 * @ClassName: AbsAlipayService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Somnus
 * @date 2018年10月17日
 */
public abstract class AbsAlipayService {
	protected void validateBuilder(RequestBuilder builder) {
		if (builder == null) {
			throw new NullPointerException("builder should not be NULL!");
		}

		if (!builder.validate())
			throw new IllegalStateException("builder validate failed! " + builder.toString());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected AlipayResponse getResponse(AlipayClient client, AlipayRequest request) {
		try {
			AlipayResponse response = client.execute(request);
			if (response != null) {
				this.log.info(response.getBody());
			}
			return response;
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return null;
	}
}
