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

import java.util.Map;
import java.util.Set;

/**
 * @ClassName: OptSendMailService
 * @Description: The interface Opt send mail service.
 * @author Somnus
 * @date 2018年10月17日
 */
public interface OptSendMailService {
	/**
	 * Send simple mail int.
	 *
	 * @param subject the subject
	 * @param text    the text
	 * @param to      the to
	 *
	 * @return the int
	 */
	int sendSimpleMail(String subject, String text, Set<String> to);

	/**
	 * Send template mail int.
	 *
	 * @param subject the subject
	 * @param text    the text
	 * @param to      the to
	 *
	 * @return the int
	 */
	int sendTemplateMail(String subject, String text, Set<String> to);

	/**
	 * Send template mail int.
	 *
	 * @param model            the model
	 * @param templateLocation the template location
	 * @param subject          the subject
	 * @param to               the to
	 *
	 * @return the int
	 */
	int sendTemplateMail(Map<String, Object> model, String templateLocation, String subject, Set<String> to);
}
