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
import com.somnus.cloud.common.base.dto.MessageQueryDto;
import com.somnus.cloud.common.util.PublicUtil;
import com.somnus.cloud.common.util.wrapper.WrapMapper;
import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.comon.core.support.BaseController;
import com.somnus.cloud.provider.model.vo.TpcMessageVo;
import com.somnus.cloud.provider.service.TpcMqMessageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName: TpcMessageMainController
 * @Description: 异常管理.
 * @author Somnus
 * @date 2018年10月17日
 */
@RestController
@RequestMapping(value = "/message", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "Web - TpcMessageMainController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TpcMessageMainController extends BaseController {
	
	@Autowired
	private TpcMqMessageService tpcMqMessageService;

	/**
	 * 异常日志列表.
	 *
	 * @param messageQueryDto the message query dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/queryRecordListWithPage")
	@ApiOperation(httpMethod = "POST", value = "分页查询各中心落地消息记录")
	public Wrapper<?> queryRecordListWithPage(@ApiParam(name = "tpcMessageQueryDto") @RequestBody MessageQueryDto messageQueryDto) {
		logger.info("分页查询各中心落地消息记录. messageQueryDto={}", messageQueryDto);
		return tpcMqMessageService.queryRecordListWithPage(messageQueryDto);
	}

	/**
	 * Resend message by id wrapper.
	 *
	 * @param messageId the message id
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/resendMessageById/{messageId}")
	@ApiOperation(httpMethod = "POST", value = "重发消息")
	public Wrapper<?> resendMessageById(@PathVariable Long messageId) {
		logger.info("重发消息. messageId={}", messageId);
		tpcMqMessageService.resendMessageByMessageId(messageId);
		return WrapMapper.ok();
	}

	/**
	 * Query reliable list with page wrapper.
	 *
	 * @param messageQueryDto the message query dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/queryReliableListWithPage")
	@ApiOperation(httpMethod = "POST", value = "分页查询可靠消息")
	public Wrapper<?> queryReliableListWithPage(@ApiParam(name = "tpcMessageQueryDto") @RequestBody MessageQueryDto messageQueryDto) {
		logger.info("分页查询可靠消息. tpcMessageQueryDto={}", messageQueryDto);
		PageHelper.startPage(messageQueryDto.getPageNum(), messageQueryDto.getPageSize());
		messageQueryDto.setOrderBy("update_time desc");
		List<TpcMessageVo> list = tpcMqMessageService.listReliableMessageVo(messageQueryDto);
		PageInfo<TpcMessageVo> pageInfo = new PageInfo<>(list);
		if (PublicUtil.isNotEmpty(list)) {
			Map<Long, TpcMessageVo> messageVoMap = this.trans2Map(list);
			List<Long> messageIdList = new ArrayList<>(messageVoMap.keySet());

			List<TpcMessageVo> mqConfirmVoList = tpcMqMessageService.listReliableMessageVo(messageIdList);
			for (TpcMessageVo vo : mqConfirmVoList) {
				Long subscribeId = vo.getId();
				if (!messageVoMap.containsKey(subscribeId)) {
					continue;
				}
				TpcMessageVo tpcMessageVo = messageVoMap.get(subscribeId);
				tpcMessageVo.setMqConfirmVoList(vo.getMqConfirmVoList());
			}
			pageInfo.setList(new ArrayList<>(messageVoMap.values()));
		}
		return WrapMapper.success(pageInfo);
	}

	private Map<Long, TpcMessageVo> trans2Map(List<TpcMessageVo> tpcMessageVoList) {
		Map<Long, TpcMessageVo> resultMap = new TreeMap<>((o1, o2) -> {
			o1 = o1 == null ? 0 : o1;
			o2 = o2 == null ? 0 : o2;
			return o2.compareTo(o1);
		});
		for (TpcMessageVo vo : tpcMessageVoList) {
			resultMap.put(vo.getId(), vo);
		}
		return resultMap;
	}
}
