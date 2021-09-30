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
package com.somnus.cloud.common.util.helper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @ClassName: LongJsonDeserializer
 * @Description: 将字符串转为Long
 * @author Somnus
 * @date 2018年10月9日
 */
@Slf4j
public class LongJsonDeserializer extends JsonDeserializer<Long> {

	/**
	 * Deserialize long.
	 *
	 * @param jsonParser             the json parser
	 * @param deserializationContext the deserialization context
	 *
	 * @return the long
	 */
	@Override
	public Long deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
		String value = null;
		try {
			value = jsonParser.getText();
		} catch (IOException e) {
			log.error("deserialize={}", e.getMessage(), e);
		}
		try {
			return value == null ? null : Long.parseLong(value);
		} catch (NumberFormatException e) {
			log.error("解析长整形错误", e);
			return null;
		}
	}
}