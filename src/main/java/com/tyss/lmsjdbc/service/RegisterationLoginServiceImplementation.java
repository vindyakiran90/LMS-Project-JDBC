package com.tyss.lmsjdbc.service;

import com.tyss.lmsjdbc.dao.RegisterationLoginDAO;
import com.tyss.lmsjdbc.dto.UserBean;
import com.tyss.lmsjdbc.factory.RegisterationLoginFactory;

public class RegisterationLoginServiceImplementation implements RegisterationLoginService {

	RegisterationLoginDAO dao = RegisterationLoginFactory.getRegisterationLoginDAO();
	@Override
	public UserBean login(String email, String password) {
		return dao.login(email, password);
	}

	@Override
	public boolean register(UserBean bean) {
		return dao.register(bean);
	}

}
