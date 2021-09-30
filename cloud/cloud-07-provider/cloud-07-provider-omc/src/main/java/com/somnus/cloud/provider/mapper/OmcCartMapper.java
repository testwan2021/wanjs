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
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.somnus.cloud.comon.core.mybatis.MyMapper;
import com.somnus.cloud.provider.model.domain.OmcCart;

import java.util.List;

/**
 * @ClassName: OmcCartMapper
 * @Description: The interface Omc cart mapper.
 * @author Somnus
 * @date 2018年10月17日
 */
@Mapper
@Component
public interface OmcCartMapper extends MyMapper<OmcCart> {
	/**
	 * Select un checked cart product count by user id int.
	 *
	 * @param userId the user id
	 *
	 * @return the int
	 */
	int selectUnCheckedCartProductCountByUserId(Long userId);

	/**
	 * Select by product id and user id omc cart.
	 *
	 * @param productId the product id
	 * @param userId    the user id
	 *
	 * @return the omc cart
	 */
	OmcCart selectByProductIdAndUserId(@Param("productId") Long productId, @Param("userId") Long userId);

	/**
	 * Delete by user id product ids int.
	 *
	 * @param userId        the user id
	 * @param productIdList the product id list
	 *
	 * @return the int
	 */
	int deleteByUserIdProductIds(@Param("userId") Long userId, @Param("productIdList") List<String> productIdList);

	/**
	 * Checked or unchecked product int.
	 *
	 * @param userId    the user id
	 * @param productId the product id
	 * @param checked   the checked
	 *
	 * @return the int
	 */
	int checkedOrUncheckedProduct(@Param("userId") Long userId, @Param("productId") Long productId, @Param("checked") int checked);

	/**
	 * Select cart product count int.
	 *
	 * @param userId the user id
	 *
	 * @return the int
	 */
	int selectCartProductCount(Long userId);

	/**
	 * Select checked cart by user id list.
	 *
	 * @param userId the user id
	 *
	 * @return the list
	 */
	List<OmcCart> selectCheckedCartByUserId(Long userId);

	/**
	 * Batch delete cart int.
	 *
	 * @param idList the id list
	 *
	 * @return the int
	 */
	int batchDeleteCart(@Param("idList") List<Long> idList);
}