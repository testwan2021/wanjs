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
import com.somnus.cloud.provider.api.model.dto.ProductCategoryDto;
import com.somnus.cloud.provider.model.domain.MdcProductCategory;
import com.somnus.cloud.provider.model.vo.MdcCategoryVo;

import java.util.List;

/**
 * @ClassName: MdcProductCategoryMapper
 * @Description: The interface Mdc product category mapper.
 * @author Somnus
 * @date 2018年10月17日
 */
@Mapper
@Component
public interface MdcProductCategoryMapper extends MyMapper<MdcProductCategory> {
	/**
	 * Select category dto list list.
	 *
	 * @param categoryPid the category pid
	 *
	 * @return the list
	 */
	List<ProductCategoryDto> selectCategoryDtoList(Long categoryPid);

	/**
	 * List category vo list.
	 *
	 * @return the list
	 */
	List<MdcCategoryVo> listCategoryVo();
}