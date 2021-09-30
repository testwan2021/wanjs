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
package com.somnus.cloud.provider.model.domain;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @ClassName: UacRoleMenu
 * @Description: The class Uac role menu.
 * @author Somnus
 * @date 2018年10月17日
 */
@Data
@Table(name = "pc_uac_role_menu")
@Alias(value = "uacRoleMenu")
public class UacRoleMenu implements Serializable {
	private static final long serialVersionUID = -9052683954152822756L;
	@Id
	@Column(name = "role_id")
	private Long roleId;

	@Id
	@Column(name = "menu_id")
	private Long menuId;
}