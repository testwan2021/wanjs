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
package com.somnus.cloud.provider.manager;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.somnus.cloud.common.base.enums.ErrorCodeEnum;
import com.somnus.cloud.provider.api.annotation.MqProducerStore;
import com.somnus.cloud.provider.api.exceptions.MdcBizException;
import com.somnus.cloud.provider.api.model.domain.MqMessageData;
import com.somnus.cloud.provider.mapper.MdcProductMapper;
import com.somnus.cloud.provider.model.domain.MdcProduct;

/**
 * @ClassName: MdcProductManager
 * @Description: The class User manager.
 * @author Somnus
 * @date 2018年10月17日
 */
@Slf4j
@Component
@Transactional(rollbackFor = Exception.class)
public class MdcProductManager {
	@Autowired
	private MdcProductMapper mdcProductMapper;

	/**
	 * Save product.
	 *
	 * @param mqMessageData the mq message data
	 * @param product       the product
	 * @param addFlag       the add flag
	 */
	@MqProducerStore
	public void saveProduct(final MqMessageData mqMessageData, final MdcProduct product, boolean addFlag) {
		log.info("保存商品信息. mqMessageData={}, product={}", mqMessageData, product);
		if (addFlag) {
			mdcProductMapper.insertSelective(product);
		} else {
			int result = mdcProductMapper.updateByPrimaryKeySelective(product);
			if (result < 1) {
				throw new MdcBizException(ErrorCodeEnum.MDC10021022, product.getId());
			}
		}
	}

	/**
	 * Delete product.
	 *
	 * @param mqMessageData the mq message data
	 * @param productId     the product id
	 */
	@MqProducerStore
	public void deleteProduct(final MqMessageData mqMessageData, final Long productId) {
		log.info("删除商品信息. mqMessageData={}, productId={}", mqMessageData, productId);
		int result = mdcProductMapper.deleteByPrimaryKey(productId);
		if (result < 1) {
			throw new MdcBizException(ErrorCodeEnum.MDC10021023, productId);
		}
	}
}
