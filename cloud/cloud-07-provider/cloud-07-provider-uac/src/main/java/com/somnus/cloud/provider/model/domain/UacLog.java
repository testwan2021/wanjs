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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.somnus.cloud.comon.core.mybatis.BaseEntity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * @ClassName: UacLog
 * @Description: The class Uac log.
 * @author Somnus
 * @date 2018年10月17日
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "pc_uac_log")
@ApiModel("操作日志")
@Alias(value = "uacLog")
public class UacLog extends BaseEntity {

	private static final long serialVersionUID = -3326236838689347547L;
	/**
	 * 组织流水号
	 */
	@Column(name = "group_id")
	private Long groupId;

	/**
	 * 组织名称
	 */
	@Column(name = "group_name")
	private String groupName;

	/**
	 * 日志类型
	 */
	@Column(name = "log_type")
	private String logType;

	/**
	 * 日志类型名称
	 */
	@Column(name = "log_name")
	private String logName;

	/**
	 * 权限ID
	 */
	@Column(name = "action_id")
	private Long actionId;

	/**
	 * 权限编码
	 */
	@Column(name = "action_code")
	private String actionCode;

	/**
	 * 权限名称
	 */
	@Column(name = "action_name")
	private String actionName;

	/**
	 * 操作系统
	 */
	private String os;

	/**
	 * 浏览器类型
	 */
	private String browser;

	/**
	 * IP地址
	 */
	private String ip;

	/**
	 * 物理地址
	 */
	private String mac;

	/**
	 * 详细描述
	 */
	private String description;

	/**
	 * 请求参数
	 */
	@Column(name = "request_data")
	private String requestData;

	/**
	 * 请求地址
	 */
	@Column(name = "request_url")
	private String requestUrl;

	/**
	 * 响应结果
	 */
	@Column(name = "response_data")
	private String responseData;

	/**
	 * 类名
	 */
	@Column(name = "class_name")
	private String className;

	/**
	 * 方法名
	 */
	@Column(name = "method_name")
	private String methodName;

	/**
	 * 开始时间
	 */
	@Column(name = "start_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date startTime;

	/**
	 * 结束时间
	 */
	@Column(name = "end_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date endTime;

	/**
	 * 耗时,秒
	 */
	@Column(name = "excute_time")
	private Long excuteTime;
	/**
	 * 登录位置
	 */
	@Column(name = "location")
	private String location;
}