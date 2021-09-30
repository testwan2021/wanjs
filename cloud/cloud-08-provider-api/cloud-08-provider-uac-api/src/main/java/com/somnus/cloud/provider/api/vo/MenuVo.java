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
package com.somnus.cloud.provider.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: MenuVo
 * @Description: The class Menu vo.
 * @author Somnus
 * @date 2018年10月16日
 */
@Data
@ApiModel("菜单")
public class MenuVo implements Serializable {
	/**
	 * serialVersionUID:用一句话描述这个变量表示什么.
	 *
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = -2099147126084213856L;

	/**
	 * menu.id;
	 */
	@ApiModelProperty("菜单编号")
	private Long id;

	/**
	 * 父id
	 */
	@ApiModelProperty("父id")
	private Long pid;

	/**
	 * 菜单编码
	 */
	@ApiModelProperty("菜单编码")
	private String menuCode;

	/**
	 * 菜单名称
	 */
	@ApiModelProperty("菜单名称")
	private String menuName;

	/**
	 * 菜单URL
	 */
	@ApiModelProperty("菜单URL")
	private String url;
	/**
	 * 图标
	 */
	@ApiModelProperty("图标")
	private String icon;

	/**
	 * 序号
	 */
	@ApiModelProperty("序号")
	private String number;

	/**
	 * 备注(研发中心)
	 */
	@ApiModelProperty("备注(研发中心)")
	private String remark;

	/**
	 * 删除标识
	 */
	@ApiModelProperty("删除标识")
	private Short yn;
	private MenuVo parentMenu;

	private List<MenuVo> subMenu;

	private boolean hasMenu = false;

}