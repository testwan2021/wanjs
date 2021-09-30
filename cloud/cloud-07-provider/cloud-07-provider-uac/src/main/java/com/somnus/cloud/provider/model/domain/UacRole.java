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
import org.hibernate.validator.constraints.Length;

import com.somnus.cloud.comon.core.mybatis.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @ClassName: UacRole
 * @Description: The class Uac role.
 * @author Somnus
 * @date 2018年10月17日
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "pc_uac_role")
@Alias(value = "uacRole")
public class UacRole extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -6049575043793281879L;

	/**
	 * 角色编码
	 */
	@Column(name = "role_code")
	@Pattern(regexp = "^[A-Za-z0-9]+$", message = "{role.roleCode.pattern}")
	@Length(min = 6, max = 20, message = "{role.roleCode.length}")
	private String roleCode;

	/**
	 * 角色名称
	 */
	@Column(name = "role_name")
	@Pattern(regexp = "^[\\u4e00-\\u9faf]+$", message = "{role.roleName.pattern}")
	@Length(min = 4, max = 10, message = "{role.roleName.length}")
	private String roleName;

	/**
	 * 状态
	 */
	private String status;

	/**
	 * 备注
	 */
	@Length(max = 150, message = "{role.remark.length}")
	private String remark;
}