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
package com.somnus.cloud.provider.api.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: UserInfoDto
 * @Description: The class User info dto.
 * @author Somnus
 * @date 2018年10月16日
 */
@Data
@ApiModel(value = "用户注册Dto")
public class UserInfoDto implements Serializable {

	private static final long serialVersionUID = -889913964833331690L;
	private Long id;

	private String userId;

	/**
	 * 登录名
	 */
	private String loginName;

	/**
	 * 盐,用于shiro加密, 字段停用
	 */
	private String salt;

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
	 * 操作员类型（）
	 */
	private String type;

	/**
	 * 最后登录IP地址
	 */
	private String lastLoginIp;

	/**
	 * 描述
	 */
	private String remark;

	/**
	 * 最后登录时间
	 */
	private Date lastLoginTime;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 用户所属的组织ID
	 */
	@ApiModelProperty(value = "用户所属的组织ID")
	private Long groupId;

	@ApiModelProperty(value = "用户所属的组织名称")
	private String groupName;
}
