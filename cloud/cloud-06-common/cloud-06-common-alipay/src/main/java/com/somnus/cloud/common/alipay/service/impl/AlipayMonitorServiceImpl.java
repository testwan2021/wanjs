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
 * @ClassName: AlipayMonitorServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Somnus
 * @date 2018年10月17日
 */
public class AlipayMonitorServiceImpl extends AbsAlipayService implements AlipayMonitorService {
	
	private AlipayClient client;

	public AlipayMonitorServiceImpl(ClientBuilder builder) {
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

		this.client = new DefaultAlipayClient(builder.getGatewayUrl(), builder.getAppid(), builder.getPrivateKey(),
				builder.getFormat(), builder.getCharset());
	}

	public MonitorHeartbeatSynResponse heartbeatSyn(AlipayHeartbeatSynContentBuilder builder) {
		return heartbeatSyn(builder, null);
	}

	public MonitorHeartbeatSynResponse heartbeatSyn(AlipayHeartbeatSynContentBuilder builder, String appAuthToken) {
		validateBuilder(builder);

		MonitorHeartbeatSynRequest request = new MonitorHeartbeatSynRequest();
		if (StringUtils.isNotEmpty(appAuthToken)) {
			request.putOtherTextParam("app_auth_token", appAuthToken);
		}

		request.setBizContent(builder.toJsonString());
		this.log.info("heartbeat.sync bizContent:" + request.getBizContent());

		return (MonitorHeartbeatSynResponse) getResponse(this.client, request);
	}

	public static class ClientBuilder {
		
		private String gatewayUrl;
		private String appid;
		private String privateKey;
		private String format;
		private String charset;

		public AlipayMonitorServiceImpl build() {
			if (StringUtils.isEmpty(this.gatewayUrl)) {
				this.gatewayUrl = Configs.getMcloudApiDomain();
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
			return new AlipayMonitorServiceImpl(this);
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
