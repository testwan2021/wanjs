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
package com.somnus.cloud.provider.api.model.enums;

import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * @ClassName: JobTaskStatusEnum
 * @Description: The enum Job task status enum.
 * @author Somnus
 * @date 2018年10月16日
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum JobTaskStatusEnum {
    /**
     * 创建任务数据  状态码0
     */
    TASK_CREATE(0, "创建任务数据"),

    /**
     * 等待处理 状态码1
     */
    TASK_WAITING(1, "等待处理"),

    /**
     * 正在处理中  状态码2
     */
    TASK_EXETING(2, "正在处理中"),

    /**
     * 处理成功
     */
    TASK_SUCCESS(3, "处理成功"),

    /**
     * 任务异常
     */
    TASK_FAIL(4, "处理失败"),;

    /**
     * The Status.
     */
    @Getter
    int status;
    /**
     * The Value.
     */
    @Getter
    String value;

    private static List<Integer> getStatusList() {
        List<Integer> list = Lists.newArrayList();
        for (JobTaskStatusEnum ele : JobTaskStatusEnum.values()) {
            list.add(ele.getStatus());
        }
        return list;
    }

    /**
     * Contains boolean.
     *
     * @param status the status
     *
     * @return the boolean
     */
    public static boolean contains(Integer status) {
        List<Integer> statusList = getStatusList();
        return statusList.contains(status);
    }

}
