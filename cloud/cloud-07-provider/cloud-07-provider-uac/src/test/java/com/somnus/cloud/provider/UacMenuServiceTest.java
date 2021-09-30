package com.somnus.cloud.provider;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.somnus.cloud.provider.api.vo.MenuVo;
import com.somnus.cloud.provider.service.UacMenuService;

public class UacMenuServiceTest extends CloudUacApplicationTests {
	
	@Autowired
	private UacMenuService uacMenuService;

	@Test
	public void findMenuListByUserIdTest() {
		List<MenuVo> menuVoListByUserId = uacMenuService.getMenuVoList(1L, 1L);
		logger.info("findByLoginNameTest = {}", menuVoListByUserId);
	}
}
