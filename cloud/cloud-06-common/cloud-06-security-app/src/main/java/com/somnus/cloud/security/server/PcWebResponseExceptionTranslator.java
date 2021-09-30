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
package com.somnus.cloud.security.server;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

/**
 * @ClassName: PcWebResponseExceptionTranslator
 * @Description: The class Pc web response exception translator.
 * @author Somnus
 * @date 2018年10月15日
 */
public class PcWebResponseExceptionTranslator implements WebResponseExceptionTranslator {
	/**
	 * Translate response entity.
	 *
	 * @param e the e
	 *
	 * @return the response entity
	 *
	 * @throws Exception the exception
	 */
	@Override
	public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
		return null;
	}
}
