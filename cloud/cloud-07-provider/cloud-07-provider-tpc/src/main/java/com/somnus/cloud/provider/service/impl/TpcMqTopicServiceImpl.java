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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.somnus.cloud.comon.core.support.BaseService;
import com.somnus.cloud.provider.mapper.TpcMqTopicMapper;
import com.somnus.cloud.provider.model.domain.TpcMqTopic;
import com.somnus.cloud.provider.model.vo.TpcMqTopicVo;
import com.somnus.cloud.provider.service.TpcMqTopicService;

/**
 * @ClassName: TpcMqTopicServiceImpl
 * @Description: The class Tpc mq topic service.
 * @author Somnus
 * @date 2018年10月17日
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TpcMqTopicServiceImpl extends BaseService<TpcMqTopic> implements TpcMqTopicService {
	@Autowired
	private TpcMqTopicMapper mdcMqTopicMapper;

	@Override
	public List<TpcMqTopicVo> listWithPage(TpcMqTopic mdcMqTopic) {
		return mdcMqTopicMapper.listTpcMqTopicVoWithPage(mdcMqTopic);
	}
}
