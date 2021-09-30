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
package com.somnus.cloud.provider.api.model.dto;

import com.somnus.cloud.common.base.dto.BaseQuery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName: ProductReqDto
 * @Description: The class Product req dto.
 * @author Somnus
 * @date 2018年10月16日
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
public class ProductReqDto extends BaseQuery {
	private static final long serialVersionUID = -9070173642901418263L;
	@ApiModelProperty(value = "分类ID")
	private Long categoryId;
	@ApiModelProperty(value = "关键词")
	private String keyword;
}
