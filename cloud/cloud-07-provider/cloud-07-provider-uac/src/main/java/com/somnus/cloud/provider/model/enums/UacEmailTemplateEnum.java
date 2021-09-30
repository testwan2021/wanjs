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
 * @ClassName: UacEmailTemplateEnum
 * @Description: The enum Uac email template enum.
 * @author Somnus
 * @date 2018年10月17日
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum UacEmailTemplateEnum {
    /**
     * 重置密码-获取验证码
     */
    RESET_PWD_GET_CODE("RESET_PWD_CODE", "重置密码-获取验证码", "email/sendRestPwdCodeTemplate.ftl"),
    /**
     * 忘记密码-等待重置密码.
     */
    RESET_PWD_SEND_MAIL("RESET_PWD_CODE", "忘记密码-等待重置密码", "email/sendRestLoginPwdTemplate.ftl"),
    /**
     * 注册用户-等待激活用户.
     */
    ACTIVE_USER("ACTIVE_USER", "注册用户-等待激活用户", "email/activeUserTemplate.ftl"),
    /**
     * 注册用户-激活用户成功.
     */
    ACTIVE_USER_SUCCESS("ACTIVE_USER_SUCCESS", "注册用户-激活用户成功", "email/activeUserSuccessTemplate.ftl"),
    /**
     * 忘记密码-重置密码
     */
    RESET_LOGIN_PWD("RESET_LOGIN_PWD", "忘记密码-重置密码", "email/sendRestLoginPwdSuccessTemplate.ftl"),
    /**
     * 忘记密码-邮件验证码
     */
    RESET_USER_EMAIL("RESET_USER_EMAIL", "修改邮箱-邮件验证码", "email/sendRestUserEmailTemplate.ftl"),;

    /**
     * The Key.
     */
    @Getter
    String key;
    /**
     * The Subject.
     */
    @Getter
    String subject;
    /**
     * The Location.
     */
    @Getter
    String location;

}
