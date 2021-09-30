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
package com.somnus.cloud.common.base.exception;

/**
 * @ClassName: ImportException
 * @Description: The class Import exception.
 * @author Somnus
 * @date 2018年9月27日
 */
public class ImportException extends RuntimeException {

	private static final long serialVersionUID = -4740091660440744697L;

	/**
	 * Instantiates a new Import exception.
	 *
	 * @param message the message
	 */
	public ImportException(String message) {
		super(message);
	}
}
