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
package com.somnus.cloud.common.alipay.model;

/**
 * @ClassName: GoodsDetail
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Somnus
 * @date 2018年10月17日
 */
public class GoodsDetail {
	@SerializedName("goods_id")
	private String goodsId;

	@SerializedName("alipay_goods_id")
	private String alipayGoodsId;

	@SerializedName("goods_name")
	private String goodsName;
	private int quantity;
	private String price;

	@SerializedName("goods_category")
	private String goodsCategory;
	private String body;

	public static GoodsDetail newInstance(String goodsId, String goodsName, long price, int quantity) {
		GoodsDetail info = new GoodsDetail();
		info.setGoodsId(goodsId);
		info.setGoodsName(goodsName);
		info.setPrice(price);
		info.setQuantity(quantity);
		return info;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("GoodsDetail{");
		sb.append("goodsId='").append(this.goodsId).append('\'');
		sb.append(", alipayGoodsId='").append(this.alipayGoodsId).append('\'');
		sb.append(", goodsName='").append(this.goodsName).append('\'');
		sb.append(", quantity=").append(this.quantity);
		sb.append(", price='").append(this.price).append('\'');
		sb.append(", goodsCategory='").append(this.goodsCategory).append('\'');
		sb.append(", body='").append(this.body).append('\'');
		sb.append('}');
		return sb.toString();
	}

	public String getGoodsId() {
		return this.goodsId;
	}

	public GoodsDetail setGoodsId(String goodsId) {
		this.goodsId = goodsId;
		return this;
	}

	public String getAlipayGoodsId() {
		return this.alipayGoodsId;
	}

	public GoodsDetail setAlipayGoodsId(String alipayGoodsId) {
		this.alipayGoodsId = alipayGoodsId;
		return this;
	}

	public String getGoodsName() {
		return this.goodsName;
	}

	public GoodsDetail setGoodsName(String goodsName) {
		this.goodsName = goodsName;
		return this;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public GoodsDetail setQuantity(int quantity) {
		this.quantity = quantity;
		return this;
	}

	public String getPrice() {
		return this.price;
	}

	public GoodsDetail setPrice(long price) {
		this.price = Utils.toAmount(price);
		return this;
	}

	public String getGoodsCategory() {
		return this.goodsCategory;
	}

	public GoodsDetail setGoodsCategory(String goodsCategory) {
		this.goodsCategory = goodsCategory;
		return this;
	}

	public String getBody() {
		return this.body;
	}

	public GoodsDetail setBody(String body) {
		this.body = body;
		return this;
	}
}
