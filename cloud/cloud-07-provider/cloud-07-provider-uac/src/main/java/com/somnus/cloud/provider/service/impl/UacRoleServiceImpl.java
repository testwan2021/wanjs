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

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.somnus.cloud.common.base.constant.GlobalConstant;
import com.somnus.cloud.common.base.dto.LoginAuthDto;
import com.somnus.cloud.common.base.enums.ErrorCodeEnum;
import com.somnus.cloud.common.util.Collections3;
import com.somnus.cloud.common.util.PublicUtil;
import com.somnus.cloud.comon.core.support.BaseService;
import com.somnus.cloud.provider.api.exceptions.UacBizException;
import com.somnus.cloud.provider.api.vo.MenuVo;
import com.somnus.cloud.provider.mapper.UacRoleMapper;
import com.somnus.cloud.provider.mapper.UacRoleMenuMapper;
import com.somnus.cloud.provider.model.domain.UacAction;
import com.somnus.cloud.provider.model.domain.UacMenu;
import com.somnus.cloud.provider.model.domain.UacRole;
import com.somnus.cloud.provider.model.domain.UacRoleAction;
import com.somnus.cloud.provider.model.domain.UacRoleMenu;
import com.somnus.cloud.provider.model.domain.UacRoleUser;
import com.somnus.cloud.provider.model.domain.UacUser;
import com.somnus.cloud.provider.model.dto.role.BindUserDto;
import com.somnus.cloud.provider.model.dto.role.RoleBindActionDto;
import com.somnus.cloud.provider.model.dto.role.RoleBindMenuDto;
import com.somnus.cloud.provider.model.dto.role.RoleBindUserDto;
import com.somnus.cloud.provider.model.dto.role.RoleBindUserReqDto;
import com.somnus.cloud.provider.model.vo.BindAuthVo;
import com.somnus.cloud.provider.model.vo.RoleVo;
import com.somnus.cloud.provider.model.vo.role.MenuCountVo;
import com.somnus.cloud.provider.service.UacActionService;
import com.somnus.cloud.provider.service.UacMenuService;
import com.somnus.cloud.provider.service.UacRoleActionService;
import com.somnus.cloud.provider.service.UacRoleMenuService;
import com.somnus.cloud.provider.service.UacRoleService;
import com.somnus.cloud.provider.service.UacRoleUserService;
import com.somnus.cloud.provider.service.UacUserService;
import com.somnus.cloud.provider.utils.TreeUtil;

