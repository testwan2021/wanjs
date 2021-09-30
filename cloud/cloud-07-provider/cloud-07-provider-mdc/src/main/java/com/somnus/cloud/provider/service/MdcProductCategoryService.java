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
import com.somnus.cloud.provider.api.model.dto.ProductCategoryDto;
import com.somnus.cloud.provider.model.domain.MdcProductCategory;
import com.somnus.cloud.provider.model.vo.MdcCategoryVo;

/**
 * @ClassName: MdcProductCategoryService
 * @Description: The interface Mdc product category service.
 * @author Somnus
 * @date 2018年10月17日
 */
public interface MdcProductCategoryService extends IService<MdcProductCategory> {

	/**
	 * Gets category dto list.
	 *
	 * @param categoryId the category id
	 *
	 * @return the category dto list
	 */
	List<ProductCategoryDto> getCategoryDtoList(Long categoryId);

	/**
	 * Gets product category list by pid.
	 *
	 * @param pid the pid
	 *
	 * @return the product category list by pid
	 */
	List<MdcProductCategory> getProductCategoryListByPid(Long pid);

	/**
	 * 递归查询本节点的id及孩子节点的id.
	 *
	 * @param categoryId the category id
	 *
	 * @return the list
	 */
	List<Long> selectCategoryAndChildrenById(Long categoryId);

	/**
	 * Gets by category id.
	 *
	 * @param categoryId the category id
	 *
	 * @return the by category id
	 */
	MdcProductCategory getByCategoryId(Long categoryId);

	/**
	 * 获取商品分类树.
	 *
	 * @return the category tree list
	 */
	List<MdcCategoryVo> getCategoryTreeList();

	/**
	 * 根据ID获取商品分类信息.
	 *
	 * @param id the id
	 *
	 * @return the mdc category vo by id
	 */
	MdcCategoryVo getMdcCategoryVoById(Long id);

	/**
	 * 更新商品分类信息.
	 *
	 * @param updateStatusDto the update status dto
	 * @param loginAuthDto    the login auth dto
	 */
	void updateMdcCategoryStatusById(UpdateStatusDto updateStatusDto, LoginAuthDto loginAuthDto);

	/**
	 * 保存商品分类信息.
	 *
	 * @param mdcCategory  the mdc category
	 * @param loginAuthDto the login auth dto
	 */
	void saveMdcCategory(MdcProductCategory mdcCategory, LoginAuthDto loginAuthDto);

	/**
	 * Check category has child category boolean.
	 *
	 * @param id the id
	 *
	 * @return the boolean
	 */
	boolean checkCategoryHasChildCategory(Long id);
}
