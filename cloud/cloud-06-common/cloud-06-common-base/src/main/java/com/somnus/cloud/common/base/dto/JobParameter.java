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
package com.somnus.cloud.common.base.dto;

import com.google.common.base.Preconditions;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: JobParameter
 * @Description: The class Job task model.
 * @author Somnus
 * @date 2018年9月27日
 */
@Data
public class JobParameter implements Serializable {
	
	private static final long serialVersionUID = -610797345091216847L;

	/**
	 * 每次查询数据量
	 */
	private int fetchNum;
	/**
	 * 取模条件
	 */
	private String condition;
	/**
	 * 取模条件
	 */
	private String taskType;

	public int getFetchNum() {
		Preconditions.checkArgument(fetchNum != 0);
		return fetchNum;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("JobTaskParameter{");
		sb.append("fetchNum=").append(fetchNum);
		sb.append(", condition='").append(condition).append('\'');
		sb.append(", taskType='").append(taskType).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
