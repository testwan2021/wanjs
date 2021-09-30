package com.somnus.cloud.provider;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.somnus.cloud.common.base.constant.GlobalConstant;
import com.somnus.cloud.provider.model.domain.UacRole;
import com.somnus.cloud.provider.service.UacRoleService;

/**
 * @ClassName: UacRoleServiceTest
 * @Description: The class Uac role service test.
 * @author Somnus
 * @date 2018年10月17日
 */
public class UacRoleServiceTest extends CloudUacApplicationTests {
	
	@Autowired
	private UacRoleService uacRoleService;

	/**
	 * Find by login name test.
	 */
	@Test
	public void findByLoginNameTest() {
		UacRole admin = uacRoleService.findByRoleCode(GlobalConstant.Sys.SUPER_MANAGER_LOGIN_NAME);
		logger.info("findByLoginNameTest = {}", admin);
	}
}
