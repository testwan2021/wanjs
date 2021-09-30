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
package com.somnus.cloud.provider.web.frontend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.somnus.cloud.common.base.dto.LoginAuthDto;
import com.somnus.cloud.common.base.dto.UpdateStatusDto;
import com.somnus.cloud.common.util.wrapper.WrapMapper;
import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.comon.core.annotation.LogAnnotation;
import com.somnus.cloud.comon.core.support.BaseController;
import com.somnus.cloud.provider.model.domain.TpcMqProducer;
import com.somnus.cloud.provider.model.vo.TpcMqProducerVo;
import com.somnus.cloud.provider.model.vo.TpcMqPublishVo;
import com.somnus.cloud.provider.service.TpcMqProducerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName: TpcMqProducerController
 * @Description: 生产者管理.
 * @author Somnus
 * @date 2018年10月17日
 */
@RestController
@RequestMapping(value = "/producer", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - TpcMqProducerController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TpcMqProducerController extends BaseController {

	@Autowired
	private TpcMqProducerService tpcMqProducerService;

	/**
	 * 查询生产者列表.
	 *
	 * @param tpcMqProducer the tpc mq producer
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/queryProducerVoListWithPage")
	@ApiOperation(httpMethod = "POST", value = "查询生产者列表")
	public Wrapper<List<TpcMqProducerVo>> queryProducerList(@ApiParam(name = "producer", value = "Mq生产者") @RequestBody TpcMqProducer tpcMqProducer) {

		logger.info("查询生产者列表tpcMqTopicQuery={}", tpcMqProducer);
		List<TpcMqProducerVo> list = tpcMqProducerService.listProducerVoWithPage(tpcMqProducer);
		return WrapMapper.success(list);
	}

	/**
	 * 查询发布者列表.
	 *
	 * @param tpcMqProducer the tpc mq producer
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/queryPublishListWithPage")
	@ApiOperation(httpMethod = "POST", value = "查询发布者列表")
	public Wrapper<PageInfo<TpcMqPublishVo>> queryPublishListWithPage(@ApiParam(name = "producer", value = "Mq生产者") @RequestBody TpcMqProducer tpcMqProducer) {
		logger.info("查询Mq发布列表tpcMqTopicQuery={}", tpcMqProducer);
		PageHelper.startPage(tpcMqProducer.getPageNum(), tpcMqProducer.getPageSize());
		tpcMqProducer.setOrderBy("update_time desc");
		List<TpcMqPublishVo> list = tpcMqProducerService.listPublishVoWithPage(tpcMqProducer);
		return WrapMapper.success(new PageInfo<>(list));
	}

	/**
	 * 修改生产者状态.
	 *
	 * @param updateStatusDto the update status dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/modifyStatusById")
	@ApiOperation(httpMethod = "POST", value = "修改生产者状态")
	@LogAnnotation
	public Wrapper<?> modifyProducerStatusById(@ApiParam(value = "修改producer状态") @RequestBody UpdateStatusDto updateStatusDto) {
		logger.info("修改producer状态 updateStatusDto={}", updateStatusDto);
		Long roleId = updateStatusDto.getId();

		LoginAuthDto loginAuthDto = getLoginAuthDto();

		TpcMqProducer producer = new TpcMqProducer();
		producer.setId(roleId);
		producer.setStatus(updateStatusDto.getStatus());
		producer.setUpdateInfo(loginAuthDto);

		int result = tpcMqProducerService.update(producer);
		return super.handleResult(result);
	}

	/**
	 * 根据生产者ID删除生产者.
	 *
	 * @param id the id
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/deleteById/{id}")
	@ApiOperation(httpMethod = "POST", value = "根据生产者ID删除生产者")
	@LogAnnotation
	public Wrapper<?> deleteProducerById(@PathVariable Long id) {
		logger.info("删除producer id={}", id);
		int result = tpcMqProducerService.deleteProducerById(id);
		return super.handleResult(result);
	}
}
