package com.tyss.lmsjdbc.service;

import com.tyss.lmsjdbc.dto.UserBean;

public interface RegisterationLoginService {
	UserBean login(String email, String password);
	boolean register(UserBean bean);
}
