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

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName: IService
 * @Description: 通用接口
 * @author Somnus
 * @date 2018年9月27日
 * @param <T> the type parameter 
 */
public interface IService<T> {
	/**
	 * 根据实体中的属性值进行查询, 查询条件使用等号  @param record the record
	 *
	 * @param record the record
	 *
	 * @return the list
	 */
	List<T> select(T record);

	/**
	 * 根据主键字段进行查询, 方法参数必须包含完整的主键属性, 查询条件使用等号  @param key the key
	 *
	 * @param key the key
	 *
	 * @return the t
	 */
	T selectByKey(Object key);

	/**
	 * 查询全部结果, select(null)方法能达到同样的效果  @return the list
	 *
	 * @return the list
	 */
	List<T> selectAll();

	/**
	 * 根据实体中的属性进行查询, 只能有一个返回值, 有多个结果是抛出异常, 查询条件使用等号  @param record the record
	 *
	 * @param record the record
	 *
	 * @return the t
	 */
	T selectOne(T record);

	/**
	 * 根据实体中的属性查询总数, 查询条件使用等号  @param record the record
	 *
	 * @param record the record
	 *
	 * @return the int
	 */
	int selectCount(T record);

	/**
	 * 保存一个实体, null的属性不会保存, 会使用数据库默认值  @param record the record
	 *
	 * @param record the record
	 *
	 * @return the int
	 */
	int save(T record);

	/**
	 * 批量保存  @param list the list
	 *
	 * @param list the list
	 *
	 * @return the int
	 */
	@Transactional(rollbackFor = Exception.class)
	int batchSave(List<T> list);

	/**
	 * 根据主键更新属性不为null的值  @param entity the entity
	 *
	 * @param entity the entity
	 *
	 * @return the int
	 */
	int update(T entity);

	/**
	 * 根据实体属性作为条件进行删除, 查询条件使用等号  @param record the record
	 *
	 * @param record the record
	 *
	 * @return the int
	 */
	int delete(T record);

	/**
	 * 批量删除  @param list the list
	 *
	 * @param list the list
	 *
	 * @return the int
	 */
	@Transactional(rollbackFor = Exception.class)
	int batchDelete(List<T> list);

	/**
	 * 根据主键字段进行删除, 方法参数必须包含完整的主键属性  @param key the key
	 *
	 * @param key the key
	 *
	 * @return the int
	 */
	int deleteByKey(Object key);

	/**
	 * 这个查询支持通过Example类指定查询列, 通过selectProperties方法指定查询列  @param example the example
	 *
	 * @param example the example
	 *
	 * @return the list
	 */
	List<T> selectByExample(Object example);

	/**
	 * 根据Example条件进行查询总数  @param example the example
	 *
	 * @param example the example
	 *
	 * @return the int
	 */
	int selectCountByExample(Object example);

	/**
	 * 根据Example条件更新实体record包含的不是null的属性值  @param record the record
	 *
	 * @param record  the record
	 * @param example the example
	 *
	 * @return the int
	 */
	int updateByExample(@Param("record") T record, @Param("example") Object example);

	/**
	 * 根据Example条件删除数据  @param example the example
	 *
	 * @param example the example
	 *
	 * @return the int
	 */
	int deleteByExample(Object example);

	/**
	 * 根据实体属性和RowBounds进行分页查询  @param record the record
	 *
	 * @param record    the record
	 * @param rowBounds the row bounds
	 *
	 * @return the list
	 */
	List<T> selectByRowBounds(T record, RowBounds rowBounds);

	/**
	 * 根据example条件和RowBounds进行分页查询  @param example the example
	 *
	 * @param example   the example
	 * @param rowBounds the row bounds
	 *
	 * @return the list
	 */
	List<T> selectByExampleAndRowBounds(Object example, RowBounds rowBounds);

}
