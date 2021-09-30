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
package com.somnus.cloud.provider.service.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.somnus.cloud.common.base.constant.AliyunMqTopicConstants;
import com.somnus.cloud.common.base.constant.GlobalConstant;
import com.somnus.cloud.common.base.dto.LoginAuthDto;
import com.somnus.cloud.common.base.enums.ErrorCodeEnum;
import com.somnus.cloud.common.util.PubUtils;
import com.somnus.cloud.common.util.PublicUtil;
import com.somnus.cloud.common.util.RedisKeyUtil;
import com.somnus.cloud.comon.core.support.BaseService;
import com.somnus.cloud.provider.api.exceptions.MdcBizException;
import com.somnus.cloud.provider.api.model.domain.MqMessageData;
import com.somnus.cloud.provider.api.model.dto.ProductDto;
import com.somnus.cloud.provider.api.model.dto.UpdateAttachmentDto;
import com.somnus.cloud.provider.api.model.dto.oss.ElementImgUrlDto;
import com.somnus.cloud.provider.api.model.dto.oss.OptBatchGetUrlRequest;
import com.somnus.cloud.provider.api.model.dto.oss.OptGetUrlRequest;
import com.somnus.cloud.provider.api.model.vo.ProductDetailVo;
import com.somnus.cloud.provider.manager.MdcProductManager;
import com.somnus.cloud.provider.mapper.MdcProductMapper;
import com.somnus.cloud.provider.model.constant.MallConstant;
import com.somnus.cloud.provider.model.domain.MdcProduct;
import com.somnus.cloud.provider.model.domain.MdcProductCategory;
import com.somnus.cloud.provider.model.dto.MdcEditProductDto;
import com.somnus.cloud.provider.model.vo.ProductVo;
import com.somnus.cloud.provider.service.MdcProductCategoryService;
import com.somnus.cloud.provider.service.MdcProductService;
import com.somnus.cloud.provider.service.OpcRpcService;

