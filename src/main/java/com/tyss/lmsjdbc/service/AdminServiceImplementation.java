package com.tyss.lmsjdbc.service;

import java.util.List;

import com.tyss.lmsjdbc.dao.AdminDAO;
import com.tyss.lmsjdbc.dto.BookBean;
import com.tyss.lmsjdbc.dto.RequestBook;
import com.tyss.lmsjdbc.dto.UserBean;
import com.tyss.lmsjdbc.factory.AdminFactory;

public class AdminServiceImplementation implements AdminService {

	AdminDAO dao = AdminFactory.getAdminDAO();

	@Override
	public boolean addBook(BookBean bean) {
		return dao.addBook(bean);
	}

	@Override
	public boolean deleteBook(int bookId) {
		return dao.deleteBook(bookId);
	}

	@Override
	public boolean issueBook(int userId, int bookId) {
		return dao.issueBook(userId, bookId);
	}

	@Override
	public boolean updateBook(String bookTitle, int numberOfBooks) {
		return dao.updateBook(bookTitle, numberOfBooks);
	}

	@Override
	public List<RequestBook> showRequest() {
		return dao.showRequest();
	}

	@Override
	public List<UserBean> showStudentUsers() {
		return dao.showStudentUsers();
	}

	@Override
	public boolean bookReturn(int userId, int bookId) {
		return dao.bookReturn(userId, bookId);
	}
}
