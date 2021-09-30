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
package com.somnus.cloud.provider.service;

import com.qiniu.common.QiniuException;
import com.somnus.cloud.common.base.dto.LoginAuthDto;
import com.somnus.cloud.comon.core.support.IService;
import com.somnus.cloud.provider.api.model.dto.UpdateAttachmentDto;
import com.somnus.cloud.provider.api.model.dto.attachment.OptAttachmentRespDto;
import com.somnus.cloud.provider.api.model.dto.oss.ElementImgUrlDto;
import com.somnus.cloud.provider.api.model.dto.oss.OptBatchGetUrlRequest;
import com.somnus.cloud.provider.api.model.dto.oss.OptGetUrlRequest;
import com.somnus.cloud.provider.api.model.dto.oss.OptUploadFileReqDto;
import com.somnus.cloud.provider.api.model.dto.oss.OptUploadFileRespDto;
import com.somnus.cloud.provider.model.domain.OptAttachment;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.List;

/**
 * @ClassName: OpcAttachmentService
 * @Description: The interface Opc attachment service.
 * @author Somnus
 * @date 2018年10月17日
 */
public interface OpcAttachmentService extends IService<OptAttachment> {
	/**
	 * Upload file string.
	 *
	 * @param multipartRequest    the multipart request
	 * @param optUploadFileReqDto the opt upload file req dto
	 * @param loginAuthDto        the login auth dto
	 * @param storeDbFlag         the store db flag
	 *
	 * @return the string
	 */
	List<OptUploadFileRespDto> uploadFile(MultipartHttpServletRequest multipartRequest, OptUploadFileReqDto optUploadFileReqDto, LoginAuthDto loginAuthDto, boolean storeDbFlag);

	/**
	 * 根据ID查询附件信息.
	 *
	 * @param id the id
	 *
	 * @return the opt attachment resp dto
	 */
	OptAttachmentRespDto queryAttachmentById(Long id);

	/**
	 * 根据关联单号查询附件信息.
	 *
	 * @param refNo the ref no
	 *
	 * @return the list
	 */
	List<OptAttachmentRespDto> queryAttachmentListByRefNo(String refNo);

	/**
	 * Query attachment by ref no list.
	 *
	 * @param refNo the ref no
	 *
	 * @return the list
	 */
	List<Long> queryAttachmentByRefNo(String refNo);

	/**
	 * Delete file int.
	 *
	 * @param fileName     the file name
	 * @param bucketName   the bucket name
	 * @param attachmentId the attachment id
	 *
	 * @return the int
	 *
	 * @throws QiniuException the qiniu exception
	 */
	int deleteFile(String fileName, String bucketName, Long attachmentId) throws QiniuException;

	/**
	 * Delete file int.
	 *
	 * @param attachmentId the attachment id
	 *
	 * @return the int
	 *
	 * @throws QiniuException the qiniu exception
	 */
	int deleteFile(Long attachmentId) throws QiniuException;

	/**
	 * Save attachment.
	 *
	 * @param optAttachment the opt attachment
	 * @param loginAuthDto  the login auth dto
	 */
	void saveAttachment(OptAttachment optAttachment, LoginAuthDto loginAuthDto);

	/**
	 * RPC上传附件信息.
	 *
	 * @param optUploadFileReqDto the opt upload file req dto
	 *
	 * @return the opt upload file resp dto
	 *
	 * @throws IOException the io exception
	 */
	OptUploadFileRespDto rpcUploadFile(OptUploadFileReqDto optUploadFileReqDto) throws IOException;

	/**
	 * 获取附件完整路径.
	 *
	 * @param optGetUrlRequest the opt get url request
	 *
	 * @return the string
	 */
	String rpcGetFileUrl(OptGetUrlRequest optGetUrlRequest);

	/**
	 * Gets by id.
	 *
	 * @param attachmentId the attachment id
	 *
	 * @return the by id
	 */
	OptAttachment getById(Long attachmentId);

	/**
	 * Upload file opt upload file resp dto.
	 *
	 * @param uploadBytes  the upload bytes
	 * @param fileName     the file name
	 * @param fileType     the file type
	 * @param filePath     the file path
	 * @param bucketName   the bucket name
	 * @param loginAuthDto the login auth dto
	 *
	 * @return the opt upload file resp dto
	 *
	 * @throws IOException the io exception
	 */
	OptUploadFileRespDto uploadFile(byte[] uploadBytes, String fileName, String fileType, String filePath, String bucketName, LoginAuthDto loginAuthDto) throws IOException;

	/**
	 * 更新附件.
	 *
	 * @param attachmentDto the attachment dto
	 *
	 * @throws QiniuException the qiniu exception
	 */
	void updateAttachment(UpdateAttachmentDto attachmentDto) throws QiniuException;

	/**
	 * List file url list.
	 *
	 * @param urlRequest the url request
	 *
	 * @return the list
	 */
	List<ElementImgUrlDto> listFileUrl(OptBatchGetUrlRequest urlRequest);

	/**
	 * List by ref no list.
	 *
	 * @param refNo the ref no
	 *
	 * @return the list
	 */
	List<OptAttachment> listByRefNo(final String refNo);

	/**
	 * 查询已过期的文件.
	 *
	 * @return the list
	 */
	List<OptAttachment> listExpireFile();
}
