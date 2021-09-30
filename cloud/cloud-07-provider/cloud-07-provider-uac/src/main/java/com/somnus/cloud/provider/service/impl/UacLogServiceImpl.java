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

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.somnus.cloud.common.base.dto.LoginAuthDto;
import com.somnus.cloud.common.util.PublicUtil;
import com.somnus.cloud.comon.core.annotation.OperationLogDto;
import com.somnus.cloud.comon.core.support.BaseService;
import com.somnus.cloud.provider.mapper.UacLogMapper;
import com.somnus.cloud.provider.model.domain.UacAction;
import com.somnus.cloud.provider.model.domain.UacLog;
import com.somnus.cloud.provider.model.dto.log.UacLogMainDto;
import com.somnus.cloud.provider.service.OpcRpcService;
import com.somnus.cloud.provider.service.UacActionService;
import com.somnus.cloud.provider.service.UacLogService;

/**
 * @ClassName: UacLogServiceImpl
 * @Description: The class Uac log service.
 * @author Somnus
 * @date 2018年10月18日
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UacLogServiceImpl extends BaseService<UacLog> implements UacLogService {
	@Autowired
	private UacLogMapper uacLogMapper;
	
	@Autowired
	private OpcRpcService opcRpcService;
	
	@Autowired
	private UacActionService uacActionService;

	@Override
	public int saveLog(UacLog uacLog, LoginAuthDto loginAuthDto) {
		// 根据uri 查询url对应的权限
		UacAction uacAction = uacActionService.matchesByUrl(uacLog.getRequestUrl());
		if (uacAction != null) {
			uacLog.setActionId(uacAction.getId());
			uacLog.setActionCode(uacAction.getActionCode());
			uacLog.setActionName(uacAction.getActionName());
		}
		uacLog.setUpdateInfo(loginAuthDto);
		uacLog.setId(this.generateId());
		return uacLogMapper.insertSelective(uacLog);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<UacLog> selectUserLogListByUserId(Long userId) {
		return uacLogMapper.selectUserLogListByUserId(userId);
	}

	@Override
	public Integer saveOperationLog(OperationLogDto operationLogDto) {
		// 根据uri 查询url对应的权限
		UacAction uacAction = uacActionService.matchesByUrl(operationLogDto.getRequestUrl());
		if (uacAction != null) {
			operationLogDto.setActionCode(uacAction.getActionCode());
			operationLogDto.setActionName(uacAction.getActionName());
			operationLogDto.setActionId(uacAction.getId());
		}
		ModelMapper modelMapper = new ModelMapper();
		UacLog uacLog = modelMapper.map(operationLogDto, UacLog.class);
		uacLog.setId(generateId());
		// 获取操作位置
		String locationById = opcRpcService.getLocationById(operationLogDto.getIp());
		uacLog.setLocation(locationById);
		return uacLogMapper.insertSelective(uacLog);
	}

	@Override
	public PageInfo<UacLog> queryLogListWithPage(UacLogMainDto uacLogQueryDtoPage) {
		List<Long> menuIdList = uacLogQueryDtoPage.getMenuIdList();
		Long actionId = null;
		if (PublicUtil.isNotEmpty(menuIdList)) {
			actionId = menuIdList.get(menuIdList.size() - 1);
		}
		uacLogQueryDtoPage.setMenuId(actionId);
		PageHelper.startPage(uacLogQueryDtoPage.getPageNum(), uacLogQueryDtoPage.getPageSize());
		List<UacLog> actionList = uacLogMapper.queryLogListWithPage(uacLogQueryDtoPage);
		return new PageInfo<>(actionList);
	}
}
