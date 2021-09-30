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
package com.somnus.cloud.provider.service;

import com.somnus.cloud.provider.model.dto.email.SendEmailMessage;

/**
 * @ClassName: EmailService
 * @Description: The interface Email service.
 * @author Somnus
 * @date 2018年10月17日
 */
public interface EmailService {
	/**
	 * 重置密码发送邮件.
	 *
	 * @param email the email
	 */
	void submitResetPwdEmail(String email);

	/**
	 * 发送验证码
	 *
	 * @param sendEmailMessage the send email message
	 * @param loginName        the login name
	 */
	void sendEmailCode(SendEmailMessage sendEmailMessage, String loginName);

	/**
	 * 校验验证码 返回 token 用户最后修改密码使用
	 *
	 * @param sendEmailMessage the send email message
	 * @param loginName        the login name
	 */
	void checkEmailCode(SendEmailMessage sendEmailMessage, String loginName);
}
