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
package com.somnus.cloud.provider.api.service;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.provider.api.model.dto.oss.ElementImgUrlDto;
import com.somnus.cloud.provider.api.model.dto.oss.OptBatchGetUrlRequest;
import com.somnus.cloud.provider.api.model.dto.oss.OptGetUrlRequest;
import com.somnus.cloud.provider.api.model.dto.oss.OptUploadFileReqDto;
import com.somnus.cloud.provider.api.model.dto.oss.OptUploadFileRespDto;
import com.somnus.cloud.provider.api.service.hystrix.OpcOssFeignApiHystrix;
import com.somnus.cloud.security.feign.OAuth2FeignAutoConfiguration;

import java.util.List;

/**
 * @ClassName: OpcOssFeignApi
 * @Description: The interface Opc oss feign api.
 * @author Somnus
 * @date 2018年10月16日
 */
@FeignClient(value = "cloud-provider-opc", configuration = OAuth2FeignAutoConfiguration.class, fallback = OpcOssFeignApiHystrix.class)
public interface OpcOssFeignApi {

	/**
	 * 上传文件.
	 *
	 * @param optUploadFileReqDto the opt upload file req dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/api/opc/oss/uploadFile")
	Wrapper<OptUploadFileRespDto> uploadFile(@RequestBody OptUploadFileReqDto optUploadFileReqDto);

	/**
	 * 获取附件完整路径.
	 *
	 * @param optGetUrlRequest the opt get url request
	 *
	 * @return the file url
	 */
	@PostMapping(value = "/api/opc/oss/getFileUrl")
	Wrapper<String> getFileUrl(@RequestBody OptGetUrlRequest optGetUrlRequest);

	/**
	 * List file url wrapper.
	 *
	 * @param urlRequest the url request
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/api/opc/oss/listFileUrl")
	Wrapper<List<ElementImgUrlDto>> listFileUrl(@RequestBody OptBatchGetUrlRequest urlRequest);

	/**
	 * Upload file with feign wrapper.
	 *
	 * @param file the file
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/api/opc/oss/handleFileUpload")
	Wrapper<OptUploadFileRespDto> handleFileUpload(@RequestPart(value = "file") MultipartFile file);

	/**
	 * 删除过期的文件.
	 */
	@PostMapping(value = "/api/opc/oss/deleteExpireFile")
	void deleteExpireFile();

	/**
	 * The class Multipart support config.
	 */
	class MultipartSupportConfig {
		/**
		 * Feign form encoder encoder.
		 *
		 * @return the encoder
		 */
		@Bean
		public Encoder feignFormEncoder() {
			return new SpringFormEncoder();
		}
	}
}
