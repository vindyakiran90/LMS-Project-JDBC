package com.tyss.lmsjdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import com.tyss.lmsjdbc.dto.BookBean;
import com.tyss.lmsjdbc.dto.RequestBook;
import com.tyss.lmsjdbc.dto.UserBean;
import com.tyss.lmsjdbc.exception.LMSException;
import com.tyss.lmsjdbc.utility.JdbcUtility;

public class AdminDAOImplementation implements AdminDAO {

	ResultSet resultSet = null;
	Connection connection = JdbcUtility.getConnection();

	@Override
	public boolean addBook(BookBean bean) throws LMSException {

		int generatedId = 0;
		boolean result = false;
		try(PreparedStatement pstmt0 = connection.prepareStatement(QueryMapper.searchBook)){
			pstmt0.setString(1, bean.getBookTitle());
			pstmt0.setString(2, bean.getAuthor());
			ResultSet resultSet0 = pstmt0.executeQuery();
			if(!(resultSet0.next() && resultSet0 != null)) {
				try(PreparedStatement pstmt = connection.prepareStatement(QueryMapper.addBookQuery, Statement.RETURN_GENERATED_KEYS)){ 

					ResultSet rs = pstmt.getGeneratedKeys();
					if(rs != null && rs.next()){
						generatedId = rs.getInt(1);
					}
					pstmt.setInt(1, generatedId);
					pstmt.setString(2, bean.getBookTitle());
					pstmt.setString(3, bean.getCategory());
					pstmt.setString(4, bean.getAuthor());
					pstmt.setString(5, bean.getPublisherName());
					pstmt.setInt(6, bean.getNumberOfBooks());
					pstmt.setInt(7, bean.getNumberOfAvailableBooks());
					pstmt.setInt(8, bean.getNumberOfIssuedBooks());

					int count = pstmt.executeUpdate();
					if(count != 0) {
						result = true;
					} else {
						result = false;
					}
				}
			}
		} catch (SQLException e) {
			throw new LMSException("Book already exists");
		} 
		return result;
	}

