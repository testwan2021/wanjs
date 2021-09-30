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

import lombok.Data;

import java.util.Date;

/**
 * @ClassName: JqTreeResponseVo
 * @Description: The class Jq tree response vo.
 * @author Somnus
 * @date 2018年10月18日
 */
@Data
public class JqTreeResponseVo {

	/**
	 * 菜单ID
	 */
	private Long id;

	/**
	 * 父菜单ID
	 */
	private Long pid;

	/**
	 * 层级(最多三级1,2,3)
	 */
	private Integer level;

	/**
	 * 是否叶子节点,1不是0是
	 */
	private boolean isLeaf;

	private boolean expanded;

	/**
	 * 菜单编码
	 */
	private String menuCode;

	/**
	 * 菜单名称
	 */
	private String menuName;

	/**
	 * 状态
	 */
	private int status;

	/**
	 * 菜单URL
	 */
	private String url;

	/**
	 * 序号
	 */
	private String number;

	/**
	 * 备注(研发中心)
	 */
	private String remark;

	/**
	 * 创建人
	 */
	private String creator;

	/**
	 * 创建人ID
	 */
	private Long creatorId;

	/**
	 * 创建时间
	 */
	private Date createdTime;

	/**
	 * 最近操作人
	 */
	private String lastOperator;

	/**
	 * 最后操作人ID
	 */
	private String lastOperatorId;

	/**
	 * 更新时间
	 */
	private Date updateTime;
}
