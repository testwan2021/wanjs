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
import com.somnus.cloud.provider.api.model.dto.oss.OptUploadFileRespDto;

import java.io.IOException;
import java.util.Set;

/**
 * @ClassName: OpcOssService
 * @Description: The interface Opc oss service.
 * @author Somnus
 * @date 2018年10月17日
 */
public interface OpcOssService {

	/**
	 * 删除文件
	 *
	 * @param fileName   文件路径 + 文件名
	 * @param bucketName oss对象名称
	 *
	 * @throws QiniuException the qiniu exception
	 */
	void deleteFile(String fileName, String bucketName) throws QiniuException;

	/**
	 * Batch delete file set.
	 *
	 * @param fileNameList the file name list
	 * @param bucketName   the bucket name
	 *
	 * @return the set
	 *
	 * @throws QiniuException the qiniu exception
	 */
	Set<String> batchDeleteFile(String[] fileNameList, String bucketName) throws QiniuException;

	/**
	 * 获取查看文件的Url
	 *
	 * @param domainOfBucket the domain of bucket
	 * @param fileName       文件路径 + 文件名
	 * @param expires        失效时间
	 *
	 * @return the file url
	 */
	String getFileUrl(String domainOfBucket, String fileName, Long expires);

	/**
	 * Gets file url.
	 *
	 * @param domainOfBucket the domain of bucket
	 * @param fileName       the file name
	 *
	 * @return the file url
	 */
	String getFileUrl(String domainOfBucket, String fileName);

	/**
	 * Upload file opt upload file resp dto.
	 *
	 * @param uploadBytes the upload bytes
	 * @param fileName    the file name
	 * @param filePath    the file path
	 * @param bucketName  the bucket name
	 *
	 * @return the opt upload file resp dto
	 *
	 * @throws IOException the io exception
	 */
	OptUploadFileRespDto uploadFile(byte[] uploadBytes, String fileName, String filePath, String bucketName) throws IOException;
}
