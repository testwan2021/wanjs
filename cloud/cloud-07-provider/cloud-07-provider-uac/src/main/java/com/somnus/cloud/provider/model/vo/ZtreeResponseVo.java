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

import java.io.Serializable;

import com.somnus.cloud.provider.model.enums.ZtreeAuthTypeEnum;

/**
 * @ClassName: ZtreeResponseVo
 * @Description: The class Z tree response vo.
 * @author Somnus
 * @date 2018年10月18日
 */
public class ZtreeResponseVo implements Serializable {

	private static final long serialVersionUID = -5567984072691093330L;

	private Long id;

	private Long pId;

	private String name;

	private boolean open;

	/**
	 * 菜单选中状态, true选中, false为未选中
	 */
	private boolean checked;

	private String iconOpen;

	private String iconClose;

	/**
	 * {@link ZtreeAuthTypeEnum}
	 */
	private String type;

	/**
	 * Gets id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets id.
	 *
	 * @param id the id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets id.
	 *
	 * @return the id
	 */
	public Long getpId() {
		return pId;
	}

	/**
	 * Sets id.
	 *
	 * @param pId the p id
	 */
	public void setpId(Long pId) {
		this.pId = pId;
	}

	/**
	 * Gets name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets name.
	 *
	 * @param name the name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Is open boolean.
	 *
	 * @return the boolean
	 */
	public boolean isOpen() {
		return open;
	}

	/**
	 * Sets open.
	 *
	 * @param open the open
	 */
	public void setOpen(boolean open) {
		this.open = open;
	}

	/**
	 * Gets icon open.
	 *
	 * @return the icon open
	 */
	public String getIconOpen() {
		return iconOpen;
	}

	/**
	 * Sets icon open.
	 *
	 * @param iconOpen the icon open
	 */
	public void setIconOpen(String iconOpen) {
		this.iconOpen = iconOpen;
	}

	/**
	 * Gets icon close.
	 *
	 * @return the icon close
	 */
	public String getIconClose() {
		return iconClose;
	}

	/**
	 * Sets icon close.
	 *
	 * @param iconClose the icon close
	 */
	public void setIconClose(String iconClose) {
		this.iconClose = iconClose;
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
	 * Equals boolean.
	 *
	 * @param o the o
	 *
	 * @return the boolean
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		ZtreeResponseVo that = (ZtreeResponseVo) o;

		return id != null ? id.equals(that.id) : that.id == null;

	}

	/**
	 * Hash code int.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

	/**
	 * Is checked boolean.
	 *
	 * @return the boolean
	 */
	public boolean isChecked() {
		return checked;
	}

	/**
	 * Sets checked.
	 *
	 * @param checked the checked
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
