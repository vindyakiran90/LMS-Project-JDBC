package com.tyss.lmsjdbc.factory;

import com.tyss.lmsjdbc.dao.RegisterationLoginDAO;
import com.tyss.lmsjdbc.dao.RegisterationLoginDAOImplementation;
import com.tyss.lmsjdbc.service.RegisterationLoginService;
import com.tyss.lmsjdbc.service.RegisterationLoginServiceImplementation;

public class RegisterationLoginFactory {
	public static RegisterationLoginDAO getRegisterationLoginDAO() {
		return new RegisterationLoginDAOImplementation();
	}
	public static RegisterationLoginService getRegisterationLoginService() {
		return new RegisterationLoginServiceImplementation();
	}
}
