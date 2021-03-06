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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.somnus.cloud.common.base.constant.GlobalConstant;
import com.somnus.cloud.common.base.dto.LoginAuthDto;
import com.somnus.cloud.common.base.enums.ErrorCodeEnum;
import com.somnus.cloud.common.util.PublicUtil;
import com.somnus.cloud.comon.core.support.BaseService;
import com.somnus.cloud.provider.api.exceptions.UacBizException;
import com.somnus.cloud.provider.api.vo.MenuVo;
import com.somnus.cloud.provider.mapper.UacMenuMapper;
import com.somnus.cloud.provider.model.constant.MenuConstant;
import com.somnus.cloud.provider.model.domain.UacMenu;
import com.somnus.cloud.provider.model.domain.UacRoleMenu;
import com.somnus.cloud.provider.model.dto.menu.UacMenuStatusDto;
import com.somnus.cloud.provider.model.enums.UacMenuStatusEnum;
import com.somnus.cloud.provider.model.vo.ViewMenuVo;
import com.somnus.cloud.provider.service.UacActionService;
import com.somnus.cloud.provider.service.UacMenuService;
import com.somnus.cloud.provider.service.UacRoleMenuService;
import com.somnus.cloud.provider.utils.TreeUtil;

/**
 * @ClassName: UacMenuServiceImpl
 * @Description: The class Uac menu service.
 * @author Somnus
 * @date 2018???10???18???
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UacMenuServiceImpl extends BaseService<UacMenu> implements UacMenuService {
	@Autowired
	private UacMenuMapper uacMenuMapper;
	
	@Autowired
	private UacRoleMenuService uacRoleMenuService;
	
	@Autowired
	private UacActionService uacActionService;

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<MenuVo> getMenuVoList(Long userId, Long applicationId) {
		// 1.???????????????????????????????????????
		List<MenuVo> menuVoList = Lists.newArrayList();
		List<UacMenu> menuList;
		Set<UacMenu> menuSet = Sets.newHashSet();
		// ?????????admin????????????????????????
		if (userId == 1L) {
			// 1.1 ???????????????????????????????????????
			UacMenu uacMenuQuery = new UacMenu();
			uacMenuQuery.setStatus(UacMenuStatusEnum.ENABLE.getType());
			uacMenuQuery.setApplicationId(applicationId);
			uacMenuQuery.setOrderBy(" level asc,number asc");
			menuList = uacMenuMapper.selectMenuList(uacMenuQuery);
		} else {
			// 1.2???????????????????????????????????????
			menuVoList = uacMenuMapper.findMenuVoListByUserId(userId);
			if (PublicUtil.isEmpty(menuVoList)) {
				return null;
			}
			Set<Long> ids = Sets.newHashSet();
			for (final MenuVo menuVo : menuVoList) {
				ids.add(menuVo.getId());
			}

			List<UacMenu> ownMenuList = this.getMenuList(ids);

			// ???????????????????????????????????????
			UacMenu uacMenu = new UacMenu();
			uacMenu.setStatus(UacMenuStatusEnum.ENABLE.getType());
			List<UacMenu> allMenuList = this.selectMenuList(uacMenu);
			Map<Long, UacMenu> map = Maps.newHashMap();
			for (final UacMenu menu : allMenuList) {
				map.put(menu.getId(), menu);
			}

			for (final UacMenu menu : ownMenuList) {
				getPid(menuSet, menu, map);
			}
			menuList = new ArrayList<>(menuSet);
		}
		List<MenuVo> list = getMenuVo(menuList);
		if (PublicUtil.isNotEmpty(menuVoList)) {
			list.addAll(menuVoList);
		}
		// 2.????????????
		return TreeUtil.getChildMenuVos(list, 0L);
	}

	private void getPid(Set<UacMenu> menuSet, UacMenu menu, Map<Long, UacMenu> map) {
		UacMenu parent = map.get(menu.getPid());
		if (parent != null && parent.getId() != 0L) {
			menuSet.add(parent);
			getPid(menuSet, parent, map);
		}
	}

	private List<MenuVo> getMenuVo(List<UacMenu> list) {
		List<MenuVo> menuVoList = Lists.newArrayList();
		for (UacMenu uacMenu : list) {
			MenuVo menuVo = new MenuVo();
			BeanUtils.copyProperties(uacMenu, menuVo);
			menuVo.setUrl(uacMenu.getUrl());
			menuVo.setMenuName(uacMenu.getMenuName());
			menuVoList.add(menuVo);
		}
		return menuVoList;
	}

	@Override
	public int saveUacMenu(UacMenu menu, LoginAuthDto loginAuthDto) {
		Long pid = menu.getPid();
		menu.setUpdateInfo(loginAuthDto);
		UacMenu parentMenu = mapper.selectByPrimaryKey(pid);
		if (PublicUtil.isEmpty(parentMenu)) {
			throw new UacBizException(ErrorCodeEnum.UAC10013001, pid);
		}
		if (menu.isNew()) {

			UacMenu updateMenu = new UacMenu();
			menu.setLevel(parentMenu.getLevel() + 1);
			updateMenu.setLeaf(MenuConstant.MENU_LEAF_NO);
			updateMenu.setId(pid);
			Long menuId = super.generateId();
			menu.setId(menuId);
			int result = mapper.updateByPrimaryKeySelective(updateMenu);
			if (result < 1) {
				throw new UacBizException(ErrorCodeEnum.UAC10013002, menuId);
			}

			menu.setStatus(UacMenuStatusEnum.ENABLE.getType());
			menu.setCreatorId(loginAuthDto.getUserId());
			menu.setCreator(loginAuthDto.getUserName());
			menu.setLastOperatorId(loginAuthDto.getUserId());
			menu.setLastOperator(loginAuthDto.getUserName());
			// ??????????????????????????????
			menu.setLeaf(MenuConstant.MENU_LEAF_YES);
			return uacMenuMapper.insertSelective(menu);
		} else {
			return uacMenuMapper.updateByPrimaryKeySelective(menu);
		}
	}

	@Override
	public int deleteUacMenuById(Long id, LoginAuthDto loginAuthDto) {
		Preconditions.checkArgument(id != null, "??????id????????????");
		int result;
		// ????????????????????????
		UacMenu uacMenuQuery = new UacMenu();
		uacMenuQuery.setId(id);
		uacMenuQuery = mapper.selectOne(uacMenuQuery);
		if (PublicUtil.isEmpty(uacMenuQuery)) {
			throw new UacBizException(ErrorCodeEnum.UAC10013003, id);
		}

		// ????????????????????????????????????
		UacRoleMenu uacRoleMenu = new UacRoleMenu();
		uacRoleMenu.setMenuId(id);
		uacRoleMenuService.delete(uacRoleMenu);


		// ????????????
		result = uacMenuMapper.deleteByPrimaryKey(id);
		if (result < 1) {
			logger.error("?????????????????? menuId={}", id);
			throw new UacBizException(ErrorCodeEnum.UAC10013008, id);
		}

		// ????????????
		// TODO ????????????????????????
		uacActionService.deleteByMenuId(id);

		// ?????????????????????????????????????????????????????????
		UacMenu updateParentUacMenu = new UacMenu();
		updateParentUacMenu.setId(uacMenuQuery.getPid());
		updateParentUacMenu.setLeaf(MenuConstant.MENU_LEAF_YES);
		// ????????????
		if (Objects.equals(MenuConstant.MENU_LEVEL_TWO, uacMenuQuery.getLevel()) || Objects.equals(MenuConstant.MENU_LEVEL_THREE, uacMenuQuery.getLevel())) {
			// ???????????????????????????
			int count = uacMenuMapper.selectMenuChildCountByPid(uacMenuQuery.getPid());
			if (count == 0) {
				uacMenuMapper.updateByPrimaryKeySelective(updateParentUacMenu);
			}
		}
		return result;
	}

	@Override
	public int enableMenuList(List<UacMenu> menuList, LoginAuthDto loginAuthDto) {
		UacMenu uacMenuUpdate = new UacMenu();
		int sum = 0;
		for (UacMenu menu : menuList) {
			uacMenuUpdate.setId(menu.getId());
			uacMenuUpdate.setVersion(menu.getVersion() + 1);
			uacMenuUpdate.setStatus(UacMenuStatusEnum.ENABLE.getType());
			uacMenuUpdate.setLastOperator(loginAuthDto.getLoginName());
			uacMenuUpdate.setLastOperatorId(loginAuthDto.getUserId());
			uacMenuUpdate.setUpdateTime(new Date());
			int result = mapper.updateByPrimaryKeySelective(uacMenuUpdate);
			if (result > 0) {
				sum += 1;
			} else {
				throw new UacBizException(ErrorCodeEnum.UAC10013004, menu.getId());
			}
		}
		return sum;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<UacMenu> getAllParentMenuByMenuId(Long menuId) {
		UacMenu uacMenuQuery = new UacMenu();
		uacMenuQuery.setId(menuId);
		uacMenuQuery = mapper.selectOne(uacMenuQuery);
		List<UacMenu> uacMenuList = Lists.newArrayList();
		uacMenuList = buildParentNote(uacMenuList, uacMenuQuery);
		return uacMenuList;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<UacMenu> getAllChildMenuByMenuId(Long menuId, String menuStatus) {
		UacMenu uacMenuQuery = new UacMenu();
		uacMenuQuery.setId(menuId);
		uacMenuQuery = mapper.selectOne(uacMenuQuery);
		List<UacMenu> uacMenuList = Lists.newArrayList();
		uacMenuList = buildNode(uacMenuList, uacMenuQuery, menuStatus);
		return uacMenuList;
	}

	@Override
	public int disableMenuList(List<UacMenu> menuList, LoginAuthDto loginAuthDto) {
		UacMenu uacMenuUpdate = new UacMenu();
		int sum = 0;
		for (UacMenu menu : menuList) {
			uacMenuUpdate.setId(menu.getId());
			uacMenuUpdate.setVersion(menu.getVersion() + 1);
			uacMenuUpdate.setStatus(UacMenuStatusEnum.DISABLE.getType());
			uacMenuUpdate.setLastOperator(loginAuthDto.getLoginName());
			uacMenuUpdate.setLastOperatorId(loginAuthDto.getUserId());
			uacMenuUpdate.setUpdateTime(new Date());
			int result = mapper.updateByPrimaryKeySelective(uacMenuUpdate);
			if (result > 0) {
				sum += 1;
			} else {
				throw new UacBizException(ErrorCodeEnum.UAC10013005, menu.getId());
			}
		}
		return sum;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<UacMenu> selectMenuList(UacMenu uacMenu) {
		return uacMenuMapper.selectMenuList(uacMenu);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<MenuVo> findAllMenuListByAuthResDto(LoginAuthDto authResDto) {
		List<MenuVo> voList = Lists.newArrayList();
		Preconditions.checkArgument(authResDto != null, "????????????");

		if (!GlobalConstant.Sys.SUPER_MANAGER_LOGIN_NAME.equals(authResDto.getLoginName())) {
			voList = uacMenuMapper.findMenuVoListByUserId(authResDto.getUserId());
		} else {
			UacMenu uacMenuQuery = new UacMenu();
			// ?????????????????????
			uacMenuQuery.setStatus(UacMenuStatusEnum.ENABLE.getType());
			uacMenuQuery.setOrderBy(" level asc,number asc");
			List<UacMenu> list = uacMenuMapper.select(uacMenuQuery);
			for (UacMenu uacMenu : list) {
				MenuVo menuVo = new MenuVo();
				BeanUtils.copyProperties(uacMenu, menuVo);
				voList.add(menuVo);
			}
		}
		return voList;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public ViewMenuVo getViewVoById(Long id) {
		Preconditions.checkArgument(id != null, "??????ID????????????");
		UacMenu menu = uacMenuMapper.selectByPrimaryKey(id);

		if (menu == null) {
			logger.error("?????????????????????id={}", id);
			throw new UacBizException(ErrorCodeEnum.UAC10013003, id);
		}

		// ????????????????????????
		UacMenu parentMenu = uacMenuMapper.selectByPrimaryKey(menu.getPid());

		ModelMapper modelMapper = new ModelMapper();
		ViewMenuVo menuVo = modelMapper.map(menu, ViewMenuVo.class);

		if (parentMenu != null) {
			menuVo.setParentMenuName(parentMenu.getMenuName());
		}

		return menuVo;
	}

	@Override
	public void updateUacMenuStatusById(UacMenuStatusDto uacMenuStatusDto, LoginAuthDto loginAuthDto) {
		Long id = uacMenuStatusDto.getId();
		String status = uacMenuStatusDto.getStatus();
		Preconditions.checkArgument(id != null, "??????ID????????????");
		Preconditions.checkArgument(StringUtils.isNotEmpty(status), "????????????????????????");

		UacMenu uacMenuQuery = this.selectByKey(id);
		if (MenuConstant.MENU_LEVEL_ROOT.equals(uacMenuQuery.getLevel())) {
			throw new UacBizException(ErrorCodeEnum.UAC10013007);
		}
		// ????????????????????????
		List<UacMenu> menuList = Lists.newArrayList();

		int result;
		if (status.equals(UacMenuStatusEnum.DISABLE.getType())) {
			// ???????????????????????????
			menuList = this.getAllChildMenuByMenuId(id, UacMenuStatusEnum.ENABLE.getType());
			// ???????????????????????????
			result = this.disableMenuList(menuList, loginAuthDto);
		} else {
			// ??????????????????????????????????????????
			UacMenu uacMenu = new UacMenu();
			uacMenu.setPid(id);
			result = this.selectCount(uacMenu);
			// ????????????????????????
			if (result > 0) {
				menuList = this.getAllChildMenuByMenuId(id, UacMenuStatusEnum.DISABLE.getType());
			}
			List<UacMenu> menuListTemp = this.getAllParentMenuByMenuId(id);
			for (UacMenu menu : menuListTemp) {
				if (!menuList.contains(menu)) {
					menuList.add(menu);
				}
			}
			// ??????????????????????????????????????????
			result = this.enableMenuList(menuList, loginAuthDto);
		}
		if (result < 1) {
			throw new UacBizException(ErrorCodeEnum.UAC10013006, id);
		}
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public boolean checkMenuHasChildMenu(Long pid) {
		Preconditions.checkArgument(pid != null, "??????pid????????????");

		UacMenu uacMenu = new UacMenu();
		uacMenu.setStatus(UacMenuStatusEnum.ENABLE.getType());
		uacMenu.setPid(pid);

		return mapper.selectCount(uacMenu) > 0;
	}

	@Override
	public List<UacMenu> listMenuListByRoleId(Long roleId) {
		List<UacMenu> menuList = uacMenuMapper.listMenuListByRoleId(roleId);
		List<UacMenu> addMenuList = Lists.newArrayList();

		if (PublicUtil.isNotEmpty(menuList)) {
			for (UacMenu uacMenu : menuList) {
				getMenuList(addMenuList, uacMenu.getPid());
			}
		}
		menuList.addAll(addMenuList);
		return new ArrayList<>(new HashSet<>(menuList));
	}

	@Override
	public List<UacMenu> getMenuList(final Set<Long> menuIdList) {
		return uacMenuMapper.listMenu(menuIdList);
	}

	private List<UacMenu> getMenuList(List<UacMenu> uacMenuList, Long menuId) {
		UacMenu uacMenu = uacMenuMapper.selectByPrimaryKey(menuId);
		if (uacMenu != null) {
			Long pid = uacMenu.getPid();
			if (pid != null) {
				uacMenuList.add(uacMenu);
				getMenuList(uacMenuList, pid);
			}
		}
		return uacMenuList;
	}

	/**
	 * ??????????????????????????????
	 */
	private List<UacMenu> buildNode(List<UacMenu> uacMenuList, UacMenu uacMenu, String menuStatus) {
		List<UacMenu> uacMenuQueryList = mapper.select(uacMenu);
		UacMenu uacMenuQuery;
		for (UacMenu menu : uacMenuQueryList) {
			// ????????????
			if (menuStatus.equals(menu.getStatus()) && !MenuConstant.MENU_LEVEL_ROOT.equals(menu.getLevel())) {
				uacMenuList.add(menu);
			}
			uacMenuQuery = new UacMenu();
			uacMenuQuery.setPid(menu.getId());
			buildNode(uacMenuList, uacMenuQuery, menuStatus);
		}
		return uacMenuList;
	}

	/**
	 * ??????????????????????????????
	 */
	private List<UacMenu> buildParentNote(List<UacMenu> uacMenuList, UacMenu uacMenu) {
		List<UacMenu> uacMenuQueryList = mapper.select(uacMenu);
		UacMenu uacMenuQuery;
		for (UacMenu menu : uacMenuQueryList) {
			if (UacMenuStatusEnum.DISABLE.getType().equals(menu.getStatus()) && !MenuConstant.MENU_LEVEL_ROOT.equals(menu.getLevel())) {
				// ????????????
				uacMenuList.add(menu);
			}
			uacMenuQuery = new UacMenu();
			uacMenuQuery.setId(menu.getPid());
			buildParentNote(uacMenuList, uacMenuQuery);
		}
		return uacMenuList;
	}
}
