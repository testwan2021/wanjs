package com.somnus.cloud.provider;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageInfo;
import com.somnus.cloud.provider.model.domain.UacUser;
import com.somnus.cloud.provider.service.UacUserService;

/**
 * @ClassName: UacUserServiceTest
 * @Description: The class Uac user service test.
 * @author Somnus
 * @date 2018年10月17日
 */
public class UacUserServiceTest extends CloudUacApplicationTests {
	
	@Autowired
	private UacUserService uacUserService;

	/**
	 * Find by login name test.
	 */
	@Test
	public void findByLoginNameTest() {
		List<UacUser> uacUsers = uacUserService.selectAll();
		logger.info("findByLoginNameTest = {}", uacUsers);
	}

	/**
	 * Query user list with page test.
	 */
	@Test
	public void queryUserListWithPageTest() {
		PageInfo<UacUser> pageInfo = uacUserService.queryUserListWithPage(new UacUser());
		logger.info("queryUserListWithPage = {}", pageInfo);
	}


}
