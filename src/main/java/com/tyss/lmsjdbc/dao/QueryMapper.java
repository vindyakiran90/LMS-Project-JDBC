package com.tyss.lmsjdbc.dao;

public interface QueryMapper {
	
	String insertRegisterQuery = "insert into userBean values(?, ?, ?, ?, ?, ?, ?);";
	
	String loginQuery = "select * from userBean where email = ? and password = ?;";
	
	String addBookQuery = "insert into bookBean values(?, ?, ?, ?, ?, ?, ?, ?);";
	
	String deleteBookQuery = "delete from bookBean where bookId = ?;";
	
	String updateBookQuery = "update bookBean set numberOfBooks = ?, numberOfAvailableBooks = ? where bookTitle = ?;";
	
	String searchBookByTitleQuery = "select * from bookBean where bookTitle = ?;";
	
	String searchBookByIdQuery = "select * from bookBean where bookId = ?;";
	
	String searchBookByAuthorQuery = "select * from bookBean where author = ?;";
	
	String requestBookQuery = "select * from requestBook;";
	
	String bookListQuery = "select * from bookBean;";
	
	String userListQuery = "select * from userBean where role = 'student';";
	
	String borrowedBookListQuery = "select * from borrowBook where userId = ?;";
	
	String countOfrequestBook = "select count(bookTitle) as requestCount from requestBook where userId = ?;";
	
	String noOfBooksBorrowed = "select count(noOfBooksBorrowed) as borrowedCount from borrowBook where userId = ?;";

	String countOfBook = "select count(bookId) as bookCount from requestBook where userId = ? and bookId = ?;";

	String countOfBook1 = "select count(bookId) as bookCount1 from borrowBook where userId = ? and bookId = ?;";

	String userAvailable = "select * from userBean where userId = ?;";
	
	String feesDue = "select sum(fees) as fee from borrowBook where userId = ?;";
	
	String requestBook = "select * from requestBook where userId = ? and bookId = ?;";

	String deleteRequestBook = "delete from requestBook where userId = ? and bookId = ?;";
	
	String borrowBook = "select * from borrowBook where userId = ? and bookId = ?;";
	
	String deleteIssueBook = "delete from issueBook where userId = ? and bookId = ?;";
	
	String updateBookBean = "update bookBean set numberOfAvailableBooks = ?, numberOfIssuedBooks = ? where bookId = ?;";
	
	String insertRequestBook = "insert into requestBook values(?, ?, ?, ?, ?, ?);";

	String insertborrowBook = "insert into borrowBook values(?, ?, ?, ?, ?, ?, ?);";

	String updateFees = "update borrowBook set fees = ? where userId = ? and bookId = ?;";
	
	String deleteBorrowBook = "delete from borrowBook where userId = ? and bookId = ?;";

	String searchBook = "select * from bookBean where bookTitle = ? and author = ?;";
}
