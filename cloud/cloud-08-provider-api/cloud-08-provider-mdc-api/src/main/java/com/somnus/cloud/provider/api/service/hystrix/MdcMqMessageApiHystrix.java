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
package com.somnus.cloud.provider.api.service.hystrix;

import com.github.pagehelper.PageInfo;
import com.somnus.cloud.common.base.dto.MessageQueryDto;
import com.somnus.cloud.common.base.dto.MqMessageVo;
import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.provider.api.service.MdcMqMessageFeignApi;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName: MdcMqMessageApiHystrix
 * @Description: The class Mdc mq message api hystrix.
 * @author Somnus
 * @date 2018年10月16日
 */
@Component
public class MdcMqMessageApiHystrix implements MdcMqMessageFeignApi {
	@Override
	public Wrapper<List<String>> queryMessageKeyList(final List<String> messageKeyList) {
		return null;
	}

	@Override
	public Wrapper<PageInfo<MqMessageVo>> queryMessageListWithPage(final MessageQueryDto messageQueryDto) {
		return null;
	}
}
