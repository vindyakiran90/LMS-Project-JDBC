package com.tyss.lmsjdbc.factory;

import com.tyss.lmsjdbc.dao.AdminDAO;
import com.tyss.lmsjdbc.dao.AdminDAOImplementation;
import com.tyss.lmsjdbc.service.AdminService;
import com.tyss.lmsjdbc.service.AdminServiceImplementation;

public class AdminFactory {
	public static AdminDAO getAdminDAO() {
		return new AdminDAOImplementation();
	}
	public static AdminService getAdminService() {
		return new AdminServiceImplementation();
	}
	
}
