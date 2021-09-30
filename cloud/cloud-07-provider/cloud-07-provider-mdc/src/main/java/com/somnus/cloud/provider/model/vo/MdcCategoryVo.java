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
package com.somnus.cloud.provider.model.vo;

import com.somnus.cloud.common.base.dto.BaseTree;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName: MdcCategoryVo
 * @Description: The class Mdc dict vo.
 * @author Somnus
 * @date 2018年10月17日
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MdcCategoryVo extends BaseTree<MdcCategoryVo, Long> {
	private static final long serialVersionUID = 5310269803239754048L;
	/**
	 * 父分类名称
	 */
	private String parentCategoryName;
	/**
	 * 字典类型 -0 常量 - 1 文件夹
	 */
	private Integer type;

	/**
	 * 字典名称
	 */
	private String name;

	/**
	 * 序号
	 */
	private Integer number;

	/**
	 * 状态
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
