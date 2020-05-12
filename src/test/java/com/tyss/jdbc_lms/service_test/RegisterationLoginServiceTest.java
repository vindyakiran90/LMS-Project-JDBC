package com.tyss.jdbc_lms.service_test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.tyss.lmsjdbc.dto.UserBean;
import com.tyss.lmsjdbc.service.RegisterationLoginService;
import com.tyss.lmsjdbc.service.RegisterationLoginServiceImplementation;

public class RegisterationLoginServiceTest {
	
	private RegisterationLoginService registerationLoginService = new RegisterationLoginServiceImplementation();
	
	@Test
	public void loginTest() {
		String email = "samanth@gmail.com";
		String password = "Samanth@123";
		UserBean userBean = registerationLoginService.login(email, password);
		Assertions.assertNotNull(userBean);
	}
	
	@Test
	public void registerTest() {
		UserBean userBean = new UserBean();
		//userBean.setUserId(10003);
		userBean.setFirstName("Manu");
		userBean.setLastName("Manju");
		userBean.setRole("Student");
		userBean.setPhoneNo(9362149674L);
		userBean.setEmail("manu@gmail.com");
		userBean.setPassword("Kumar#123");
		boolean status =registerationLoginService.register(userBean);
		Assertions.assertTrue(status);
	}


	
}
