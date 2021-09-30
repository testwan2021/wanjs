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
package com.somnus.cloud.provider.model.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: UacUserSourceEnum
 * @Description: The enum Uac user source enum.
 * @author Somnus
 * @date 2018年10月17日
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum UacUserSourceEnum {
    /**
     * 注册
     */
    REGISTER("REGISTER", "注册"),
    /**
     * 后台新增
     */
    INSERT("INSERT", "后台新增"),
    /**
     * 后台导入
     */
    IMPORT("IMPORT", "后台导入"),
    /**
     * 手机
     */
    APP("APP", "手机"),;

    /**
     * The Key.
     */
    @Getter
    String key;
    /**
     * The Value.
     */
    @Getter
    String value;


    /**
     * 获取key获取value
     *
     * @param key key
     *
     * @return value value
     */
    public static String getValue(String key) {
        for (UacUserSourceEnum ele : UacUserSourceEnum.values()) {
            if (key.equals(ele.getKey())) {
                return ele.getValue();
            }
        }
        return null;
    }

    /**
     * 根据key获取该对象
     *
     * @param key key
     *
     * @return this enum
     */
    public static UacUserSourceEnum keyOf(String key) {
        for (UacUserSourceEnum ele : UacUserSourceEnum.values()) {
            if (key.equals(ele.getKey())) {
                return ele;
            }
        }
        return null;
    }

    /**
     * 获取List集合
     *
     * @return List list
     */
    public static List<UacUserSourceEnum> getList() {
        return Arrays.asList(UacUserSourceEnum.values());
    }
}
