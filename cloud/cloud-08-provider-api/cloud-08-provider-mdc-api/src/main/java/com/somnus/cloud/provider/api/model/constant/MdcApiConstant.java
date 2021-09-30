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
package com.somnus.cloud.provider.api.model.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName: MdcApiConstant
 * @Description: The class Mdc api constant.
 * @author Somnus
 * @date 2018年10月16日
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MdcApiConstant {

    /**
     * The enum Product status enum.
     */
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum ProductStatusEnum {
        /**
         * On sale product status enum.
         */
        ON_SALE(1, "在线"),
        /**
         * Off sale product status enum.
         */
        OFF_SALE(2, "下架"),
        /**
         * Delete product status enum.
         */
        DELETE(3, "删除");

        @Getter
        private int code;

        @Getter
        private String value;

    }
}
