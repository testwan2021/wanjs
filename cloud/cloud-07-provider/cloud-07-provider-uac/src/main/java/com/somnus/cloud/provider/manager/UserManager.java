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
package com.somnus.cloud.provider.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;
import com.somnus.cloud.common.base.constant.GlobalConstant;
import com.somnus.cloud.common.base.enums.ErrorCodeEnum;
import com.somnus.cloud.provider.api.annotation.MqProducerStore;
import com.somnus.cloud.provider.api.exceptions.UacBizException;
import com.somnus.cloud.provider.api.model.domain.MqMessageData;
import com.somnus.cloud.provider.api.model.enums.MqSendTypeEnum;
import com.somnus.cloud.provider.mapper.UacGroupUserMapper;
import com.somnus.cloud.provider.mapper.UacRoleUserMapper;
import com.somnus.cloud.provider.mapper.UacUserMapper;
import com.somnus.cloud.provider.model.domain.UacGroupUser;
import com.somnus.cloud.provider.model.domain.UacRoleUser;
import com.somnus.cloud.provider.model.domain.UacUser;
import com.somnus.cloud.provider.service.impl.RedisServiceImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: UserManager
 * @Description: The class User manager.
 * @author Somnus
 * @date 2018年10月17日
 */
@Slf4j
@Component
@Transactional(rollbackFor = Exception.class)
public class UserManager {
	@Autowired
	private UacUserMapper uacUserMapper;
	
	@Autowired
	private UacRoleUserMapper uacRoleUserMapper;
	
	@Autowired
	private UacGroupUserMapper uacGroupUserMapper;
	
	@Autowired
	private RedisServiceImpl redisService;

	@MqProducerStore(sendType = MqSendTypeEnum.SAVE_AND_SEND)
	public void submitResetPwdEmail(final MqMessageData messageData) {
		log.info("重置密码发送邮件. messageData={}", messageData);
	}

	@MqProducerStore
	public void register(final MqMessageData mqMessageData, final UacUser uacUser) {
		log.info("注册用户. mqMessageData={}, user={}", mqMessageData, uacUser);
		uacUserMapper.insertSelective(uacUser);
	}

	@MqProducerStore
	public void resetLoginPwd(final MqMessageData mqMessageData, final UacUser update) {
		log.info("重置密码. mqMessageData={}, user={}", mqMessageData, update);
		int updateResult = uacUserMapper.updateByPrimaryKeySelective(update);
		if (updateResult < 1) {
			log.error("用户【 {} 】重置密码失败", update.getLoginName());
		} else {
			log.info("用户【 {} 】重置密码失败", update.getLoginName());
		}
	}

	@MqProducerStore
	public void activeUser(final MqMessageData mqMessageData, final UacUser uacUser, final String activeUserKey) {
		log.info("激活用户. mqMessageData={}, user={}", mqMessageData, uacUser);
		int result = uacUserMapper.updateByPrimaryKeySelective(uacUser);
		if (result < 1) {
			throw new UacBizException(ErrorCodeEnum.UAC10011038, uacUser.getId());
		}

		// 绑定一个访客角色默认值roleId=10000
		final Long userId = uacUser.getId();
		Preconditions.checkArgument(userId != null, "用戶Id不能爲空");

		final Long roleId = 10000L;

		UacRoleUser roleUser = new UacRoleUser();
		roleUser.setUserId(userId);
		roleUser.setRoleId(roleId);
		uacRoleUserMapper.insertSelective(roleUser);
		// 绑定一个组织
		UacGroupUser groupUser = new UacGroupUser();
		groupUser.setUserId(userId);
		groupUser.setGroupId(GlobalConstant.Sys.SUPER_MANAGER_GROUP_ID);
		uacGroupUserMapper.insertSelective(groupUser);
		// 删除 activeUserToken
		redisService.deleteKey(activeUserKey);
	}

	@MqProducerStore(sendType = MqSendTypeEnum.SAVE_AND_SEND)
	public void sendSmsCode(final MqMessageData mqMessageData) {
		log.info("发送短信验证码. mqMessageData={}", mqMessageData);
	}

	@MqProducerStore(sendType = MqSendTypeEnum.SAVE_AND_SEND)
	public void sendEmailCode(final MqMessageData mqMessageData) {
		log.info("发送邮件验证码. mqMessageData={}", mqMessageData);
	}
}
