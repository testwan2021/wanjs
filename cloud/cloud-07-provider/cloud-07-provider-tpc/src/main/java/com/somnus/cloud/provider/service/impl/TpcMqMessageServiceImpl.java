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

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.somnus.cloud.common.base.dto.MessageQueryDto;
import com.somnus.cloud.common.base.dto.MqMessageVo;
import com.somnus.cloud.common.base.enums.ErrorCodeEnum;
import com.somnus.cloud.common.util.PublicUtil;
import com.somnus.cloud.common.util.wrapper.WrapMapper;
import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.common.zk.generator.UniqueIdGenerator;
import com.somnus.cloud.comon.core.support.BaseService;
import com.somnus.cloud.provider.api.exceptions.TpcBizException;
import com.somnus.cloud.provider.api.model.dto.TpcMqMessageDto;
import com.somnus.cloud.provider.mapper.TpcMqConfirmMapper;
import com.somnus.cloud.provider.mapper.TpcMqMessageMapper;
import com.somnus.cloud.provider.model.domain.TpcMqConfirm;
import com.somnus.cloud.provider.model.domain.TpcMqMessage;
import com.somnus.cloud.provider.model.dto.MessageTaskQueryDto;
import com.somnus.cloud.provider.model.enums.MqSendStatusEnum;
import com.somnus.cloud.provider.model.enums.PIDEnum;
import com.somnus.cloud.provider.model.vo.TpcMessageVo;
import com.somnus.cloud.provider.mq.RocketMqProducer;
import com.somnus.cloud.provider.service.MdcRpcService;
import com.somnus.cloud.provider.service.OpcRpcService;
import com.somnus.cloud.provider.service.TpcMqConsumerService;
import com.somnus.cloud.provider.service.TpcMqMessageService;
import com.somnus.cloud.provider.service.UacRpcService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: TpcMqMessageServiceImpl
 * @Description: The class Tpc mq message service.
 * @author Somnus
 * @date 2018年10月17日
 */
@Service
@Slf4j
@Transactional(rollbackFor = Throwable.class)
public class TpcMqMessageServiceImpl extends BaseService<TpcMqMessage> implements TpcMqMessageService {
	@Autowired
	private TpcMqMessageMapper tpcMqMessageMapper;
	
	@Autowired
	private TpcMqConfirmMapper tpcMqConfirmMapper;
	
	@Autowired
	private TpcMqConsumerService tpcMqConsumerService;
	@Autowired
	private UacRpcService uacRpcService;
	
	@Autowired
	private MdcRpcService mdcRpcService;
	
	@Autowired
	private OpcRpcService opcRpcService;

	@Override
	public void saveMessageWaitingConfirm(TpcMqMessageDto messageDto) {

		if (StringUtils.isEmpty(messageDto.getMessageTopic())) {
			throw new TpcBizException(ErrorCodeEnum.TPC10050001);
		}

		Date now = new Date();
		TpcMqMessage message = new ModelMapper().map(messageDto, TpcMqMessage.class);
		message.setMessageStatus(MqSendStatusEnum.WAIT_SEND.sendStatus());
		message.setUpdateTime(now);
		message.setCreatedTime(now);
		tpcMqMessageMapper.insertSelective(message);
	}

	@Override
	public void confirmAndSendMessage(String messageKey) {
		final TpcMqMessage message = tpcMqMessageMapper.getByMessageKey(messageKey);
		if (message == null) {
			throw new TpcBizException(ErrorCodeEnum.TPC10050002);
		}

		TpcMqMessage update = new TpcMqMessage();
		update.setMessageStatus(MqSendStatusEnum.SENDING.sendStatus());
		update.setId(message.getId());
		update.setUpdateTime(new Date());
		tpcMqMessageMapper.updateByPrimaryKeySelective(update);
		// 创建消费待确认列表
		this.createMqConfirmListByTopic(message.getMessageTopic(), message.getId(), message.getMessageKey());
		// 直接发送消息
		this.directSendMessage(message.getMessageBody(), message.getMessageTopic(), message.getMessageTag(), message.getMessageKey(), message.getProducerGroup(), message.getDelayLevel());
	}

