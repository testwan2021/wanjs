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

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Preconditions;
import com.somnus.cloud.common.base.dto.LoginAuthDto;
import com.somnus.cloud.common.util.wrapper.WrapMapper;
import com.somnus.cloud.common.util.wrapper.Wrapper;
import com.somnus.cloud.comon.core.annotation.LogAnnotation;
import com.somnus.cloud.comon.core.support.BaseController;
import com.somnus.cloud.provider.api.vo.MenuVo;
import com.somnus.cloud.provider.model.domain.UacMenu;
import com.somnus.cloud.provider.model.dto.menu.UacEditMenuDto;
import com.somnus.cloud.provider.model.dto.menu.UacMenuStatusDto;
import com.somnus.cloud.provider.model.vo.ViewMenuVo;
import com.somnus.cloud.provider.service.UacMenuService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName: UacMenuMainController
 * @Description: 菜单主页面.
 * @author Somnus
 * @date 2018年10月17日
 */
@RestController
@RequestMapping(value = "/menu", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "Web - UacMenuMainController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacMenuMainController extends BaseController {

	@Autowired
	private UacMenuService uacMenuService;

	/**
	 * 获取菜单列表数据
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/getTree")
	@ApiOperation(httpMethod = "POST", value = "获取菜单树")
	public Wrapper<List<MenuVo>> queryMenuTreeList() {
		List<MenuVo> menuVoList = uacMenuService.getMenuVoList(getLoginAuthDto().getUserId(), null);
		return WrapMapper.success(menuVoList);
	}

	/**
	 * 编辑菜单.
	 *
	 * @param id the id
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/queryById/{id}")
	@ApiOperation(httpMethod = "POST", value = "编辑菜单")
	public Wrapper<ViewMenuVo> queryMenuVoById(@ApiParam(name = "id", value = "菜单id") @PathVariable Long id) {
		ViewMenuVo menuVo = uacMenuService.getViewVoById(id);
		return WrapMapper.success(menuVo);
	}


	/**
	 * 根据id修改菜单的禁用状态
	 *
	 * @param uacMenuStatusDto the uac menu status dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/modifyStatus")
	@ApiOperation(httpMethod = "POST", value = "修改菜单状态")
	@LogAnnotation
	public Wrapper<?> updateUacMenuStatusById(@ApiParam(name = "uacMenuStatusDto", value = "修改菜单状态Dto") @RequestBody UacMenuStatusDto uacMenuStatusDto) {
		logger.info("根据id修改菜单的禁用状态 uacMenuStatusDto={}", uacMenuStatusDto);
		LoginAuthDto loginAuthDto = getLoginAuthDto();
		uacMenuService.updateUacMenuStatusById(uacMenuStatusDto, loginAuthDto);
		return WrapMapper.ok();
	}

	/**
	 * 新增菜单.
	 *
	 * @param uacMenuAddDto the uac menu add dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/save")
	@ApiOperation(httpMethod = "POST", value = "新增菜单")
	@LogAnnotation
	public Wrapper<?> saveMenu(@ApiParam(name = "saveMenu", value = "保存菜单") @RequestBody UacEditMenuDto uacMenuAddDto) {
		UacMenu uacMenu = new UacMenu();
		LoginAuthDto loginAuthDto = getLoginAuthDto();
		BeanUtils.copyProperties(uacMenuAddDto, uacMenu);
		uacMenuService.saveUacMenu(uacMenu, loginAuthDto);
		return WrapMapper.ok();
	}

	/**
	 * 根据id删除菜单
	 *
	 * @param id the id
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/deleteById/{id}")
	@ApiOperation(httpMethod = "POST", value = "删除菜单")
	@LogAnnotation
	public Wrapper<Integer> deleteUacMenuById(@ApiParam(name = "id", value = "菜单id") @PathVariable Long id) {
		logger.info(" 根据id删除菜单 id={}", id);
		LoginAuthDto loginAuthDto = getLoginAuthDto();

		Preconditions.checkArgument(id != null, "菜单ID不能为空");

		// 判断此菜单是否有子节点
		boolean hasChild = uacMenuService.checkMenuHasChildMenu(id);
		if (hasChild) {
			return WrapMapper.wrap(Wrapper.ERROR_CODE, "此菜单含有子菜单, 请先删除子菜单");
		}

		int result = uacMenuService.deleteUacMenuById(id, loginAuthDto);
		return super.handleResult(result);
	}
}