package com.tyss.lmsjdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.tyss.lmsjdbc.dto.BookBean;
import com.tyss.lmsjdbc.dto.BorrowBook;
import com.tyss.lmsjdbc.exception.LMSException;
import com.tyss.lmsjdbc.utility.JdbcUtility;

public class StudentDAOImplementation implements StudentDAO {

	ResultSet resultSet = null;
	Connection connection = JdbcUtility.getConnection();

	@Override
	public boolean bookRequest(int userId, int bookId) {
		int generatorId1 = 0;
		//To check whether user exists or not
		try(PreparedStatement pstm = connection.prepareStatement(QueryMapper.userAvailable)){ 
			pstm.setInt(1, userId);
			ResultSet rs = pstm.executeQuery();
			if(rs.next()) {
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				System.out.println("First name "+ firstName);
				//To check whether book exists or not
				try(PreparedStatement pstmt0 = connection.prepareStatement(QueryMapper.searchBookByIdQuery)){ 
					pstmt0.setInt(1, bookId);
					ResultSet resultSet0 = pstmt0.executeQuery();
					if(resultSet0.next()) {
						int bookId1 = resultSet0.getInt("bookId");
						String bookTitle = resultSet0.getString("bookTitle");
						int numberOfAvailableBooks = resultSet0.getInt("numberOfAvailableBooks");
						System.out.println("numberOfAvailableBooks "+numberOfAvailableBooks);
						if(bookId1 != 0) {
							if(numberOfAvailableBooks != 0) {
								//To check number of books requested
								try(PreparedStatement pstmt = connection.prepareStatement(QueryMapper.countOfrequestBook)){ 
									pstmt.setInt(1, userId);
									ResultSet resultSet = pstmt.executeQuery();
									if(resultSet.next()) {
										int count = resultSet.getInt("requestCount");
										System.out.println("requestCount "+ count);
										if(count >= 0 && count < 2) {
											//To check number of books borrowed
											try(PreparedStatement pstmt1 = connection.prepareStatement(QueryMapper.noOfBooksBorrowed)){ 
												pstmt1.setInt(1, userId);
												ResultSet resultSet1 = pstmt1.executeQuery();
												if(resultSet1.next()) {
													int count1 = resultSet1.getInt("borrowedCount");
													if(count1 >= 0 && count1 < 2) {
														//To check whether the same book is requested
														try(PreparedStatement pstmt2 = connection.prepareStatement(QueryMapper.countOfBook, Statement.RETURN_GENERATED_KEYS)){ 
															pstmt2.setInt(1, userId);
															pstmt2.setInt(2, bookId);
															ResultSet resultSet2 = pstmt2.executeQuery();
															if(resultSet2.next()) {
																int count2 = resultSet2.getInt("bookCount");
																if(count2 == 0) {
																	//To check whether the book requested is already issued to the same user
																	try(PreparedStatement pstmt3 = connection.prepareStatement(QueryMapper.countOfBook1)){ 
																		pstmt3.setInt(1, userId);
																		pstmt3.setInt(2, bookId);
																		ResultSet resultSet3 = pstmt3.executeQuery();
																		if(resultSet3.next()) {
																			int count3 = resultSet3.getInt("bookCount1");
																			if(count3 >= 0 && count3 < 3) {
																				try(PreparedStatement pstmt4 = connection.prepareStatement(QueryMapper.insertRequestBook, Statement.RETURN_GENERATED_KEYS)){ 
																					ResultSet rs1 = pstmt4.getGeneratedKeys();
																					if(rs1 != null && rs1.next()) {
																						generatorId1 = rs1.getInt(1);
																					}
																					pstmt4.setInt(1, generatorId1);
																					pstmt4.setInt(2, bookId);
																					pstmt4.setInt(3, userId);
																					pstmt4.setString(4, firstName);
																					pstmt4.setString(5, lastName);
																					pstmt4.setString(6, bookTitle);
																					int count4 = pstmt4.executeUpdate();
																					if(count4 != 0) {
																						return true;
																					}
																				}
																			}
																		} else {
																			throw new LMSException("Book is already borrowed, cannot be issued again");
																		}
																	}
																}
															} else {
																throw new LMSException("Book is already requested, cannot request again");
															}
														}
													}
												} else {
													throw new LMSException("Exceeded limit to borrow the book");
												}
											}
										}
									} else {
										throw new LMSException("Exceeded limit to request the book");
									}
								}
							} else {
								throw new LMSException("Book is not in stock");
							}
						}
					} else {
						throw new LMSException("Book does not exist");
					}
				}
			} else {
				throw new LMSException("User does not exist");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<BorrowBook> borrowedBook(int userId) {
		List<BorrowBook> beans = new LinkedList<BorrowBook>();
		try(PreparedStatement pstmt = connection.prepareStatement(QueryMapper.borrowedBookListQuery)){ 
			pstmt.setInt(1, userId);
			try(ResultSet resultSet = pstmt.executeQuery()){
				while(resultSet.next()) {
					BorrowBook bean = new BorrowBook();
					bean.setBookId(resultSet.getInt("bookId"));
					bean.setBorrowId(resultSet.getInt("borrowId"));
					bean.setDateOfBorrowed(resultSet.getDate("dateOfBorrowed"));
					bean.setDateOfReturn(resultSet.getDate("dateOfReturn"));
					bean.setFees(resultSet.getDouble("fees"));
					bean.setNoOfBooksBorrowed(resultSet.getInt("noOfBooksBorrowed"));
					bean.setUserId(resultSet.getInt("userId"));
					beans.add(bean);
				}
			}
			return beans;
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("4");
			return null;
		}
	}

	@Override
	public BookBean searchBookById(int bookId) {

		BookBean bean = new BookBean();
		try(PreparedStatement pstmt = connection.prepareStatement(QueryMapper.searchBookByIdQuery)){ 
			pstmt.setInt(1, bookId);

			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				bean.setBookId(rs.getInt("bookId"));
				bean.setBookTitle(rs.getString("bookTitle"));
				bean.setCategory(rs.getString("category"));
				bean.setAuthor(rs.getString("author"));
				bean.setNumberOfBooks(rs.getInt("numberOfBooks"));
				bean.setNumberOfAvailableBooks(rs.getInt("numberOfAvailableBooks"));
				bean.setNumberOfIssuedBooks(rs.getInt("numberOfIssuedBooks"));
				bean.setPublisherName(rs.getString("publisherName"));
				return bean;
			} else {
				System.out.println("No book found");
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} 
	}

	@Override
	public BookBean searchBookByTitle(String bookTitle) {

		BookBean bean = new BookBean();
		try(PreparedStatement pstmt = 
				connection.prepareStatement(QueryMapper.searchBookByTitleQuery)){ 
			pstmt.setString(1, bookTitle);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				bean.setBookId(rs.getInt("bookId"));
				bean.setBookTitle(rs.getString("bookTitle"));
				bean.setCategory(rs.getString("category"));
				bean.setAuthor(rs.getString("author"));
				bean.setNumberOfBooks(rs.getInt("numberOfBooks"));
				bean.setNumberOfAvailableBooks(rs.getInt("numberOfAvailableBooks"));
				bean.setNumberOfIssuedBooks(rs.getInt("numberOfIssuedBooks"));
				bean.setPublisherName(rs.getString("publisherName"));
				return bean;
			} else {
				System.out.println("No book found");
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} 
	}

	@Override
	public BookBean searchBookByAuthor(String author) {

		BookBean bean = new BookBean();
		try(PreparedStatement pstmt = 
				connection.prepareStatement(QueryMapper.searchBookByAuthorQuery)){ 

			pstmt.setString(1, author);

			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				bean.setBookId(rs.getInt("bookId"));
				bean.setBookTitle(rs.getString("bookTitle"));
				bean.setCategory(rs.getString("category"));
				bean.setAuthor(rs.getString("author"));
				bean.setNumberOfBooks(rs.getInt("numberOfBooks"));
				bean.setNumberOfAvailableBooks(rs.getInt("numberOfAvailableBooks"));
				bean.setNumberOfIssuedBooks(rs.getInt("numberOfIssuedBooks"));
				bean.setPublisherName(rs.getString("publisherName"));
				return bean;
			} else {
				System.out.println("No book found");
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} 
	}

	@Override
	public List<BookBean> getBooksInfo() {

		try(Statement stmt = connection.createStatement();
				ResultSet resultSet = stmt.executeQuery(QueryMapper.bookListQuery);){ 
			List<BookBean> beans = new LinkedList<BookBean>();
			while(resultSet.next()) {
				BookBean bean = new BookBean();
				bean.setBookId(resultSet.getInt("bookId"));
				bean.setBookTitle(resultSet.getString("bookTitle"));
				bean.setAuthor(resultSet.getString("author"));
				bean.setCategory(resultSet.getString("category"));
				bean.setPublisherName(resultSet.getString("publisherName"));
				bean.setNumberOfBooks(resultSet.getInt("numberOfBooks"));
				bean.setNumberOfAvailableBooks(resultSet.getInt("numberOfAvailableBooks"));
				bean.setNumberOfIssuedBooks(resultSet.getInt("numberOfIssuedBooks"));
				beans.add(bean);
			}
			return beans;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
