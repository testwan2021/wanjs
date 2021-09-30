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

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: GroupZtreeVo
 * @Description: The class Group z tree vo.
 * @author Somnus
 * @date 2018年10月18日
 */
@Getter
@Setter
public class GroupZtreeVo extends ZtreeResponseVo implements Serializable {

	private static final long serialVersionUID = 8835704500635133372L;
	/**
	 * 组织类型
	 */
	private String groupType;
	/**
	 * 组织编码
	 */
	private String groupCode;

	/**
	 * 组织流水号
	 */
	private String groupSerialNo;

	/**
	 * 组织名称
	 */
	private String groupName;

	/**
	 * 详细地址
	 */
	private String groupAddress;

	/**
	 * 所在城市
	 */
	private String groupCity;

	/**
	 * 联系人
	 */
	private String contact;

	/**
	 * 联系人电话
	 */
	private String contactPhone;

	/**
	 * 创建人
	 */
	private String creator;

	/**
	 * 图标样式
	 */
	private String iconSkin;

	/**
	 * 创建时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createdTime;

	/**
	 * 状态
	 */
	private int status;

	/**
	 * 层级
	 */
	private Integer level;
	/**
	 * 是否是节点
	 */
	private Integer leaf;

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
		if (!super.equals(o)) {
			return false;
		}

		GroupZtreeVo that = (GroupZtreeVo) o;

		return this.getId().equals(that.getId());

	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	/**
	 * To string string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "GroupZTreeVo{" +
				"groupType='" + groupType + '\'' +
				", groupCode='" + groupCode + '\'' +
				", groupSerialNo='" + groupSerialNo + '\'' +
				", groupName='" + groupName + '\'' +
				", groupAddress='" + groupAddress + '\'' +
				", groupCity='" + groupCity + '\'' +
				", contact='" + contact + '\'' +
				", contactPhone='" + contactPhone + '\'' +
				", creator='" + creator + '\'' +
				", iconSkin='" + iconSkin + '\'' +
				", createdTime=" + createdTime +
				", status='" + status + '\'' +
				", level=" + level +
				", leaf=" + leaf +
				'}';
	}
}
