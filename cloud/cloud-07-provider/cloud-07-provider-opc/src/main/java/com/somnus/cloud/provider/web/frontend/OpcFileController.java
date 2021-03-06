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
package com.somnus.cloud.provider.web.frontend;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.somnus.cloud.common.base.constant.GlobalConstant;
import com.somnus.cloud.common.base.exception.BusinessException;
import com.somnus.cloud.common.util.wrapper.WrapMapper;
import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.comon.core.support.BaseController;
import com.somnus.cloud.provider.api.model.dto.attachment.OptAttachmentRespDto;
import com.somnus.cloud.provider.api.model.dto.oss.OptUploadFileReqDto;
import com.somnus.cloud.provider.api.model.dto.oss.OptUploadFileRespDto;
import com.somnus.cloud.provider.service.OpcAttachmentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName: OpcFileController
 * @Description: The class Opt file controller.
 * @author Somnus
 * @date 2018???10???17???
 */
@RestController
@RequestMapping(value = "/file", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - OptFileRest", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OpcFileController extends BaseController {

	@Autowired
	private OpcAttachmentService optAttachmentService;
	
	/**
	 * ????????????.
	 *
	 * @param request             the request
	 * @param optUploadFileReqDto the opt upload file req dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(consumes = "multipart/form-data", value = "/uploadFile")
	@ApiOperation(httpMethod = "POST", value = "????????????")
	public Wrapper<String> uploadFile(HttpServletRequest request, OptUploadFileReqDto optUploadFileReqDto) {
		StringBuilder temp = new StringBuilder();
		logger.info("uploadFile - ????????????. optUploadFileReqDto={}", optUploadFileReqDto);
		Preconditions.checkArgument(StringUtils.isNotEmpty(optUploadFileReqDto.getFileType()), "??????????????????");
		Preconditions.checkArgument(StringUtils.isNotEmpty(optUploadFileReqDto.getBucketName()), "??????????????????");

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<OptUploadFileRespDto> optUploadFileRespDtos = optAttachmentService.uploadFile(multipartRequest, optUploadFileReqDto, getLoginAuthDto(), true);
		for (final OptUploadFileRespDto fileRespDto : optUploadFileRespDtos) {
			temp.append(fileRespDto.getAttachmentId()).append(",");
		}
		String attachmentIds = temp.toString();
		if (StringUtils.isNotEmpty(attachmentIds)) {
			attachmentIds = StringUtils.substringBeforeLast(attachmentIds, GlobalConstant.Symbol.COMMA);
		}
		return WrapMapper.success(attachmentIds);
	}

	/**
	 * Upload picture with wang editor map.
	 *
	 * @param request             the request
	 * @param optUploadFileReqDto the opt upload file req dto
	 *
	 * @return the map
	 */
	@PostMapping(consumes = "multipart/form-data", value = "/uploadPictureWithWangEditor")
	@ApiOperation(httpMethod = "POST", value = "????????????")
	public Map<String, Object> uploadPictureWithWangEditor(HttpServletRequest request, OptUploadFileReqDto optUploadFileReqDto) {
		logger.info("uploadWangEditor - ????????????. optUploadFileReqDto={}", optUploadFileReqDto);

		String fileType = optUploadFileReqDto.getFileType();
		String bucketName = optUploadFileReqDto.getBucketName();
		Preconditions.checkArgument(StringUtils.isNotEmpty(fileType), "??????????????????");
		Preconditions.checkArgument(StringUtils.isNotEmpty(bucketName), "??????????????????");
		optUploadFileReqDto.setFilePath("cloud/picture/wangEditor/");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<OptUploadFileRespDto> optUploadFileRespDtos = optAttachmentService.uploadFile(multipartRequest, optUploadFileReqDto, getLoginAuthDto(), false);

		List<String> imgUrlList = Lists.newArrayList();
		for (final OptUploadFileRespDto fileRespDto : optUploadFileRespDtos) {
			imgUrlList.add(fileRespDto.getAttachmentUrl());
		}

		Map<String, Object> map = Maps.newHashMap();
		map.put("errno", 0);
		map.put("data", imgUrlList.toArray());
		return map;
	}

	/**
	 * ??????ID??????????????????.
	 *
	 * @param id the id
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/queryAttachmentById/{id}")
	@ApiOperation(httpMethod = "POST", value = "??????ID??????????????????")
	public Wrapper<OptAttachmentRespDto> queryAttachment(@PathVariable Long id) {
		logger.info("queryAttachment -??????ID??????????????????. id={}", id);

		OptAttachmentRespDto optAttachmentRespDto = optAttachmentService.queryAttachmentById(id);
		return WrapMapper.success(optAttachmentRespDto);
	}

	/**
	 * ????????????????????????????????????.
	 *
	 * @param refNo the ref no
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/queryAttachmentListByRefNo/{refNo}")
	@ApiOperation(httpMethod = "POST", value = "????????????????????????????????????")
	public Wrapper<List<OptAttachmentRespDto>> queryAttachmentListByRefNo(@PathVariable String refNo) {
		logger.info("queryAttachment -??????????????????. refNo={}", refNo);

		List<OptAttachmentRespDto> optAttachmentRespDtos = optAttachmentService.queryAttachmentListByRefNo(refNo);
		return WrapMapper.success(optAttachmentRespDtos);
	}

	/**
	 * ??????????????????.
	 *
	 * @param attachmentId the attachment id
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/deleteAttachment/{attachmentId}")
	@ApiOperation(httpMethod = "POST", value = "??????????????????")
	public Wrapper<?> deleteAttachment(@PathVariable Long attachmentId) {
		logger.info("deleteAttachment - ????????????. attachmentId={}", attachmentId);
		int result;
		try {
			result = optAttachmentService.deleteFile(attachmentId);
		} catch (BusinessException ex) {
			logger.error("????????????, ????????????={}", ex.getMessage(), ex);
			return WrapMapper.wrap(Wrapper.ERROR_CODE, "????????????");
		} catch (Exception e) {
			logger.error("???????????????, ????????????={}", e.getMessage(), e);
			return WrapMapper.error();
		}
		return handleResult(result);
	}

}
