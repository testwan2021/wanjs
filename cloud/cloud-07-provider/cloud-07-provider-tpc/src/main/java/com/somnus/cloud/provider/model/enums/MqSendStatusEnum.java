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
 * @ClassName: MqSendStatusEnum
 * @Description: The enum Mq send status enum.
 * @author Somnus
 * @date 2018年10月17日
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum MqSendStatusEnum {
    /**
     * 未发送.
     */
    WAIT_SEND(10, "未发送"),

    /**
     * 已发送.
     */
    SENDING(20, "已发送"),

    /**
     * 已完成
     */
    FINISH(30, "已完成");

    @Getter
    private int sendStatus;

    @Getter
    private String value;

}
