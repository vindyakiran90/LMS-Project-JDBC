package com.tyss.jdbc_lms.dao_test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.tyss.lmsjdbc.dao.RegisterationLoginDAO;
import com.tyss.lmsjdbc.dao.RegisterationLoginDAOImplementation;
import com.tyss.lmsjdbc.dto.UserBean;

public class RegisterationLoginDAOTest {

	private RegisterationLoginDAO dao = new RegisterationLoginDAOImplementation();

	@Test
	public void loginTest() {
		String email = "samanth@gmail.com";
		String password = "Samanth@123";
		UserBean userBean = dao.login(email, password);
		Assertions.assertNotNull(userBean);
	}
	
	@Test
	public void registerTest() {
		UserBean userBean = new UserBean();
		//userBean.setUserId(10002);
		userBean.setFirstName("Manjesh");
		userBean.setLastName("Manjunath");
		userBean.setRole("Student");
		userBean.setPhoneNo(9362145874L);
		userBean.setEmail("manjesh@gmail.com");
		userBean.setPassword("Manjesh#123");
		boolean status = dao.register(userBean);
		Assertions.assertTrue(status);
	}
}
