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
import org.apache.ibatis.type.Alias;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: TpcJobTask
 * @Description: The class Tpc job task.
 * @author Somnus
 * @date 2018年10月17日
 */
@Data
@Table(name = "pc_tpc_job_task")
@Alias(value = "tpcJobTask")
public class TpcJobTask implements Serializable {
	private static final long serialVersionUID = -7833392442916077253L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer version;

	/**
	 * 关联业务单号
	 */
	@Column(name = "ref_no")
	private String refNo;

	/**
	 * 业务类型
	 */
	@Column(name = "task_type")
	private String taskType;

	/**
	 * 执行次数
	 */
	@Column(name = "task_exe_count")
	private Integer taskExeCount;

	/**
	 * 任务数据
	 */
	@Column(name = "task_data")
	private String taskData;

	/**
	 * 是否死亡
	 */
	private Integer dead;

	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * 执行实例IP
	 */
	@Column(name = "exe_instance_ip")
	private String exeInstanceIp;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	private Date createTime;

	/**
	 * 更新时间
	 */
	@Column(name = "update_time")
	private Date updateTime;

	/**
	 * 执行时间
	 */
	@Column(name = "exe_time")
	private Integer exeTime;

	/**
	 * 删除标识
	 */
	private Integer yn;

	/**
	 * Add send times.
	 */
	public void addSendTimes() {
		this.taskExeCount++;
	}
}