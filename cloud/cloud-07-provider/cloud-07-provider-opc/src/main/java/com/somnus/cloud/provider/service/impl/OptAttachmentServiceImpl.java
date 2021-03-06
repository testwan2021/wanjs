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
package com.somnus.cloud.provider.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.qiniu.common.QiniuException;
import com.somnus.cloud.common.base.constant.GlobalConstant;
import com.somnus.cloud.common.base.dto.LoginAuthDto;
import com.somnus.cloud.common.base.enums.ErrorCodeEnum;
import com.somnus.cloud.common.config.properties.CloudProperties;
import com.somnus.cloud.common.util.PublicUtil;
import com.somnus.cloud.comon.core.support.BaseService;
import com.somnus.cloud.provider.api.exceptions.OpcBizException;
import com.somnus.cloud.provider.api.model.dto.UpdateAttachmentDto;
import com.somnus.cloud.provider.api.model.dto.attachment.OptAttachmentRespDto;
import com.somnus.cloud.provider.api.model.dto.attachment.OptUploadFileByteInfoReqDto;
import com.somnus.cloud.provider.api.model.dto.oss.ElementImgUrlDto;
import com.somnus.cloud.provider.api.model.dto.oss.OptBatchGetUrlRequest;
import com.somnus.cloud.provider.api.model.dto.oss.OptGetUrlRequest;
import com.somnus.cloud.provider.api.model.dto.oss.OptUploadFileReqDto;
import com.somnus.cloud.provider.api.model.dto.oss.OptUploadFileRespDto;
import com.somnus.cloud.provider.mapper.OptAttachmentMapper;
import com.somnus.cloud.provider.model.domain.OptAttachment;
import com.somnus.cloud.provider.model.dto.attachment.OptAttachmentReqDto;
import com.somnus.cloud.provider.service.OpcAttachmentService;
import com.somnus.cloud.provider.service.OpcOssService;
import com.somnus.cloud.provider.utils.CheckFileUtil;
import com.xiaoleilu.hutool.io.FileTypeUtil;

/**
 * @ClassName: OptAttachmentServiceImpl
 * @Description: The class Opt attachment service.
 * @author Somnus
 * @date 2018???10???17???
 */
@Service
public class OptAttachmentServiceImpl extends BaseService<OptAttachment> implements OpcAttachmentService {
	@Autowired
	private OptAttachmentMapper optAttachmentMapper;
	
	@Autowired
	private OpcOssService optOssService;
	
	@Autowired
	private CloudProperties cloudProperties;

	@Override
	public List<OptUploadFileRespDto> uploadFile(MultipartHttpServletRequest multipartRequest, OptUploadFileReqDto optUploadFileReqDto, LoginAuthDto loginAuthDto, boolean storeDbFlag) {
		String fileType = optUploadFileReqDto.getFileType();
		String filePath = optUploadFileReqDto.getFilePath();
		String bucketName = optUploadFileReqDto.getBucketName();
		List<OptUploadFileRespDto> result = Lists.newArrayList();
		if (PublicUtil.isEmpty(filePath)) {
			filePath = GlobalConstant.Sys.DEFAULT_FILE_PATH;
		}
		try {
			List<MultipartFile> fileList = multipartRequest.getFiles("file");
			if (fileList.isEmpty()) {
				return result;
			}

			for (MultipartFile multipartFile : fileList) {
				String fileName = multipartFile.getOriginalFilename();
				if (StringUtils.isEmpty(fileName)) {
					continue;
				}
				Preconditions.checkArgument(multipartFile.getSize() <= GlobalConstant.FILE_MAX_SIZE, "????????????????????????5M");
				InputStream inputStream = multipartFile.getInputStream();

				String inputStreamFileType = FileTypeUtil.getType(inputStream);
				CheckFileUtil.checkFileType(fileType, inputStreamFileType);
				OptUploadFileRespDto fileInfo;
				if (storeDbFlag) {
					fileInfo = this.uploadFile(multipartFile.getBytes(), fileName, fileType, filePath, bucketName, loginAuthDto);
				} else {
					fileInfo = optOssService.uploadFile(multipartFile.getBytes(), fileName, filePath, bucketName);
				}
				result.add(fileInfo);
			}
		} catch (IOException e) {
			logger.error("??????????????????={}", e.getMessage(), e);
		}
		return result;
	}

	@Override
	public OptAttachmentRespDto queryAttachmentById(Long id) {
		Preconditions.checkArgument(id != null, "ID????????????");
		OptAttachmentReqDto optAttachmentReqDto = new OptAttachmentReqDto();
		optAttachmentReqDto.setId(id);
		List<OptAttachmentRespDto> optAttachmentRespDtos = optAttachmentMapper.queryAttachment(optAttachmentReqDto);
		return optAttachmentRespDtos == null ? null : optAttachmentRespDtos.get(0);
	}

