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
package com.somnus.cloud.provider.api.model.dto.robot;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: LinkDto
 * @Description: The class Link dto.
 * @author Somnus
 * @date 2018年10月16日
 */
@Data
@ApiModel(value = "link类型")
public class LinkDto implements Serializable {
	private static final long serialVersionUID = 414254079011512757L;
	@ApiModelProperty(value = "消息标题", required = true)
	private String title;
	@ApiModelProperty(value = "消息内容。如果太长只会部分展示", required = true)
	private String text;
	@ApiModelProperty(value = "点击消息跳转的URL", required = true)
	private String messageUrl;
	@ApiModelProperty(value = "图片URL")
	private String picUrl;
}
