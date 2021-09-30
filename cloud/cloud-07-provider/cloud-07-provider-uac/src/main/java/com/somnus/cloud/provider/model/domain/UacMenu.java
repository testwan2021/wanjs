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
 * @ClassName: UacMenu
 * @Description: The class Uac menu.
 * @author Somnus
 * @date 2018年10月17日
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "pc_uac_menu")
@Alias(value = "uacMenu")
public class UacMenu extends BaseEntity {
	private static final long serialVersionUID = 454644589405700059L;

	/**
	 * 菜单编码
	 */
	@Column(name = "menu_code")
	private String menuCode;

	/**
	 * 菜单名称
	 */
	@Column(name = "menu_name")
	private String menuName;

	/**
	 * 状态
	 */
	private String status;

	/**
	 * 菜单URL
	 */
	private String url;

	/**
	 * 图标
	 */
	private String icon;

	/**
	 * 父ID
	 */
	private Long pid;

	/**
	 * 层级(最多三级1,2,3)
	 */
	private Integer level;

	/**
	 * 是否叶子节点,1不是0是
	 */
	private Integer leaf;

	/**
	 * 序号
	 */
	private Integer number;

	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 系统ID
	 */
	@Column(name = "application_id")
	private Long applicationId;
}