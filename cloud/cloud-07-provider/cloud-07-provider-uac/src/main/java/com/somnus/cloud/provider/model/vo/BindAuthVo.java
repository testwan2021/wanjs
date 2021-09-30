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
package com.somnus.cloud.provider.model.vo;

import java.util.List;

import com.somnus.cloud.provider.api.vo.MenuVo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @ClassName: BindAuthVo
 * @Description: The class Bind auth vo.
 * @author Somnus
 * @date 2018年10月18日
 */
@Data
@ApiModel
public class BindAuthVo {
	/**
	 * 包含按钮权限和菜单权限
	 */
	private List<MenuVo> authTree;
	/**
	 * 该角色含有的权限ID
	 */
	private List<Long> checkedAuthList;
}
