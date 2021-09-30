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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.somnus.cloud.common.base.dto.BaseVo;
import com.somnus.cloud.provider.api.vo.MenuVo;
import com.somnus.cloud.provider.model.domain.UacRole;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @ClassName: UserVo
 * @Description: The class User vo.
 * @author Somnus
 * @date 2018年10月18日
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserVo extends BaseVo {

	private static final long serialVersionUID = -5489240986004922469L;

	/**
	 * 登录名
	 */
	private String loginName;

	/**
	 * 工号
	 */
	private String userCode;

	/**
	 * 姓名
	 */
	private String userName;

	/**
	 * 手机号
	 */
	private String mobileNo;

	/**
	 * 状态
	 */
	private String status;

	/**
	 * 用户来源
	 */
	private String userSource;

	/**
	 * 操作员类型（1:超级管理员, 0:普通操作员）
	 */
	private String type;

	/**
	 * 最后登录IP地址
	 */
	private String lastLoginIp;

	/**
	 * 最后登录时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date lastLoginTime;

	/**
	 * 组织名称
	 */
	private String groupName;

	/**
	 * 用户角色信息
	 */
	private Set<UacRole> roles;

	/**
	 * 用户拥有的菜单权限和按钮权限
	 */
	private List<MenuVo> authTree;
}