/**
 * @ClassName: UacRoleServiceImpl
 * @Description: The class Uac role service.
 * @author Somnus
 * @date 2018???10???18???
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UacRoleServiceImpl extends BaseService<UacRole> implements UacRoleService {
	
	@Autowired
	private UacRoleMapper uacRoleMapper;
	
	@Autowired
	private UacRoleUserService uacRoleUserService;
	
	@Autowired
	private UacRoleMenuMapper uacRoleMenuMapper;
	
	@Autowired
	private UacUserService uacUserService;
	
	@Autowired
	private UacRoleMenuService uacRoleMenuService;
	
	@Autowired
	private UacMenuService uacMenuService;
	
	@Autowired
	private UacActionService uacActionService;
	
	@Autowired
	private UacRoleActionService uacRoleActionService;

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public UacRole findByRoleCode(String roleCode) {
		return uacRoleMapper.findByRoleCode(roleCode);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<RoleVo> queryRoleListWithPage(UacRole role) {
		return uacRoleMapper.queryRoleListWithPage(role);
	}

	@Override
	public int deleteRoleById(Long roleId) {
		//???????????????????????????????????????, ???????????????????????????
		if (null == roleId) {
			throw new IllegalArgumentException(ErrorCodeEnum.UAC10012001.getMsg());
		}

		// ???????????????????????????
		if (Objects.equals(roleId, GlobalConstant.Sys.SUPER_MANAGER_ROLE_ID)) {
			throw new UacBizException(ErrorCodeEnum.UAC10012003);
		}

		List<UacRoleUser> uruList = uacRoleUserService.listByRoleId(roleId);

		if (!uruList.isEmpty()) {
			uacRoleUserService.deleteByRoleId(roleId);
		}

		uacRoleActionService.deleteByRoleId(roleId);
		uacRoleMenuService.deleteByRoleId(roleId);
		return uacRoleMapper.deleteByPrimaryKey(roleId);
	}

	@Override
	public int saveRole(UacRole role, LoginAuthDto loginAuthDto) {
		int result = 0;
		role.setUpdateInfo(loginAuthDto);
		if (role.isNew()) {
			role.setId(super.generateId());
			uacRoleMapper.insertSelective(role);
		} else {
			result = uacRoleMapper.updateByPrimaryKeySelective(role);
		}
		return result;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<Long> getAuthTreeNoCheckMenu(Long roleId) {
		//?????????????????????????????????????????????????????????, ???????????????????????????0???????????????????????????
		List<MenuCountVo> menuCountVos = uacRoleMenuMapper.countChildMenuNum(roleId);
		List<Long> noCheckedMenu = Lists.newArrayList();
		for (MenuCountVo vo : menuCountVos) {
			noCheckedMenu.add(vo.getId());
		}

		return noCheckedMenu;
	}

	@Override
	public void bindAction(RoleBindActionDto grantAuthRole) {
		Long roleId = grantAuthRole.getRoleId();
		Set<Long> actionIdList = grantAuthRole.getActionIdList();

		if (roleId == null) {
			throw new UacBizException(ErrorCodeEnum.UAC10012001);
		}

		if (Objects.equals(roleId, GlobalConstant.Sys.SUPER_MANAGER_ROLE_ID)) {
			logger.error("????????????, ????????????????????????????????????");
			throw new UacBizException(ErrorCodeEnum.UAC10011023);
		}

		UacRole uacRole = uacRoleMapper.selectByPrimaryKey(roleId);

		if (uacRole == null) {
			logger.error("?????????????????????. roleId={}", roleId);
			throw new UacBizException(ErrorCodeEnum.UAC10012005, roleId);
		}

		// TODO ????????????????????????(??????????????? ????????? ??????????????????????????????????????????)
		List<UacRoleAction> uacRoleActionList = uacRoleActionService.listByRoleId(roleId);

		if (PublicUtil.isNotEmpty(uacRoleActionList)) {
			uacRoleActionService.deleteByRoleId(roleId);
		}

		if (PublicUtil.isEmpty(actionIdList)) {
			logger.error("??????????????????Id??????, ????????????????????????");
		} else {
			// ????????????
			uacRoleActionService.insert(roleId, actionIdList);
		}
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public UacRole getRoleById(Long roleId) {
		return uacRoleMapper.selectByPrimaryKey(roleId);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public RoleBindUserDto getRoleBindUserDto(Long roleId, Long currentUserId) {
		RoleBindUserDto roleBindUserDto = new RoleBindUserDto();
		Set<Long> alreadyBindUserIdSet = Sets.newHashSet();
		UacRole uacRole = uacRoleMapper.selectByPrimaryKey(roleId);
		if (PublicUtil.isEmpty(uacRole)) {
			logger.error("?????????roleId={}, ?????????", roleId);
			throw new UacBizException(ErrorCodeEnum.UAC10012005, roleId);
		}

		// ??????????????????????????????????????????
		List<BindUserDto> bindUserDtoList = uacRoleMapper.selectAllNeedBindUser(GlobalConstant.Sys.SUPER_MANAGER_ROLE_ID, currentUserId);
		// ??????????????????????????????
		List<UacRoleUser> setAlreadyBindUserSet = uacRoleUserService.listByRoleId(roleId);
		Set<BindUserDto> allUserSet = new HashSet<>(bindUserDtoList);

		for (UacRoleUser uacRoleUser : setAlreadyBindUserSet) {
			alreadyBindUserIdSet.add(uacRoleUser.getUserId());
		}

		roleBindUserDto.setAllUserSet(allUserSet);
		roleBindUserDto.setAlreadyBindUserIdSet(alreadyBindUserIdSet);

		return roleBindUserDto;
	}

	@Override
	public void bindUser4Role(RoleBindUserReqDto roleBindUserReqDto, LoginAuthDto authResDto) {

		if (roleBindUserReqDto == null) {
			logger.error("??????????????????");
			throw new IllegalArgumentException("??????????????????");
		}

		Long roleId = roleBindUserReqDto.getRoleId();
		Long loginUserId = authResDto.getUserId();
		List<Long> userIdList = roleBindUserReqDto.getUserIdList();

		if (null == roleId) {
			throw new IllegalArgumentException(ErrorCodeEnum.UAC10012001.getMsg());
		}

		UacRole role = this.getRoleById(roleId);

		if (role == null) {
			logger.error("????????????????????? roleId={}", roleId);
			throw new UacBizException(ErrorCodeEnum.UAC10012005, roleId);
		}

		if (PublicUtil.isNotEmpty(userIdList) && userIdList.contains(loginUserId)) {
			logger.error("?????????????????????????????? userId={}", loginUserId);
			throw new UacBizException(ErrorCodeEnum.UAC10011023);
		}

		// ???????????????????????????Id??????
		List<Long> superUserList = uacRoleUserService.listSuperUser(GlobalConstant.Sys.SUPER_MANAGER_ROLE_ID);
		List<Long> unionList = Collections3.intersection(userIdList, superUserList);
		if (PublicUtil.isNotEmpty(userIdList) && PublicUtil.isNotEmpty(unionList)) {
			logger.error("????????????????????????????????? ????????????={}", unionList);
			throw new UacBizException(ErrorCodeEnum.UAC10011023);
		}

		// 1. ????????????????????????????????????(??????????????????????????????)
		List<UacRoleUser> userRoles = uacRoleUserService.listByRoleId(roleId);

		if (PublicUtil.isNotEmpty(userRoles)) {
			uacRoleUserService.deleteExcludeSuperMng(roleId, GlobalConstant.Sys.SUPER_MANAGER_ROLE_ID);
		}

		if (PublicUtil.isEmpty(userIdList)) {
			// ???????????????????????????????????????
			logger.info("????????????????????????????????????????????????");
			return;
		}

		// ??????????????????
		for (Long userId : userIdList) {
			UacUser uacUser = uacUserService.queryByUserId(userId);
			if (PublicUtil.isEmpty(uacUser)) {
				logger.error("???????????????????????? userId={}", userId);
				throw new UacBizException(ErrorCodeEnum.UAC10011024, userId);
			}
			uacRoleUserService.saveRoleUser(userId, roleId);
		}

	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<UacRole> findAllRoleInfoByUserId(Long userId) {
		return uacRoleMapper.selectAllRoleInfoByUserId(userId);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public BindAuthVo getActionTreeByRoleId(Long roleId) {
		BindAuthVo bindAuthVo = new BindAuthVo();
		if (roleId == null) {
			throw new UacBizException(ErrorCodeEnum.UAC10012001);
		}

		UacRole roleById = this.getRoleById(roleId);
		if (roleById == null) {
			logger.error("????????????????????? roleId={}", roleId);
			throw new UacBizException(ErrorCodeEnum.UAC10012005, roleId);
		}

		List<UacMenu> uacMenus = uacMenuService.listMenuListByRoleId(roleId);
		if (PublicUtil.isEmpty(uacMenus)) {
			throw new UacBizException(ErrorCodeEnum.UAC10013009);
		}
		// ???????????????????????????
		List<UacAction> uacActions = uacActionService.listActionList(uacMenus);
		// ??????????????????????????? ?????????????????????

		List<MenuVo> menuVoList = this.getAuthList(uacMenus, uacActions);

		List<MenuVo> tree = TreeUtil.getChildMenuVos(menuVoList, 0L);

		// ??????????????????????????????????????????Id??????
		List<Long> checkedAuthList = uacActionService.getCheckedActionList(roleId);

		bindAuthVo.setAuthTree(tree);
		bindAuthVo.setCheckedAuthList(checkedAuthList);

		return bindAuthVo;
	}

	private List<MenuVo> getAuthList(List<UacMenu> uacMenus, List<UacAction> uacActions) {
		List<MenuVo> menuVoList = Lists.newArrayList();
		MenuVo menuVo;
		for (UacMenu uacMenu : uacMenus) {
			menuVo = new MenuVo();
			BeanUtils.copyProperties(uacMenu, menuVo);
			menuVo.setRemark("menu");
			menuVoList.add(menuVo);
		}
		if (PublicUtil.isNotEmpty(uacActions)) {
			for (UacAction uacAction : uacActions) {
				menuVo = new MenuVo();
				menuVo.setId(uacAction.getId());
				menuVo.setMenuName(uacAction.getActionName());
				menuVo.setMenuCode(uacAction.getActionCode());
				menuVo.setPid(uacAction.getMenuId());
				menuVo.setUrl(uacAction.getUrl());
				menuVo.setRemark("action");
				menuVoList.add(menuVo);
			}
		}
		return menuVoList;
	}

	@Override
	public void batchDeleteByIdList(List<Long> roleIdList) {
		logger.info("??????????????????. idList={}", roleIdList);
		Preconditions.checkArgument(PublicUtil.isNotEmpty(roleIdList), "????????????ID?????????");

		List<UacRoleUser> uruList = uacRoleUserService.listByRoleIdList(roleIdList);
		if (!uruList.isEmpty()) {
			uacRoleUserService.deleteByRoleIdList(roleIdList);
		}

		uacRoleMenuService.deleteByRoleIdList(roleIdList);
		uacRoleActionService.deleteByRoleIdList(roleIdList);

		int result = uacRoleMapper.batchDeleteByIdList(roleIdList);
		if (result < roleIdList.size()) {
			throw new UacBizException(ErrorCodeEnum.UAC10012006, Joiner.on(GlobalConstant.Symbol.COMMA).join(roleIdList));
		}
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<MenuVo> getOwnAuthTree(Long userId) {
		if (userId == null) {
			throw new UacBizException(ErrorCodeEnum.UAC10011001);
		}

		return uacMenuService.getMenuVoList(userId, GlobalConstant.Sys.OPER_APPLICATION_ID);
	}

	@Override
	public void bindMenu(RoleBindMenuDto roleBindMenuDto) {

		Long roleId = roleBindMenuDto.getRoleId();
		Set<Long> menuIdList = roleBindMenuDto.getMenuIdList();

		if (roleId == null) {
			throw new UacBizException(ErrorCodeEnum.UAC10012001);
		}

		if (Objects.equals(roleId, GlobalConstant.Sys.SUPER_MANAGER_ROLE_ID)) {
			logger.error("????????????, ????????????????????????????????????");
			throw new UacBizException(ErrorCodeEnum.UAC10011023);
		}

		UacRole uacRole = uacRoleMapper.selectByPrimaryKey(roleId);

		if (uacRole == null) {
			logger.error("?????????????????????. roleId={}", roleId);
			throw new UacBizException(ErrorCodeEnum.UAC10012005, roleId);
		}

		// TODO ????????????????????????(??????????????? ????????? ??????????????????????????????????????????)
		List<UacRoleMenu> uacRoleMenuList = uacRoleMenuService.listByRoleId(roleId);

		if (PublicUtil.isNotEmpty(uacRoleMenuList)) {
			uacRoleMenuService.deleteByRoleId(roleId);
		}

		// menuSet actionIdList ??????????????? ???????????????????????????
		if (PublicUtil.isEmpty(menuIdList)) {
			logger.error("??????????????????Id??????, ??????????????????");
		} else {
			// ????????????
			uacRoleMenuService.insert(roleId, menuIdList);

		}
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public BindAuthVo getMenuTreeByRoleId(Long roleId) {
		BindAuthVo bindAuthVo = new BindAuthVo();
		Preconditions.checkArgument(roleId != null, ErrorCodeEnum.UAC10012001.getMsg());

		UacRole roleById = this.getRoleById(roleId);
		if (roleById == null) {
			logger.error("????????????????????? roleId={}", roleId);
			throw new UacBizException(ErrorCodeEnum.UAC10012005, roleId);
		}

		// ???????????????????????????
		List<UacMenu> uacMenus = uacMenuService.selectAll();
		// ??????????????????????????? ?????????????????????

		List<MenuVo> menuVoList = this.getAuthList(uacMenus, null);

		List<MenuVo> tree = TreeUtil.getChildMenuVos(menuVoList, 0L);

		// ??????????????????????????????????????????Id??????
		List<Long> checkedAuthList = uacActionService.getCheckedMenuList(roleId);

		bindAuthVo.setAuthTree(tree);
		bindAuthVo.setCheckedAuthList(checkedAuthList);

		return bindAuthVo;
	}
}
