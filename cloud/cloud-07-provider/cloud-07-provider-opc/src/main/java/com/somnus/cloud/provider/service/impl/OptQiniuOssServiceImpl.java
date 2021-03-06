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

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.BatchStatus;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.somnus.cloud.common.base.enums.ErrorCodeEnum;
import com.somnus.cloud.common.config.properties.CloudProperties;
import com.somnus.cloud.common.util.PublicUtil;
import com.somnus.cloud.common.util.RedisKeyUtil;
import com.somnus.cloud.common.util.UrlUtil;
import com.somnus.cloud.common.zk.generator.UniqueIdGenerator;
import com.somnus.cloud.provider.api.exceptions.OpcBizException;
import com.somnus.cloud.provider.api.model.dto.oss.OptUploadFileRespDto;
import com.somnus.cloud.provider.service.OpcOssService;
import com.xiaoleilu.hutool.io.FileTypeUtil;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: OptQiniuOssServiceImpl
 * @Description: The class Opt qiniu oss service.
 * @author Somnus
 * @date 2018???10???17???
 */
@Slf4j
@Service
public class OptQiniuOssServiceImpl implements OpcOssService {
	@Autowired
	private BucketManager bucketManager;
	
	@Autowired
	private Auth auth;
	
	@Autowired
	private CloudProperties cloudProperties;
	
	@Autowired
	private UploadManager uploadManager;
	
	@Autowired
	private StringRedisTemplate srt;

	private static final String OPEN_IMG_BUCKET = "open-img-bucket";

	@Override
	@Retryable(value = Exception.class, backoff = @Backoff(delay = 5000, multiplier = 2))
	public void deleteFile(String fileName, String bucketName) throws QiniuException {
		log.info("deleteFile - ??????OSS??????. fileName={}, bucketName={}", fileName, bucketName);

		Preconditions.checkArgument(PublicUtil.isNotEmpty(fileName), ErrorCodeEnum.OPC10040010.getMsg());
		Preconditions.checkArgument(PublicUtil.isNotEmpty(bucketName), "????????????????????????");


		Response response = bucketManager.delete(bucketName, fileName);
		log.info("deleteFile - ??????OSS??????. [OK] response={}", response);
	}

	@Override
	public Set<String> batchDeleteFile(String[] fileNameList, String bucketName) throws QiniuException {
		log.info("batchDeleteFile - ??????OSS??????. fileNameList={}, bucketName={}", fileNameList, bucketName);
		BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
		batchOperations.addDeleteOp(bucketName, fileNameList);

		Response response = bucketManager.batch(batchOperations);
		BatchStatus[] batchStatusList = response.jsonToObject(BatchStatus[].class);

		Set<String> failSet = Sets.newHashSet();
		for (int i = 0; i < fileNameList.length; i++) {
			BatchStatus status = batchStatusList[i];
			String fileName = fileNameList[i];
			if (status.code != 200) {
				failSet.add(fileName);
				log.error("batchDeleteFile - ??????OSS??????. [FAIL] fileName={}, error={}", fileName, status.data.error);
			} else {
				log.info("batchDeleteFile - ??????OSS??????. [OK] fileName={}, bucketName={}", fileName, bucketName);
			}
		}
		return failSet;
	}

	@Override
	public String getFileUrl(String domainOfBucket, String fileName, Long expires) {
		String downloadUrl;
		String encodedFileName = UrlUtil.getURLEncoderString(fileName);
		String url = String.format("%s/%s", domainOfBucket, encodedFileName);
		log.info("getFileUrl - ?????????????????????. url={}", url);

		if (null == expires) {
			downloadUrl = auth.privateDownloadUrl(url);
		} else {
			downloadUrl = auth.privateDownloadUrl(url, expires);
		}
		log.info("getFileUrl - ?????????????????????. [OK] downloadUrl={}", downloadUrl);
		return downloadUrl;
	}

	@Override
	public String getFileUrl(String domainOfBucket, String fileName) {
		return this.getFileUrl(domainOfBucket, fileName, null);
	}

	@Override
	public OptUploadFileRespDto uploadFile(byte[] uploadBytes, String fileName, String filePath, String bucketName) throws IOException {
		log.info("uploadFile - ????????????. fileName={}, bucketName={}", fileName, bucketName);

		Preconditions.checkArgument(uploadBytes != null, "??????????????????");
		Preconditions.checkArgument(PublicUtil.isNotEmpty(fileName), ErrorCodeEnum.OPC10040010.getMsg());
		Preconditions.checkArgument(PublicUtil.isNotEmpty(filePath), "????????????????????????");
		Preconditions.checkArgument(PublicUtil.isNotEmpty(bucketName), "????????????????????????");

		InputStream is = new ByteArrayInputStream(uploadBytes);
		String inputStreamFileType = FileTypeUtil.getType(is);
		String newFileName = UniqueIdGenerator.generateId() + "." + inputStreamFileType;

		// ??????????????????
		this.checkFileSize(uploadBytes);

		Response response = uploadManager.put(uploadBytes, filePath + newFileName, getUpToken(bucketName));
		DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
		log.info("uploadFile - ????????????. [OK] putRet={}", putRet);
		if (PublicUtil.isEmpty(putRet) || PublicUtil.isEmpty(putRet.key)) {
			throw new OpcBizException(ErrorCodeEnum.OPC10040009);
		}
		String fileUrl;
		// ??????????????????
		if (Objects.equals(OPEN_IMG_BUCKET, bucketName)) {
			fileUrl = cloudProperties.getQiniu().getOss().getPublicHost() + "/" + filePath + newFileName;
		} else {
			String domainUrl = cloudProperties.getQiniu().getOss().getPrivateHost();
			fileUrl = this.getFileUrl(domainUrl, fileName);
		}
		OptUploadFileRespDto result = new OptUploadFileRespDto();
		result.setAttachmentUrl(fileUrl);
		result.setAttachmentName(newFileName);
		result.setAttachmentPath(filePath);
		return result;
	}

	private String getUpToken(String bucketName) {
		return auth.uploadToken(bucketName);
	}

	private void checkFileSize(byte[] uploadFileByte) {
		long redisFileSize;
		Long fileMaxSize = cloudProperties.getQiniu().getOss().getFileMaxSize();
		Preconditions.checkArgument(fileMaxSize != null, "???????????????????????????????????????");

		String fileSizeKey = RedisKeyUtil.getFileSizeKey();
		long fileSize = uploadFileByte.length;

		String redisFileSizeStr = srt.opsForValue().get(fileSizeKey);

		if(PublicUtil.isEmpty(redisFileSizeStr)) {
			redisFileSizeStr = "0";
		}
		redisFileSize = Long.valueOf(redisFileSizeStr);
		if (fileSize + redisFileSize > fileMaxSize) {
			throw new OpcBizException(ErrorCodeEnum.OPC10040011);
		}

		srt.opsForValue().set(fileSizeKey, String.valueOf(redisFileSize + fileSize), 1, TimeUnit.DAYS);
	}
}
