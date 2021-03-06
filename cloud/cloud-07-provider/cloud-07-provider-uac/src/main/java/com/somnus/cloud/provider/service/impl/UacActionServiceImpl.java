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
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AntPathMatcher;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.somnus.cloud.common.base.constant.GlobalConstant;
import com.somnus.cloud.common.base.dto.LoginAuthDto;
import com.somnus.cloud.common.base.enums.ErrorCodeEnum;
import com.somnus.cloud.common.util.PublicUtil;
import com.somnus.cloud.comon.core.support.BaseService;
import com.somnus.cloud.provider.api.exceptions.UacBizException;
import com.somnus.cloud.provider.api.vo.MenuVo;
import com.somnus.cloud.provider.mapper.UacActionMapper;
import com.somnus.cloud.provider.mapper.UacRoleActionMapper;
import com.somnus.cloud.provider.model.domain.UacAction;
import com.somnus.cloud.provider.model.domain.UacMenu;
import com.somnus.cloud.provider.model.dto.action.ActionMainQueryDto;
import com.somnus.cloud.provider.model.vo.ActionVo;
import com.somnus.cloud.provider.service.UacActionService;

/**
 * @ClassName: UacActionServiceImpl
 * @Description: The class Uac action service.
 * @author Somnus
 * @date 2018???10???18???
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UacActionServiceImpl extends BaseService<UacAction> implements UacActionService {
	@Autowired
	private UacActionMapper uacActionMapper;
	
	@Autowired
	private UacRoleActionMapper uacRoleActionMapper;
	
	private AntPathMatcher antPathMatcher = new AntPathMatcher();

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public PageInfo<ActionVo> queryActionListWithPage(ActionMainQueryDto actionMainQueryDto) {
		List<Long> menuIdList = actionMainQueryDto.getMenuIdList();
		Long menuId = null;
		if (PublicUtil.isNotEmpty(menuIdList)) {
			menuId = menuIdList.get(menuIdList.size() - 1);
		}
		UacAction uacAction = new UacAction();
		uacAction.setMenuId(menuId);
		BeanUtils.copyProperties(actionMainQueryDto, uacAction);
		uacAction.setOrderBy("update_time desc");
		PageHelper.startPage(actionMainQueryDto.getPageNum(), actionMainQueryDto.getPageSize());
		List<ActionVo> actionList = uacActionMapper.queryActionListWithPage(uacAction);
		return new PageInfo<>(actionList);
	}

	@Override
	public int deleteActionById(Long actionId) {
		//???????????????????????????????????????, ???????????????????????????
		if (null == actionId) {
			throw new IllegalArgumentException("??????ID????????????");
		}

		UacAction uacAction = uacActionMapper.selectByPrimaryKey(actionId);
		if (uacAction == null) {
			logger.error("????????????????????? actionId={}", actionId);
			throw new UacBizException(ErrorCodeEnum.UAC10014001, actionId);
		}

		// ???????????????????????????  ???????????? ???????????????
		uacRoleActionMapper.deleteByActionId(actionId);

		return uacActionMapper.deleteByPrimaryKey(actionId);
	}

	@Override
	public void batchDeleteByIdList(List<Long> deleteIdList) {
		logger.info("??????????????????. deleteIdList={}", deleteIdList);
		Preconditions.checkArgument(PublicUtil.isNotEmpty(deleteIdList), "????????????ID????????????");
		int result = uacActionMapper.batchDeleteByIdList(deleteIdList);
		if (result < deleteIdList.size()) {
			throw new UacBizException(ErrorCodeEnum.UAC10014002, Joiner.on(GlobalConstant.Symbol.COMMA).join(deleteIdList));
		}
	}

	@Override
	public void saveAction(UacAction action, LoginAuthDto loginAuthDto) {
		List<Long> menuIdList = action.getMenuIdList();
		Long menuId;
		Preconditions.checkArgument(PublicUtil.isNotEmpty(menuIdList), "????????????????????????");
		menuId = menuIdList.get(menuIdList.size() - 1);
		action.setMenuId(menuId);
		action.setUpdateInfo(loginAuthDto);
		if (action.isNew()) {
			Long actionId = super.generateId();
			action.setId(actionId);
			uacActionMapper.insertSelective(action);
		} else {
			int result = uacActionMapper.updateByPrimaryKeySelective(action);
			if (result < 1) {
				throw new UacBizException(ErrorCodeEnum.UAC10014003);
			}
		}
	}

	@Override
	public int deleteByMenuId(Long id) {
		Preconditions.checkArgument(id != null, "??????ID????????????");

		return uacActionMapper.deleteByMenuId(id);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<Long> getCheckedActionList(Long roleId) {
		if (roleId == null) {
			throw new UacBizException(ErrorCodeEnum.UAC10012001);
		}
		return uacActionMapper.getCheckedActionList(roleId);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<MenuVo> getOwnAuthList(Long userId) {
		return uacActionMapper.getOwnAuthList(userId);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<Long> getCheckedMenuList(Long roleId) {
		if (roleId == null) {
			throw new UacBizException(ErrorCodeEnum.UAC10012001);
		}
		return uacActionMapper.getCheckedMenuList(roleId);
	}

	@Override
	public List<UacAction> getOwnActionListByUserId(Long userId) {
		if (userId == null) {
			throw new UacBizException(ErrorCodeEnum.UAC10011001);
		}
		List<UacAction> uacActionList;
		if (Objects.equals(userId, GlobalConstant.Sys.SUPER_MANAGER_USER_ID)) {
			// ????????????????????????
			uacActionList = uacActionMapper.selectAll();
		} else {
			uacActionList = uacActionMapper.getOwnUacActionListByUserId(userId);
		}
		return uacActionList;
	}

	@Override
	public List<UacAction> listActionListByRoleId(Long roleId) {
		return uacActionMapper.listActionListByRoleId(roleId);
	}

	@Override
	public List<UacAction> listActionList(List<UacMenu> uacMenus) {
		return uacActionMapper.listActionList(uacMenus);
	}

	@Override
	public UacAction matchesByUrl(String requestUrl) {
		List<UacAction> uacActionList = uacActionMapper.selectAll();
		for (UacAction uacAction : uacActionList) {
			String url = uacAction.getUrl();
			if (StringUtils.isEmpty(url)) {
				continue;
			}

			if (antPathMatcher.match(url, requestUrl)) {
				return uacAction;
			}

		}
		return null;
	}
}
