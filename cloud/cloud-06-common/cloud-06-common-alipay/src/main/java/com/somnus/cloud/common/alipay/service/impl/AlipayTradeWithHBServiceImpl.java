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
 * @ClassName: AlipayTradeWithHBServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Somnus
 * @date 2018年10月17日
 */
public class AlipayTradeWithHBServiceImpl extends AbsAlipayTradeService {
	
	private TradeListener listener;

	public AlipayTradeWithHBServiceImpl(ClientBuilder builder) {
		if (StringUtils.isEmpty(builder.getGatewayUrl())) {
			throw new NullPointerException("gatewayUrl should not be NULL!");
		}
		if (StringUtils.isEmpty(builder.getAppid())) {
			throw new NullPointerException("appid should not be NULL!");
		}
		if (StringUtils.isEmpty(builder.getPrivateKey())) {
			throw new NullPointerException("privateKey should not be NULL!");
		}
		if (StringUtils.isEmpty(builder.getFormat())) {
			throw new NullPointerException("format should not be NULL!");
		}
		if (StringUtils.isEmpty(builder.getCharset())) {
			throw new NullPointerException("charset should not be NULL!");
		}
		if (StringUtils.isEmpty(builder.getAlipayPublicKey())) {
			throw new NullPointerException("alipayPublicKey should not be NULL!");
		}
		if (builder.getListener() == null) {
			throw new NullPointerException("listener should not be NULL!");
		}

		this.listener = builder.getListener();
		this.client = new DefaultAlipayClient(builder.getGatewayUrl(), builder.getAppid(), builder.getPrivateKey(),
				builder.getFormat(), builder.getCharset(), builder.getAlipayPublicKey());
	}

	private AlipayTradePayResponse getResponse(AlipayClient client, AlipayTradePayRequest request, final String outTradeNo,
			final long beforeCall) {
		try {
			AlipayTradePayResponse response = (AlipayTradePayResponse) client.execute(request);
			if (response != null) {
				this.log.info(response.getBody());
			}
			return response;
		} catch (AlipayApiException e) {
			Throwable cause = e.getCause();

			if (((cause instanceof ConnectException)) || ((cause instanceof NoRouteToHostException))) {
				executorService.submit(new Runnable() {
					public void run() {
						listener.onConnectException(outTradeNo, beforeCall);
					}
				});
			} else if ((cause instanceof SocketException)) {
				executorService.submit(new Runnable() {
					public void run() {
						listener.onSendException(outTradeNo, beforeCall);
					}
				});
			} else if ((cause instanceof SocketTimeoutException)) {
				executorService.submit(new Runnable() {
					public void run() {
						listener.onReceiveException(outTradeNo, beforeCall);
					}
				});
			}
			e.printStackTrace();
		}
		return null;
	}

	public AlipayF2FPayResult tradePay(AlipayTradePayContentBuilder builder) {
		validateBuilder(builder);

		final String outTradeNo = builder.getOutTradeNo();

		AlipayTradePayRequest request = new AlipayTradePayRequest();
		request.setBizContent(builder.toJsonString());
		this.log.info("trade.pay bizContent:" + request.getBizContent());

		final long beforeCall = System.currentTimeMillis();
		AlipayTradePayResponse response = getResponse(this.client, request, outTradeNo, beforeCall);

		AlipayF2FPayResult result = new AlipayF2FPayResult(response);
		if ((response != null) && ("10000".equals(response.getCode()))) {
			result.setTradeStatus(TradeStatus.SUCCESS);

			executorService.submit(new Runnable() {
				public void run() {
					listener.onPayTradeSuccess(outTradeNo, beforeCall);
				}
			});
		} else {
			if ((response != null) && ("10003".equals(response.getCode()))) {
				executorService.submit(new Runnable() {
					public void run() {
						listener.onPayInProgress(outTradeNo, beforeCall);
					}
				});
				AlipayTradeQueryResponse loopQueryResponse = loopQueryResult(outTradeNo);
				return checkQueryAndCancel(outTradeNo, result, loopQueryResponse, beforeCall);
			}
			if (tradeError(response)) {
				AlipayTradeQueryResponse queryResponse = tradeQuery(outTradeNo);
				return checkQueryAndCancel(outTradeNo, result, queryResponse, beforeCall);
			}

			result.setTradeStatus(TradeStatus.FAILED);

			executorService.submit(new Runnable() {
				public void run() {
					listener.onPayFailed(outTradeNo, beforeCall);
				}
			});
		}
		return result;
	}

