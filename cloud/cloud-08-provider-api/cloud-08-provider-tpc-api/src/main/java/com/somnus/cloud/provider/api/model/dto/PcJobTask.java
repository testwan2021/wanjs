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
package com.somnus.cloud.provider.api.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: PcJobTask
 * @Description: The class Pc job task.
 * @author Somnus
 * @date 2018年10月16日
 */
@Data
public class PcJobTask implements Serializable {

	private static final long serialVersionUID = -1689940882253489536L;

	/**
	 * 自增ID
	 */
	private String id;

	/**
	 * 版本号
	 */
	private String version;

	/**
	 * 关联单号
	 */
	private String refNo;

	/**
	 * Worker任务类型
	 */
	private String taskType;

	/**
	 * 业务json数据
	 */
	private String taskData;

	/**
	 * 执行次数
	 */
	private Integer taskExeCount;

	/**
	 * 执行实例IP
	 */
	private String exeInstanceIp;

	/**
	 * 任务运行状态
	 */
	private Integer taskStatus;

	/**
	 * 前置状态
	 */
	private List<Integer> preStatusList;
}