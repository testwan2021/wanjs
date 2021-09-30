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
package com.somnus.cloud.comon.core.support;

import com.google.common.collect.Lists;
import com.somnus.cloud.common.base.dto.BaseTree;
import com.somnus.cloud.common.util.PublicUtil;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import org.springframework.util.ObjectUtils;

/**
 * @ClassName: AbstractTreeService
 * @Description: The class Tree service.
 * @author Somnus
 * @date 2018年9月27日
 * @param <T>  the type parameter
 * @param <ID> the type parameter
 */
public abstract class AbstractTreeService<T extends BaseTree<T, ID>, ID extends Serializable> implements ITree<T, ID> {

	/**
	 * Gets child tree objects.
	 *
	 * @param list     the list
	 * @param parentId the parent id
	 *
	 * @return the child tree objects
	 */
	@Override
	public List<T> getChildTreeObjects(List<T> list, ID parentId) {
		List<T> returnList = Lists.newArrayList();
		for (T res : list) {
			if (res.getPid() == null) {
				continue;
			}
			if (Objects.equals(res.getPid(), parentId)) {
				recursionFn(list, res);
				returnList.add(res);
			}
		}
		return returnList;
	}

	/**
	 * 递归列表
	 *
	 * @param list the list
	 * @param t    the t
	 */
	@Override
	public void recursionFn(List<T> list, T t) {
		List<T> children = getChildList(list, t);
		if (PublicUtil.isEmpty(children)) {
			t.setChildren(children);
			t.setHasChild(true);
		}
		for (T nextChild : children) {
			// 下一个对象，与所有的资源集进行判断
			if (hasChild(list, nextChild)) {
				// 有下一个子节点,递归
				for (T node : children) {
					// 所有的对象--跟当前这个childList 的对象子节点
					recursionFn(list, node);
				}
				nextChild.setHasChild(true);
			}
		}
	}

	/**
	 * 获得指定节点下的所有子节点
	 *
	 * @param list the list
	 * @param t    the t
	 *
	 * @return the child list
	 */
	@Override
	public List<T> getChildList(List<T> list, T t) {
		List<T> childList = Lists.newArrayList();
		for (T child : list) {
			if (ObjectUtils.isEmpty(child.getPid())) {
				continue;
			}
			// 判断集合的父ID是否等于上一级的id
			if (Objects.equals(child.getPid(), t.getId())) {
				childList.add(child);
			}
		}
		return childList;
	}

	/**
	 * 判断是否还有下一个子节点
	 *
	 * @param list the list
	 * @param t    the t
	 *
	 * @return the boolean
	 */
	@Override
	public boolean hasChild(List<T> list, T t) {
		return !getChildList(list, t).isEmpty();
	}
}