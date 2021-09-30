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
 * @ClassName: AddressTypeEnum
 * @Description: 地址类型枚举类
 * @author Somnus
 * @date 2018年10月17日
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum AddressTypeEnum {
    /**
     * Province address type enum.
     */
    PROVINCE("province"),
    /**
     * City address type enum.
     */
    CITY("city"),
    /**
     * District address type enum.
     */
    DISTRICT("district"),
    /**
     * Street address type enum.
     */
    STREET("street");

    @Getter
    private String type;

    /**
     * Gets enum.
     *
     * @param type the type
     *
     * @return the enum
     */
    public static AddressTypeEnum typeOf(String type) {
        for (AddressTypeEnum ele : AddressTypeEnum.values()) {
            if (ele.getType().equals(type)) {
                return ele;
            }
        }
        return null;
    }
}
