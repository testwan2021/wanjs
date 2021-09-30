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
package com.somnus.cloud.comon.core.mybatis;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.somnus.cloud.common.base.dto.LoginAuthDto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: BaseEntity
 * @Description: The class Base entity.
 * @author Somnus
 * @date 2018年9月27日
 */
@Data
public class BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 2393269568666085258L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 版本号
	 */
	private Integer version;
	/**
	 * 创建人
	 */
	private String creator;

	/**
	 * 创建人ID
	 */
	@Column(name = "creator_id")
	private Long creatorId;

	/**
	 * 创建时间
	 */
	@Column(name = "created_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createdTime;

	/**
	 * 最近操作人
	 */
	@Column(name = "last_operator")
	private String lastOperator;

	/**
	 * 最后操作人ID
	 */
	@Column(name = "last_operator_id")
	private Long lastOperatorId;

	/**
	 * 更新时间
	 */
	@Column(name = "update_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date updateTime;

	@Transient
	private Integer pageNum;

	@Transient
	private Integer pageSize;

	@Transient
	private String orderBy;

	/**
	 * Is new boolean.
	 *
	 * @return the boolean
	 */
	@Transient
	@JsonIgnore
	public boolean isNew() {
		return this.id == null;
	}

	/**
	 * Sets update info.
	 *
	 * @param user the user
	 */
	@Transient
	@JsonIgnore
	public void setUpdateInfo(LoginAuthDto user) {

		if (isNew()) {
			this.creatorId = (this.lastOperatorId = user.getUserId());
			this.creator = user.getUserName();
			this.createdTime = (this.updateTime = new Date());
		}
		this.lastOperatorId = user.getUserId();
		this.lastOperator = user.getUserName() == null ? user.getLoginName() : user.getUserName();
		this.updateTime = new Date();
	}
}
