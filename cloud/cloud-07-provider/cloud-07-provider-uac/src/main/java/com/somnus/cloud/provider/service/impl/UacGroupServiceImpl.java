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

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.somnus.cloud.common.base.constant.GlobalConstant;
import com.somnus.cloud.common.base.dto.LoginAuthDto;
import com.somnus.cloud.common.base.enums.ErrorCodeEnum;
import com.somnus.cloud.common.util.Collections3;
import com.somnus.cloud.common.util.PublicUtil;
import com.somnus.cloud.comon.core.support.BaseService;
import com.somnus.cloud.provider.api.dto.user.IdStatusDto;
import com.somnus.cloud.provider.api.exceptions.UacBizException;
import com.somnus.cloud.provider.api.vo.MenuVo;
import com.somnus.cloud.provider.mapper.UacGroupMapper;
import com.somnus.cloud.provider.mapper.UacGroupUserMapper;
import com.somnus.cloud.provider.mapper.UacRoleMapper;
import com.somnus.cloud.provider.mapper.UacRoleUserMapper;
import com.somnus.cloud.provider.model.domain.UacGroup;
import com.somnus.cloud.provider.model.domain.UacGroupUser;
import com.somnus.cloud.provider.model.domain.UacUser;
import com.somnus.cloud.provider.model.dto.group.GroupBindUserDto;
import com.somnus.cloud.provider.model.dto.group.GroupBindUserReqDto;
import com.somnus.cloud.provider.model.dto.role.BindUserDto;
import com.somnus.cloud.provider.model.enums.UacGroupStatusEnum;
import com.somnus.cloud.provider.model.vo.GroupZtreeVo;
import com.somnus.cloud.provider.service.MdcAddressService;
import com.somnus.cloud.provider.service.UacGroupService;
import com.somnus.cloud.provider.service.UacUserService;
import com.somnus.cloud.provider.utils.TreeUtil;

