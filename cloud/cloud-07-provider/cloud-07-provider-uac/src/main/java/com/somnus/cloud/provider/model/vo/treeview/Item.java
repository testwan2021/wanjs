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
package com.somnus.cloud.provider.model.vo.treeview;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @ClassName: Item
 * @Description: The class Item.
 * @author Somnus
 * @date 2018年10月18日
 */
@Data
@ApiModel
public class Item {

	private Long id;
	/**
	 * 节点的名字
	 */
	private String text;

	/**
	 * 节点的类型："item":文件 "folder":目录
	 */
	private String type;

	/**
	 * 子节点的信息
	 */
	private AdditionalParameters additionalParameters;

}
