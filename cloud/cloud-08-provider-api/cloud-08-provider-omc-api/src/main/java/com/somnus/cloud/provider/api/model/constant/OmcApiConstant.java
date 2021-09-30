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
 * @ClassName: OmcApiConstant
 * @Description: The class Omc api constant.
 * @author Somnus
 * @date 2018年10月16日
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OmcApiConstant {

    /**
     * The interface Cart.
     */
    public interface Cart {
        /**
         * 即购物车选中状态
         */
        int CHECKED = 1;

        /**
         * 购物车中未选中状态
         */
        int UN_CHECKED = 0;
        /**
         * The constant LIMIT_NUM_FAIL.
         */
        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";
        /**
         * The constant LIMIT_NUM_SUCCESS.
         */
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
    }

    /**
     * The interface Shipping.
     */
    public interface Shipping {
        /**
         * The constant DEFAULT.
         */
        int DEFAULT = 1;
        /**
         * The constant NOT_DEFAULT.
         */
        int NOT_DEFAULT = 0;
    }

    /**
     * The enum Order status enum.
     */
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum OrderStatusEnum {
        /**
         * Canceled order status enum.
         */
        CANCELED(0, "已取消"),
        /**
         * No pay order status enum.
         */
        NO_PAY(10, "未支付"),
        /**
         * Paid order status enum.
         */
        PAID(20, "已付款"),
        /**
         * Shipped order status enum.
         */
        SHIPPED(40, "已发货"),
        /**
         * Order success order status enum.
         */
        ORDER_SUCCESS(50, "订单完成"),
        /**
         * Order close order status enum.
         */
        ORDER_CLOSE(60, "订单关闭");

        @Getter
        private int code;

        @Getter
        private String value;

        /**
         * Code of order status enum.
         *
         * @param code the code
         *
         * @return the order status enum
         */
        public static OrderStatusEnum codeOf(int code) {
            OrderStatusEnum result = null;
            for (OrderStatusEnum paymentTypeEnum : values()) {
                if (paymentTypeEnum.getCode() == code) {
                    result = paymentTypeEnum;
                    break;
                }
            }
            return result;
        }
    }
}
