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
 * @ClassName: PtcApiConstant
 * @Description: The class Ptc api constant.
 * @author Somnus
 * @date 2018年10月16日
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PtcApiConstant {


    /**
     * The interface Alipay callback.
     */
    public interface AlipayCallback {
        /**
         * The constant TRADE_STATUS_WAIT_BUYER_PAY.
         */
        String TRADE_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
        /**
         * The constant TRADE_STATUS_TRADE_SUCCESS.
         */
        String TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";

        /**
         * The constant RESPONSE_SUCCESS.
         */
        String RESPONSE_SUCCESS = "success";
        /**
         * The constant RESPONSE_FAILED.
         */
        String RESPONSE_FAILED = "failed";
    }

    /**
     * The enum Pay platform enum.
     */
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum PayPlatformEnum {
        /**
         * Alipay pay platform enum.
         */
        ALIPAY(1, "支付宝");

        @Getter
        private int code;

        @Getter
        private String value;
    }
}