	@Override
	public void saveAndSendMessage(TpcMqMessageDto tpcMqMessageDto) {
		if (StringUtils.isEmpty(tpcMqMessageDto.getMessageTopic())) {
			throw new TpcBizException(ErrorCodeEnum.TPC10050001);
		}

		Date now = new Date();
		TpcMqMessage message = new ModelMapper().map(tpcMqMessageDto, TpcMqMessage.class);
		message.setMessageStatus(MqSendStatusEnum.SENDING.sendStatus());
		message.setId(generateId());
		message.setUpdateTime(now);
		message.setCreatedTime(now);

		tpcMqMessageMapper.insertSelective(message);
		// 创建消费待确认列表
		this.createMqConfirmListByTopic(message.getMessageTopic(), message.getId(), message.getMessageKey());
		this.directSendMessage(tpcMqMessageDto.getMessageBody(), tpcMqMessageDto.getMessageTopic(), tpcMqMessageDto.getMessageTag(), tpcMqMessageDto.getMessageKey(), tpcMqMessageDto.getProducerGroup(), tpcMqMessageDto.getDelayLevel());
	}

	@Override
	public void directSendMessage(String body, String topic, String tag, String key, String pid, Integer delayLevel) {
		RocketMqProducer.sendSimpleMessage(body, topic, tag, key, pid, delayLevel);
	}

	@Override
	public void resendMessageByMessageId(Long messageId) {
		final TpcMqMessage message = tpcMqMessageMapper.selectByPrimaryKey(messageId);
		if (message == null) {
			throw new TpcBizException(ErrorCodeEnum.TPC10050006);
		}
		this.resendMessage(message);
	}

	@Override
	public void resendMessageByMessageKey(String messageKey) {
		final TpcMqMessage task = tpcMqMessageMapper.getByMessageKey(messageKey);
		this.resendMessage(task);
	}

	@Override
	public void setMessageToAlreadyDead(Long messageId) {
		final TpcMqMessage task = tpcMqMessageMapper.selectByPrimaryKey(messageId);
		if (task == null) {
			throw new TpcBizException(ErrorCodeEnum.TPC10050006);
		}

		tpcMqMessageMapper.updateAlreadyDeadByMessageId(messageId);
	}

	@Override
	public void deleteMessageByMessageKey(String messageKey) {

		int result = tpcMqMessageMapper.deleteMessageByMessageKey(messageKey);
		if (result < 1) {
			throw new TpcBizException(ErrorCodeEnum.TPC10050003, messageKey);
		}
	}

	@Override
	public void resendAllDeadMessageByTopicName(String topicName, int batchSize) {
		// 1.查询该topic下所有死亡的消息
		// 2.分页
	}

	@Override
	public List<TpcMqMessage> listMessageForWaitingProcess(MessageTaskQueryDto query) {
		return tpcMqMessageMapper.listMessageForWaitingProcess(query);
	}

	@Override
	public void confirmReceiveMessage(final String cid, final String messageKey) {
		// 1. 校验cid
		// 2. 校验messageKey
		// 3. 校验cid 和 messageKey
		Long confirmId = tpcMqConfirmMapper.getIdMqConfirm(cid, messageKey);
		// 3. 更新消费信息的状态
		tpcMqConfirmMapper.confirmReceiveMessage(confirmId);
	}

	@Override
	public void confirmConsumedMessage(final String cid, final String messageKey) {
		Long confirmId = tpcMqConfirmMapper.getIdMqConfirm(cid, messageKey);
		tpcMqConfirmMapper.confirmConsumedMessage(confirmId);
	}

	@Override
	public int updateMqConfirmStatus(final String cid, final String messageKey) {
		return 0;
	}

	@Override
	public void createMqConfirmListByTopic(final String topic, final Long messageId, final String messageKey) {
		List<TpcMqConfirm> list = Lists.newArrayList();
		TpcMqConfirm tpcMqConfirm;
		List<String> consumerGroupList = tpcMqConsumerService.listConsumerGroupByTopic(topic);
		if (PublicUtil.isEmpty(consumerGroupList)) {
			throw new TpcBizException(ErrorCodeEnum.TPC100500010, topic);
		}
		for (final String cid : consumerGroupList) {
			tpcMqConfirm = new TpcMqConfirm(UniqueIdGenerator.generateId(), messageId, messageKey, cid);
			list.add(tpcMqConfirm);
		}

		tpcMqConfirmMapper.batchCreateMqConfirm(list);
	}

