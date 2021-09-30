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
package com.somnus.cloud.provider.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: MdcEditCategoryDto
 * @Description: The class Mdc edit category dto.
 * @author Somnus
 * @date 2018年10月17日
 */
@Data
public class MdcEditCategoryDto implements Serializable {
	private static final long serialVersionUID = 8578699727403591194L;
	/**
	 * 首图的流水号
	 */
	private Long imgId;
	/**
	 * ID
	 */
	private Long id;

	/**
	 * 父类别id当id=0时说明是根节点,一级类别
	 */
	private Long pid;

	/**
	 * 类别名称
	 */
	private String name;

	/**
	 * 类别状态1-正常,2-已废弃
	 */
	private Integer status;

	/**
	 * 排序编号,同类展示顺序,数值相等则自然排序
	 */
	private Integer sortOrder;

	/**
	 * 分类编码
	 */
	private String categoryCode;
}
