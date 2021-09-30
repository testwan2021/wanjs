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
package com.somnus.cloud.provider.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.somnus.cloud.comon.core.mybatis.MyMapper;
import com.somnus.cloud.provider.model.domain.MdcDict;
import com.somnus.cloud.provider.model.vo.MdcDictVo;

import java.util.List;

/**
 * @ClassName: MdcDictMapper
 * @Description: The interface Mdc dict mapper.
 * @author Somnus
 * @date 2018年10月17日
 */
@Mapper
@Component
public interface MdcDictMapper extends MyMapper<MdcDict> {
	/**
	 * List dict vo list.
	 *
	 * @return the list
	 */
	List<MdcDictVo> listDictVo();
}