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

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.somnus.cloud.common.base.constant.GlobalConstant;
import com.somnus.cloud.comon.core.support.BaseService;
import com.somnus.cloud.provider.api.model.dto.GlobalExceptionLogDto;
import com.somnus.cloud.provider.api.model.dto.robot.ChatRobotMsgDto;
import com.somnus.cloud.provider.api.model.factory.ChatRobotMsgFactory;
import com.somnus.cloud.provider.mapper.MdcExceptionLogMapper;
import com.somnus.cloud.provider.model.domain.MdcExceptionLog;
import com.somnus.cloud.provider.model.dto.MdcExceptionQueryDto;
import com.somnus.cloud.provider.service.MdcExceptionLogService;
import com.somnus.cloud.provider.service.OpcRpcService;

/**
 * @ClassName: MdcExceptionLogServiceImpl
 * @Description: The class Mdc exception log service.
 * @author Somnus
 * @date 2018年10月17日
 */
@Service
public class MdcExceptionLogServiceImpl extends BaseService<MdcExceptionLog> implements MdcExceptionLogService {
	@Autowired
	private MdcExceptionLogMapper mdcExceptionLogMapper;
	
	@Autowired
	private TaskExecutor taskExecutor;
	
	@Autowired
	private OpcRpcService opcRpcService;
	
	@Value("${cloud.dingTalk.webhookToken.sendException}")
	private String webhookToken;

	@Override
	public void saveAndSendExceptionLog(final GlobalExceptionLogDto exceptionLogDto) {
		MdcExceptionLog exceptionLog = new ModelMapper().map(exceptionLogDto, MdcExceptionLog.class);

		exceptionLog.setId(generateId());
		exceptionLog.setCreateTime(new Date());
		mdcExceptionLogMapper.insertSelective(exceptionLog);

		taskExecutor.execute(() -> {
			if (judgeIsSend(exceptionLogDto.getProfile())) {
				String text = exceptionLog.getApplicationName() + "出现异常. 环境：" + exceptionLogDto.getProfile() + "，操作人：" + exceptionLogDto.getCreator() + ".异常类型：" + exceptionLogDto.getExceptionSimpleName();
				ChatRobotMsgDto chatRobotMsgDto = ChatRobotMsgFactory.createChatRobotTextMsg(webhookToken, text, false, null);
				opcRpcService.sendChatRobotMsg(chatRobotMsgDto);
			}
		});

	}

	@Override
	public PageInfo<MdcExceptionLog> queryExceptionListWithPage(final MdcExceptionQueryDto mdcExceptionQueryDto) {
		PageHelper.startPage(mdcExceptionQueryDto.getPageNum(), mdcExceptionQueryDto.getPageSize());
		List<MdcExceptionLog> actionList = mdcExceptionLogMapper.queryExceptionListWithPage(mdcExceptionQueryDto);
		return new PageInfo<>(actionList);
	}

	private boolean judgeIsSend(String profile) {
		Calendar calendar = Calendar.getInstance();
		int time = calendar.get(Calendar.HOUR_OF_DAY);
		return GlobalConstant.PRO_PROFILE.equals(profile) || time >= 10 && time <= 18;
	}
}