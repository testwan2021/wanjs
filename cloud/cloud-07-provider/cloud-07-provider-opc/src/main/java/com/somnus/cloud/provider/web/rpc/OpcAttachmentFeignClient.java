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
package com.somnus.cloud.provider.web.rpc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.qiniu.common.QiniuException;
import com.somnus.cloud.common.base.exception.BusinessException;
import com.somnus.cloud.common.util.wrapper.WrapMapper;
import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.comon.core.support.BaseController;
import com.somnus.cloud.provider.api.exceptions.OpcBizException;
import com.somnus.cloud.provider.api.model.dto.oss.ElementImgUrlDto;
import com.somnus.cloud.provider.api.model.dto.oss.OptBatchGetUrlRequest;
import com.somnus.cloud.provider.api.model.dto.oss.OptGetUrlRequest;
import com.somnus.cloud.provider.api.model.dto.oss.OptUploadFileReqDto;
import com.somnus.cloud.provider.api.model.dto.oss.OptUploadFileRespDto;
import com.somnus.cloud.provider.api.service.OpcOssFeignApi;
import com.somnus.cloud.provider.model.domain.OptAttachment;
import com.somnus.cloud.provider.service.OpcAttachmentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName: OpcAttachmentFeignClient
 * @Description: The class Opc attachment feign client.
 * @author Somnus
 * @date 2018???10???17???
 */
@RestController
@Api(value = "API - OpcAttachmentFeignClient", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OpcAttachmentFeignClient extends BaseController implements OpcOssFeignApi {

	@Autowired
	private OpcAttachmentService opcAttachmentService;
	
	@Override
	@ApiOperation(httpMethod = "POST", value = "????????????")
	public Wrapper<OptUploadFileRespDto> uploadFile(@RequestBody OptUploadFileReqDto optUploadFileReqDto) throws OpcBizException {
		OptUploadFileRespDto result;
		try {
			logger.info("rpcUploadFile - RPC??????????????????. optUploadFileReqDto={}", optUploadFileReqDto);
			result = opcAttachmentService.rpcUploadFile(optUploadFileReqDto);
		} catch (BusinessException ex) {
			logger.error("RPC??????????????????, ????????????={}", ex.getMessage(), ex);
			return WrapMapper.wrap(ex);
		} catch (Exception e) {
			logger.error("RPC??????????????????, ????????????={}", e.getMessage(), e);
			return WrapMapper.error();
		}
		return WrapMapper.wrap(Wrapper.SUCCESS_CODE, "????????????", result);
	}

	@Override
	@ApiOperation(httpMethod = "POST", value = "????????????????????????")
	public Wrapper<String> getFileUrl(@RequestBody OptGetUrlRequest optGetUrlRequest) {
		String result;
		try {
			logger.info("getFileUrl - ????????????????????????. optGetUrlRequest={}", optGetUrlRequest);
			result = opcAttachmentService.rpcGetFileUrl(optGetUrlRequest);
		} catch (BusinessException ex) {
			logger.error("RPC????????????????????????, ????????????={}", ex.getMessage(), ex);
			return WrapMapper.wrap(ex);
		} catch (Exception e) {
			logger.error("RPC????????????????????????, ????????????={}", e.getMessage(), e);
			return WrapMapper.error();
		}
		return WrapMapper.success(result);
	}

	@Override
	public Wrapper<List<ElementImgUrlDto>> listFileUrl(@RequestBody OptBatchGetUrlRequest urlRequest) {
		logger.info("getFileUrl - ????????????url??????. urlRequest={}", urlRequest);
		List<ElementImgUrlDto> result = opcAttachmentService.listFileUrl(urlRequest);
		return WrapMapper.success(result);
	}

	@Override
	public Wrapper<OptUploadFileRespDto> handleFileUpload(@RequestPart(value = "file") MultipartFile file) {
		return null;
	}

	@Override
	public void deleteExpireFile() {
		// 1.?????????????????????
		List<OptAttachment> list = opcAttachmentService.listExpireFile();
		// 2.??????????????????
		for (final OptAttachment attachment : list) {
			try {
				opcAttachmentService.deleteFile(attachment.getPath() + attachment.getName(), attachment.getBucketName(), attachment.getId());
			} catch (QiniuException e) {
				logger.info("??????????????????, attachmentId={}", attachment.getId(), e);
			}
		}
	}
}
