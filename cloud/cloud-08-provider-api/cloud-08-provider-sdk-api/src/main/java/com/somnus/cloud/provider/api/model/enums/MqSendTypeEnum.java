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

/**
 * @ClassName: MqSendTypeEnum
 * @Description: The enum Mq send type enum.
 * @author Somnus
 * @date 2018年10月16日
 */
public enum MqSendTypeEnum {
    /**
     * 等待确认.
     */
    WAIT_CONFIRM,

    /**
     * 直接发送.
     */
    SAVE_AND_SEND,

    /**
     * 直接发送
     */
    //TODO 消费切口 有问题, 日后修复 暂时不可用
    @Deprecated
    DIRECT_SEND
}
