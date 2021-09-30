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
package com.somnus.cloud.provider.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.somnus.cloud.common.base.enums.ErrorCodeEnum;
import com.somnus.cloud.comon.core.support.BaseService;
import com.somnus.cloud.provider.api.exceptions.UacBizException;
import com.somnus.cloud.provider.mapper.UacRoleMenuMapper;
import com.somnus.cloud.provider.model.domain.UacRoleMenu;
import com.somnus.cloud.provider.service.UacRoleMenuService;

/**
 * @ClassName: UacRoleMenuServiceImpl
 * @Description: The class Uac role menu service.
 * @author Somnus
 * @date 2018年10月18日
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UacRoleMenuServiceImpl extends BaseService<UacRoleMenu> implements UacRoleMenuService {
	
	@Autowired
	private UacRoleMenuMapper uacRoleMenuMapper;

	@Override
	public int delRoleMenuList(Set<UacRoleMenu> uacRoleMenus) {
		int result = 0;
		for (UacRoleMenu uacRoleMenu : uacRoleMenus) {
			result += uacRoleMenuMapper.delete(uacRoleMenu);
		}
		return result;
	}

	@Override
	public void deleteByRoleId(Long roleId) {
		if (roleId == null) {
			throw new UacBizException(ErrorCodeEnum.UAC10012001);
		}
		UacRoleMenu roleMenu = new UacRoleMenu();
		roleMenu.setRoleId(roleId);
		uacRoleMenuMapper.delete(roleMenu);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<UacRoleMenu> listByRoleId(Long roleId) {
		if (roleId == null) {
			throw new UacBizException(ErrorCodeEnum.UAC10012001);
		}
		UacRoleMenu roleMenu = new UacRoleMenu();
		roleMenu.setRoleId(roleId);
		return uacRoleMenuMapper.select(roleMenu);
	}

	@Override
	public void insert(Long roleId, Set<Long> menuIdList) {
		if (roleId == null) {
			throw new UacBizException(ErrorCodeEnum.UAC10012001);
		}
		UacRoleMenu uacRoleMenu = new UacRoleMenu();
		uacRoleMenu.setRoleId(roleId);
		for (Long menuId : menuIdList) {
			uacRoleMenu.setMenuId(menuId);
			uacRoleMenuMapper.insertSelective(uacRoleMenu);
		}
	}

	@Override
	public void deleteByRoleIdList(final List<Long> roleIdList) {
		uacRoleMenuMapper.deleteByRoleIdList(roleIdList);
	}
}
