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

import com.somnus.cloud.common.base.dto.LoginAuthDto;
import com.somnus.cloud.common.base.dto.UpdateStatusDto;
import com.somnus.cloud.comon.core.support.IService;
import com.somnus.cloud.provider.model.domain.MdcDict;
import com.somnus.cloud.provider.model.vo.MdcDictVo;

/**
 * @ClassName: MdcDictService
 * @Description: The interface Mdc dict service.
 * @author Somnus
 * @date 2018年10月17日
 */
public interface MdcDictService extends IService<MdcDict> {

	/**
	 * 获取字典树.
	 *
	 * @return the dict tree list
	 */
	List<MdcDictVo> getDictTreeList();

	/**
	 * 根据ID获取字典信息.
	 *
	 * @param dictId the dict id
	 *
	 * @return the mdc dict vo by id
	 */
	MdcDictVo getMdcDictVoById(Long dictId);

	/**
	 * 根据id修改字典信息.
	 *
	 * @param updateStatusDto the update status dto
	 * @param loginAuthDto    the login auth dto
	 *
	 * @return the int
	 */
	void updateMdcDictStatusById(UpdateStatusDto updateStatusDto, LoginAuthDto loginAuthDto);

	/**
	 * 编辑字典.
	 *
	 * @param mdcDict      the mdc dict
	 * @param loginAuthDto the login auth dto
	 */
	void saveMdcDict(MdcDict mdcDict, LoginAuthDto loginAuthDto);

	/**
	 * Check dict has child dict boolean.
	 *
	 * @param dictId the dict id
	 *
	 * @return the boolean
	 */
	boolean checkDictHasChildDict(Long dictId);
}
