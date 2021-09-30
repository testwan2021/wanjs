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

import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.somnus.cloud.common.config.properties.CloudProperties;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: QiniuOssConfiguration
 * @Description: The class Qiniu oss configuration.
 * @author Somnus
 * @date 2018年10月17日
 */
@Slf4j
@Configuration
public class QiniuOssConfiguration {

	@Autowired
	private CloudProperties cloudProperties;

	/**
	 * Auth auth.
	 *
	 * @return the auth
	 */
	@Bean
	public Auth auth() {
		Auth auth = Auth.create(cloudProperties.getQiniu().getKey().getAccessKey(), cloudProperties.getQiniu().getKey().getSecretKey());
		log.info("Create Auth OK.");
		return auth;
	}

	/**
	 * Upload manager upload manager.
	 *
	 * @return the upload manager
	 */
	@Bean
	public UploadManager uploadManager() {
		Zone zone = Zone.autoZone();
		//创建上传对象
		UploadManager uploadManager = new UploadManager(new com.qiniu.storage.Configuration(zone));
		log.info("Create UploadManager OK.");
		return uploadManager;
	}

	/**
	 * Bucket manager bucket manager.
	 *
	 * @return the bucket manager
	 */
	@Bean
	public BucketManager bucketManager() {
		Zone zone = Zone.autoZone();
		//创建上传对象
		BucketManager uploadManager = new BucketManager(auth(), new com.qiniu.storage.Configuration(zone));
		log.info("Create BucketManager OK.");
		return uploadManager;
	}


}
