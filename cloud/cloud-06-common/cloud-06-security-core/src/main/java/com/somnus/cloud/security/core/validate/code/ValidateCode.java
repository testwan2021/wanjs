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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName: ValidateCode
 * @Description: 验证码信息封装类
 * @author Somnus
 * @date 2018年10月12日
 */
public class ValidateCode implements Serializable {


	private static final long serialVersionUID = 1588203828504660915L;

	private String code;

	private String type;

	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime expireTime;

	/**
	 * Instantiates a new Validate code.
	 */
	public ValidateCode() {
	}

	/**
	 * Instantiates a new Validate code.
	 *
	 * @param code     the code
	 * @param expireIn the expire in
	 */
	public ValidateCode(String code, int expireIn) {
		this.code = code;
		this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
	}

	/**
	 * Instantiates a new Validate code.
	 *
	 * @param code       the code
	 * @param expireTime the expire time
	 */
	public ValidateCode(String code, LocalDateTime expireTime) {
		this.code = code;
		this.expireTime = expireTime;
	}

	/**
	 * Is expired boolean.
	 *
	 * @return the boolean
	 */
	@JsonIgnore
	public boolean isExpired() {
		return LocalDateTime.now().isAfter(expireTime);
	}

	/**
	 * Gets code.
	 *
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets code.
	 *
	 * @param code the code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Gets expire time.
	 *
	 * @return the expire time
	 */
	public LocalDateTime getExpireTime() {
		return expireTime;
	}

	/**
	 * Sets expire time.
	 *
	 * @param expireTime the expire time
	 */
	public void setExpireTime(LocalDateTime expireTime) {
		this.expireTime = expireTime;
	}

	/**
	 * Gets type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets type.
	 *
	 * @param type the type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * To string string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("ValidateCode{");
		sb.append("code='").append(code).append('\'');
		sb.append(", type='").append(type).append('\'');
		sb.append(", expireTime=").append(expireTime);
		sb.append('}');
		return sb.toString();
	}
}
