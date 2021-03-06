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
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.somnus.cloud.common.base.constant.AliyunMqTopicConstants;
import com.somnus.cloud.common.base.constant.GlobalConstant;
import com.somnus.cloud.common.base.dto.LoginAuthDto;
import com.somnus.cloud.common.base.enums.ErrorCodeEnum;
import com.somnus.cloud.common.util.HttpAesUtil;
import com.somnus.cloud.common.util.PubUtils;
import com.somnus.cloud.common.util.PublicUtil;
import com.somnus.cloud.common.util.RandomUtil;
import com.somnus.cloud.common.util.RedisKeyUtil;
import com.somnus.cloud.comon.core.enums.LogTypeEnum;
import com.somnus.cloud.comon.core.support.BaseService;
import com.somnus.cloud.comon.core.utils.RequestUtil;
import com.somnus.cloud.provider.api.dto.user.ForgetResetPasswordDto;
import com.somnus.cloud.provider.api.dto.user.LoginReqDto;
import com.somnus.cloud.provider.api.dto.user.Perm;
import com.somnus.cloud.provider.api.dto.user.UserRegisterDto;
import com.somnus.cloud.provider.api.exceptions.UacBizException;
import com.somnus.cloud.provider.api.model.domain.MqMessageData;
import com.somnus.cloud.provider.api.vo.MenuVo;
import com.somnus.cloud.provider.manager.UserManager;
import com.somnus.cloud.provider.mapper.UacActionMapper;
import com.somnus.cloud.provider.mapper.UacMenuMapper;
import com.somnus.cloud.provider.mapper.UacUserMapper;
import com.somnus.cloud.provider.mapper.UacUserMenuMapper;
import com.somnus.cloud.provider.model.domain.UacAction;
import com.somnus.cloud.provider.model.domain.UacGroupUser;
import com.somnus.cloud.provider.model.domain.UacLog;
import com.somnus.cloud.provider.model.domain.UacRole;
import com.somnus.cloud.provider.model.domain.UacRoleUser;
import com.somnus.cloud.provider.model.domain.UacUser;
import com.somnus.cloud.provider.model.domain.UacUserMenu;
import com.somnus.cloud.provider.model.dto.menu.UserMenuChildrenDto;
import com.somnus.cloud.provider.model.dto.menu.UserMenuDto;
import com.somnus.cloud.provider.model.dto.user.BindRoleDto;
import com.somnus.cloud.provider.model.dto.user.BindUserRolesDto;
import com.somnus.cloud.provider.model.dto.user.ResetLoginPwdDto;
import com.somnus.cloud.provider.model.dto.user.UserModifyPwdDto;
import com.somnus.cloud.provider.model.dto.user.UserResetPwdDto;
import com.somnus.cloud.provider.model.enums.UacEmailTemplateEnum;
import com.somnus.cloud.provider.model.enums.UacUserSourceEnum;
import com.somnus.cloud.provider.model.enums.UacUserStatusEnum;
import com.somnus.cloud.provider.model.enums.UacUserTypeEnum;
import com.somnus.cloud.provider.model.vo.UserBindRoleVo;
import com.somnus.cloud.provider.mq.producer.EmailProducer;
import com.somnus.cloud.provider.service.OpcRpcService;
import com.somnus.cloud.provider.service.RedisService;
import com.somnus.cloud.provider.service.UacActionService;
import com.somnus.cloud.provider.service.UacGroupUserService;
import com.somnus.cloud.provider.service.UacLogService;
import com.somnus.cloud.provider.service.UacMenuService;
import com.somnus.cloud.provider.service.UacRoleService;
import com.somnus.cloud.provider.service.UacRoleUserService;
import com.somnus.cloud.provider.service.UacUserMenuService;
import com.somnus.cloud.provider.service.UacUserService;
import com.somnus.cloud.provider.service.UacUserTokenService;
import com.somnus.cloud.provider.utils.Md5Util;
import com.somnus.cloud.security.core.SecurityUser;
import com.xiaoleilu.hutool.date.DateUtil;

import eu.bitwalker.useragentutils.UserAgent;

