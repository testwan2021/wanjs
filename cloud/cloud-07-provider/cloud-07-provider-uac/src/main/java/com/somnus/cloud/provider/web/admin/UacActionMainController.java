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

import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import com.somnus.cloud.common.base.dto.LoginAuthDto;
import com.somnus.cloud.common.util.wrapper.WrapMapper;
import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.comon.core.annotation.LogAnnotation;
import com.somnus.cloud.comon.core.annotation.ValidateAnnotation;
import com.somnus.cloud.comon.core.support.BaseController;
import com.somnus.cloud.comon.core.utils.RequestUtil;
import com.somnus.cloud.provider.model.domain.UacAction;
import com.somnus.cloud.provider.model.dto.action.ActionMainQueryDto;
import com.somnus.cloud.provider.model.dto.base.ModifyStatusDto;
import com.somnus.cloud.provider.model.vo.ActionVo;
import com.somnus.cloud.provider.service.UacActionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName: UacActionMainController
 * @Description: The class Uac action main controller.
 * @author Somnus
 * @date 2018???10???17???
 */
@RestController
@RequestMapping(value = "/action", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "Web - UacActionMainController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacActionMainController extends BaseController {

	@Autowired
	private UacActionService uacActionService;

	/**
	 * ????????????????????????.
	 *
	 * @param action the action
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/queryListWithPage")
	@ApiOperation(httpMethod = "POST", value = "??????????????????")
	public Wrapper<?> queryUacActionListWithPage(@ApiParam(name = "action", value = "????????????") @RequestBody ActionMainQueryDto action) {

		logger.info("??????????????????actionQuery={}", action);
		PageInfo<ActionVo> pageInfo = uacActionService.queryActionListWithPage(action);
		return WrapMapper.success(pageInfo);
	}

	/**
	 * ??????????????????.
	 *
	 * @param id the id
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/deleteActionById/{id}")
	@ApiOperation(httpMethod = "POST", value = "????????????")
	@LogAnnotation
	public Wrapper<?> deleteUacActionById(@ApiParam(name = "id", value = "??????id") @PathVariable Long id) {
		int result = uacActionService.deleteActionById(id);
		return super.handleResult(result);
	}

	/**
	 * ??????????????????.
	 *
	 * @param deleteIdList the delete id list
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/batchDeleteByIdList")
	@ApiOperation(httpMethod = "POST", value = "??????????????????")
	@LogAnnotation
	public Wrapper<?> batchDeleteByIdList(@ApiParam(name = "deleteIdList", value = "??????Id") @RequestBody List<Long> deleteIdList) {
		logger.info("?????????????????? idList={}", deleteIdList);
		uacActionService.batchDeleteByIdList(deleteIdList);
		return WrapMapper.ok();
	}


	/**
	 * ??????????????????.
	 *
	 * @param action the action
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/save")
	@ApiOperation(httpMethod = "POST", value = "????????????")
	@ValidateAnnotation
	@LogAnnotation
	public Wrapper<?> save(@ApiParam(name = "action", value = "????????????") @RequestBody UacAction action) {
		LoginAuthDto loginAuthDto = RequestUtil.getLoginUser();
		uacActionService.saveAction(action, loginAuthDto);
		return WrapMapper.ok();
	}

	/**
	 * ????????????Id??????????????????.
	 *
	 * @param modifyStatusDto the modify status dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/modifyStatus")
	@ApiOperation(httpMethod = "POST", value = "????????????Id??????????????????")
	@LogAnnotation
	public Wrapper<?> modifyActionStatus(@ApiParam(name = "modifyActionStatus", value = "??????????????????") @RequestBody ModifyStatusDto modifyStatusDto) {
		logger.info("????????????Id?????????????????? modifyStatusDto={}", modifyStatusDto);
		Long actionId = modifyStatusDto.getId();
		Preconditions.checkArgument(actionId != null, "??????ID????????????");

		UacAction uacRole = new UacAction();
		uacRole.setId(actionId);
		uacRole.setStatus(modifyStatusDto.getStatus());
		uacRole.setUpdateInfo(getLoginAuthDto());

		int result = uacActionService.update(uacRole);
		return super.handleResult(result);
	}
}