	@Override
	public List<OptAttachmentRespDto> queryAttachmentListByRefNo(String refNo) {
		Preconditions.checkArgument(StringUtils.isNotEmpty(refNo), "????????????????????????");
		OptAttachmentReqDto optAttachmentReqDto = new OptAttachmentReqDto();
		optAttachmentReqDto.setRefNo(refNo);
		return optAttachmentMapper.queryAttachment(optAttachmentReqDto);
	}

	@Override
	public List<Long> queryAttachmentByRefNo(final String refNo) {
		return optAttachmentMapper.queryAttachmentByRefNo(refNo);
	}

	@Override
	public int deleteFile(String fileName, String bucketName, Long attachmentId) throws QiniuException {
		Preconditions.checkArgument(StringUtils.isNotEmpty(fileName), ErrorCodeEnum.OPC10040010.getMsg());
		Preconditions.checkArgument(StringUtils.isNotEmpty(bucketName), "????????????????????????");

		optOssService.deleteFile(fileName, bucketName);
		return optAttachmentMapper.deleteByPrimaryKey(attachmentId);
	}

	@Override
	public int deleteFile(final Long attachmentId) throws QiniuException {
		OptAttachment optAttachment = optAttachmentMapper.selectByPrimaryKey(attachmentId);
		if (optAttachment != null) {
			optOssService.deleteFile(optAttachment.getPath() + optAttachment.getName(), optAttachment.getBucketName());
			return optAttachmentMapper.deleteByPrimaryKey(attachmentId);
		}
		return 1;
	}

	@Override
	public void saveAttachment(OptAttachment optAttachment, LoginAuthDto loginAuthDto) {
		optAttachment.setUpdateInfo(loginAuthDto);
		if (optAttachment.isNew()) {
			optAttachmentMapper.insertSelective(optAttachment);
		} else {
			int result = optAttachmentMapper.updateByPrimaryKeySelective(optAttachment);
			if (result < 1) {
				throw new OpcBizException(ErrorCodeEnum.OPC10040007, optAttachment.getId());
			}
		}
	}

	@Override
	public OptUploadFileRespDto rpcUploadFile(OptUploadFileReqDto optUploadFileReqDto) throws IOException {
		String fileType = optUploadFileReqDto.getFileType();
		String filePath = optUploadFileReqDto.getFilePath();
		String bucketName = optUploadFileReqDto.getBucketName();
		OptUploadFileByteInfoReqDto uploadFileByteInfoReqDto = optUploadFileReqDto.getUploadFileByteInfoReqDto();
		LoginAuthDto authResDto = new LoginAuthDto();
		authResDto.setUserId(optUploadFileReqDto.getUserId());
		authResDto.setUserName(optUploadFileReqDto.getUserName());

		if (StringUtils.isEmpty(filePath)) {
			filePath = GlobalConstant.Oss.DEFAULT_FILE_PATH;
		}
		InputStream is = null;
		try {
			Preconditions.checkArgument(uploadFileByteInfoReqDto != null, "????????????????????????");
			byte[] fileByteArray = uploadFileByteInfoReqDto.getFileByteArray();
			Preconditions.checkArgument(fileByteArray.length / GlobalConstant.M_SIZE <= GlobalConstant.FILE_MAX_SIZE, "????????????????????????5M");

			String fileName = uploadFileByteInfoReqDto.getFileName();
			is = new ByteArrayInputStream(fileByteArray);
			String type = FileTypeUtil.getType(is);
			Preconditions.checkArgument(type.equals(fileType), "??????????????????");
			fileName = filePath + fileName;
			OptUploadFileRespDto optUploadFileRespDto = this.uploadFile(fileByteArray, fileName, fileType, filePath, bucketName, authResDto);
			optUploadFileRespDto.setFileType(fileType);
			return optUploadFileRespDto;
		} catch (IOException e) {
			logger.error("??????????????????={}", e.getMessage(), e);
		} finally {
			if (null != is) {
				is.close();
			}
		}
		return null;
	}

	/**
	 * Rpc get file url string.
	 *
	 * @param optGetUrlRequest the opt get url request
	 *
	 * @return the string
	 */
	@Override
	public String rpcGetFileUrl(OptGetUrlRequest optGetUrlRequest) {
		Long attachmentId = optGetUrlRequest.getAttachmentId();
		Long expires = optGetUrlRequest.getExpires();
		boolean encrypt = optGetUrlRequest.isEncrypt();
		if (null == attachmentId) {
			throw new IllegalArgumentException("????????????, ???????????????");
		}

		OptAttachment optAttachment = this.getById(attachmentId);
		String fileName = optAttachment.getPath() + optAttachment.getName();

		return getUrl(expires, encrypt, fileName);
	}