	@Override
	public boolean deleteBook(int bookId) {

		boolean result = false;
		try(PreparedStatement pstmt = connection.prepareStatement(QueryMapper.deleteBookQuery)){ 
			pstmt.setInt(1, bookId);

			int count = pstmt.executeUpdate();
			if(count != 0) {
				result = true;
			} else {
				result = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			result = false;
		} 
		return result;
	}

	@Override
	public boolean issueBook(int userId, int bookId) {

		//To check whether user exists or not
		try(PreparedStatement pstmt1 = connection.prepareStatement(QueryMapper.userAvailable)){ 
			pstmt1.setInt(1, userId);
			ResultSet resultSet1 = pstmt1.executeQuery();
			if(resultSet1.next()) {
				//To check whether book exists or not
				try(PreparedStatement pstmt2 = connection.prepareStatement(QueryMapper.searchBookByIdQuery)){ 
					pstmt2.setInt(1, bookId);
					ResultSet resultSet2 = pstmt2.executeQuery();
					if(resultSet2.next()) {
						int availableBooks = resultSet2.getInt("numberOfAvailableBooks");
						if(availableBooks != 0) {
							//To check number of books borrowed
							try(PreparedStatement pstmt3 = connection.prepareStatement(QueryMapper.noOfBooksBorrowed)){ 
								pstmt3.setInt(1, userId);
								ResultSet resultSet3 = pstmt3.executeQuery();
								if(resultSet3.next()) {
									int count3 = resultSet3.getInt("borrowedCount");
									if(count3 >= 0 && count3 < 2) { 
										//To check whether the book requested is already issued to the same user
										try(PreparedStatement pstmt4 = connection.prepareStatement(QueryMapper.countOfBook1)){ 
											pstmt4.setInt(1, userId);
											pstmt4.setInt(2, bookId);
											ResultSet resultSet4 = pstmt4.executeQuery();
											if(resultSet4.next()) {
												int count4 = resultSet4.getInt("bookCount1");
												if(count4 >= 0 && count4 < 2) {
													//To check whether book is requested from the user or not
													try(PreparedStatement pstmt5 = connection.prepareStatement(QueryMapper.requestBook)){ 
														pstmt5.setInt(1, userId);
														pstmt5.setInt(2, bookId);
														ResultSet resultSet5 = pstmt5.executeQuery();
														if(resultSet5.next()) {
															//To check the students fees due
															try(PreparedStatement pstmt6 = connection.prepareStatement(QueryMapper.feesDue)){ 
																pstmt6.setInt(1, userId);
																ResultSet resultSet6 = pstmt6.executeQuery();
																if(resultSet6.next()) {
																	double fees = resultSet6.getDouble("fee");
																	if(fees == 0) {
																		//Inserting book into borrowbook
																		try(PreparedStatement pstmt7 = connection.prepareStatement(QueryMapper.insertborrowBook, Statement.RETURN_GENERATED_KEYS)){

																			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
																			Calendar calendar =  Calendar.getInstance();
																			String date = sdf.format(calendar.getTime());
																			Date currentDate = java.sql.Date.valueOf(date);
																			System.out.println("current date" + currentDate);

																			calendar.add(Calendar.DAY_OF_MONTH, 10);
																			String date1 = sdf.format(calendar.getTime());
																			Date returnDate =  java.sql.Date.valueOf(date1);
																			System.out.println("returnDate " + returnDate);
																			int generatorId1 = 0;
																			ResultSet resultSet7 = pstmt7.getGeneratedKeys();
																			if(resultSet7 != null && resultSet7.next()) {
																				generatorId1 = resultSet7.getInt(1);
																			}
																			pstmt7.setInt(1, generatorId1);
																			pstmt7.setInt(2, bookId);
																			pstmt7.setInt(3, userId);
																			pstmt7.setInt(4, count3);
																			pstmt7.setDate(5, currentDate);
																			pstmt7.setDate(6, returnDate);
																			pstmt7.setDouble(7, fees);
																			int count7 = pstmt7.executeUpdate();
																			if(count7 != 0) { 
																				//Search the book
																				try(PreparedStatement pstmt8 = connection.prepareStatement(QueryMapper.searchBookByIdQuery)){
																					pstmt8.setInt(1, bookId);
																					ResultSet resultSet8 = pstmt8.executeQuery();
																					if(resultSet8.next()) {
																						int numberOfIssuedBooks = resultSet8.getInt("numberOfIssuedBooks");
																						int numberOfAvailableBooks = resultSet8.getInt("numberOfAvailableBooks");
																						//Updating the book bean
																						try(PreparedStatement pstmt9 = connection.prepareStatement(QueryMapper.updateBookBean)){ 
																							pstmt9.setInt(1, numberOfAvailableBooks - 1);
																							pstmt9.setInt(2, numberOfIssuedBooks + 1);
																							pstmt9.setInt(3, bookId);
																							int count9 = pstmt9.executeUpdate();
																							if(count9 != 0) {
																								//To delete the requested book in requestbook table
																								try(PreparedStatement pstmt10 = connection.prepareStatement(QueryMapper.deleteRequestBook)){ 
																									pstmt10.setInt(1, userId);
																									pstmt10.setInt(2, bookId);
																									int count10 = pstmt10.executeUpdate();
																									if(count10 != 0) {
																										return true;			
																									}
																								}
																							}
																						} catch(Exception e) {
																							throw new LMSException("Unable to update in the bookbean");
																						}
																					}
																				} catch(Exception e) {
																					throw new LMSException("Unable to find the book");
																				}
																			}
																		} catch(Exception e) {
																			throw new LMSException("Unable to insert into borrow book");
																		}
																	} 
																}
															} catch(Exception e) {
																throw new LMSException("Fees is due");
															}
														}
													} catch(Exception e) {
														throw new LMSException("Book is not requested from the user");
													}
												}
											}
										} catch(Exception e) {
											throw new LMSException("Book is already borrowed, cannot be issued again");
										}
									}
								}
							} catch(Exception e) {
								throw new LMSException("Exceeded limit to borrow the book");
							}
						} else {
							throw new LMSException("Book is not in stock");
						}
					}
				}catch (Exception e) {
					throw new LMSException("Book does not exist");
				}
			}
		}catch (Exception e) {
			throw new LMSException("User does not exist");
		}
		return false;
	}

	@Override
	public boolean updateBook(String bookTitle, int numberOfBooks) {
		boolean result = false;
		try(PreparedStatement pstmt = connection.prepareStatement(QueryMapper.searchBookByTitleQuery)){
			pstmt.setString(1, bookTitle);
			ResultSet resultSet = pstmt.executeQuery();
			if(resultSet.next()) {
				int numberOfBooks1 = resultSet.getInt("numberOfBooks");
				int numberOfAvailableBooks = resultSet.getInt("numberOfAvailableBooks");
				try(PreparedStatement pstmt1 = connection.prepareStatement(QueryMapper.updateBookQuery)){ 
					pstmt1.setInt(1, (numberOfBooks + numberOfBooks1));
					pstmt1.setInt(2, (numberOfBooks + numberOfAvailableBooks));
					pstmt1.setString(3, bookTitle);
					int count = pstmt1.executeUpdate();
					if(count != 0) {
						result = true;
					} else {
						result = false;
					}
				}
			}
		} catch (SQLException e) {
			result = false;
		} 
		return result;
	}

	@Override
	public List<RequestBook> showRequest() {

		try(Statement stmt = connection.createStatement()){
			try(ResultSet resultSet = stmt.executeQuery(QueryMapper.requestBookQuery)){
				List<RequestBook> beans = new LinkedList<RequestBook>();
				while(resultSet.next()) {
					RequestBook requestBook = new RequestBook();
					requestBook.setBookId(resultSet.getInt("bookId"));
					requestBook.setBookTitle(resultSet.getString("bookTitle"));
					requestBook.setFirstName(resultSet.getString("firstName"));
					requestBook.setLastName(resultSet.getString("lastName"));
					requestBook.setRequestId(resultSet.getInt("requestId"));
					requestBook.setUserId(resultSet.getInt("userId"));
					beans.add(requestBook);
				}
				return beans;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<UserBean> showStudentUsers() {
		try(Statement stmt = connection.createStatement()){
			try(ResultSet resultSet = stmt.executeQuery(QueryMapper.userListQuery)){
				List<UserBean> beans = new LinkedList<UserBean>();
				while(resultSet.next()) {
					UserBean userBean = new UserBean();
					userBean.setUserId(resultSet.getInt("userId"));
					userBean.setFirstName(resultSet.getString("firstName"));
					userBean.setLastName(resultSet.getString("lastName"));
					userBean.setPassword(resultSet.getString("password"));
					userBean.setPhoneNo(resultSet.getLong("phoneNo"));
					userBean.setRole(resultSet.getString("role"));
					beans.add(userBean);
				}
				return beans;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean bookReturn(int userId, int bookId) {
		try(PreparedStatement pstmt = connection.prepareStatement(QueryMapper.borrowBook)){ 
			pstmt.setInt(1, userId);
			pstmt.setInt(2, bookId);

			ResultSet resultSet= pstmt.executeQuery();

			if(resultSet.next()) {
				Date returnDate = resultSet.getDate("dateOfReturn");

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Calendar calendar =  Calendar.getInstance();
				String date = sdf.format(calendar.getTime());
				Date currentDate = java.sql.Date.valueOf(date);

				long noOfDaysBetween = currentDate.getTime() - returnDate.getTime();
				float daysBetween = (noOfDaysBetween / (1000 * 60 * 60 * 24));

				if(daysBetween > 0) {
					try(PreparedStatement pstmt1 = connection.prepareStatement(QueryMapper.updateFees)){ 
						pstmt1.setDouble(1, daysBetween * 5.0);
						pstmt1.setInt(2, userId);
						pstmt1.setInt(3, bookId);
						int count = pstmt1.executeUpdate();
						if(count != 0) {
							System.out.println("Student should pay "+ daysBetween * 5 +" Rupees");
						}
					} catch(Exception e) {
						throw new LMSException("Student should pay the fine for delaying to return the book");
					}
				} else {
					try(PreparedStatement pstmt1 = connection.prepareStatement(QueryMapper.deleteBorrowBook)){ 
						pstmt1.setInt(1, userId);
						pstmt1.setInt(2, bookId);
						int count = pstmt1.executeUpdate();
						System.out.println("count "+count);
						if(count != 0) {

							try(PreparedStatement pstmt2 = connection.prepareStatement(QueryMapper.deleteIssueBook)){ 
								pstmt2.setInt(1, userId);
								pstmt2.setInt(2, bookId);
								int count1 = pstmt2.executeUpdate();
								System.out.println("count1 "+count1);
								if(count1 == 0) {

									try(PreparedStatement pstmt3 = connection.prepareStatement(QueryMapper.searchBookByIdQuery)){ 
										pstmt3.setInt(1, bookId);
										ResultSet resultSet3 = pstmt3.executeQuery();
										if(resultSet3.next()) {
											try(PreparedStatement pstmt4 = connection.prepareStatement(QueryMapper.updateBookBean)){ 
												pstmt4.setInt(1, (resultSet3.getInt("numberOfAvailableBooks") + 1));
												pstmt4.setInt(2, (resultSet3.getInt("numberOfIssuedBooks") - 1));
												pstmt4.setInt(3, bookId);
												int count4 = pstmt4.executeUpdate();
												if(count4 != 0) {
													return true;
												}
											}
										}
									}
								}
							} catch(Exception e) {
								e.printStackTrace();
							}
						}

					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
