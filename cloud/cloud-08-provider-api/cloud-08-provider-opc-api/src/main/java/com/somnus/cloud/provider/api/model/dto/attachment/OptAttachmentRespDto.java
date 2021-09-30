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
package com.somnus.cloud.provider.api.model.dto.attachment;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: OptAttachmentRespDto
 * @Description: The class Opt attachment resp dto.
 * @author Somnus
 * @date 2018年10月16日
 */
@Data
public class OptAttachmentRespDto implements Serializable {
	private static final long serialVersionUID = 7156713126848745258L;
	private Long id;
	private String serialNo;
	private String refNo;
	private String name;
	private String path;
	private String type;
	private String format;
	private String description;
	/**
	 * 文件服务器根目录
	 */
	private String bucketName;

}
