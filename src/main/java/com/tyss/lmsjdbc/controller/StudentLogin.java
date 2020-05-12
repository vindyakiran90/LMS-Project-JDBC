package com.tyss.lmsjdbc.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import javax.xml.bind.ValidationException;

import com.tyss.lmsjdbc.dto.BookBean;
import com.tyss.lmsjdbc.dto.BorrowBook;
import com.tyss.lmsjdbc.dto.UserBean;
import com.tyss.lmsjdbc.factory.RegisterationLoginFactory;
import com.tyss.lmsjdbc.factory.StudentFactory;
import com.tyss.lmsjdbc.service.RegisterationLoginService;
import com.tyss.lmsjdbc.service.StudentService;
import com.tyss.lmsjdbc.validation.Validation;

public class StudentLogin {

	boolean flag = false;
	Validation validation = new Validation();
	RegisterationLoginService registerationLoginService = RegisterationLoginFactory.getRegisterationLoginService();
	StudentService studentService = StudentFactory.getStudentService();

	public void login() {

		try(Scanner scanner = new Scanner(System.in);
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))){
			do {
				try {
					System.out.println("Enter Email");
					String email = scanner.next();
					System.out.println("Enter Password");
					String password = scanner.next();
					if(validation.validateEmail(email) && validation.validatePassword(password)) {
						UserBean userBean = registerationLoginService.login(email, password);
						if(userBean.getRole().equals("Student")) {
							System.out.println("Logged in");
							flag = true;
							while(flag) {
								System.out.println("Enter your choice");
								System.out.println("1:Search the Book \n"
										+ "2:Book List \n"
										+ "3:Book Request\n"
										+ "4:Books Borrowed\n"
										+ "5:Go back to the Main");
								int ch = scanner.nextInt();
								switch(ch) {
								case 1:
									BookBean bookBean1 = new BookBean();
									System.out.println("Enter your choice");
									System.out.println("1:Search Book By Title \t 2:Search book By Author");
									int ch1 = scanner.nextInt();
									switch(ch1) {
									case 1:
										System.out.println("Enter the book title");
										String bookTitle = bufferedReader.readLine();
										do {
											try {
												if(validation.validateBook(bookTitle)) {
													flag = true;
												}
											} catch (InputMismatchException e) {
												flag = false;
												System.err.println("Enter the booktitle");
											} catch (ValidationException e) {
												flag = false;
												System.err.println(e.getMessage());
											}
										}while(!flag);

										System.out.println("----------------------------------------------------------------------------------------"
												+ "---------------------------------------------------------------------------------------------");
										bookBean1 = studentService.searchBookByTitle(bookTitle);
										if(bookBean1 != null) {
											System.out.println("Book is available");
										} else {
											System.out.println("Book doesn't exist");
										}
										System.out.println("----------------------------------------------------------------------------------------"
												+ "---------------------------------------------------------------------------------------------");
										break;
									case 2:
										System.out.println("Enter the book author");
										String author = bufferedReader.readLine();
										do {
											try {
												if(validation.validateBook(author)) {
													flag = true;
												}
											} catch (InputMismatchException e) {
												flag = false;
												System.err.println("Invalid author name");
											} catch (ValidationException e) {
												flag = false;
												System.err.println(e.getMessage());
											}
										}while(!flag);

										System.out.println("----------------------------------------------------------------------------------------"
												+ "---------------------------------------------------------------------------------------------");
										bookBean1 = studentService.searchBookByAuthor(author);
										if(bookBean1 != null) {
											System.out.println("Book is available");
										} else {
											System.out.println("Book doesn't exist");
										}
										System.out.println("----------------------------------------------------------------------------------------"
												+ "---------------------------------------------------------------------------------------------");
										break;
									default:
										System.out.println("Invalid choice");
									}//End of switch case		
									break;
								case 2: 
									List<BookBean> allBookInfo = studentService.getBooksInfo();

									System.out.println("----------------------------------------------------------------------------------------"
											+ "---------------------------------------------------------------------------------------------");

									if(!allBookInfo.isEmpty()) {
										System.out.println(String.format("%-10s %-15s %-15s %-15s %-15s %-20s %-20s %-20s", 
												"BOOK ID", "BOOK TITLE", "CATEGORY", "AUTHOR", "PUBLISHER NAME", "NUMBER OF BOOKS", 
												"NUMBER OF AVAILABLE BOOKS", "NUMBER OF ISSUED BOOKS"));
										System.out.println("----------------------------------------------------------------------------------------"
												+ "---------------------------------------------------------------------------------------------");
										System.out.println();
										for(BookBean beanInfo : allBookInfo) {

											System.out.println(beanInfo.toString());
										}
									} else {
										System.out.println("No books are available");
									}
									System.out.println("----------------------------------------------------------------------------------------"
											+ "---------------------------------------------------------------------------------------------");
									break;
								case 3:

									System.out.println("Enter User Id"); 
									int user_Id = scanner.nextInt();

									System.out.println("Enter Book Id");
									int book_Id = scanner.nextInt();

									do { 
										try { 
											if(validation.validateId(user_Id)) { 
												flag = true; 
											} 
										} catch	(InputMismatchException e) { 
											flag = false;
											System.err.println("Invalid User Id"); 
										} catch (ValidationException e) { 
											flag = false; 
											System.err.println(e.getMessage()); 
										} 
									}while(!flag);

									do {
										try {
											if(validation.validateBookId(book_Id)) {
												flag = true;
											}
										} catch (InputMismatchException e) {
											flag = false;
											System.err.println("Invalid Book Id");
										} catch (ValidationException e) {
											flag = false;
											System.err.println(e.getMessage());
										}
									}while(!flag);

									System.out.println("----------------------------------------------------------------------------------------"
											+ "---------------------------------------------------------------------------------------------");
									if(studentService.bookRequest(user_Id, book_Id)) {
										System.out.println("Requested book from the Admin");
									} else {
										System.out.println("Unable to request the book");
									}
									System.out.println("----------------------------------------------------------------------------------------"
											+ "---------------------------------------------------------------------------------------------");
									break;
								case 4:
									System.out.println("Enter User Id"); 
									int user_Id1 = scanner.nextInt();

									do { 
										try { 
											if(validation.validateId(user_Id1)) { 
												
												flag = true; 
											} 
										} catch	(InputMismatchException e) { 
											flag = false;
											System.err.println("Invalid User Id"); 
										} catch (ValidationException e) { 
											flag = false; 
											System.err.println(e.getMessage()); 
										} 
									}while(!flag);
									List<BorrowBook> borrowedBooks = studentService.borrowedBook(user_Id1);
									System.out.println("----------------------------------------------------------------------------------------"
											+ "---------------------------------------------------------------------------------------------");
									if(!(borrowedBooks.isEmpty()) && borrowedBooks != null) {
										System.out.println(String.format("%-10s %-10s %-10s %-20s %-20s %-15s %-5s", "BORROW ID", "USER ID", 
												"BOOK ID", "NO OF BOOKS BORROWED", "DATE OF BORROWED", "DATE OF RETURN", "FEES"));
										System.out.println("----------------------------------------------------------------------------------------"
												+ "---------------------------------------------------------------------------------------------");
										System.out.println();
										for(BorrowBook beanInfo : borrowedBooks) {
											System.out.println(beanInfo.toString());
										}
									} else {
										System.out.println("No books are borrowed");
									}
									System.out.println("----------------------------------------------------------------------------------------"
											+ "---------------------------------------------------------------------------------------------");
									break;

								case 5:
									System.out.println("----------------------------------------------------------------------------------------"
											+ "---------------------------------------------------------------------------------------------");
									String args[] = {"Welcome to Main Controller"}; 
									MainController.main(args);

								}
							}//While loop
						} else {
							System.out.println("Not a Student");
						}
					} else {

						System.out.println("Invalid Email/Password");
					}
				}
				catch(Exception e) {
					flag = false;
					System.out.println("Invalid Email/Password");
				}
			}while(!flag);

		} catch (IOException e1) {
			System.out.println("Invalid Input");
		}
	}
}