	private String getUrl(final Long expires, final boolean encrypt, final String fileName) {
		final String domainOfBucket;
		if (encrypt) {
			domainOfBucket = cloudProperties.getQiniu().getOss().getPrivateHost();
			return optOssService.getFileUrl(domainOfBucket, fileName, expires);
		} else {
			domainOfBucket = cloudProperties.getQiniu().getOss().getPublicHost();
			return domainOfBucket + "/" + fileName;
		}
	}

	@Override
	public OptAttachment getById(Long attachmentId) {
		Preconditions.checkArgument(attachmentId != null, "???????????????????????????");
		OptAttachment optAttachment = optAttachmentMapper.selectByPrimaryKey(attachmentId);
		if (PublicUtil.isEmpty(optAttachment)) {
			throw new OpcBizException(ErrorCodeEnum.OPC10040008, attachmentId);
		}
		return optAttachment;
	}

	@Override
	public OptUploadFileRespDto uploadFile(byte[] uploadBytes, String fileName, String fileType, String filePath, String bucketName, LoginAuthDto loginAuthDto) throws IOException {
		OptUploadFileRespDto fileInfo = optOssService.uploadFile(uploadBytes, fileName, filePath, bucketName);
		insertAttachment(fileType, bucketName, loginAuthDto, fileInfo);
		return fileInfo;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateAttachment(final UpdateAttachmentDto attachmentDto) throws QiniuException {
		List<Long> attachmentIdList = attachmentDto.getAttachmentIdList();
		LoginAuthDto loginAuthDto = attachmentDto.getLoginAuthDto();
		String refNo = attachmentDto.getRefNo();
		List<Long> idList = optAttachmentMapper.queryAttachmentByRefNo(refNo);
		if (PublicUtil.isNotEmpty(idList)) {
			idList.removeAll(attachmentIdList);
			for (final Long id : idList) {
				this.deleteFile(id);
			}
		}
		for (final Long id : attachmentIdList) {
			OptAttachment optAttachment = new OptAttachment();
			optAttachment.setId(id);
			optAttachment.setRefNo(refNo);
			optAttachment.setUpdateInfo(loginAuthDto);
			optAttachmentMapper.updateByPrimaryKeySelective(optAttachment);
		}
	}

	@Override
	public List<ElementImgUrlDto> listFileUrl(final OptBatchGetUrlRequest urlRequest) {
		List<ElementImgUrlDto> result = Lists.newArrayList();
		String refNo = urlRequest.getRefNo();
		Long expires = urlRequest.getExpires();
		boolean encrypt = urlRequest.isEncrypt();
		Preconditions.checkArgument(StringUtils.isNotEmpty(refNo), "????????????????????????");

		List<OptAttachment> list = this.listByRefNo(refNo);
		for (final OptAttachment optAttachment : list) {
			String fileName = optAttachment.getPath() + optAttachment.getName();
			String url = getUrl(expires, encrypt, fileName);
			if (StringUtils.isNotEmpty(url)) {
				ElementImgUrlDto dto = new ElementImgUrlDto(url, optAttachment.getName(), optAttachment.getId());
				result.add(dto);
			}
		}
		return result;
	}

	@Override
	public List<OptAttachment> listByRefNo(final String refNo) {
		OptAttachment optAttachment = new OptAttachment();
		optAttachment.setRefNo(refNo);
		return optAttachmentMapper.select(optAttachment);
	}

	@Override
	public List<OptAttachment> listExpireFile() {
		return optAttachmentMapper.listExpireFile();
	}

	private void insertAttachment(String fileType, String bucketName, LoginAuthDto loginAuthDto, OptUploadFileRespDto fileInfo) {
		String attachmentName = fileInfo.getAttachmentName();
		long id = generateId();
		OptAttachment optAttachment = new OptAttachment();
		optAttachment.setBucketName(bucketName);
		optAttachment.setFormat(fileInfo.getFileType());
		optAttachment.setName(attachmentName);
		optAttachment.setType(fileType);
		optAttachment.setFormat(StringUtils.substringAfterLast(attachmentName, "."));
		optAttachment.setPath(fileInfo.getAttachmentPath());
		optAttachment.setId(id);
		optAttachment.setCenterName(bucketName);
		fileInfo.setAttachmentId(id);
		optAttachment.setUpdateInfo(loginAuthDto);
		optAttachmentMapper.insertSelective(optAttachment);
	}
}
