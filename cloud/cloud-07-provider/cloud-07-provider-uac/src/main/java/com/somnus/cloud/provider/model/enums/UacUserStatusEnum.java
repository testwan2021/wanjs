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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: UacUserStatusEnum
 * @Description: The enum Uac user status enum.
 * @author Somnus
 * @date 2018年10月17日
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum UacUserStatusEnum {
    /**
     * 启用
     */
    ENABLE("ENABLE", "启用"),
    /**
     * 禁用
     */
    DISABLE("DISABLE", "禁用");

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
        for (UacUserStatusEnum ele : UacUserStatusEnum.values()) {
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
    public static UacUserStatusEnum keyOf(String key) {
        for (UacUserStatusEnum ele : UacUserStatusEnum.values()) {
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
    public static List<UacUserStatusEnum> getList() {
        return Arrays.asList(UacUserStatusEnum.values());
    }


    /**
     * 获取map类型集合
     *
     * @return Map类型List集合 map 2 list
     */
    public static List<Map<String, String>> getMap2List() {
        List<Map<String, String>> list = Lists.newArrayList();
        for (UacUserStatusEnum ele : UacUserStatusEnum.values()) {
            Map<String, String> map = Maps.newHashMap();
            map.put("key", ele.getKey());
            map.put("value", ele.getValue());
            list.add(map);
        }
        return list;
    }
}