	@Override
	public List<String> queryWaitingConfirmMessageKeyList(final MessageTaskQueryDto query) {
		return tpcMqMessageMapper.queryWaitingConfirmMessageKeyList(query);
	}

	@Override
	public void handleWaitingConfirmMessage(final List<String> deleteKeyList, final List<String> resendKeyList) {
		tpcMqMessageMapper.batchDeleteMessage(deleteKeyList);
		for (final String messageKey : resendKeyList) {
			this.confirmAndSendMessage(messageKey);
		}
	}

	@Override
	public int updateMqMessageTaskStatus(final TpcMqMessage message) {
		return tpcMqMessageMapper.updateMqMessageTaskStatus(message);
	}

	@Override
	public int updateMqMessageStatus(final TpcMqMessage update) {
		return tpcMqMessageMapper.updateByPrimaryKeySelective(update);
	}

	@Override
	public Wrapper<?> queryRecordListWithPage(final MessageQueryDto messageQueryDto) {
		String producerGroup = messageQueryDto.getProducerGroup();
		String messageKey = messageQueryDto.getMessageKey();
		Preconditions.checkArgument(StringUtils.isNotEmpty(producerGroup) || StringUtils.isNotEmpty(messageKey), "messageKey 和 pid 必须选择一个");
		if (StringUtils.isEmpty(producerGroup)) {
			List<MqMessageVo> result = Lists.newArrayList();
			Wrapper<PageInfo<MqMessageVo>> uacWrapper = uacRpcService.queryMessageListWithPage(messageQueryDto);
			Wrapper<PageInfo<MqMessageVo>> mdcWrapper = mdcRpcService.queryMessageListWithPage(messageQueryDto);
			Wrapper<PageInfo<MqMessageVo>> opcWrapper = opcRpcService.queryMessageListWithPage(messageQueryDto);

			if (uacWrapper != null && uacWrapper.getResult() != null) {
				List<MqMessageVo> list = uacWrapper.getResult().getList();
				result.addAll(list);
			}
			if (mdcWrapper != null && mdcWrapper.getResult() != null) {
				List<MqMessageVo> list = mdcWrapper.getResult().getList();
				result.addAll(list);
			}
			if (opcWrapper != null && opcWrapper.getResult() != null) {
				List<MqMessageVo> list = opcWrapper.getResult().getList();
				result.addAll(list);
			}
			return WrapMapper.success(new PageInfo<>(result));
		}
		if (StringUtils.equals(PIDEnum.PID_UAC.name(), producerGroup)) {
			return uacRpcService.queryMessageListWithPage(messageQueryDto);
		} else if (StringUtils.equals(PIDEnum.PID_MDC.name(), producerGroup)) {
			return mdcRpcService.queryMessageListWithPage(messageQueryDto);
		} else if (StringUtils.equals(PIDEnum.PID_OPC.name(), producerGroup)) {
			return opcRpcService.queryMessageListWithPage(messageQueryDto);
		} else {
			log.error("pid没有维护 pid={}", producerGroup);
		}
		return null;
	}

	@Override
	public List<TpcMessageVo> listReliableMessageVo(final MessageQueryDto messageQueryDto) {
		return tpcMqMessageMapper.listReliableMessageVoWithPage(messageQueryDto);
	}

	@Override
	public List<TpcMessageVo> listReliableMessageVo(final List<Long> messageIdList) {
		return tpcMqMessageMapper.listReliableMessageVo(messageIdList);
	}

	private void resendMessage(TpcMqMessage message) {
		if (message == null) {
			throw new TpcBizException(ErrorCodeEnum.TPC10050002);
		}
		tpcMqMessageMapper.addTaskExeCountById(message.getId());
		//TODO 记录重发日志 1.系统自动重发 2.人工重发
		this.directSendMessage(message.getMessageBody(), message.getMessageTopic(), message.getMessageTag(), message.getMessageKey(), message.getProducerGroup(), message.getDelayLevel());
	}
}
