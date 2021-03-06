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
package com.somnus.cloud.provider.web.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.somnus.cloud.common.base.dto.LoginAuthDto;
import com.somnus.cloud.common.base.enums.ErrorCodeEnum;
import com.somnus.cloud.common.util.wrapper.WrapMapper;
import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.comon.core.annotation.LogAnnotation;
import com.somnus.cloud.comon.core.annotation.ValidateAnnotation;
import com.somnus.cloud.comon.core.support.BaseController;
import com.somnus.cloud.comon.core.utils.RequestUtil;
import com.somnus.cloud.provider.api.exceptions.UacBizException;
import com.somnus.cloud.provider.model.domain.UacRole;
import com.somnus.cloud.provider.model.domain.UacRoleUser;
import com.somnus.cloud.provider.model.dto.base.ModifyStatusDto;
import com.somnus.cloud.provider.model.enums.UacRoleStatusEnum;
import com.somnus.cloud.provider.model.vo.RoleVo;
import com.somnus.cloud.provider.service.UacRoleService;
import com.somnus.cloud.provider.service.UacRoleUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName: UacRoleMainController
 * @Description: ?????????????????????.
 * @author Somnus
 * @date 2018???10???17???
 */
@RestController
@RequestMapping(value = "/role", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "Web - UacRoleMainController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacRoleMainController extends BaseController {

	@Autowired
	private UacRoleService uacRoleService;
	
	@Autowired
	private UacRoleUserService uacRoleUserService;

	/**
	 * ????????????????????????.
	 *
	 * @param role the role
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/queryRoleListWithPage")
	@ApiOperation(httpMethod = "POST", value = "??????????????????")
	public Wrapper<PageInfo<RoleVo>> queryUacRoleListWithPage(@ApiParam(name = "role", value = "????????????") @RequestBody UacRole role) {

		logger.info("??????????????????roleQuery={}", role);
		PageHelper.startPage(role.getPageNum(), role.getPageSize());
		role.setOrderBy("update_time desc");
		List<RoleVo> roleVoList = uacRoleService.queryRoleListWithPage(role);
		return WrapMapper.success(new PageInfo<>(roleVoList));
	}

	/**
	 * ??????????????????.
	 *
	 * @param id the id
	 *
	 * @return the wrapper
	 */
	@LogAnnotation
	@PostMapping(value = "/deleteRoleById/{id}")
	@ApiOperation(httpMethod = "POST", value = "????????????")
	public Wrapper<?> deleteUacRoleById(@ApiParam(name = "id", value = "??????id") @PathVariable Long id) {
		int result = uacRoleService.deleteRoleById(id);
		return super.handleResult(result);
	}

	/**
	 * ??????????????????.
	 *
	 * @param deleteIdList the delete id list
	 *
	 * @return the wrapper
	 */
	@LogAnnotation
	@PostMapping(value = "/batchDeleteByIdList")
	@ApiOperation(httpMethod = "POST", value = "??????????????????")
	public Wrapper<?> batchDeleteByIdList(@ApiParam(name = "deleteIdList", value = "??????Id") @RequestBody List<Long> deleteIdList) {
		logger.info("?????????????????? idList={}", deleteIdList);
		uacRoleService.batchDeleteByIdList(deleteIdList);
		return WrapMapper.ok();
	}

	/**
	 * ??????????????????.
	 *
	 * @param modifyStatusDto the modify status dto
	 *
	 * @return the wrapper
	 */
	@LogAnnotation
	@PostMapping(value = "/modifyRoleStatusById")
	@ApiOperation(httpMethod = "POST", value = "????????????Id??????????????????")
	public Wrapper<?> modifyUacRoleStatusById(@ApiParam(name = "modifyRoleStatusDto", value = "????????????????????????") @RequestBody ModifyStatusDto modifyStatusDto) {
		logger.info("????????????Id?????????????????? modifyStatusDto={}", modifyStatusDto);
		Long roleId = modifyStatusDto.getId();
		if (roleId == null) {
			throw new UacBizException(ErrorCodeEnum.UAC10012001);
		}

		LoginAuthDto loginAuthDto = getLoginAuthDto();
		Long userId = loginAuthDto.getUserId();

		UacRoleUser ru = uacRoleUserService.getByUserIdAndRoleId(userId, roleId);

		if (ru != null && UacRoleStatusEnum.DISABLE.getType().equals(modifyStatusDto.getStatus())) {
			throw new UacBizException(ErrorCodeEnum.UAC10012002);
		}

		UacRole uacRole = new UacRole();
		uacRole.setId(roleId);
		uacRole.setStatus(modifyStatusDto.getStatus());
		uacRole.setUpdateInfo(loginAuthDto);

		int result = uacRoleService.update(uacRole);
		return super.handleResult(result);
	}


	/**
	 * ????????????.
	 *
	 * @param role the role
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/save")
	@ApiOperation(httpMethod = "POST", value = "????????????")
	@ValidateAnnotation
	@LogAnnotation
	public Wrapper<?> save(@ApiParam(name = "role", value = "????????????") @RequestBody UacRole role) {
		LoginAuthDto loginAuthDto = RequestUtil.getLoginUser();
		uacRoleService.saveRole(role, loginAuthDto);
		return WrapMapper.ok();
	}
}