/**
 * @ClassName: UacGroupServiceImpl
 * @Description: The class Uac group service.
 * @author Somnus
 * @date 2018???10???18???
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UacGroupServiceImpl extends BaseService<UacGroup> implements UacGroupService {

	@Autowired
	private UacGroupMapper uacGroupMapper;
	
	@Autowired
	private UacGroupUserMapper uacGroupUserMapper;
	
	@Autowired
	private UacRoleUserMapper uacRoleUserMapper;
	
	@Autowired
	private UacRoleMapper uacRoleMapper;
	
	@Autowired
	private UacUserService uacUserService;
	
	@Autowired
	private MdcAddressService mdcAddressService;

	private int addUacGroup(UacGroup group) {
		if (StringUtils.isEmpty(group.getStatus())) {
			group.setStatus(UacGroupStatusEnum.ENABLE.getStatus());
		}
		return uacGroupMapper.insertSelective(group);
	}

	private int editUacGroup(UacGroup group) {
		return uacGroupMapper.updateByPrimaryKeySelective(group);
	}

	@Override
	public int updateUacGroupStatusById(IdStatusDto idStatusDto, LoginAuthDto loginAuthDto) {

		Long groupId = idStatusDto.getId();
		Integer status = idStatusDto.getStatus();

		UacGroup uacGroup = new UacGroup();
		uacGroup.setId(groupId);
		uacGroup.setStatus(status);

		UacGroup group = uacGroupMapper.selectByPrimaryKey(groupId);
		if (PublicUtil.isEmpty(group)) {
			throw new UacBizException(ErrorCodeEnum.UAC10015001, groupId);
		}
		if (!UacGroupStatusEnum.contains(status)) {
			throw new UacBizException(ErrorCodeEnum.UAC10015002);
		}

		//?????????????????????
		List<UacGroup> totalGroupList = uacGroupMapper.selectAll();
		List<GroupZtreeVo> totalList = Lists.newArrayList();
		GroupZtreeVo zTreeVo;
		for (UacGroup vo : totalGroupList) {
			zTreeVo = new GroupZtreeVo();
			zTreeVo.setId(vo.getId());
			totalList.add(zTreeVo);
		}

		UacGroupUser uacGroupUser = new UacGroupUser();
		uacGroupUser.setUserId(loginAuthDto.getUserId());
		UacGroupUser groupUser = uacGroupUserMapper.selectOne(uacGroupUser);
		// ??????????????????????????????????????????
		UacGroup currentUserUacGroup = uacGroupMapper.selectByPrimaryKey(groupUser.getGroupId());
		// ????????????????????????????????????????????????
		List<GroupZtreeVo> childGroupList = this.getGroupTree(currentUserUacGroup.getId());
		// ???????????????????????????= ??????????????? - ????????????????????????
		totalList.removeAll(childGroupList);
		// ??????????????????????????????????????????????????????
		GroupZtreeVo zTreeVo1 = new GroupZtreeVo();
		zTreeVo1.setId(group.getId());
		if (totalList.contains(zTreeVo1)) {
			throw new UacBizException(ErrorCodeEnum.UAC10011023);
		}
		if (groupUser.getGroupId().equals(uacGroup.getId()) && UacGroupStatusEnum.ENABLE.getStatus() == group.getStatus()) {
			throw new UacBizException(ErrorCodeEnum.UAC10011023);
		}
		uacGroup.setGroupName(group.getGroupName());
		uacGroup.setGroupCode(group.getGroupCode());
		uacGroup.setVersion(group.getVersion() + 1);
		int result = uacGroupMapper.updateByPrimaryKeySelective(uacGroup);
		// ??????????????????????????????????????????
		List<GroupZtreeVo> childUacGroupList = this.getGroupTree(uacGroup.getId());
		// ????????????????????????
		if (PublicUtil.isNotEmpty(childUacGroupList)) {
			UacGroup childGroup;
			for (GroupZtreeVo uacGroup1 : childUacGroupList) {
				if (UacGroupStatusEnum.ENABLE.getStatus() == status) {
					UacGroup parentGroup = uacGroupMapper.selectByPrimaryKey(uacGroup1.getpId());
					if (parentGroup.getStatus() == UacGroupStatusEnum.DISABLE.getStatus()) {
						throw new UacBizException(ErrorCodeEnum.UAC10015003);
					}
				}
				childGroup = new UacGroup();
				childGroup.setStatus(uacGroup.getStatus());
				childGroup.setId(uacGroup1.getId());
				result = uacGroupMapper.updateByPrimaryKeySelective(childGroup);
				if (result < 1) {
					throw new UacBizException(ErrorCodeEnum.UAC10015006, uacGroup1.getId());
				}
			}
		}
		return result;
	}

	@Override
	public int deleteUacGroupById(Long id) {

		Preconditions.checkArgument(id != null, "??????id??????");
		Preconditions.checkArgument(!Objects.equals(id, GlobalConstant.Sys.SUPER_MANAGER_GROUP_ID), "?????????????????????");

		// ????????????????????????????????????????????????????????????
		UacGroup uacGroup = uacGroupMapper.selectByPrimaryKey(id);
		if (PublicUtil.isEmpty(uacGroup)) {
			throw new UacBizException(ErrorCodeEnum.UAC10015004, id);
		}
		//???????????????????????????????????????
		UacGroup childGroup = new UacGroup();
		childGroup.setPid(id);
		List<UacGroup> childGroupList = uacGroupMapper.select(childGroup);
		if (PublicUtil.isNotEmpty(childGroupList)) {
			throw new UacBizException(ErrorCodeEnum.UAC10015007, id);
		}
		//????????????????????????????????????
		UacGroupUser uacGroupUser = new UacGroupUser();
		uacGroupUser.setGroupId(id);
		List<UacGroupUser> uacGroupUserList = uacGroupUserMapper.select(uacGroupUser);
		if (PublicUtil.isNotEmpty(uacGroupUserList)) {
			throw new UacBizException(ErrorCodeEnum.UAC10015008, id);
		}

		return mapper.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public UacGroup queryById(Long groupId) {
		Preconditions.checkArgument(PublicUtil.isNotEmpty(groupId), "??????Id????????????");
		UacGroup query = new UacGroup();
		query.setId(groupId);
		return uacGroupMapper.selectOne(query);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<GroupZtreeVo> getGroupTree(Long groupId) {
		// 1. ?????????????????? ??????????????????????????????ztreeVo??????
		List<GroupZtreeVo> tree = Lists.newArrayList();

		UacGroup uacGroup = uacGroupMapper.selectByPrimaryKey(groupId);

		GroupZtreeVo zTreeMenuVo = buildGroupZTreeVoByGroup(uacGroup);
		if (0L == uacGroup.getPid()) {
			zTreeMenuVo.setOpen(true);
		}

		tree.add(zTreeMenuVo);

		// 2.???????????????id???????????????+??????????????????

		// ??????????????? ????????????id???
		UacGroup uacGroupQuery = new UacGroup();
		uacGroupQuery.setPid(groupId);
		List<UacGroup> groupList = uacGroupMapper.select(uacGroupQuery);
		if (PublicUtil.isNotEmpty(groupList)) {
			tree = buildNode(groupList, tree);
		}

		return tree;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<MenuVo> getGroupTreeListByUserId(Long userId) {
		UacGroupUser groupUser = uacGroupUserMapper.getByUserId(userId);
		Long groupId = groupUser.getGroupId();
		//??????????????????????????????????????????
		UacGroup currentUserUacGroup = uacGroupMapper.selectByPrimaryKey(groupId);
		//??????????????????????????????????????????
		List<GroupZtreeVo> childUacGroupList = this.getGroupTree(currentUserUacGroup.getId());
		return this.buildGroupTree(childUacGroupList, groupId);
	}

	private List<MenuVo> buildGroupTree(List<GroupZtreeVo> childUacGroupList, Long currentGroupId) {
		List<MenuVo> listVo = Lists.newArrayList();
		MenuVo menuVo;
		for (GroupZtreeVo group : childUacGroupList) {
			menuVo = new MenuVo();
			menuVo.setId(group.getId());
			if (currentGroupId.equals(group.getId())) {
				menuVo.setPid(0L);
			} else {
				menuVo.setPid(group.getpId());
			}
			menuVo.setMenuCode(group.getGroupCode());
			menuVo.setMenuName(group.getGroupName());
			listVo.add(menuVo);
		}

		return TreeUtil.getChildMenuVos(listVo, 0L);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public GroupBindUserDto getGroupBindUserDto(Long groupId, Long currentUserId) {
		GroupBindUserDto groupBindUserDto = new GroupBindUserDto();
		Set<Long> alreadyBindUserIdSet = Sets.newHashSet();
		UacGroup uacGroup = uacGroupMapper.selectByPrimaryKey(groupId);
		if (PublicUtil.isEmpty(uacGroup)) {
			logger.error("?????????uacGroup={}, ?????????", uacGroup);
			throw new UacBizException(ErrorCodeEnum.UAC10015001, groupId);
		}

		// ??????????????????????????????????????????
		List<BindUserDto> bindUserDtoList = uacRoleMapper.selectAllNeedBindUser(GlobalConstant.Sys.SUPER_MANAGER_ROLE_ID, currentUserId);
		// ??????????????????????????????
		List<UacGroupUser> setAlreadyBindUserSet = uacGroupUserMapper.listByGroupId(groupId);

		Set<BindUserDto> allUserSet = new HashSet<>(bindUserDtoList);

		for (UacGroupUser uacGroupUser : setAlreadyBindUserSet) {
			alreadyBindUserIdSet.add(uacGroupUser.getUserId());
		}

		groupBindUserDto.setAllUserSet(allUserSet);
		groupBindUserDto.setAlreadyBindUserIdSet(alreadyBindUserIdSet);

		return groupBindUserDto;
	}

	/**
	 * Bind uac user 4 group int.
	 *
	 * @param groupBindUserReqDto the group bind user req dto
	 * @param authResDto          the auth res dto
	 */
	@Override
	public void bindUacUser4Group(GroupBindUserReqDto groupBindUserReqDto, LoginAuthDto authResDto) {
		if (groupBindUserReqDto == null) {
			logger.error("??????????????????");
			throw new IllegalArgumentException("??????????????????");
		}

		Long groupId = groupBindUserReqDto.getGroupId();
		Long loginUserId = authResDto.getUserId();
		List<Long> userIdList = groupBindUserReqDto.getUserIdList();

		if (null == groupId) {
			throw new IllegalArgumentException("??????ID????????????");
		}

		UacGroup group = uacGroupMapper.selectByPrimaryKey(groupId);

		if (group == null) {
			logger.error("????????????????????? groupId={}", groupId);
			throw new UacBizException(ErrorCodeEnum.UAC10015001, groupId);
		}

		if (PublicUtil.isNotEmpty(userIdList) && userIdList.contains(loginUserId)) {
			logger.error("?????????????????????????????? userId={}", loginUserId);
			throw new UacBizException(ErrorCodeEnum.UAC10011023);
		}

		// ???????????????????????????Id??????
		List<Long> superUserList = uacRoleUserMapper.listSuperUser(GlobalConstant.Sys.SUPER_MANAGER_ROLE_ID);
		List<Long> unionList = Collections3.intersection(userIdList, superUserList);
		if (PublicUtil.isNotEmpty(userIdList) && PublicUtil.isNotEmpty(unionList)) {
			logger.error("????????????????????????????????? ????????????={}", unionList);
			throw new UacBizException(ErrorCodeEnum.UAC10011023);
		}

		// 1. ????????????????????????????????????(??????????????????????????????)
		List<UacGroupUser> groupUsers = uacGroupUserMapper.listByGroupId(groupId);

		if (PublicUtil.isNotEmpty(groupUsers)) {
			uacGroupUserMapper.deleteExcludeSuperMng(groupId, GlobalConstant.Sys.SUPER_MANAGER_ROLE_ID);
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
			UacGroupUser uacGroupUser = new UacGroupUser();
			uacGroupUser.setUserId(userId);
			uacGroupUser.setGroupId(groupId);
			uacGroupUserMapper.insertSelective(uacGroupUser);
		}
	}

	@Override
	public int saveUacGroup(UacGroup group, LoginAuthDto loginAuthDto) {

		int result;
		Preconditions.checkArgument(!StringUtils.isEmpty(group.getPid()), "????????????????????????");

		UacGroup parenGroup = uacGroupMapper.selectByPrimaryKey(group.getPid());
		if (PublicUtil.isEmpty(parenGroup)) {
			throw new UacBizException(ErrorCodeEnum.UAC10015009, group.getPid());
		}
		setGroupAddress(group);
		group.setUpdateInfo(loginAuthDto);

		if (group.isNew()) {
			Long groupId = super.generateId();
			group.setId(groupId);
			group.setLevel(parenGroup.getLevel() + 1);
			result = this.addUacGroup(group);
		} else {
			result = this.editUacGroup(group);
		}
		return result;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public UacGroup getById(Long id) {
		UacGroup uacGroup = uacGroupMapper.selectByPrimaryKey(id);
		if (PublicUtil.isEmpty(uacGroup)) {
			throw new UacBizException(ErrorCodeEnum.UAC10015001, id);
		}
		UacGroup parentGroup = uacGroupMapper.selectByPrimaryKey(uacGroup.getPid());
		if (parentGroup != null) {
			uacGroup.setParentGroupCode(parentGroup.getGroupCode());
			uacGroup.setParentGroupName(parentGroup.getGroupName());
		}
		// ???????????????????????????????????????
		Long provinceId = uacGroup.getProvinceId();
		Long cityId = uacGroup.getCityId();
		Long areaId = uacGroup.getAreaId();
		Long streetId = uacGroup.getStreetId();
		List<Long> addressList = Lists.newArrayList();
		if (provinceId != null) {
			addressList.add(provinceId);
		}
		if (cityId != null) {
			addressList.add(cityId);
		}
		if (areaId != null) {
			addressList.add(areaId);
		}
		if (streetId != null) {
			addressList.add(streetId);
		}
		uacGroup.setAddressList(addressList);
		return uacGroup;
	}

	private void setGroupAddress(UacGroup uacGroup) {
		List<Long> addressList = uacGroup.getAddressList();
		Preconditions.checkArgument(!PublicUtil.isEmpty(addressList), "??????????????????");
		Preconditions.checkArgument(addressList.size() >= GlobalConstant.TWO_INT, "?????????????????????");

		StringBuilder groupAddress = new StringBuilder();
		int level = 0;
		for (Long addressId : addressList) {
			// ????????????ID??????????????????
			String addressName = mdcAddressService.getAddressById(addressId).getName();
			if (level == 0) {
				uacGroup.setProvinceId(addressId);
				uacGroup.setProvinceName(addressName);
			} else if (level == 1) {
				uacGroup.setCityId(addressId);
				uacGroup.setCityName(addressName);
			} else if (level == 2) {
				uacGroup.setAreaId(addressId);
				uacGroup.setAreaName(addressName);
			} else {
				uacGroup.setStreetId(addressId);
				uacGroup.setStreetName(addressName);
			}
			groupAddress.append(addressName);
			level++;
		}
		uacGroup.setGroupAddress(groupAddress.toString());
	}

	private GroupZtreeVo buildGroupZTreeVoByGroup(UacGroup group) {
		GroupZtreeVo vo = new GroupZtreeVo();

		vo.setId(group.getId());
		vo.setpId(group.getPid());
		vo.setName(group.getGroupName());
		vo.setType(group.getType());
		vo.setStatus(group.getStatus());
		vo.setLeaf(group.getLevel());
		vo.setLevel(group.getLevel());
		vo.setGroupCode(group.getGroupCode());

		vo.setContact(group.getContact());
		vo.setContactPhone(group.getContactPhone());
		vo.setCreatedTime(group.getCreatedTime() == null ? new Date() : group.getCreatedTime());
		vo.setCreator(group.getCreator());
		vo.setGroupAddress(group.getGroupAddress());
		vo.setGroupName(group.getGroupName());

		return vo;
	}

	private List<GroupZtreeVo> buildNode(List<UacGroup> groupList, List<GroupZtreeVo> tree) {

		for (UacGroup group : groupList) {

			GroupZtreeVo groupZTreeVo = buildGroupZTreeVoByGroup(group);

			if (0L == group.getPid()) {
				groupZTreeVo.setOpen(true);
			}
			// ???????????????
			tree.add(groupZTreeVo);

			UacGroup query = new UacGroup();
			query.setPid(group.getId());

			List<UacGroup> groupChildrenList = uacGroupMapper.select(query);
			// ???????????? ????????????
			if (PublicUtil.isNotEmpty(groupChildrenList)) {
				buildNode(groupChildrenList, tree);
			}

		}
		return tree;
	}
}
