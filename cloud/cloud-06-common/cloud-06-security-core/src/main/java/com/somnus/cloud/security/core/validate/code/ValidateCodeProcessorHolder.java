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
package com.somnus.cloud.security.core.validate.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @ClassName: ValidateCodeProcessorHolder
 * @Description: 校验码处理器管理器
 * @author Somnus
 * @date 2018年10月12日
 */
@Component
public class ValidateCodeProcessorHolder {

	private final Map<String, ValidateCodeProcessor> validateCodeProcessors;

	/**
	 * Instantiates a new Validate code processor holder.
	 *
	 * @param validateCodeProcessors the validate code processors
	 */
	@Autowired
	public ValidateCodeProcessorHolder(Map<String, ValidateCodeProcessor> validateCodeProcessors) {
		this.validateCodeProcessors = validateCodeProcessors;
	}

	/**
	 * Find validate code processor validate code processor.
	 *
	 * @param type the type
	 *
	 * @return validate code processor
	 */
	ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeType type) {
		return findValidateCodeProcessor(type.toString().toLowerCase());
	}

	/**
	 * Find validate code processor validate code processor.
	 *
	 * @param type the type
	 *
	 * @return validate code processor
	 */
	ValidateCodeProcessor findValidateCodeProcessor(String type) {
		String name = type.toLowerCase() + ValidateCodeProcessor.class.getSimpleName();
		ValidateCodeProcessor processor = validateCodeProcessors.get(name);
		if (processor == null) {
			throw new ValidateCodeException("验证码处理器" + name + "不存在");
		}
		return processor;
	}

}
