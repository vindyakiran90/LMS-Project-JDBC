package com.tyss.lmsjdbc.dao;

import com.tyss.lmsjdbc.dto.UserBean;

public interface RegisterationLoginDAO {
	UserBean login(String email, String password);
	boolean register(UserBean bean);
}
