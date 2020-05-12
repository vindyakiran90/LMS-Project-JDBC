package com.tyss.lmsjdbc.factory;

import com.tyss.lmsjdbc.dao.StudentDAO;
import com.tyss.lmsjdbc.dao.StudentDAOImplementation;
import com.tyss.lmsjdbc.service.StudentService;
import com.tyss.lmsjdbc.service.StudentServiceImplementation;

public class StudentFactory {

	public static StudentDAO getStudentDAO() {
		return new StudentDAOImplementation();
	}
	public static StudentService getStudentService() {
		return new StudentServiceImplementation();
	}
}