/**
 * @ClassName: MdcProductServiceImpl
 * @Description: The class Mdc product service.
 * @author Somnus
 * @date 2018年10月17日
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MdcProductServiceImpl extends BaseService<MdcProduct> implements MdcProductService {

	@Autowired
	private MdcProductMapper mdcProductMapper;
	
	@Autowired
	private MdcProductCategoryService mdcProductCategoryService;
	
	@Autowired
	private MdcProductManager mdcProductManager;
	
	@Autowired
	private OpcRpcService opcRpcService;

	@Override
	public List<MdcProduct> selectByNameAndCategoryIds(String productName, List<Long> categoryIdList, String orderBy) {
		return mdcProductMapper.selectByNameAndCategoryIds(productName, categoryIdList, orderBy);
	}

	@Override
	public ProductDetailVo getProductDetail(Long productId) {
		logger.info("获取商品明细信息, productId={}", productId);
		Preconditions.checkArgument(productId != null, ErrorCodeEnum.MDC10021021.getMsg());

		MdcProduct product = mdcProductMapper.selectByPrimaryKey(productId);
		if (product == null) {
			throw new MdcBizException(ErrorCodeEnum.MDC10021017, productId);
		}
		if (product.getStatus() != MallConstant.ProductStatusEnum.ON_SALE.getCode()) {
			throw new MdcBizException(ErrorCodeEnum.MDC10021017, productId);
		}

		return assembleProductDetailVo(product);
	}

	@Override
	public int updateProductStockById(ProductDto productDto) {
		Preconditions.checkArgument(!PubUtils.isNull(productDto, productDto.getId()), ErrorCodeEnum.MDC10021021.getMsg());
		return mdcProductMapper.updateProductStockById(productDto);
	}

	@Override
	public List<ProductVo> queryProductListWithPage(final MdcProduct mdcProduct) {
		return mdcProductMapper.queryProductListWithPage(mdcProduct);
	}

	@Override
	public void saveProduct(final MdcEditProductDto mdcEditProductDto, final LoginAuthDto loginAuthDto) {
		String productCode = mdcEditProductDto.getProductCode();
		MdcProduct product = new MdcProduct();
		BeanUtils.copyProperties(mdcEditProductDto, product);
		List<Long> categoryIdList = mdcEditProductDto.getCategoryIdList();
		Long categoryId = categoryIdList.get(categoryIdList.size() - 1);
		product.setCategoryId(categoryId);
		List<Long> attachmentIdList = mdcEditProductDto.getAttachmentIdList();
		product.setUpdateInfo(loginAuthDto);
		if (PublicUtil.isEmpty(attachmentIdList)) {
			product.setMainImage(String.valueOf(attachmentIdList.get(0)));
			product.setSubImages(Joiner.on(GlobalConstant.Symbol.COMMA).join(attachmentIdList));
		}
		MqMessageData mqMessageData;
		if (product.isNew()) {
			productCode = String.valueOf(generateId());
		} else {
			Preconditions.checkArgument(StringUtils.isNotEmpty(productCode), ErrorCodeEnum.MDC10021024.getMsg());
		}
		product.setProductCode(productCode);
		UpdateAttachmentDto updateAttachmentDto = new UpdateAttachmentDto(productCode, attachmentIdList, loginAuthDto);

		String body = JSON.toJSONString(updateAttachmentDto);
		String topic = AliyunMqTopicConstants.MqTagEnum.UPDATE_ATTACHMENT.getTopic();
		String tag = AliyunMqTopicConstants.MqTagEnum.UPDATE_ATTACHMENT.getTag();
		String key = RedisKeyUtil.createMqKey(topic, tag, product.getProductCode(), body);

		if (product.isNew() && PublicUtil.isNotEmpty(attachmentIdList)) {
			product.setId(generateId());
			mqMessageData = new MqMessageData(body, topic, tag, key);
			mdcProductManager.saveProduct(mqMessageData, product, true);
		} else if (product.isNew() && PublicUtil.isEmpty(attachmentIdList)) {
			product.setId(generateId());
			mdcProductMapper.insertSelective(product);
		} else {
			mqMessageData = new MqMessageData(body, topic, tag, key);
			mdcProductManager.saveProduct(mqMessageData, product, false);
		}
	}

	@Override
	public void deleteProductById(final Long id) {
		MdcProduct product = mdcProductMapper.selectByPrimaryKey(id);
		if (product != null) {
			String body = product.getProductCode();
			String topic = AliyunMqTopicConstants.MqTagEnum.DELETE_ATTACHMENT.getTopic();
			String tag = AliyunMqTopicConstants.MqTagEnum.DELETE_ATTACHMENT.getTag();
			String key = RedisKeyUtil.createMqKey(topic, tag, product.getProductCode(), body);
			MqMessageData mqMessageData = new MqMessageData(body, topic, tag, key);
			mdcProductManager.deleteProduct(mqMessageData, id);
		}
	}

	@Override
	public ProductVo getProductVo(final Long id) {
		MdcProduct mdcProduct = mdcProductMapper.selectByPrimaryKey(id);
		ProductVo productVo = new ModelMapper().map(mdcProduct, ProductVo.class);
		List<Long> categoryIdList = Lists.newArrayList();
		buildCategoryIdList(categoryIdList, mdcProduct.getCategoryId());
		// 获取分类节点集合
		Collections.reverse(categoryIdList);
		productVo.setCategoryIdList(categoryIdList);
		// 获取图片集合
		final OptBatchGetUrlRequest request = new OptBatchGetUrlRequest(mdcProduct.getProductCode());
		request.setEncrypt(true);
		List<ElementImgUrlDto> imgUrlList = opcRpcService.listFileUrl(request);
		productVo.setImgUrlList(imgUrlList);
		return productVo;
	}

	@Override
	public String getMainImage(final Long productId) {
		MdcProduct product = mdcProductMapper.selectByPrimaryKey(productId);
		String url = null;

		if (product != null) {
			final OptGetUrlRequest request = new OptGetUrlRequest();
			request.setEncrypt(true);
			request.setAttachmentId(Long.valueOf(product.getMainImage()));
			request.setExpires(3600 * 12 * 7L);
			url = opcRpcService.getFileUrl(request);
		}
		return url;
	}

	private List<Long> buildCategoryIdList(List<Long> categoryIdList, Long categoryId) {
		MdcProductCategory category = mdcProductCategoryService.getByCategoryId(categoryId);
		if (category != null) {
			categoryIdList.add(categoryId);
			buildCategoryIdList(categoryIdList, category.getPid());
		}
		return categoryIdList;
	}

	private ProductDetailVo assembleProductDetailVo(MdcProduct product) {
		ProductDetailVo productDetailVo = new ProductDetailVo();
		String mainImage = product.getMainImage();
		String subImages = product.getSubImages();
		if (StringUtils.isNotEmpty(mainImage)) {
			// 图片查询
			OptGetUrlRequest request = new OptGetUrlRequest();
			request.setAttachmentId(Long.valueOf(mainImage));
			request.setEncrypt(true);
			String url = opcRpcService.getFileUrl(request);
			productDetailVo.setMainImage(url);
		}
		if (StringUtils.isNotEmpty(subImages)) {
			List<String> urlList = Lists.newArrayList();
			List<String> subImageList = Splitter.on(GlobalConstant.Symbol.COMMA).trimResults().splitToList(subImages);
			for (final String subImage : subImageList) {
				OptGetUrlRequest request = new OptGetUrlRequest();
				request.setAttachmentId(Long.valueOf(subImage));
				request.setEncrypt(true);
				String url = opcRpcService.getFileUrl(request);
				urlList.add(url);
			}
			productDetailVo.setSubImages(Joiner.on(GlobalConstant.Symbol.COMMA).join(urlList));
		}

		productDetailVo.setId(product.getId());
		productDetailVo.setSubtitle(product.getSubtitle());
		productDetailVo.setPrice(product.getPrice());

		productDetailVo.setDetail(product.getDetail());
		productDetailVo.setName(product.getName());
		productDetailVo.setStatus(product.getStatus());
		productDetailVo.setStock(product.getStock());

		productDetailVo.setImageHost("");

		MdcProductCategory category = mdcProductCategoryService.getByCategoryId(product.getId());
		if (category == null) {
			//默认根节点
			productDetailVo.setPid(0L);
		} else {
			productDetailVo.setPid(category.getPid());
		}

		return productDetailVo;
	}
}
