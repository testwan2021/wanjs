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
package com.somnus.cloud.provider.service;

import java.util.List;

import com.somnus.cloud.comon.core.support.IService;
import com.somnus.cloud.provider.model.domain.TpcMqTopic;
import com.somnus.cloud.provider.model.vo.TpcMqTopicVo;

/**
 * @ClassName: TpcMqTopicService
 * @Description: The interface Tpc mq topic service.
 * @author Somnus
 * @date 2018年10月17日
 */
public interface TpcMqTopicService extends IService<TpcMqTopic> {
	/**
	 * 查询MQ topic列表.
	 *
	 * @param mdcMqTopic the mdc mq topic
	 *
	 * @return the list
	 */
	List<TpcMqTopicVo> listWithPage(TpcMqTopic mdcMqTopic);

}
