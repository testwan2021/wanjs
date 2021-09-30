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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: ProductCategoryDto
 * @Description: The class Product category dto.
 * @author Somnus
 * @date 2018年10月16日
 */
@Data
@ApiModel
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ProductCategoryDto implements Serializable {
	private static final long serialVersionUID = -5217129132596232253L;
	@ApiModelProperty(value = "类别ID")
	private Long categoryId;
	@ApiModelProperty(value = "类别名称")
	private String categoryName;
	@ApiModelProperty(value = "首图URL")
	private String imgUrl;

	private List<ProductCategoryDto> categoryList;

}
