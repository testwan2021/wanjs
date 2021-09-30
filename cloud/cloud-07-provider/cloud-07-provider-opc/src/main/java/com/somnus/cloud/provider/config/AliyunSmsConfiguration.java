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
package com.somnus.cloud.provider.config;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.somnus.cloud.common.config.properties.CloudProperties;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: AliyunSmsConfiguration
 * @Description: The class Aliyun sms configuration.
 * @author Somnus
 * @date 2018年10月17日
 */
@Slf4j
@Configuration
public class AliyunSmsConfiguration {

	@Autowired
	private CloudProperties cloudProperties;

	/**
	 * Acs client acs client.
	 *
	 * @return the acs client
	 *
	 * @throws ClientException the client exception
	 */
	@SuppressWarnings("deprecation")
	@Bean
	public IAcsClient acsClient() throws ClientException {
		log.info("SMS Bean IAcsClient Start");
		IClientProfile profile = DefaultProfile.getProfile(cloudProperties.getAliyun().getSms().getRegionId(), cloudProperties.getAliyun().getKey().getAccessKeyId(), cloudProperties.getAliyun().getKey().getAccessKeySecret());
		DefaultProfile.addEndpoint(cloudProperties.getAliyun().getSms().getEndpointName(), cloudProperties.getAliyun().getSms().getRegionId(), cloudProperties.getAliyun().getSms().getProduct(), cloudProperties.getAliyun().getSms().getDomain());
		DefaultAcsClient defaultAcsClient = new DefaultAcsClient(profile);
		log.info("加载SMS Bean IAcsClient OK");
		return defaultAcsClient;
	}

}
