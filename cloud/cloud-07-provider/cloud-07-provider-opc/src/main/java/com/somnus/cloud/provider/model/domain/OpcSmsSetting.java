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
 * @ClassName: OpcSmsSetting
 * @Description: The class Opc sms setting.
 * @author Somnus
 * @date 2018年10月17日
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "pc_opc_sms_setting")
@Alias(value = "opcSmsSetting")
public class OpcSmsSetting extends BaseEntity {

	private static final long serialVersionUID = -1811960778993500439L;
	/**
	 * 可再次发送时间（毫秒）
	 */
	@Column(name = "again_send_time")
	private Integer againSendTime;

	/**
	 * 失效时间（分钟）
	 */
	@Column(name = "invalid_time")
	private Integer invalidTime;

	/**
	 * 短信类型
	 */
	private String type;

	/**
	 * 类型描述
	 */
	@Column(name = "type_desc")
	private String typeDesc;

	/**
	 * 模板code
	 */
	@Column(name = "templet_code")
	private String templetCode;

	/**
	 * 模板内容
	 */
	@Column(name = "templet_content")
	private String templetContent;

	/**
	 * 一天中可发送的最大数量
	 */
	@Column(name = "send_max_num")
	private Integer sendMaxNum;

	/**
	 * 一个IP一天中可发送的最大数量
	 */
	@Column(name = "ip_send_max_num")
	private Integer ipSendMaxNum;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 删除标识(1-已删除；0-未删除)
	 */
	private Integer yn;
}