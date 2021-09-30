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

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.somnus.cloud.common.base.dto.LoginAuthDto;
import com.somnus.cloud.common.base.dto.UpdateStatusDto;
import com.somnus.cloud.common.base.enums.ErrorCodeEnum;
import com.somnus.cloud.common.util.PublicUtil;
import com.somnus.cloud.comon.core.support.BaseService;
import com.somnus.cloud.comon.core.support.TreeUtils;
import com.somnus.cloud.provider.api.exceptions.MdcBizException;
import com.somnus.cloud.provider.mapper.MdcDictMapper;
import com.somnus.cloud.provider.model.domain.MdcDict;
import com.somnus.cloud.provider.model.enums.MdcDictStatusEnum;
import com.somnus.cloud.provider.model.vo.MdcDictVo;
import com.somnus.cloud.provider.service.MdcDictService;

/**
 * @ClassName: MdcDictServiceImpl
 * @Description: The class Mdc dict service.
 * @author Somnus
 * @date 2018年10月17日
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MdcDictServiceImpl extends BaseService<MdcDict> implements MdcDictService {
	@Autowired
	private MdcDictMapper mdcDictMapper;

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<MdcDictVo> getDictTreeList() {
		List<MdcDictVo> list = mdcDictMapper.listDictVo();
		return new TreeUtils<MdcDictVo, Long>().getChildTreeObjects(list, 0L);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public MdcDictVo getMdcDictVoById(Long dictId) {
		MdcDict dict = mdcDictMapper.selectByPrimaryKey(dictId);

		if (dict == null) {
			logger.error("找不到数据字典信息id={}", dictId);
			throw new MdcBizException(ErrorCodeEnum.MDC10021018, dictId);
		}

		// 获取父级菜单信息
		MdcDict parentDict = mdcDictMapper.selectByPrimaryKey(dict.getPid());

		ModelMapper modelMapper = new ModelMapper();
		MdcDictVo dictVo = modelMapper.map(dict, MdcDictVo.class);

		if (parentDict != null) {
			dictVo.setParentDictName(parentDict.getDictName());
		}

		return dictVo;
	}

	@Override
	public void updateMdcDictStatusById(UpdateStatusDto updateStatusDto, LoginAuthDto loginAuthDto) {
		Long id = updateStatusDto.getId();
		Integer status = updateStatusDto.getStatus();
		// 要处理的菜单集合
		List<MdcDict> mdcDictList = Lists.newArrayList();

		int result;
		if (status.equals(MdcDictStatusEnum.DISABLE.getType())) {
			// 获取菜单以及子菜单
			mdcDictList = this.getAllDictFolder(id, MdcDictStatusEnum.ENABLE.getType());
		} else {
			// 获取菜单、其子菜单以及父菜单
			MdcDict uacMenu = new MdcDict();
			uacMenu.setPid(id);
			result = this.selectCount(uacMenu);
			// 此菜单含有子菜单
			if (result > 0) {
				mdcDictList = this.getAllDictFolder(id, MdcDictStatusEnum.DISABLE.getType());
			}
			List<MdcDict> dictListTemp = this.getAllParentDictFolderByMenuId(id);
			for (MdcDict dict : dictListTemp) {
				if (!mdcDictList.contains(dict)) {
					mdcDictList.add(dict);
				}
			}
		}

		this.updateDictStatus(mdcDictList, loginAuthDto, status);
	}

	private void updateDictStatus(List<MdcDict> mdcDictList, LoginAuthDto loginAuthDto, int status) {
		MdcDict update = new MdcDict();
		for (MdcDict dict : mdcDictList) {
			update.setId(dict.getId());
			update.setVersion(dict.getVersion() + 1);
			update.setStatus(status);
			update.setUpdateInfo(loginAuthDto);
			int result = mapper.updateByPrimaryKeySelective(update);
			if (result < 1) {
				throw new MdcBizException(ErrorCodeEnum.MDC10021019, dict.getId());
			}
		}
	}

	private List<MdcDict> getAllDictFolder(Long id, int dictStatus) {
		MdcDict mdcDict = new MdcDict();
		mdcDict.setId(id);
		mdcDict = mapper.selectOne(mdcDict);
		List<MdcDict> mdcDictList = Lists.newArrayList();
		mdcDictList = buildNode(mdcDictList, mdcDict, dictStatus);
		return mdcDictList;
	}

	private List<MdcDict> getAllParentDictFolderByMenuId(Long dictId) {
		MdcDict mdcDictQuery = new MdcDict();
		mdcDictQuery.setId(dictId);
		mdcDictQuery = mapper.selectOne(mdcDictQuery);
		List<MdcDict> mdcDictList = Lists.newArrayList();
		mdcDictList = buildParentNote(mdcDictList, mdcDictQuery);
		return mdcDictList;
	}

	/**
	 * 递归获取菜单的子节点
	 */
	private List<MdcDict> buildNode(List<MdcDict> mdcDictList, MdcDict uacMenu, int dictStatus) {
		List<MdcDict> uacMenuQueryList = mapper.select(uacMenu);
		MdcDict uacMenuQuery;
		for (MdcDict dict : uacMenuQueryList) {
			if (dictStatus == dict.getStatus()) {
				mdcDictList.add(dict);
			}
			uacMenuQuery = new MdcDict();
			uacMenuQuery.setPid(dict.getId());
			buildNode(mdcDictList, uacMenuQuery, dictStatus);
		}
		return mdcDictList;
	}

	/**
	 * 递归获取菜单的父菜单
	 */
	private List<MdcDict> buildParentNote(List<MdcDict> mdcDictList, MdcDict mdcDict) {
		List<MdcDict> mdcDictQueryList = mapper.select(mdcDict);
		MdcDict uacMenuQuery;
		for (MdcDict dict : mdcDictQueryList) {
			if (MdcDictStatusEnum.DISABLE.getType() == dict.getStatus()) {
				mdcDictList.add(dict);
			}
			uacMenuQuery = new MdcDict();
			uacMenuQuery.setId(dict.getPid());
			buildParentNote(mdcDictList, uacMenuQuery);
		}
		return mdcDictList;
	}

	@Override
	public void saveMdcDict(MdcDict mdcDict, LoginAuthDto loginAuthDto) {
		Long pid = mdcDict.getPid();
		mdcDict.setUpdateInfo(loginAuthDto);
		MdcDict parentMenu = mapper.selectByPrimaryKey(pid);
		if (PublicUtil.isEmpty(parentMenu)) {
			throw new MdcBizException(ErrorCodeEnum.MDC10021020, pid);
		}
		if (mdcDict.isNew()) {
			MdcDict updateMenu = new MdcDict();
			updateMenu.setId(pid);
			Long dictId = super.generateId();
			mdcDict.setId(dictId);
			mapper.insertSelective(mdcDict);
		} else {
			mapper.updateByPrimaryKeySelective(mdcDict);
		}

	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public boolean checkDictHasChildDict(Long dictId) {
		logger.info("检查数据字典id={}是否存在生效节点", dictId);
		MdcDict uacMenu = new MdcDict();
		uacMenu.setStatus(MdcDictStatusEnum.ENABLE.getType());
		uacMenu.setPid(dictId);

		return mapper.selectCount(uacMenu) > 0;
	}

}