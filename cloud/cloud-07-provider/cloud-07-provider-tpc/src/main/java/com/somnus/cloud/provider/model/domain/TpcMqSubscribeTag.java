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
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

import com.somnus.cloud.comon.core.mybatis.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName: TpcMqSubscribeTag
 * @Description: The class Tpc mq consumer tag.
 * @author Somnus
 * @date 2018年10月17日
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "pc_tpc_mq_subscribe_tag")
@Alias(value = "tpcMqConsumerTag")
public class TpcMqSubscribeTag extends BaseEntity {
	private static final long serialVersionUID = 6227704457895628954L;
	/**
	 * 消费者ID
	 */
	@Id
	@Column(name = "subscribe_id")
	private Long subscribeId;

	/**
	 * TAG_ID
	 */
	@Id
	@Column(name = "tag_id")
	private Long tagId;
}