/**
 * @ClassName: UacUserServiceImpl
 * @Description: The class Uac user service.
 * @author Somnus
 * @date 2018???10???18???
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UacUserServiceImpl extends BaseService<UacUser> implements UacUserService {
	
	@Autowired
	private UacUserMapper uacUserMapper;
	
	@Autowired
	private UacMenuService uacMenuService;
	
	@Autowired
	private UacActionMapper uacActionMapper;
	
	@Autowired
	private UacMenuMapper uacMenuMapper;
	
	@Autowired
	private UacGroupUserService uacGroupUserService;
	
	@Autowired
	private UacLogService uacLogService;
	
	@Autowired
	private UacRoleService uacRoleService;
	
	@Autowired
	private UacRoleUserService uacRoleUserService;
	@Autowired
	private UacUserMenuMapper uacUserMenuMapper;
	
	@Autowired
	private UacUserMenuService uacUserMenuService;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private EmailProducer emailProducer;
	
	@Value("${cloud.auth.active-user-url}")
	private String activeUserUrl;
	
	@Autowired
	private UacActionService uacActionService;
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	private TaskExecutor taskExecutor;
	
	@Autowired
	private UacUserTokenService uacUserTokenService;
	
	@Autowired
	private OpcRpcService opcRpcService;
	
	@Autowired
	private UserManager userManager;

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public UacUser findByLoginName(String loginName) {
		logger.info("findByLoginName - ?????????????????????????????????. loginName={}", loginName);

		return uacUserMapper.findByLoginName(loginName);
	}

	@Override
	public UacUser findByMobileNo(String mobileNo) {
		return uacUserMapper.findByMobileNo(mobileNo);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public void checkUserIsCorrect(LoginReqDto loginReqDto) {
		logger.info("?????????" + loginReqDto.getLoginName() + "?????????????????????......");

		Map<String, String> loginNamePwdMap = Maps.newHashMap();
		loginNamePwdMap.put("loginName", loginReqDto.getLoginName());
		loginNamePwdMap.put("loginPwd", loginReqDto.getLoginPwd());

		UacUser uacUser = uacUserMapper.findByLoginNameAndLoginPwd(loginNamePwdMap);
		if (PublicUtil.isEmpty(uacUser)) {
			logger.info("?????????" + loginReqDto.getLoginName() + "?????????????????????");
			throw new UacBizException(ErrorCodeEnum.UAC10011002, loginReqDto.getLoginName());
		}

		logger.info("?????????" + loginReqDto.getLoginName() + "?????????????????????");
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<Perm> getAllPerms() {
		logger.info("?????????????????????...");
		return uacActionMapper.findAllPerms();
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<String> getUserPerms(Long userId) {
		logger.info("????????????????????????userId={}", userId);

		//1.????????????????????????
		List<String> menuPermList = uacMenuMapper.findMenuCodeListByUserId(userId);
		//2.???????????????????????????
		List<String> buttonPermList = uacActionMapper.findActionCodeListByUserId(userId);
		//3.????????????????????????????????????????????????
		Set<String> set = Sets.newHashSet();
		set.addAll(menuPermList);
		set.addAll(buttonPermList);
		return new ArrayList<>(set);
	}

	@Override
	public int updateUser(UacUser uacUser) {
		logger.info("?????????????????? uacUser={}", uacUser);
		int updateResult = uacUserMapper.updateByPrimaryKeySelective(uacUser);
		if (updateResult < 1) {
			logger.info("????????? {} ???????????????????????????", uacUser.getId());
		} else {
			logger.info("????????? {} ???????????????????????????", uacUser.getId());
		}
		return updateResult;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public PageInfo<UacUser> queryUserListWithPage(UacUser uacUser) {
		PageHelper.startPage(uacUser.getPageNum(), uacUser.getPageSize());
		uacUser.setOrderBy("u.update_time desc");
		List<UacUser> uacUserList = uacUserMapper.selectUserList(uacUser);
		return new PageInfo<>(uacUserList);
	}

	@Override
	public int deleteUserById(Long userId) {
		return 0;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public UacUser findUserInfoByUserId(Long userId) {
		return uacUserMapper.selectUserInfoByUserId(userId);
	}

	@Override
	public void saveUacUser(UacUser user, LoginAuthDto loginAuthDto) {

		String loginName = user.getLoginName();

		Preconditions.checkArgument(!StringUtils.isEmpty(loginName), "?????????????????????");
		Preconditions.checkArgument(user.getGroupId() != null, "??????????????????");

		user.setUpdateInfo(loginAuthDto);
		if (user.isNew()) {
			String loginPwd = user.getLoginPwd();
			Preconditions.checkArgument(!StringUtils.isEmpty(loginPwd), ErrorCodeEnum.UAC10011014.getMsg());

			user.setLoginPwd(Md5Util.encrypt(loginPwd));
			// ???????????????????????????
			UacUser query = new UacUser();
			query.setLoginName(loginName);
			int count = uacUserMapper.selectCount(query);
			if (count > 0) {
				throw new UacBizException(ErrorCodeEnum.UAC10011025, loginName);
			}
			Long userId = super.generateId();
			// 1.??????????????????
			user.setId(userId);
			user.setLoginName(loginName);
			user.setType(UacUserTypeEnum.OPERATE.getKey());
			user.setUserSource(UacUserSourceEnum.INSERT.getKey());
			// TODO ????????????????????????
			uacUserMapper.insertSelective(user);

			// 2.??????????????????
			UacGroupUser groupUser = new UacGroupUser();
			groupUser.setGroupId(user.getGroupId());
			groupUser.setUserId(userId);
			uacGroupUserService.save(groupUser);
		} else {
			UacUser uacUser = uacUserMapper.selectByPrimaryKey(user.getId());
			Preconditions.checkArgument(uacUser != null, "???????????????");
			// 1.??????????????????
			int updateInt = uacUserMapper.updateUacUser(user);
			if (updateInt < 1) {
				throw new UacBizException(ErrorCodeEnum.UAC10011026, user.getId());
			}
			// 2.??????????????????
			UacGroupUser uacGroupUser = uacGroupUserService.queryByUserId(user.getId());
			if (uacGroupUser == null) {
				// ??????????????????
				UacGroupUser groupUser = new UacGroupUser();
				groupUser.setGroupId(user.getGroupId());
				groupUser.setUserId(user.getId());
				uacGroupUserService.save(groupUser);
			} else {
				//????????????
				UacGroupUser groupUser = new UacGroupUser();
				groupUser.setUserId(user.getId());
				groupUser.setGroupId(user.getGroupId());
				uacGroupUserService.updateByUserId(groupUser);
			}
		}

	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<UacLog> queryUserLogListWithUserId(Long userId) {
		if (PublicUtil.isEmpty(userId)) {
			throw new UacBizException(ErrorCodeEnum.UAC10011001);
		}
		return uacLogService.selectUserLogListByUserId(userId);
	}

	@Override
	public int modifyUserStatusById(UacUser uacUser, LoginAuthDto authResDto) {
		Long loginUserId = authResDto.getUserId();
		Long userId = uacUser.getId();
		if (loginUserId.equals(userId)) {
			throw new UacBizException(ErrorCodeEnum.UAC10011023);
		}
		UacUser u = uacUserMapper.selectByPrimaryKey(userId);
		if (u == null) {
			throw new UacBizException(ErrorCodeEnum.UAC10011011, userId);
		}

		// ??????????????????????????????????????????
		uacUser.setVersion(u.getVersion() + 1);
		uacUser.setUpdateInfo(authResDto);
		return uacUserMapper.updateByPrimaryKeySelective(uacUser);
	}

	@Override
	public void bindUserRoles(BindUserRolesDto bindUserRolesDto, LoginAuthDto authResDto) {

		if (bindUserRolesDto == null) {
			logger.error("??????????????????");
			throw new IllegalArgumentException("??????????????????");
		}

		Long operUserId = bindUserRolesDto.getUserId();
		Long loginUserId = authResDto.getUserId();
		List<Long> roleIdList = bindUserRolesDto.getRoleIdList();

		if (null == operUserId) {
			throw new UacBizException(ErrorCodeEnum.UAC10011001);
		}

		// ????????????????????????admin??????
		if (Objects.equals(operUserId, GlobalConstant.Sys.SUPER_MANAGER_USER_ID)) {
			throw new UacBizException(ErrorCodeEnum.UAC10011034);
		}

		UacUser user = this.queryByUserId(operUserId);

		if (user == null) {
			logger.error("????????????????????? operUserId={}", operUserId);
			throw new UacBizException(ErrorCodeEnum.UAC10011003, operUserId);
		}

		if (PublicUtil.isNotEmpty(roleIdList) && roleIdList.contains(GlobalConstant.Sys.SUPER_MANAGER_ROLE_ID)) {
			logger.error("??????????????????????????? userId={}", loginUserId);
			throw new UacBizException(ErrorCodeEnum.UAC10011023);
		}

		// 1. ????????????????????????????????????(??????????????????????????????)
		List<UacRoleUser> userRoles = uacRoleUserService.listByUserId(operUserId);

		if (PublicUtil.isNotEmpty(userRoles)) {
			uacRoleUserService.deleteByUserId(operUserId);
		}

		// ???????????????????????????
		final UacUser updateUser = new UacUser();
		updateUser.setId(operUserId);
		updateUser.setUpdateInfo(authResDto);
		uacUserMapper.updateUacUser(updateUser);

		if (PublicUtil.isEmpty(roleIdList)) {
			// ???????????????????????????????????????
			logger.info("??????????????????");
			return;
		}

		// ??????????????????
		for (Long roleId : roleIdList) {
			UacRole uacRole = uacRoleService.getRoleById(roleId);
			if (uacRole == null) {
				logger.error("????????????????????????. roleId={}", roleId);
				throw new UacBizException(ErrorCodeEnum.UAC10012008, roleId);
			}
			uacRoleUserService.saveRoleUser(operUserId, roleId);
		}
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<UserMenuDto> queryUserMenuDtoData(LoginAuthDto authResDto) {
		// ??????????????????
		List<UserMenuDto> list = Lists.newArrayList();
		List<MenuVo> menuList; // ?????????????????????????????????
		Long userId = authResDto.getUserId();
		List<Long> ownerMenuIdList = Lists.newArrayList();
		Preconditions.checkArgument(!PubUtils.isNull(authResDto, userId), "???????????????");

		// ?????????????????????????????????Id??????
		UacUserMenu query = new UacUserMenu();
		query.setUserId(userId);
		List<UacUserMenu> userMenus = uacUserMenuMapper.select(query);
		for (UacUserMenu userMenu : userMenus) {
			if (PublicUtil.isEmpty(userMenu.getUserId())) {
				continue;
			}
			ownerMenuIdList.add(userMenu.getMenuId());
		}


		//???????????????admin?????? ??????????????????????????????, ???????????????????????????
		menuList = uacMenuService.getMenuVoList(userId, 1L);
		for (MenuVo menuVo : menuList) {
			// ????????????
			UserMenuDto userMenuDto = new UserMenuDto();
			userMenuDto.setFistMenuIcon(menuVo.getIcon());
			userMenuDto.setFistMenuName(menuVo.getMenuName());
			List<MenuVo> sub2Menu = menuVo.getSubMenu();
			List<UserMenuChildrenDto> children = Lists.newArrayList();
			for (MenuVo vo2 : sub2Menu) {
				// ???????????? ??????????????????????????????
				if (!vo2.isHasMenu()) {
					UserMenuChildrenDto userMenuChildrenDto = new UserMenuChildrenDto(vo2);
					//???????????????????????????, ?????????true, ????????????false
					if (ownerMenuIdList.contains(vo2.getId())) {
						userMenuChildrenDto.setChecked(true);
					}
					children.add(userMenuChildrenDto);
					continue;
				}
				// ???????????????????????????????????????????????????
				List<MenuVo> sub3Menu = vo2.getSubMenu();
				for (MenuVo vo3 : sub3Menu) {
					UserMenuChildrenDto dto = new UserMenuChildrenDto(vo3);
					//???????????????????????????, ?????????true, ????????????false
					if (ownerMenuIdList.contains(vo3.getId())) {
						dto.setChecked(true);
					}
					children.add(dto);
				}
			}
			userMenuDto.setChildren(children);
			list.add(userMenuDto);
		}

		return list;
	}

	@Override
	public int bindUserMenus(List<Long> menuIdList, LoginAuthDto authResDto) {
		// 1.1 ??????menuIdList is null ??????????????????
		// 1.2 ??????????????????(???????????????????????????????????????)
		// 1.3 ?????????????????????????????????,???????????????????????????????????????
		// 2 ????????????,??????????????????, ???????????????????????????????????????????????????????????????????????????????????????,?????????????????????,?????????????????????????????????????????????,?????????????????????
		Long userId = authResDto.getUserId();
		List<UacUserMenu> uacUserMenuList = Lists.newArrayList();

		UacUserMenu uacUserMenu = new UacUserMenu();
		uacUserMenu.setUserId(userId);

		if (PublicUtil.isEmpty(menuIdList)) {
			return deleteUserMenuList(uacUserMenu);
		}

		// ????????????
		this.checkUserMenuList(menuIdList, authResDto, uacUserMenuList);

		// ???????????????
		return handleUserMenuList(uacUserMenuList, uacUserMenu);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public UacUser queryByUserId(Long userId) {
		logger.info("queryByUserId - ????????????????????????????????????. userId={}", userId);
		UacUser uacUser = uacUserMapper.selectByPrimaryKey(userId);
		if (PublicUtil.isNotEmpty(uacUser)) {
			uacUser.setLoginPwd("");
		}
		return uacUser;
	}

	@Override
	public int userModifyPwd(UserModifyPwdDto userModifyPwdDto, LoginAuthDto authResDto) {
		String loginName = userModifyPwdDto.getLoginName();
		String oldPassword = userModifyPwdDto.getOldPassword();
		String newPassword = userModifyPwdDto.getNewPassword();
		String confirmPwd = userModifyPwdDto.getConfirmPwd();

		Preconditions.checkArgument(!PublicUtil.isEmpty(loginName), ErrorCodeEnum.UAC10011007.getMsg());
		Preconditions.checkArgument(!PublicUtil.isEmpty(oldPassword), "????????????????????????");
		Preconditions.checkArgument(!PublicUtil.isEmpty(newPassword), "?????????????????????");
		Preconditions.checkArgument(!PublicUtil.isEmpty(confirmPwd), ErrorCodeEnum.UAC10011009.getMsg());
		Preconditions.checkArgument(newPassword.equals(confirmPwd), "?????????????????????, ??????????????????");


		UacUser user = uacUserMapper.findByLoginName(loginName);
		if (PublicUtil.isEmpty(user)) {
			throw new UacBizException(ErrorCodeEnum.UAC10011002, loginName);
		}

		String oldPwd = user.getLoginPwd();
		String newEncrypt = Md5Util.encrypt(newPassword);

		if (!Md5Util.matches(oldPassword, oldPwd)) {
			throw new UacBizException(ErrorCodeEnum.UAC10011035);
		}

		UacUser uacUser = new UacUser();
		if (Md5Util.matches(newPassword, oldPwd)) {
			throw new UacBizException(ErrorCodeEnum.UAC10011036);
		}

		uacUser.setLoginPwd(Md5Util.encrypt(newPassword));
		uacUser.setId(user.getId());
		uacUser.setLoginPwd(newEncrypt);
		// ??????????????????????????????
		uacUser.setIsChangedPwd(Short.valueOf("1"));
		uacUser.setUpdateInfo(authResDto);

		return uacUserMapper.updateByPrimaryKeySelective(uacUser);

		// TODO ?????????????????????????????????
	}

	@Override
	public int userResetPwd(UserResetPwdDto userResetPwdDto) {
		String mobileNo = userResetPwdDto.getMobileNo();
		String newPassword = userResetPwdDto.getNewPassword();
		String confirmPwd = userResetPwdDto.getConfirmPwd();

		Preconditions.checkArgument(!StringUtils.isEmpty(mobileNo), "????????????????????????");
		Preconditions.checkArgument(!StringUtils.isEmpty(newPassword), "?????????????????????");
		Preconditions.checkArgument(!StringUtils.isEmpty(confirmPwd), ErrorCodeEnum.UAC10011009.getMsg());
		Preconditions.checkArgument(newPassword.equals(confirmPwd), "?????????????????????");

		UacUser query = new UacUser();
		query.setMobileNo(mobileNo);
		UacUser user = uacUserMapper.selectOne(query);
		if (user == null) {
			throw new UacBizException(ErrorCodeEnum.UAC10011027, mobileNo);
		}

		UacUser uacUser = new UacUser();
		uacUser.setLoginPwd(Md5Util.encrypt(newPassword));
		uacUser.setId(user.getId());

		// ??????????????????????????????????????????
		uacUser.setVersion(user.getVersion() + 1);
		uacUser.setLastOperator(user.getLoginName());
		uacUser.setLastOperatorId(user.getId());
		uacUser.setUpdateTime(new Date());

		return uacUserMapper.updateByPrimaryKeySelective(uacUser);
	}

	@Override
	public void register(UserRegisterDto registerDto) {
		// ??????????????????
		validateRegisterInfo(registerDto);
		String mobileNo = registerDto.getMobileNo();
		String email = registerDto.getEmail();
		Date row = new Date();
		String salt = String.valueOf(generateId());
		// ??????????????????
		long id = generateId();
		UacUser uacUser = new UacUser();
		uacUser.setLoginName(registerDto.getLoginName());
		uacUser.setSalt(salt);
		uacUser.setLoginPwd(Md5Util.encrypt(registerDto.getLoginPwd()));
		uacUser.setMobileNo(mobileNo);
		uacUser.setStatus(UacUserStatusEnum.DISABLE.getKey());
		uacUser.setUserSource(UacUserSourceEnum.REGISTER.getKey());
		uacUser.setCreatedTime(row);
		uacUser.setUpdateTime(row);
		uacUser.setEmail(email);
		uacUser.setId(id);
		uacUser.setCreatorId(id);
		uacUser.setCreator(registerDto.getLoginName());
		uacUser.setLastOperatorId(id);
		uacUser.setUserName(registerDto.getLoginName());
		uacUser.setLastOperator(registerDto.getLoginName());

		// ??????????????????
		String activeToken = PubUtils.uuid() + super.generateId();
		redisService.setKey(RedisKeyUtil.getActiveUserKey(activeToken), email, 1, TimeUnit.DAYS);

		Map<String, Object> param = Maps.newHashMap();
		param.put("loginName", registerDto.getLoginName());
		param.put("email", registerDto.getEmail());
		param.put("activeUserUrl", activeUserUrl + activeToken);
		param.put("dateTime", DateUtil.formatDateTime(new Date()));

		Set<String> to = Sets.newHashSet();
		to.add(registerDto.getEmail());

		MqMessageData mqMessageData = emailProducer.sendEmailMq(to, UacEmailTemplateEnum.ACTIVE_USER, AliyunMqTopicConstants.MqTagEnum.ACTIVE_USER, param);
		userManager.register(mqMessageData, uacUser);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public boolean checkLoginName(String loginName) {
		Preconditions.checkArgument(!StringUtils.isEmpty(loginName), ErrorCodeEnum.UAC10011007.getMsg());

		UacUser uacUser = new UacUser();
		uacUser.setLoginName(loginName);
		int result = 1;
		try {
			result = uacUserMapper.selectCount(uacUser);
		} catch (Exception e) {
			logger.error(" ???????????????????????????,????????????={}", e.getMessage(), e);
		}
		return result < 1;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public boolean checkEmail(String email) {
		Preconditions.checkArgument(!StringUtils.isEmpty(email), "email????????????");

		UacUser uacUser = new UacUser();
		uacUser.setEmail(email);
		int result = 1;
		try {
			result = uacUserMapper.selectCount(uacUser);
		} catch (Exception e) {
			logger.error(" ??????email????????????,????????????={}", e.getMessage(), e);
		}
		return result < 1;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public boolean checkMobileNo(String mobileNo) {
		Preconditions.checkArgument(!StringUtils.isEmpty(mobileNo), "????????????????????????");

		UacUser uacUser = new UacUser();
		uacUser.setMobileNo(mobileNo);
		int result = 1;
		try {
			result = uacUserMapper.selectCount(uacUser);
		} catch (Exception e) {
			logger.error(" ??????email????????????,????????????={}", e.getMessage(), e);
		}
		return result < 1;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public int countUserByLoginNameAndEmail(String loginName, String email) {
		Preconditions.checkArgument(!StringUtils.isEmpty(loginName), ErrorCodeEnum.UAC10011007.getMsg());
		Preconditions.checkArgument(!StringUtils.isEmpty(email), ErrorCodeEnum.UAC10011018.getMsg());

		UacUser uacUser = new UacUser();
		uacUser.setLoginName(loginName);
		uacUser.setEmail(email);

		return uacUserMapper.selectCount(uacUser);
	}

	@Override
	public int userEmailResetPwd(ForgetResetPasswordDto forgetResetPasswordDto) {
		this.validateEmailResetPwd(forgetResetPasswordDto);
		String loginName = forgetResetPasswordDto.getLoginName();

		UacUser user = this.findByLoginName(loginName);
		UacUser uacUser = new UacUser();
		uacUser.setLoginPwd(Md5Util.encrypt(forgetResetPasswordDto.getLoginPwd()));
		uacUser.setId(user.getId());
		LoginAuthDto authUser = new LoginAuthDto();
		authUser.setLoginName(loginName);
		authUser.setUserId(user.getId());
		authUser.setUserName(user.getUserName());
		uacUser.setUpdateInfo(authUser);

		return uacUserMapper.updateByPrimaryKeySelective(uacUser);


	}

	@Override
	public void modifyUserEmail(String email, String emailCode, LoginAuthDto loginAuthDto) {
		// ??????????????????
		Preconditions.checkArgument(!StringUtils.isEmpty(email), ErrorCodeEnum.UAC10011018.getMsg());
		Preconditions.checkArgument(PubUtils.isEmail(email), "?????????????????????");

		UacUser uacUser = new UacUser();
		uacUser.setId(loginAuthDto.getUserId());
		uacUser.setEmail(email);
		uacUser.setUpdateInfo(loginAuthDto);
		int result = this.updateUser(uacUser);
		if (result < 1) {
			throw new UacBizException(ErrorCodeEnum.UAC10011037, loginAuthDto.getUserId());
		}
	}

	@Override
	public void resetLoginPwd(Long userId, LoginAuthDto loginAuthDto) {
		if (userId == null) {
			throw new UacBizException(ErrorCodeEnum.UAC10011001);
		}
		UacUser uacUser = this.queryByUserId(userId);
		if (uacUser == null) {
			logger.error("???????????????. userId={}", userId);
			throw new UacBizException(ErrorCodeEnum.UAC10011003, userId);
		}

		Preconditions.checkArgument(!StringUtils.isEmpty(uacUser.getEmail()), "????????????????????????");

		String newLoginPwd = RandomUtil.createComplexCode(8);
		UacUser update = new UacUser();
		update.setId(uacUser.getId());
		update.setLoginPwd(Md5Util.encrypt(newLoginPwd));
		short isChangedPwd = 0;
		update.setIsChangedPwd(isChangedPwd);

		Map<String, Object> param = Maps.newHashMap();
		param.put("loginName", uacUser.getLoginName());
		param.put("newLoginPwd", newLoginPwd);
		param.put("dateTime", DateUtil.formatDateTime(new Date()));

		Set<String> to = Sets.newHashSet();
		to.add(uacUser.getEmail());

		final MqMessageData mqMessageData = emailProducer.sendEmailMq(to, UacEmailTemplateEnum.RESET_LOGIN_PWD, AliyunMqTopicConstants.MqTagEnum.RESET_LOGIN_PWD, param);
		userManager.resetLoginPwd(mqMessageData, update);


	}

	@Override
	public void resetLoginPwd(ResetLoginPwdDto resetLoginPwdDto) {
		String confirmPwd = resetLoginPwdDto.getConfirmPwd();
		String newPassword = resetLoginPwdDto.getNewPassword();
		String resetPwdKey = resetLoginPwdDto.getResetPwdKey();

		Preconditions.checkArgument(!StringUtils.isEmpty(newPassword), ErrorCodeEnum.UAC10011014.getMsg());
		Preconditions.checkArgument(!StringUtils.isEmpty(confirmPwd), ErrorCodeEnum.UAC10011009.getMsg());
		Preconditions.checkArgument(!StringUtils.isEmpty(resetPwdKey), "???????????????");
		Preconditions.checkArgument(newPassword.equals(confirmPwd), "???????????????????????????");

		String resetPwdTokenKey = RedisKeyUtil.getResetPwdTokenKey(resetPwdKey);
		UacUser uacUser = (UacUser) redisTemplate.opsForValue().get(resetPwdTokenKey);

		if (StringUtils.isEmpty(uacUser)) {
			throw new UacBizException(ErrorCodeEnum.UAC10011028);
		}

		LoginAuthDto loginAuthDto = new LoginAuthDto();
		loginAuthDto.setUserName(uacUser.getUserName());
		loginAuthDto.setLoginName(uacUser.getLoginName());
		loginAuthDto.setUserId(uacUser.getId());

		UacUser update = new UacUser();
		String salt = generateId() + "";
		update.setLoginPwd(Md5Util.encrypt(newPassword));
		update.setSalt(salt);
		update.setId(uacUser.getId());
		// ??????????????????????????????
		update.setIsChangedPwd((Short.valueOf("1")));
		update.setUpdateInfo(loginAuthDto);

		int result = uacUserMapper.updateByPrimaryKeySelective(update);
		if (result < 1) {
			throw new UacBizException(ErrorCodeEnum.UAC10011029);
		}
		redisTemplate.delete(resetPwdTokenKey);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public UserBindRoleVo getUserBindRoleDto(Long userId) {
		UserBindRoleVo userBindRoleVo = new UserBindRoleVo();
		Set<Long> alreadyBindRoleIdSet = Sets.newHashSet();
		UacUser uacUser = this.queryByUserId(userId);
		if (uacUser == null) {
			logger.error("?????????userId={}, ?????????", userId);
			throw new UacBizException(ErrorCodeEnum.UAC10011003, userId);
		}

		// ????????????????????????????????????????????????
		List<BindRoleDto> bindRoleDtoList = uacUserMapper.selectAllNeedBindRole(GlobalConstant.Sys.SUPER_MANAGER_ROLE_ID);
		// ??????????????????????????????
		List<UacRoleUser> setAlreadyBindRoleSet = uacRoleUserService.listByUserId(userId);

		Set<BindRoleDto> allUserSet = new HashSet<>(bindRoleDtoList);

		for (UacRoleUser uacRoleUser : setAlreadyBindRoleSet) {
			alreadyBindRoleIdSet.add(uacRoleUser.getRoleId());
		}

		userBindRoleVo.setAllRoleSet(allUserSet);
		userBindRoleVo.setAlreadyBindRoleIdSet(alreadyBindRoleIdSet);

		return userBindRoleVo;
	}

	@Override
	public void activeUser(String activeUserToken) {
		Preconditions.checkArgument(!StringUtils.isEmpty(activeUserToken), "??????????????????");

		String activeUserKey = RedisKeyUtil.getActiveUserKey(activeUserToken);

		String email = redisService.getKey(activeUserKey);

		if (StringUtils.isEmpty(email)) {
			throw new UacBizException(ErrorCodeEnum.UAC10011030);
		}
		// ??????????????????, ??????????????????
		UacUser uacUser = new UacUser();
		uacUser.setEmail(email);

		uacUser = uacUserMapper.selectOne(uacUser);
		if (uacUser == null) {
			logger.error("?????????????????????. email={}", email);
			throw new UacBizException(ErrorCodeEnum.UAC10011004, email);
		}

		UacUser update = new UacUser();
		update.setId(uacUser.getId());
		update.setStatus(UacUserStatusEnum.ENABLE.getKey());
		LoginAuthDto loginAuthDto = new LoginAuthDto();
		loginAuthDto.setUserId(uacUser.getId());
		loginAuthDto.setUserName(uacUser.getLoginName());
		loginAuthDto.setLoginName(uacUser.getLoginName());
		update.setUpdateInfo(loginAuthDto);

		UacUser user = this.queryByUserId(uacUser.getId());

		Map<String, Object> param = Maps.newHashMap();
		param.put("loginName", user.getLoginName());
		param.put("dateTime", DateUtil.formatDateTime(new Date()));

		Set<String> to = Sets.newHashSet();
		to.add(user.getEmail());


		MqMessageData mqMessageData = emailProducer.sendEmailMq(to, UacEmailTemplateEnum.ACTIVE_USER_SUCCESS, AliyunMqTopicConstants.MqTagEnum.ACTIVE_USER_SUCCESS, param);
		userManager.activeUser(mqMessageData, update, activeUserKey);
	}

	@Override
	public Collection<GrantedAuthority> loadUserAuthorities(Long userId) {

		List<UacAction> ownAuthList = uacActionService.getOwnActionListByUserId(userId);
		List<GrantedAuthority> authList = Lists.newArrayList();
		for (UacAction action : ownAuthList) {
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(action.getUrl());
			authList.add(grantedAuthority);
		}
		return authList;
	}

	@Override
	public void handlerLoginData(OAuth2AccessToken token, final SecurityUser principal, HttpServletRequest request) {

		final UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
		//???????????????????????????
		final String os = userAgent.getOperatingSystem().getName();
		//????????????????????????
		final String browser = userAgent.getBrowser().getName();
		final String remoteAddr = RequestUtil.getRemoteAddr(request);
		// ??????IP??????????????????
		final String remoteLocation = opcRpcService.getLocationById(remoteAddr);
		final String requestURI = request.getRequestURI();

		UacUser uacUser = new UacUser();
		Long userId = principal.getUserId();
		uacUser.setLastLoginIp(remoteAddr);
		uacUser.setId(userId);
		uacUser.setLastLoginTime(new Date());
		uacUser.setLastLoginLocation(remoteLocation);
		LoginAuthDto loginAuthDto = new LoginAuthDto(userId, principal.getLoginName(), principal.getNickName(), principal.getGroupId(), principal.getGroupName());
		// ??????token??????
		String accessToken = token.getValue();
		String refreshToken = token.getRefreshToken().getValue();
		uacUserTokenService.saveUserToken(accessToken, refreshToken, loginAuthDto, request);
		// ????????????????????????
		taskExecutor.execute(() -> this.updateUser(uacUser));
		// ??????????????????

		UacLog log = new UacLog();
		log.setGroupId(principal.getGroupId());
		log.setGroupName(principal.getGroupName());
		log.setIp(remoteAddr);
		log.setLocation(remoteLocation);
		log.setOs(os);
		log.setBrowser(browser);
		log.setRequestUrl(requestURI);
		log.setLogType(LogTypeEnum.LOGIN_LOG.getType());
		log.setLogName(LogTypeEnum.LOGIN_LOG.getName());

		taskExecutor.execute(() -> uacLogService.saveLog(log, loginAuthDto));
	}

	@Override
	public UacUser findUserInfoByLoginName(final String loginName) {
		return uacUserMapper.findUserInfoByLoginName(loginName);
	}

	private void validateEmailResetPwd(ForgetResetPasswordDto forgetResetPasswordDto) {
		//??????????????????
		String forgetToken = forgetResetPasswordDto.getForgetToken();
		String loginPwd = forgetResetPasswordDto.getLoginPwd();
		String loginName = forgetResetPasswordDto.getLoginName();
		String email = forgetResetPasswordDto.getEmail();
		String emailCode = forgetResetPasswordDto.getEmailCode();

		Preconditions.checkArgument(!StringUtils.isEmpty(loginName), ErrorCodeEnum.UAC10011007.getMsg());
		Preconditions.checkArgument(!StringUtils.isEmpty(email), ErrorCodeEnum.UAC10011018.getMsg());
		Preconditions.checkArgument(!StringUtils.isEmpty(loginPwd), ErrorCodeEnum.UAC10011014.getMsg());
		Preconditions.checkArgument(!StringUtils.isEmpty(forgetToken), "????????????");
		Preconditions.checkArgument(!StringUtils.isEmpty(emailCode), "?????????????????????");

		// ??????token
		String key = RedisKeyUtil.getResetPwdTokenKey(email);
		String forgetKey = redisService.getKey(key);

		try {
			HttpAesUtil.decrypt(forgetToken, forgetKey, false, forgetKey);
		} catch (Exception e) {
			throw new UacBizException(ErrorCodeEnum.UAC10011031);
		}

		int count = this.countUserByLoginNameAndEmail(loginName, email);
		// ??????token
		if (count < 1) {
			throw new UacBizException(ErrorCodeEnum.UAC10011032, loginName, email);
		}
	}

	/**
	 * ?????????????????????
	 */
	private int deleteUserMenuList(UacUserMenu uacUserMenu) {
		int selCount = uacUserMenuMapper.selectCount(uacUserMenu);
		// ????????????????????????, ???????????????????????????
		if (selCount < 1) {
			return 1;
		}

		int delCount = uacUserMenuMapper.delete(uacUserMenu);
		if (delCount < selCount) {
			logger.error("????????????????????????????????? delCount = {} selCount = {}", delCount, selCount);
			throw new UacBizException(ErrorCodeEnum.UAC10011033);
		}

		return delCount;
	}

	/**
	 * ????????????????????????
	 *
	 * @param menuIdList      ?????????????????????Id??????
	 * @param authResDto      ????????????
	 * @param uacUserMenuList ?????????????????????
	 */
	private void checkUserMenuList(List<Long> menuIdList, LoginAuthDto authResDto, List<UacUserMenu> uacUserMenuList) {

		List<MenuVo> currentUserMenuVoList = uacMenuService.findAllMenuListByAuthResDto(authResDto);
		List<Long> currentUserMenuIdList = Lists.newArrayList();
		for (MenuVo menuVo : currentUserMenuVoList) {
			Long menuId = menuVo.getId();

			if (PublicUtil.isEmpty(menuId)) {
				continue;
			}
			currentUserMenuIdList.add(menuId);
		}

		Preconditions.checkArgument(currentUserMenuIdList.containsAll(menuIdList), "????????????");

		// TODO ??????????????????????????????????????????
		for (Long menuId : menuIdList) {
			if (uacMenuService.checkMenuHasChildMenu(menuId)) {
				logger.error(" ??????????????????????????? menuId= {}", menuId);
				throw new UacBizException(ErrorCodeEnum.UAC10013010, menuId);
			}
			UacUserMenu uacUserMenu = new UacUserMenu();
			uacUserMenu.setUserId(authResDto.getUserId());
			uacUserMenu.setMenuId(menuId);
			uacUserMenuList.add(uacUserMenu);
		}
	}

	private int handleUserMenuList(List<UacUserMenu> uacUserMenuList, UacUserMenu uacUserMenu) {
		// ????????????????????????????????????
		deleteUserMenuList(uacUserMenu);

		return uacUserMenuService.batchSave(uacUserMenuList);
	}

	private void validateRegisterInfo(UserRegisterDto registerDto) {
		String mobileNo = registerDto.getMobileNo();

		Preconditions.checkArgument(!StringUtils.isEmpty(registerDto.getLoginName()), ErrorCodeEnum.UAC10011007.getMsg());
		Preconditions.checkArgument(!StringUtils.isEmpty(registerDto.getEmail()), ErrorCodeEnum.UAC10011018.getMsg());
		Preconditions.checkArgument(!StringUtils.isEmpty(mobileNo), "?????????????????????");
		Preconditions.checkArgument(!StringUtils.isEmpty(registerDto.getLoginPwd()), ErrorCodeEnum.UAC10011014.getMsg());
		Preconditions.checkArgument(!StringUtils.isEmpty(registerDto.getConfirmPwd()), ErrorCodeEnum.UAC10011009.getMsg());
		Preconditions.checkArgument(!StringUtils.isEmpty(registerDto.getRegisterSource()), "??????????????????");
		Preconditions.checkArgument(registerDto.getLoginPwd().equals(registerDto.getConfirmPwd()), "?????????????????????");

		UacUser uacUser = new UacUser();
		uacUser.setLoginName(registerDto.getLoginName());
		int count = uacUserMapper.selectCount(uacUser);
		if (count > 0) {
			throw new UacBizException(ErrorCodeEnum.UAC10011012);
		}

		uacUser = new UacUser();
		uacUser.setMobileNo(registerDto.getMobileNo());
		count = uacUserMapper.selectCount(uacUser);
		if (count > 0) {
			throw new UacBizException(ErrorCodeEnum.UAC10011013);
		}

		uacUser = new UacUser();
		uacUser.setEmail(registerDto.getEmail());
		count = uacUserMapper.selectCount(uacUser);
		if (count > 0) {
			throw new UacBizException(ErrorCodeEnum.UAC10011019);
		}

	}
}
