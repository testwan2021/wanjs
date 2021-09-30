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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
import com.somnus.cloud.common.util.PublicUtil;
import com.somnus.cloud.common.util.wrapper.WrapMapper;
import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.comon.core.annotation.LogAnnotation;
import com.somnus.cloud.comon.core.support.BaseController;
import com.somnus.cloud.provider.model.domain.TpcMqConsumer;
import com.somnus.cloud.provider.model.vo.TpcMqConsumerVo;
import com.somnus.cloud.provider.model.vo.TpcMqSubscribeVo;
import com.somnus.cloud.provider.service.TpcMqConsumerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName: TpcMqConsumerController
 * @Description: 消费者管理.
 * @author Somnus
 * @date 2018年10月17日
 */
@RestController
@RequestMapping(value = "/consumer", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - TpcMqConsumerController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TpcMqConsumerController extends BaseController {

	@Autowired
	private TpcMqConsumerService tpcMqConsumerService;

	/**
	 * 查询Mq消费者列表.
	 *
	 * @param tpcMqConsumer the tpc mq consumer
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/queryConsumerVoListWithPage")
	@ApiOperation(httpMethod = "POST", value = "查询Mq消费者列表")
	public Wrapper<List<TpcMqConsumerVo>> queryConsumerVoList(@ApiParam(name = "consumer", value = "Mq消费者") @RequestBody TpcMqConsumer tpcMqConsumer) {

		logger.info("查询消费者列表tpcMqProducerQuery={}", tpcMqConsumer);
		List<TpcMqConsumerVo> list = tpcMqConsumerService.listConsumerVoWithPage(tpcMqConsumer);
		return WrapMapper.success(list);
	}

	/**
	 * 查询订阅者列表.
	 *
	 * @param tpcMqConsumer the tpc mq consumer
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/querySubscribeListWithPage")
	@ApiOperation(httpMethod = "POST", value = "查询订阅者列表")
	public Wrapper<PageInfo<TpcMqSubscribeVo>> querySubscribeListWithPage(@ApiParam(name = "consumer", value = "Mq消费者") @RequestBody TpcMqConsumer tpcMqConsumer) {
		logger.info("查询Mq订阅列表tpcMqConsumerQuery={}", tpcMqConsumer);
		PageHelper.startPage(tpcMqConsumer.getPageNum(), tpcMqConsumer.getPageSize());
		tpcMqConsumer.setOrderBy("update_time desc");
		List<TpcMqSubscribeVo> list = tpcMqConsumerService.listSubscribeVoWithPage(tpcMqConsumer);
		PageInfo<TpcMqSubscribeVo> pageInfo = new PageInfo<>(list);
		if (PublicUtil.isNotEmpty(list)) {
			Map<Long, TpcMqSubscribeVo> tpcMqSubscribeVoMap = this.trans2Map(list);
			List<Long> subscribeIdList = new ArrayList<>(tpcMqSubscribeVoMap.keySet());
			List<TpcMqSubscribeVo> tagVoList = tpcMqConsumerService.listSubscribeVo(subscribeIdList);
			for (TpcMqSubscribeVo vo : tagVoList) {
				Long subscribeId = vo.getId();
				if (!tpcMqSubscribeVoMap.containsKey(subscribeId)) {
					continue;
				}
				TpcMqSubscribeVo tpcMqSubscribeVo = tpcMqSubscribeVoMap.get(subscribeId);
				tpcMqSubscribeVo.setTagVoList(vo.getTagVoList());
			}
			pageInfo.setList(new ArrayList<>(tpcMqSubscribeVoMap.values()));
		}
		return WrapMapper.success(pageInfo);
	}

	private Map<Long, TpcMqSubscribeVo> trans2Map(List<TpcMqSubscribeVo> resultDTOS) {
		Map<Long, TpcMqSubscribeVo> resultMap = new TreeMap<>((o1, o2) -> {
			o1 = o1 == null ? 0 : o1;
			o2 = o2 == null ? 0 : o2;
			return o2.compareTo(o1);
		});
		for (TpcMqSubscribeVo resultDTO : resultDTOS) {
			resultMap.put(resultDTO.getId(), resultDTO);
		}
		return resultMap;
	}

	/**
	 * 更改消费者状态.
	 *
	 * @param updateStatusDto the update status dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/modifyStatusById")
	@ApiOperation(httpMethod = "POST", value = "更改消费者状态")
	@LogAnnotation
	public Wrapper<?> modifyConsumerStatusById(@ApiParam(value = "更改消费者状态") @RequestBody UpdateStatusDto updateStatusDto) {
		logger.info("修改consumer状态 updateStatusDto={}", updateStatusDto);
		Long consumerId = updateStatusDto.getId();

		LoginAuthDto loginAuthDto = getLoginAuthDto();

		TpcMqConsumer consumer = new TpcMqConsumer();
		consumer.setId(consumerId);
		consumer.setStatus(updateStatusDto.getStatus());
		consumer.setUpdateInfo(loginAuthDto);

		int result = tpcMqConsumerService.update(consumer);
		return super.handleResult(result);
	}

	/**
	 * 根据消费者ID删除消费者.
	 *
	 * @param id the id
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/deleteById/{id}")
	@ApiOperation(httpMethod = "POST", value = "根据消费者ID删除消费者")
	@LogAnnotation
	public Wrapper<?> deleteConsumerById(@PathVariable Long id) {
		logger.info("删除consumer id={}", id);
		int result = tpcMqConsumerService.deleteConsumerById(id);
		return super.handleResult(result);
	}
}
