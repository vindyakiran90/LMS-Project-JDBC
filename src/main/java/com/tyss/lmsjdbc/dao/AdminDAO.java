package com.tyss.lmsjdbc.dao;

import java.util.List;

import com.tyss.lmsjdbc.dto.BookBean;
import com.tyss.lmsjdbc.dto.RequestBook;
import com.tyss.lmsjdbc.dto.UserBean;

public interface AdminDAO {
	boolean addBook(BookBean bean);
	boolean deleteBook(int bookId);
	boolean issueBook(int userId, int bookId);
	boolean updateBook(String bookTitle, int numberOfBooks);
	List<RequestBook> showRequest();
	List<UserBean> showStudentUsers();
	boolean bookReturn(int userId, int bookId);
}
