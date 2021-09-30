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

import java.io.Serializable;
import java.util.List;

import com.somnus.cloud.common.base.dto.BaseTree;

/**
 * @ClassName: ITree
 * @Description: The interface Tree interface.
 * @author Somnus
 * @date 2018年9月27日
 * @param <T>  the type parameter
 * @param <ID> the type parameter
 */
public interface ITree<T extends BaseTree<T, ID>, ID extends Serializable> {
	/**
	 * 获得指定节点下所有归档
	 *
	 * @param list     the list
	 * @param parentId the parent id
	 *
	 * @return the child tree objects
	 */
	List<T> getChildTreeObjects(List<T> list, ID parentId);

	/**
	 * 递归列表
	 *
	 * @param list the list
	 * @param t    the t
	 */
	void recursionFn(List<T> list, T t);

	/**
	 * 获得指定节点下的所有子节点
	 *
	 * @param list the list
	 * @param t    the t
	 *
	 * @return the child list
	 */
	List<T> getChildList(List<T> list, T t);

	/**
	 * 判断是否还有下一个子节点
	 *
	 * @param list the list
	 * @param t    the t
	 *
	 * @return the boolean
	 */
	boolean hasChild(List<T> list, T t);
}