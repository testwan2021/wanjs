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
package com.somnus.cloud.provider.model.dto.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.somnus.cloud.common.base.dto.BaseQuery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: UacLogMainDto
 * @Description: The class Uac log main dto.
 * @author Somnus
 * @date 2018年10月18日
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
public class UacLogMainDto extends BaseQuery {

	private static final long serialVersionUID = 3967075132487249652L;
	/**
	 * 日志类型
	 */
	@ApiModelProperty(value = "日志类型")
	private String logType;
	/**
	 * 操作用户名称
	 */
	@ApiModelProperty(value = "操作用户名称")
	private String creator;
	/**
	 * 权限名称
	 */
	@ApiModelProperty(value = "权限名称")
	private String actionName;

	/**
	 * 权限编码
	 */
	@ApiModelProperty(value = "权限编码")
	private String actionCode;

	/**
	 * 菜单ID集合
	 */
	@ApiModelProperty(value = "菜单ID集合")
	private List<Long> menuIdList;

	/**
	 * 菜单ID集合
	 */
	@ApiModelProperty(value = "菜单ID")
	private Long menuId;

	/**
	 * 开始时间
	 */
	@ApiModelProperty(value = "开始时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date startQueryTime;

	/**
	 * 结束时间
	 */
	@ApiModelProperty(value = "结束时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date endQueryTime;

	@ApiModelProperty(value = "登录IP")
	private String ip;

	@ApiModelProperty(value = "登录位置")
	private String location;

}
