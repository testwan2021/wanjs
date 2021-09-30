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
package com.somnus.cloud.provider.model.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

import com.somnus.cloud.comon.core.mybatis.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @ClassName: MdcDict
 * @Description: 数据字典.
 * @author Somnus
 * @date 2018年10月17日
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "pc_mdc_dict")
@Alias(value = "mdcDict")
public class MdcDict extends BaseEntity {

	private static final long serialVersionUID = -4257346287088805156L;
	/**
	 * 字典类型 -0 常量 - 1 文件夹
	 */
	private Integer type;

	/**
	 * 枚举编码
	 */
	@Column(name = "dict_key")
	private String dictKey;

	/**
	 * 枚举扩展字段
	 */
	@Column(name = "extend_key")
	private String extendKey;

	/**
	 * 枚举值
	 */
	@Column(name = "dict_value")
	private String dictValue;

	/**
	 * 字典编码
	 */
	@Column(name = "dict_code")
	private String dictCode;

	/**
	 * 字典名称
	 */
	@Column(name = "dict_name")
	private String dictName;

	/**
	 * 父ID
	 */
	private Long pid;

	/**
	 * 序号
	 */
	private Integer number;

	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * 备注
	 */
	private String remark;
}