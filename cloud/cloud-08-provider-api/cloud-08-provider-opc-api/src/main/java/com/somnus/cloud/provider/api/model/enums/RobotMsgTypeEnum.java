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

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName: RobotMsgTypeEnum
 * @Description: The enum Robot msg type enum.
 * @author Somnus
 * @date 2018年10月16日
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum RobotMsgTypeEnum {
    /**
     * 操作日志
     */
    MARKDOWN("markdown", "markdown"),
    /**
     * Link robot msg type enum.
     */
    LINK("link", "link"),
    /**
     * Text robot msg type enum.
     */
    TEXT("text", "text"),;

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
        for (RobotMsgTypeEnum ele : RobotMsgTypeEnum.values()) {
            if (ele.getType().equals(type)) {
                return ele.getName();
            }
        }
        return null;
    }

}
