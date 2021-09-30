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
import com.somnus.cloud.provider.mapper.TpcMqProducerMapper;
import com.somnus.cloud.provider.model.domain.TpcMqProducer;
import com.somnus.cloud.provider.model.vo.TpcMqProducerVo;
import com.somnus.cloud.provider.model.vo.TpcMqPublishVo;
import com.somnus.cloud.provider.service.TpcMqProducerService;

/**
 * @ClassName: TpcMqProducerServiceImpl
 * @Description: The class Tpc mq producer service.
 * @author Somnus
 * @date 2018年10月17日
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TpcMqProducerServiceImpl extends BaseService<TpcMqProducer> implements TpcMqProducerService {

	@Autowired
	private TpcMqProducerMapper mdcMqProducerMapper;

	@Override
	public List<TpcMqProducerVo> listProducerVoWithPage(TpcMqProducer mdcMqProducer) {
		return mdcMqProducerMapper.listTpcMqProducerVoWithPage(mdcMqProducer);
	}

	@Override
	public List<TpcMqPublishVo> listPublishVoWithPage(TpcMqProducer mdcMqProducer) {
		return mdcMqProducerMapper.listTpcMqPublishVoWithPage(mdcMqProducer);
	}

	@Override
	public int deleteProducerById(Long producerId) {
		// 删除consumer
		mdcMqProducerMapper.deleteByPrimaryKey(producerId);
		// 删除发布关系
		return mdcMqProducerMapper.deletePublishByProducerId(producerId);
	}

	@Override
	public void updateOnLineStatusByPid(final String producerGroup) {
		logger.info("更新生产者pid={}状态为在线", producerGroup);
		this.updateStatus(producerGroup, 10);

	}

	@Override
	public void updateOffLineStatusByPid(final String producerGroup) {
		logger.info("更新生产者pid={}状态为离线", producerGroup);
		this.updateStatus(producerGroup, 20);
	}

	private void updateStatus(final String producerGroup, final int status) {
		TpcMqProducer tpcMqProducer = mdcMqProducerMapper.getByPid(producerGroup);
		if (tpcMqProducer.getStatus() != null && tpcMqProducer.getStatus() != status) {
			TpcMqProducer update = new TpcMqProducer();
			update.setStatus(status);
			update.setId(tpcMqProducer.getId());
			mdcMqProducerMapper.updateByPrimaryKeySelective(update);
		}
	}
}
