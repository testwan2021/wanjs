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
package com.somnus.cloud.common.alipay.config;

/**
 * @ClassName: Configs
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Somnus
 * @date 2018年10月17日
 */
public class Configs {
	private static Configuration configs;
	private static String openApiDomain;
	private static String mcloudApiDomain;
	private static String pid;
	private static String appid;
	private static String privateKey;
	private static String publicKey;
	private static String alipayPublicKey;
	private static int maxQueryRetry;
	private static long queryDuration;
	private static int maxCancelRetry;
	private static long cancelDuration;
	private static long heartbeatDelay;
	private static long heartbeatDuration;

	public static synchronized void init(String filePath) {
		if (configs != null) {
			return;
		}
		try {
			configs = new PropertiesConfiguration(filePath);
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}

		if (configs == null) {
			throw new IllegalStateException("can`t find file by path:" + filePath);
		}

		openApiDomain = configs.getString("open_api_domain");
		mcloudApiDomain = configs.getString("mcloud_api_domain");

		pid = configs.getString("pid");
		appid = configs.getString("appid");

		privateKey = configs.getString("private_key");
		publicKey = configs.getString("public_key");
		alipayPublicKey = configs.getString("alipay_public_key");

		maxQueryRetry = configs.getInt("max_query_retry");
		queryDuration = configs.getLong("query_duration");
		maxCancelRetry = configs.getInt("max_cancel_retry");
		cancelDuration = configs.getLong("cancel_duration");

		heartbeatDelay = configs.getLong("heartbeat_delay");
		heartbeatDuration = configs.getLong("heartbeat_duration");

		log.info("配置文件名: " + filePath);
		log.info(description());
	}

	public static String description() {
		StringBuilder sb = new StringBuilder("Configs{");
		sb.append("支付宝openapi网关: ").append(openApiDomain).append("\n");
		if (StringUtils.isNotEmpty(mcloudApiDomain)) {
			sb.append(", 支付宝mcloudapi网关域名: ").append(mcloudApiDomain).append("\n");
		}

		if (StringUtils.isNotEmpty(pid)) {
			sb.append(", pid: ").append(pid).append("\n");
		}
		sb.append(", appid: ").append(appid).append("\n");

		sb.append(", 商户RSA私钥: ").append(getKeyDescription(privateKey)).append("\n");
		sb.append(", 商户RSA公钥: ").append(getKeyDescription(publicKey)).append("\n");
		sb.append(", 支付宝RSA公钥: ").append(getKeyDescription(alipayPublicKey)).append("\n");

		sb.append(", 查询重试次数: ").append(maxQueryRetry).append("\n");
		sb.append(", 查询间隔(毫秒): ").append(queryDuration).append("\n");
		sb.append(", 撤销尝试次数: ").append(maxCancelRetry).append("\n");
		sb.append(", 撤销重试间隔(毫秒): ").append(cancelDuration).append("\n");

		sb.append(", 交易保障调度延迟(秒): ").append(heartbeatDelay).append("\n");
		sb.append(", 交易保障调度间隔(秒): ").append(heartbeatDuration).append("\n");
		sb.append("}");
		return sb.toString();
	}

	public static String getKeyDescription(String key) {
		int showLength = 10;
		if (StringUtils.isNotEmpty(key)) {
			return key.substring(0, showLength) + "******" + key.substring(key.length() - showLength);
		}

		return null;
	}

	public static Configuration getConfigs() {
		return configs;
	}

	public static String getOpenApiDomain() {
		return openApiDomain;
	}

	public static String getMcloudApiDomain() {
		return mcloudApiDomain;
	}

	public static void setMcloudApiDomain(String mcloudApiDomain) {
		Configs.mcloudApiDomain = mcloudApiDomain;
	}

	public static String getPid() {
		return pid;
	}

	public static String getAppid() {
		return appid;
	}

	public static String getPrivateKey() {
		return privateKey;
	}

	public static String getPublicKey() {
		return publicKey;
	}

	public static String getAlipayPublicKey() {
		return alipayPublicKey;
	}

	public static int getMaxQueryRetry() {
		return maxQueryRetry;
	}

	public static long getQueryDuration() {
		return queryDuration;
	}

	public static int getMaxCancelRetry() {
		return maxCancelRetry;
	}

	public static long getCancelDuration() {
		return cancelDuration;
	}

	public static void setConfigs(Configuration configs) {
		Configs.configs = configs;
	}

	public static void setOpenApiDomain(String openApiDomain) {
		Configs.openApiDomain = openApiDomain;
	}

	public static void setPid(String pid) {
		Configs.pid = pid;
	}

	public static void setAppid(String appid) {
		Configs.appid = appid;
	}

	public static void setPrivateKey(String privateKey) {
		Configs.privateKey = privateKey;
	}

	public static void setPublicKey(String publicKey) {
		Configs.publicKey = publicKey;
	}

	public static void setAlipayPublicKey(String alipayPublicKey) {
		Configs.alipayPublicKey = alipayPublicKey;
	}

	public static void setMaxQueryRetry(int maxQueryRetry) {
		Configs.maxQueryRetry = maxQueryRetry;
	}

	public static void setQueryDuration(long queryDuration) {
		Configs.queryDuration = queryDuration;
	}

	public static void setMaxCancelRetry(int maxCancelRetry) {
		Configs.maxCancelRetry = maxCancelRetry;
	}

	public static void setCancelDuration(long cancelDuration) {
		Configs.cancelDuration = cancelDuration;
	}

	public static long getHeartbeatDelay() {
		return heartbeatDelay;
	}

	public static void setHeartbeatDelay(long heartbeatDelay) {
		Configs.heartbeatDelay = heartbeatDelay;
	}

	public static long getHeartbeatDuration() {
		return heartbeatDuration;
	}

	public static void setHeartbeatDuration(long heartbeatDuration) {
		Configs.heartbeatDuration = heartbeatDuration;
	}
}
