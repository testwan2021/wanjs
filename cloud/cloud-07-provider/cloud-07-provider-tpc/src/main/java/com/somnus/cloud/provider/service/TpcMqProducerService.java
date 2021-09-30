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
import com.somnus.cloud.provider.model.domain.TpcMqProducer;
import com.somnus.cloud.provider.model.vo.TpcMqProducerVo;
import com.somnus.cloud.provider.model.vo.TpcMqPublishVo;

/**
 * @ClassName: TpcMqProducerService
 * @Description: The interface Tpc mq producer service.
 * @author Somnus
 * @date 2018年10月17日
 */
public interface TpcMqProducerService extends IService<TpcMqProducer> {
	/**
	 * List producer vo with page list.
	 *
	 * @param mdcMqProducer the mdc mq producer
	 *
	 * @return the list
	 */
	List<TpcMqProducerVo> listProducerVoWithPage(TpcMqProducer mdcMqProducer);

	/**
	 * 查询发布者列表.
	 *
	 * @param mdcMqProducer the mdc mq producer
	 *
	 * @return the list
	 */
	List<TpcMqPublishVo> listPublishVoWithPage(TpcMqProducer mdcMqProducer);

	/**
	 * 根据生产者ID删除生产者.
	 *
	 * @param id the id
	 *
	 * @return the int
	 */
	int deleteProducerById(Long id);

	/**
	 * 根据pid更新生产者状态为在线.
	 *
	 * @param producerGroup the producer group
	 */
	void updateOnLineStatusByPid(String producerGroup);

	/**
	 * 根据pid更新生产者状态为离线.
	 *
	 * @param producerGroup the producer group
	 */
	void updateOffLineStatusByPid(String producerGroup);
}
