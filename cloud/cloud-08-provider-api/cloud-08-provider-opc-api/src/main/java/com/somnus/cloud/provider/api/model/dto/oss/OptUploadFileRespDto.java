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
package com.somnus.cloud.provider.api.model.dto.oss;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: OptUploadFileRespDto
 * @Description: The class Opt upload file resp dto.
 * @author Somnus
 * @date 2018年10月16日
 */
@Data
@ApiModel(value = "OptUploadFileReqDto")
public class OptUploadFileRespDto implements Serializable {

	private static final long serialVersionUID = -8008720269972450739L;

	@ApiModelProperty(value = "附件ID")
	private Long attachmentId;
	@ApiModelProperty(value = "文件完整url")
	private String attachmentUrl;
	@ApiModelProperty(value = "文件名")
	private String attachmentName;
	@ApiModelProperty(value = "文件路径(等于七牛文件名)")
	private String attachmentPath;
	@ApiModelProperty(value = "文件类型")
	private String fileType;
	@ApiModelProperty(value = "关联业务单号")
	private String refNo;

}
