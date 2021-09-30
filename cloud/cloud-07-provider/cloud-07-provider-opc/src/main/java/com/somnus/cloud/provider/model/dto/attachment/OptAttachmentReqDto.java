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
package com.somnus.cloud.provider.model.dto.attachment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: OptAttachmentReqDto
 * @Description: The class Opt attachment req dto.
 * @author Somnus
 * @date 2018年10月17日
 */
@Data
@ApiModel(value = "OptAttachmentReqDto")
public class OptAttachmentReqDto implements Serializable {

	private static final long serialVersionUID = -1727131719075160349L;
	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	private Long id;

	/**
	 * 附件流水号
	 */
	@ApiModelProperty(value = "附件流水号")
	private String serialNo;

	/**
	 * 上传附件的相关业务流水号
	 */
	@ApiModelProperty(value = "上传附件的相关业务流水号")
	private String refNo;

}
