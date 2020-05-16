package com.tyss.lmsjdbc.service;

import java.util.List;

import com.tyss.lmsjdbc.dao.StudentDAO;
import com.tyss.lmsjdbc.dto.BookBean;
import com.tyss.lmsjdbc.dto.BorrowBook;
import com.tyss.lmsjdbc.factory.StudentFactory;

public class StudentServiceImplementation implements StudentService {

	private StudentDAO dao = StudentFactory.getStudentDAO();

	@Override
	public boolean bookRequest(int userId, int bookId) {
		return dao.bookRequest(userId, bookId);
	}

	@Override
	public List<BorrowBook> borrowedBook(int userId) {
		return dao.borrowedBook(userId);
	}

	@Override
	public BookBean searchBookById(int bookId) {
		return dao.searchBookById(bookId);
	}

	@Override
	public BookBean searchBookByTitle(String bookTitle) {
		return dao.searchBookByTitle(bookTitle);
	}

	@Override
	public BookBean searchBookByAuthor(String author) {
		return dao.searchBookByAuthor(author);
	}

	@Override
	public List<BookBean> getBooksInfo() {
		return dao.getBooksInfo();
	}

	@Override
	public boolean bookReturn(int userId, int bookId) {
		return dao.bookReturn(userId, bookId);
	}
}
