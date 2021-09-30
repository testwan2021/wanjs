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
 * @ClassName: MqConfirmStatusEnum
 * @Description: The enum Mq confirm status enum.
 * @author Somnus
 * @date 2018年10月17日
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum MqConfirmStatusEnum {
    /**
     * 未确认.
     */
    UN_CONFIRMED(10, "未确认"),

    /**
     * 已确认.
     */
    CONFIRMED(20, "已确认"),

    /**
     * 已消费
     */
    CONSUMED(30, "已消费");

    @Getter
    private int confirmStatus;

    @Getter
    private String value;

}
