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

import com.somnus.cloud.provider.model.dto.user.LoginRespDto;

/**
 * @ClassName: UacLoginService
 * @Description: The interface Uac login service.
 * @author Somnus
 * @date 2018年10月17日
 */
public interface UacLoginService {

	/**
	 * Login after login resp dto.
	 *
	 * @param applicationId the application id
	 *
	 * @return the login resp dto
	 */
	LoginRespDto loginAfter(Long applicationId);

}