	private AlipayF2FPayResult checkQueryAndCancel(final String outTradeNo, AlipayF2FPayResult result,
			AlipayTradeQueryResponse queryResponse, final long beforeCall) {
		if (querySuccess(queryResponse)) {
			result.setTradeStatus(TradeStatus.SUCCESS);
			result.setResponse(toPayResponse(queryResponse));

			executorService.submit(new Runnable() {
				public void run() {
					listener.onPayTradeSuccess(outTradeNo, beforeCall);
				}
			});
			return result;
		}

		executorService.submit(new Runnable() {
			public void run() {
				listener.onPayFailed(outTradeNo, beforeCall);
			}
		});
		AlipayTradeCancelResponse cancelResponse = cancelPayResult(outTradeNo);
		if (tradeError(cancelResponse)) {
			result.setTradeStatus(TradeStatus.UNKNOWN);
		} else {
			result.setTradeStatus(TradeStatus.FAILED);
		}

		return result;
	}

	public static class ClientBuilder {
		private String gatewayUrl;
		private String appid;
		private String privateKey;
		private String format;
		private String charset;
		private String alipayPublicKey;
		private TradeListener listener;

		public AlipayTradeWithHBServiceImpl build() {
			if (StringUtils.isEmpty(this.gatewayUrl)) {
				this.gatewayUrl = Configs.getOpenApiDomain();
			}
			if (StringUtils.isEmpty(this.appid)) {
				this.appid = Configs.getAppid();
			}
			if (StringUtils.isEmpty(this.privateKey)) {
				this.privateKey = Configs.getPrivateKey();
			}
			if (StringUtils.isEmpty(this.format)) {
				this.format = "json";
			}
			if (StringUtils.isEmpty(this.charset)) {
				this.charset = "utf-8";
			}
			if (StringUtils.isEmpty(this.alipayPublicKey)) {
				this.alipayPublicKey = Configs.getAlipayPublicKey();
			}
			if (this.listener == null) {
				this.listener = new HbListener();
			}

			return new AlipayTradeWithHBServiceImpl(this);
		}

		public TradeListener getListener() {
			return this.listener;
		}

		public ClientBuilder setListener(TradeListener listener) {
			this.listener = listener;
			return this;
		}

		public ClientBuilder setAlipayPublicKey(String alipayPublicKey) {
			this.alipayPublicKey = alipayPublicKey;
			return this;
		}

		public ClientBuilder setAppid(String appid) {
			this.appid = appid;
			return this;
		}

		public ClientBuilder setCharset(String charset) {
			this.charset = charset;
			return this;
		}

		public ClientBuilder setFormat(String format) {
			this.format = format;
			return this;
		}

		public ClientBuilder setGatewayUrl(String gatewayUrl) {
			this.gatewayUrl = gatewayUrl;
			return this;
		}

		public ClientBuilder setPrivateKey(String privateKey) {
			this.privateKey = privateKey;
			return this;
		}

		public String getAlipayPublicKey() {
			return this.alipayPublicKey;
		}

		public String getAppid() {
			return this.appid;
		}

		public String getCharset() {
			return this.charset;
		}

		public String getFormat() {
			return this.format;
		}

		public String getGatewayUrl() {
			return this.gatewayUrl;
		}

		public String getPrivateKey() {
			return this.privateKey;
		}
	}
}