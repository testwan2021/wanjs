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

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: ElementImgUrlDto
 * @Description: The class Element img url dto.
 * @author Somnus
 * @date 2018年10月16日
 */
@Data
@AllArgsConstructor
public class ElementImgUrlDto implements Serializable {
	public ElementImgUrlDto() {
	}

	private static final long serialVersionUID = -5800852605728871320L;
	/**
	 * 图片完整地址
	 */
	private String url;
	/**
	 * 图片名称
	 */
	private String name;
	/**
	 * 图片关联的附件ID
	 */
	private Long attachmentId;
}
