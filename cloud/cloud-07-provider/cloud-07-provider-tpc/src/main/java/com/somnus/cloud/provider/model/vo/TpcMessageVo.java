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

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Transient;

import com.somnus.cloud.common.base.dto.BaseVo;

import java.util.List;

/**
 * @ClassName: TpcMessageVo
 * @Description: The class Tpc mq message.
 * @author Somnus
 * @date 2018年10月17日
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TpcMessageVo extends BaseVo {
	private static final long serialVersionUID = 7353473261430862023L;

	private Integer version;

	/**
	 * 消息key
	 */
	private String messageKey;

	/**
	 * topic
	 */
	private String messageTopic;

	/**
	 * tag
	 */
	private String messageTag;

	/**
	 * 消息类型: 10 - 生产者 ; 20 - 消费者
	 */
	private Integer messageType;

	/**
	 * 生产者PID
	 */
	private String producerGroup;

	/**
	 * 延时级别 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
	 */
	private Integer delayLevel;

	/**
	 * 顺序类型 0有序 1无序
	 */
	private Integer orderType;

	/**
	 * 消息状态
	 */
	private Integer messageStatus;

	/**
	 * 消息内容
	 */
	private String messageBody;

	/**
	 * 状态
	 */
	private Integer taskStatus;

	/**
	 * 执行次数
	 */
	private Integer resendTimes;

	/**
	 * 是否死亡 0 - 活着; 1-死亡
	 */
	private Integer dead;

	/**
	 * 执行时间
	 */
	private Integer nextExeTime;

	/**
	 * 是否删除 -0 未删除 -1 已删除
	 */
	private Integer yn;

	@Transient
	private List<TpcMqConfirmVo> mqConfirmVoList;
}