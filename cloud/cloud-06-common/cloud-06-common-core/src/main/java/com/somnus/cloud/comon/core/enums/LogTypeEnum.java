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
package com.somnus.cloud.comon.core.enums;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: LogTypeEnum
 * @Description: The enum Log type enum.
 * @author Somnus
 * @date 2018年9月27日
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum LogTypeEnum {
    /**
     * 操作日志
     */
    OPERATION_LOG("10", "操作日志"),
    /**
     * 登录日志
     */
    LOGIN_LOG("20", "登录日志"),
    /**
     * 异常日志
     */
    EXCEPTION_LOG("30", "异常日志");

    /**
     * The Type.
     */
    @Getter
    String type;
    /**
     * The Name.
     */
    @Getter
    String name;

    /**
     * Gets name.
     *
     * @param type the type
     *
     * @return the name
     */
    public static String getName(String type) {
        for (LogTypeEnum ele : LogTypeEnum.values()) {
            if (type.equals(ele.getType())) {
                return ele.getName();
            }
        }
        return null;
    }

    /**
     * Gets enum.
     *
     * @param type the type
     *
     * @return the enum
     */
    public static LogTypeEnum typeOf(String type) {
        for (LogTypeEnum ele : LogTypeEnum.values()) {
            if (type.equals(ele.getType())) {
                return ele;
            }
        }
        return LogTypeEnum.OPERATION_LOG;
    }

    /**
     * Gets list.
     *
     * @return the list
     */
    public static List<Map<String, Object>> getList() {
        List<Map<String, Object>> list = Lists.newArrayList();
        for (LogTypeEnum ele : LogTypeEnum.values()) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("key", ele.getType());
            map.put("value", ele.getName());
            list.add(map);
        }
        return list;
    }

}
