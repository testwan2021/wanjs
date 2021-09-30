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

/**
 * @ClassName: UacRoleStatusEnum
 * @Description: The enum Uac role status enum.
 * @author Somnus
 * @date 2018年10月17日
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum UacRoleStatusEnum {
    /**
     * 启用
     */
    ENABLE("ENABLE", "启用"),
    /**
     * 禁用
     */
    DISABLE("DISABLE", "禁用");

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
        for (UacRoleStatusEnum ele : UacRoleStatusEnum.values()) {
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
    public static UacRoleStatusEnum typeOf(String type) {
        for (UacRoleStatusEnum ele : UacRoleStatusEnum.values()) {
            if (type.equals(ele.getType())) {
                return ele;
            }
        }
        return UacRoleStatusEnum.ENABLE;
    }
}
